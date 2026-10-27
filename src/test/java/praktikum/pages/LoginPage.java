package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import praktikum.utils.Config;

/**
 * Страница входа.
 */
public class LoginPage extends BasePage {

    public static final String PATH = "/login";

    private final By pageHeader = By.xpath("//h2[text()='Вход']");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу входа")
    public LoginPage open() {
        driver.get(Config.baseUrl() + PATH);
        waitVisible(pageHeader);
        return this;
    }

    @Step("Проверить, что открыта страница входа")
    public boolean isOpened() {
        return waitForPath(PATH) && isDisplayed(pageHeader);
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        type(emailInput, email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        type(passwordInput, password);
    }

    @Step("Кликнуть по кнопке «Войти»")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Войти в аккаунт: ввести email и пароль, нажать «Войти»")
    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }
}
