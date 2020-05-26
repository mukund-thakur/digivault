package org.company.digivault.api;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.company.digivault.config.DigiVaultConstants;
import org.company.digivault.request.UserSignUpRequest;
import org.company.digivault.response.UserSignUpResponse;
import org.digivault.entity.Asset;
import org.digivault.entity.User;
import org.digivault.services.AssetMetaService;
import org.digivault.services.UserMetaService;

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

  @GET
  @Path("/{id}")
  @UnitOfWork
  public Response getUserById(@PathParam("id") Long id) {

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
  public Response getAssetsByUserId(@PathParam("userId") Long userId) {
    List<Asset> assets = assetMetaService.getAllAssetOfUser(userId);

    return Response
            .ok(assets)
            .build();
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
