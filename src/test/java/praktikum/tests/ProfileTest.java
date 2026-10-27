package praktikum.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.MainPage;
import praktikum.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Личный кабинет")
class ProfileTest extends BaseUserTest {

    private MainPage mainPage;
    private ProfilePage profilePage;

    @BeforeEach
    void logIn() {
        loginViaUi();
        mainPage = new MainPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на «Личный Кабинет»")
    @Description("Открывается профиль /account/profile с данными авторизованного пользователя")
    void accountLinkOpensProfile() {
        mainPage.clickAccountLink();

        assertTrue(profilePage.isOpened(),
                () -> "Ожидался личный кабинет /account/profile, текущий URL: " + profilePage.getCurrentUrl());
        assertEquals(user.getEmail(), profilePage.getLoginValue(), "В профиле отображается чужой логин");
        assertEquals(user.getName(), profilePage.getNameValue(), "В профиле отображается чужое имя");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по ссылке «Конструктор»")
    void constructorLinkFromProfileOpensMainPage() {
        mainPage.clickAccountLink();
        assertTrue(profilePage.isOpened(), "Предусловие не выполнено: личный кабинет не открылся");

        profilePage.clickConstructorLink();

        assertTrue(mainPage.isOpened(),
                () -> "Ожидалась главная страница, текущий URL: " + mainPage.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по логотипу Stellar Burgers")
    void logoFromProfileOpensMainPage() {
        mainPage.clickAccountLink();
        assertTrue(profilePage.isOpened(), "Предусловие не выполнено: личный кабинет не открылся");

        profilePage.clickLogo();

        assertTrue(mainPage.isOpened(),
                () -> "Ожидалась главная страница, текущий URL: " + mainPage.getCurrentUrl());
    }
}
