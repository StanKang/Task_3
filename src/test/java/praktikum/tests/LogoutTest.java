package praktikum.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Выход из аккаунта")
class LogoutTest extends BaseUserTest {

    @Test
    @DisplayName("Выход по кнопке «Выход» в личном кабинете")
    void logoutButtonOpensLoginPage() {
        loginViaUi();
        MainPage mainPage = new MainPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.clickAccountLink();
        assertTrue(profilePage.isOpened(), "Предусловие не выполнено: личный кабинет не открылся");

        profilePage.clickLogoutButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isOpened(),
                () -> "После выхода ожидалась страница /login, текущий URL: " + loginPage.getCurrentUrl());
    }
}
