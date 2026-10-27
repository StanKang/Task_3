package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import praktikum.utils.Config;

/**
 * Страница регистрации.
 */
public class RegisterPage extends BasePage {

    public static final String PATH = "/register";

    private final By pageHeader = By.xpath("//h2[text()='Регистрация']");
    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//main//a[@href='/login']");
    private final By passwordError = By.xpath("//p[contains(@class,'input__error')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу регистрации")
    public RegisterPage open() {
        driver.get(Config.baseUrl() + PATH);
        waitVisible(pageHeader);
        return this;
    }

    @Step("Проверить, что открыта страница регистрации")
    public boolean isOpened() {
        return waitForPath(PATH) && isDisplayed(pageHeader);
    }

    @Step("Ввести имя: {name}")
    public void setName(String name) {
        type(nameInput, name);
    }

    @Step("Ввести email: {email}")
    public void setEmail(String email) {
        type(emailInput, email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        type(passwordInput, password);
    }

    @Step("Кликнуть по кнопке «Зарегистрироваться»")
    public void clickRegisterButton() {
        click(registerButton);
    }

    @Step("Зарегистрироваться: ввести имя, email и пароль, нажать «Зарегистрироваться»")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @Step("Кликнуть по ссылке «Войти»")
    public void clickLoginLink() {
        click(loginLink);
    }

    @Step("Получить текст ошибки пароля")
    public String getPasswordErrorText() {
        return getText(passwordError);
    }
}
