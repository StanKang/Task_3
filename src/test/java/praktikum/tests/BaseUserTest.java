package praktikum.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import praktikum.api.UserApi;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Базовый класс для тестов, которым нужен зарегистрированный пользователь:
 * создаётся через API перед тестом и удаляется после.
 */
public abstract class BaseUserTest extends BaseTest {

    protected User user;
    private String accessToken;

    @BeforeEach
    void createUser() {
        user = UserGenerator.randomUser();
        accessToken = UserApi.createUser(user);
    }

    @AfterEach
    void deleteUser() {
        if (accessToken != null) {
            UserApi.deleteUser(accessToken);
        }
    }

    @Step("Предусловие: авторизоваться через страницу входа")
    protected void loginViaUi() {
        new LoginPage(driver).open().login(user.getEmail(), user.getPassword());
        assertTrue(new MainPage(driver).isMakeOrderButtonDisplayed(),
                "Предусловие не выполнено: после входа нет кнопки «Оформить заказ»");
    }
}
