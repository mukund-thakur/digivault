package org.company.digivault.api;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.Claims;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.company.digivault.config.DigiVaultConstants;
import org.company.digivault.models.AuthUser;
import org.company.digivault.models.Role;
import org.company.digivault.request.UpdateUserRequest;
import org.company.digivault.request.UserSignUpRequest;
import org.company.digivault.response.UserSignUpResponse;
import org.digivault.entity.Asset;
import org.company.digivault.request.UserSignInRequest;
import org.digivault.entity.User;
import org.digivault.services.AssetMetaService;
import org.digivault.services.TokenService;
import org.digivault.services.UserMetaService;
import org.digivault.services.impl.JwtTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(DigiVaultConstants.UM_BASE_API_PATH + "/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource  {

  private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

  private UserMetaService userMetaService;

  private AssetMetaService assetMetaService;

  public UserResource(UserMetaService userMetaService, AssetMetaService assetMetaService) {
    this.userMetaService = userMetaService;
    this.assetMetaService = assetMetaService;
  }

  @POST
  @UnitOfWork
  public Response createUser(UserSignUpRequest request) {
    validateSignupRequest(request);
    User user = createUserFromSignupRequest(request);

    User createdUser = userMetaService.createUser(user);

    UserSignUpResponse signUpResponse = getUserSignUpResponse(createdUser);

    return Response
            .ok(signUpResponse)
            .status(Response.Status.CREATED)
            .build();
  }

  @PUT
  @UnitOfWork
  @Path("/{userId}")
  public Response updateUser(@Auth AuthUser user,
                             UpdateUserRequest updateUserRequest,
                             @PathParam("userId") Long userId) {
    User userFromDb = userMetaService.getUserById(userId);

    if (userFromDb == null) {
      throw new WebApplicationException("User not registered. Please signup.",
              Response.Status.NOT_FOUND);
    }
    LOG.debug("Update user request received for id {}, requestBody {}",
            userId, updateUserRequest.toString());
    User updatedUser = getUpdatedUser(userFromDb, updateUserRequest);
    updatedUser = userMetaService.updateUser(updatedUser);

    return Response
            .ok(updatedUser)
            .build();
  }

  @POST
  @Path("/signin")
  @UnitOfWork
  public Response signIn(UserSignInRequest signInRequest) {

    User user = userMetaService.getUserById(signInRequest.getUserId());

    if (user == null) {
      throw new WebApplicationException("User not registered. Please signup.",
              Response.Status.NOT_FOUND);
    }
    LOG.info("User signin request received for {} ", user.getId());
    validateSignInReq(signInRequest, user);
    String token = createJwt(user);
    return Response
            .ok()
            .header(DigiVaultConstants.AUTHORIZATION_HEADER, token)
            .build();
  }

  @GET
  @Path("/{id}")
  @UnitOfWork
  @PermitAll
  public Response getUserById(@Auth AuthUser user, @PathParam("id") Long id) {

    User userFromDb = userMetaService.getUserById(id);

    if (userFromDb == null) {
      throw new WebApplicationException("User not registered. Please signup.",
              Response.Status.NOT_FOUND);
    }

    return Response
            .ok(userFromDb)
            .build();
  }

  @GET
  @Path("/{userId}/assets")
  @UnitOfWork
  @PermitAll
  public Response getAssetsByUserId(@Auth AuthUser auth, @PathParam("userId") Long userId) {
    List<Asset> assets = assetMetaService.getAllAssetOfUser(userId);

    return Response
            .ok(assets)
            .build();
  }

  private void validateSignInReq(UserSignInRequest signInRequest, User actualUserFromId)
          throws WebApplicationException {
    boolean validContact = signInRequest.getContactNum() != null &&
            signInRequest.getContactNum().equals(actualUserFromId.getContactNum());
    boolean validEmail = signInRequest.getEmail() != null &&
            signInRequest.getEmail().equals(actualUserFromId.getEmail());
    if (!validEmail && !validContact) {
      throw new WebApplicationException("Incorrect phone number/email id.", Response.Status.FORBIDDEN);
    }

    if (signInRequest.getPassword() != null &&
            !signInRequest.getPassword().equals(actualUserFromId.getPassword())) {
      throw new WebApplicationException("Incorrect Password.", Response.Status.FORBIDDEN);
    }
  }

  private String createJwt(User user) {
    TokenService<Claims> tokenService = new JwtTokenServiceImpl();
    final Set<Role> allowedRoles = new HashSet<Role>();
    allowedRoles.add(Role.USER);
    return tokenService.createToken(String.valueOf(user.getId()), allowedRoles);
  }

  private UserSignUpResponse getUserSignUpResponse(User createdUser) {
    UserSignUpResponse signUpResponse = new UserSignUpResponse();
    signUpResponse.setName(createdUser.getName());
    signUpResponse.setUserId(createdUser.getId());
    return signUpResponse;
  }

  private User createUserFromSignupRequest(UserSignUpRequest request) {
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setEmailVerified(request.isEmailVerified());
    user.setContactNum(request.getContactNum());
    user.setPassword(request.getPassword());
    user.setDob(request.getDob());
    user.setGcmId(request.getGcmId());
    user.setGender(request.getGender());
    return user;
  }

  private void validateSignupRequest(UserSignUpRequest request) {
    boolean passwordNull = request.getPassword() == null;
    boolean passwordEmpty = request.getPassword() != null && request.getPassword().isEmpty() ;
    if (passwordEmpty || passwordNull) {
      throw new WebApplicationException("Password can't be empty or null", Response.Status.BAD_REQUEST);
    }
    boolean emailPresent = request.getEmail() != null && !request.getEmail().isEmpty();
    boolean contactNumPresent = request.getContactNum() != null && !request.getContactNum().isEmpty();
    if (!emailPresent && !contactNumPresent) {
      throw new WebApplicationException("Either email or contact number should be set", Response.Status.BAD_REQUEST);
    }

    Optional<User> existingUserByEmail = userMetaService.getUserByEmail(request.getEmail());

    if (existingUserByEmail.isPresent()) {
      throw new WebApplicationException("User already registered with email id " + request.getEmail(),
              Response.Status.BAD_REQUEST);
    }

    Optional<User> existingUserByContact = userMetaService.getUserByContact(request.getContactNum());

    if (existingUserByContact.isPresent()) {
      throw new WebApplicationException("User already registered with contactNum " + request.getContactNum(),
              Response.Status.BAD_REQUEST);
    }
  }


  private User getUpdatedUser(User userFromDb, UpdateUserRequest updateUserRequest) {
    userFromDb.setName(updateUserRequest.getName());
    userFromDb.setDob(updateUserRequest.getDob());
    userFromDb.setContactNum(updateUserRequest.getContactNum());
    userFromDb.setPassword(updateUserRequest.getPassword());
    userFromDb.setEmail(updateUserRequest.getEmail());
    userFromDb.setEmailVerified(updateUserRequest.isEmailVerified());
    userFromDb.setPan(updateUserRequest.getPan());
    userFromDb.setAadhar(updateUserRequest.getAadhar());
    userFromDb.setGcmId(updateUserRequest.getGcmId());
    return userFromDb;
  }

}
