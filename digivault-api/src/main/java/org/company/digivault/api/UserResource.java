package org.company.digivault.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.Claims;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.company.digivault.config.DigiVaultConstants;
import org.company.digivault.exception.SignInException;
import org.company.digivault.models.AuthUser;
import org.company.digivault.models.Role;
import org.company.digivault.request.UserSignUpRequest;
import org.company.digivault.response.APIResult;
import org.company.digivault.response.UserSignUpResponse;
import org.digivault.entity.Asset;
import org.company.digivault.request.UserSignInRequest;
import org.digivault.entity.User;
import org.digivault.services.AssetMetaService;
import org.digivault.services.TokenService;
import org.digivault.services.UserMetaService;
import org.digivault.services.impl.JwtTokenServiceImpl;

@Path(DigiVaultConstants.UM_BASE_API_PATH + "/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource  {


  private UserMetaService userMetaService;

  private AssetMetaService assetMetaService;

  public UserResource(UserMetaService userMetaService, AssetMetaService assetMetaService) {
    this.userMetaService = userMetaService;
    this.assetMetaService = assetMetaService;
  }

  @POST
  @UnitOfWork
  public Response createUser(UserSignUpRequest request) {
    User user = createUserFromSignupRequest(request);

    User createdUser = userMetaService.createUser(user);

    UserSignUpResponse signUpResponse = getUserSignUpResponse(createdUser);

    return Response
            .ok(signUpResponse)
            .status(Response.Status.CREATED)
            .build();
  }

  @POST
  @Path("/signin")
  @UnitOfWork
  public Response signIn(UserSignInRequest signInRequest) {

    User user = userMetaService.getUserById(signInRequest.getUserId());

    if (user == null) {
      return Response
              .status(Response.Status.NOT_FOUND)
              .entity(new APIResult("User not registered. Please signup."))
              .build();
    }

    validUser(signInRequest, user);
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

    User userForDb = userMetaService.getUserById(id);

    if (userForDb == null) {
      return Response
              .status(Response.Status.NOT_FOUND)
              .build();
    }

    return Response
            .ok(userForDb)
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

  private void validUser(UserSignInRequest signInRequest, User actualUserFromId)
          throws WebApplicationException {
    if (!signInRequest.getContactNum().equals(actualUserFromId.getContactNum())) {
      throw new WebApplicationException("Incorrect phone number.", Response.Status.FORBIDDEN);
    }

    if (!signInRequest.getPassword().equals(actualUserFromId.getPassword())) {
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
    user.setContactNum(request.getContactNum());
    user.setPassword(request.getPassword());
    user.setDob(request.getDob());
    user.setGcmId(request.getGcmId());
    user.setGender(request.getGender());
    return user;
  }
}
