package api.tests;

import api.model.request.UpdateUser;
import api.model.request.User;
import api.model.response.ListUsersResponse;
import api.model.response.UpdateUserResponse;
import api.model.response.UserResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static api.specs.BaseSpec.*;
import static api.tests.TestData.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInTests {

    private UserResponse getUserById(int userID) {
        return given(commonRequestSpec)
                .get("/users/" + userID)
                .then()
                .spec(commonResponseSpec)
                .extract().as(UserResponse.class);
    }

    private ListUsersResponse getUsersByPage(int page) {
        return given(commonRequestSpec)
                .get("/users?page=" + page)
                .then()
                .spec(commonResponseSpec)
                .extract().as(ListUsersResponse.class);
    }

    private void assertEqualsUserParams(UserResponse userResponse) {
        assertEquals(USER10.getId(), userResponse.getData().getId());
        assertEquals(USER10.getEmail(), userResponse.getData().getEmail());
        assertEquals(USER10.getFirstName(), userResponse.getData().getFirstName());
        assertEquals(USER10.getLastName(), userResponse.getData().getLastName());
        assertEquals("https://reqres.in/img/faces/2-image.jpg", userResponse.getData().getAvatar());
        assertEquals("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral", userResponse.getSupport().getUrl());
        assertEquals("Tired of writing endless social media content? Let Content Caddy generate it for you.", userResponse.getSupport().getText());
    }

    private void assertEqualsUsersParams(ListUsersResponse listUsersResponse) {
        List<User> expectedUsers = Arrays.asList(USER0, USER1, USER2, USER3, USER4, USER5);

        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i), listUsersResponse.getData().get(i));
        }
    }

    @Test
    void getSingleUserSuccess() {
        UserResponse userResponse = getUserById(2);
        assertEqualsUserParams(userResponse);
    }

    @Test
    void singleUserNotFound() {
        given(commonRequestSpec)
                .get("/users/23")
                .then()
                .spec(commonResponseSpecWithoutExpStatusCode)
                .statusCode(404);
    }

    @Test
    void getListUsers() {
        ListUsersResponse listUsersResponse = getUsersByPage(2);
        assertEqualsUsersParams(listUsersResponse);

    }

    @Test
    void updateUser() {
        UpdateUser updatedUser = new UpdateUser();
        updatedUser.setName("morpheus");
        updatedUser.setJob("zion resident");

        UpdateUserResponse updateUserResponse = given(commonRequestSpec)
                .body(updatedUser)
                .put("/api/users/2")
                .then()
                .spec(commonResponseSpec)
                .extract().as(UpdateUserResponse.class);
        assertEquals("morpheus", updateUserResponse.getName());
        assertEquals("zion resident", updateUserResponse.getJob());
    }
}
