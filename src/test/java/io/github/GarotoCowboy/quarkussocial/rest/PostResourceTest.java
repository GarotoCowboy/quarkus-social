package io.github.GarotoCowboy.quarkussocial.rest;

import io.github.GarotoCowboy.quarkussocial.domain.model.Follower;
import io.github.GarotoCowboy.quarkussocial.domain.model.Post;
import io.github.GarotoCowboy.quarkussocial.domain.model.User;
import io.github.GarotoCowboy.quarkussocial.domain.repository.FollowerRepository;
import io.github.GarotoCowboy.quarkussocial.domain.repository.PostRepository;
import io.github.GarotoCowboy.quarkussocial.domain.repository.UserRepository;
import io.github.GarotoCowboy.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;

    @Inject
    FollowerRepository followerRepository;

    @Inject
    PostRepository postRepository;

    Long userID;
    Long userNotFollowerId;
    Long userFollowerId;

    @BeforeEach
    @Transactional
    public void setUP() {
        //Default User to test
        var user = new User();
        user.setAge(30);
        user.setName("Joe");
        userRepository.persist(user);
        userID = user.getId();

        //Created a post to user
    Post post = new Post();
    post.setText("Hello World");
    post.setUser(user);
    postRepository.persist(post);


        //User that not follower anybody
        var userNotFollower = new User();
        userNotFollower.setAge(20);
        userNotFollower.setName("Ciclan");
        userRepository.persist(userNotFollower);
        userNotFollowerId = userNotFollower.getId();

        //User follower
        var userFollower = new User();
        userFollower.setAge(20);
        userFollower.setName("Ciclan");
        userRepository.persist(userFollower);
        userFollowerId = userFollower.getId();


        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollower(userFollower);
        followerRepository.persist(follower);


    }

    @Test
    @DisplayName("should create a post for a user")
    public void createPostTest() {

        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", userID)
                .when()
                .post()
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("Should 404 when trying to make a post for an inexistent user")
    public void postForAnInexistentUserTest() {

        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        var inexistentUserId = 999;

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", inexistentUserId)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return 404 when user doesn't exist")
    public void listPostUserNotFoundTest() {

        var inexistentUserId = 999;

        given()
                .pathParam("userId", inexistentUserId)
                .when()
                .get()
                .then()
                .statusCode(404);
    }


    @Test
    @DisplayName("should return 400 when followerId header is not present")
    public void listPostFollowerHeaderNotSendTest() {

        given()
                .pathParam("userId", userID)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body(Matchers.equalTo("You forgot the header followerId"));

    }

    @Test
    @DisplayName("Should return 400 when follower doesn't exists")
    public void listPostFollowerNotFoundTest() {

        var inexistentFollowerId = 999;

        given()
                .pathParam("userId", userID)
                .header("followerId", inexistentFollowerId)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body(Matchers.equalTo("inexistent followerId"));
    }

    @Test
    @DisplayName("Should return 403 when follower isn't a follower")
    public void listPostNotAFollowerTest() {

        given()
                .pathParam("userId", userID)
                .header("followerId", userNotFollowerId)
                .when()
                .get()
                .then()
                .statusCode(403)
                .body(Matchers.equalTo("You can't see these posts"));
    }

    @Test
    @DisplayName("Should return posts")
    public void listPostTest() {

        given()
                .pathParam("userId", userID)
                .header("followerId", userFollowerId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }
}