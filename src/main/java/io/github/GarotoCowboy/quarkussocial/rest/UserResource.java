package io.github.GarotoCowboy.quarkussocial.rest;

import io.github.GarotoCowboy.quarkussocial.domain.model.User;
import io.github.GarotoCowboy.quarkussocial.domain.repository.UserRepository;
import io.github.GarotoCowboy.quarkussocial.rest.dto.CreateUserRequest;
import io.github.GarotoCowboy.quarkussocial.rest.dto.ResponseError;
import io.github.GarotoCowboy.quarkussocial.rest.dto.UpdateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;


@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository userRepository;
    private final Validator validator;


    @Inject
    public UserResource(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);

        if (!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return responseError
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        userRepository.persist(user);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    public Response listAllUsers() {

        PanacheQuery<User> query = userRepository.findAll();

        return Response.ok(query.list()).build();
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        User user = userRepository.findById(id);

        if (user != null) {
            userRepository.delete(user);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, UpdateUserRequest userData) {
    User user = userRepository.findById(id);

    if (user != null) {
        user.setName(userData.getName());
        user.setAge(userData.getAge());
        return Response.noContent().build();
    }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
