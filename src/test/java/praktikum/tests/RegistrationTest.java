package praktikum.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.api.UserApi;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.RegisterPage;
import praktikum.utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Регистрация")
class RegistrationTest extends BaseTest {

    private static final String PASSWORD_ERROR_TEXT = "Некорректный пароль";
    private static final String TOO_SHORT_PASSWORD = "12345";

    private User user;

    @AfterEach
    void deleteUser() {
        // Пользователь мог быть создан через UI, поэтому удаляем его, залогинившись по API
        if (user != null) {
            UserApi.deleteUserIfExists(user.getEmail(), user.getPassword());
        }
    }

    @Test
    @DisplayName("Успешная регистрация перенаправляет на страницу входа")
    @Description("Регистрация с корректными данными открывает страницу /login")
    void successfulRegistrationOpensLoginPage() {
        user = UserGenerator.randomUser();
        RegisterPage registerPage = new RegisterPage(driver).open();

        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isOpened(),
                () -> "После регистрации ожидалась страница /login, текущий URL: " + loginPage.getCurrentUrl());
    }

    @Test
    @DisplayName("Пароль короче 6 символов: ошибка «Некорректный пароль»")
    @Description("Для пароля из 5 символов показывается ошибка, пользователь остаётся на странице регистрации")
    void shortPasswordShowsError() {
        user = UserGenerator.randomUserWithPassword(TOO_SHORT_PASSWORD);
        RegisterPage registerPage = new RegisterPage(driver).open();

        registerPage.register(user.getName(), user.getEmail(), user.getPassword());

        assertEquals(PASSWORD_ERROR_TEXT, registerPage.getPasswordErrorText(),
                "Текст ошибки пароля не соответствует ожидаемому");
        assertTrue(registerPage.isOpened(),
                () -> "Ожидалось, что пользователь останется на /register, текущий URL: " + registerPage.getCurrentUrl());
    }
}
