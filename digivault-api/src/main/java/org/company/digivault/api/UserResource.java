package org.company.digivault.api;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.company.digivault.request.UserSignUpRequest;
import org.company.digivault.response.UserSignUpResponse;
import org.digivault.dao.UserDao;
import org.digivault.entity.User;

@Path("/digivault/um/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource  {

  private UserDao userDao;

  public UserResource(UserDao userDao) {
    this.userDao = userDao;
  }

  @POST
  @UnitOfWork
  public Response createUser(UserSignUpRequest request) {
    User user = createUserFromSignupRequest(request);

    User createdUser = userDao.create(user);

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

    User userForDb = userDao.getById(id);

    if (userForDb == null) {
      return Response
              .status(Response.Status.NOT_FOUND)
              .build();
    }

    return Response
            .ok(userForDb)
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
