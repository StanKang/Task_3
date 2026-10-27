package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import praktikum.utils.Config;

/**
 * Страница восстановления пароля.
 */
public class ForgotPasswordPage extends BasePage {

    public static final String PATH = "/forgot-password";

    private final By pageHeader = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.xpath("//main//a[@href='/login']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу восстановления пароля")
    public ForgotPasswordPage open() {
        driver.get(Config.baseUrl() + PATH);
        waitVisible(pageHeader);
        return this;
    }

    @Step("Проверить, что открыта страница восстановления пароля")
    public boolean isOpened() {
        return waitForPath(PATH) && isDisplayed(pageHeader);
    }

    @Step("Кликнуть по ссылке «Войти»")
    public void clickLoginLink() {
        click(loginLink);
    }
}
