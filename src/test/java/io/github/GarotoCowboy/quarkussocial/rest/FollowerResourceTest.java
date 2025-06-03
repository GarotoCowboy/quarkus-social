package io.github.GarotoCowboy.quarkussocial.rest;

import io.github.GarotoCowboy.quarkussocial.domain.model.User;
import io.github.GarotoCowboy.quarkussocial.domain.repository.FollowerRepository;
import io.github.GarotoCowboy.quarkussocial.domain.repository.UserRepository;
import io.github.GarotoCowboy.quarkussocial.rest.dto.FollowerRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {

    @Inject
    UserRepository userRepository;

    @Inject
    FollowerRepository followerRepository;

    Long userId;
    Long followerUserId;

    @BeforeEach
    @Transactional
    void setUp() {

        var user = new User();
        user.setName("Joe");
        user.setAge(30);
        userRepository.persist(user);
        userId = user.getId();

        var followerUser = new User();
        followerUser.setName("Ciclan");
        followerUser.setAge(25);
        userRepository.persist(followerUser);
        followerUserId = followerUser.getId();
    }

    @Test
    @DisplayName("should return 409 when followerID is equal to userID")
    public void sameUserAsFollowerTest() {

        var body = new FollowerRequest();
        body.setFollowerId(userId);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", userId)
                .when()
                .put()
                .then()
                .statusCode(409)
                .body(Matchers.is("You can't follow yourself"));
    }

    @Test
    @DisplayName("should return 404 when userID doesn't exist")
    public void userNotFoundTest() {


        var body = new FollowerRequest();
        body.setFollowerId(userId);

        var inexistentUserId = 999;


        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", inexistentUserId)
                .when()
                .put()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should follow a user")
    public void followUserTest() {


        var body = new FollowerRequest();
        body.setFollowerId(followerUserId);



        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", userId)
                .when()
                .put()
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }


}