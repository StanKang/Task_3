package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import praktikum.models.User;
import praktikum.utils.Config;

import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

/**
 * Работа с пользователями через API: используется для подготовки и очистки тестовых данных.
 */
public final class UserApi {

    private static final String REGISTER_PATH = "/api/auth/register";
    private static final String LOGIN_PATH = "/api/auth/login";
    private static final String USER_PATH = "/api/auth/user";

    private UserApi() {
    }

    @Step("Создать пользователя через API")
    public static String createUser(User user) {
        return given()
                .baseUri(Config.baseUrl())
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    @Step("Получить токен пользователя через логин по API")
    public static Optional<String> loginAndGetToken(String email, String password) {
        Response response = given()
                .baseUri(Config.baseUrl())
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "password", password))
                .when()
                .post(LOGIN_PATH);
        if (response.statusCode() != 200) {
            return Optional.empty();
        }
        return Optional.ofNullable(response.path("accessToken"));
    }

    @Step("Удалить пользователя через API")
    public static void deleteUser(String accessToken) {
        given()
                .baseUri(Config.baseUrl())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then()
                .statusCode(202);
    }

    @Step("Удалить пользователя по email и паролю, если он существует")
    public static void deleteUserIfExists(String email, String password) {
        loginAndGetToken(email, password).ifPresent(UserApi::deleteUser);
    }
}
