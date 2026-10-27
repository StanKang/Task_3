package praktikum.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.ForgotPasswordPage;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вход в аккаунт")
class LoginTest extends BaseUserTest {

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    @Description("После входа открывается конструктор с кнопкой «Оформить заказ»")
    void loginViaMainPageButtonOpensConstructor() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoggedIn(mainPage);
    }

    @Test
    @DisplayName("Вход через кнопку «Личный Кабинет»")
    @Description("Неавторизованного пользователя из личного кабинета отправляют на вход, после входа открывается конструктор")
    void loginViaAccountButtonOpensConstructor() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickAccountLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoggedIn(mainPage);
    }

    @Test
    @DisplayName("Вход через кнопку «Войти» в форме регистрации")
    @Description("Со страницы регистрации по ссылке «Войти» открывается вход, после него - конструктор")
    void loginViaRegisterFormLinkOpensConstructor() {
        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoggedIn(new MainPage(driver));
    }

    @Test
    @DisplayName("Вход через кнопку «Войти» в форме восстановления пароля")
    @Description("Со страницы восстановления пароля по ссылке «Войти» открывается вход, после него - конструктор")
    void loginViaForgotPasswordFormLinkOpensConstructor() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver).open();
        forgotPasswordPage.clickLoginLink();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        assertLoggedIn(new MainPage(driver));
    }

    private void assertLoggedIn(MainPage mainPage) {
        assertTrue(mainPage.isMakeOrderButtonDisplayed(),
                () -> "После входа не появилась кнопка «Оформить заказ», текущий URL: " + mainPage.getCurrentUrl());
        assertTrue(mainPage.isOpened(),
                () -> "После входа ожидалась главная страница, текущий URL: " + mainPage.getCurrentUrl());
    }
}
