package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Личный кабинет: профиль пользователя.
 */
public class ProfilePage extends BasePage {

    public static final String PATH = "/account/profile";

    private final By profileLink = By.xpath("//main//a[@href='/account/profile']");
    private final By logoutButton = By.xpath("//main//button[text()='Выход']");
    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By loginInput = By.xpath("//label[text()='Логин']/following-sibling::input");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что открыт личный кабинет (профиль)")
    public boolean isOpened() {
        return waitForPath(PATH) && isDisplayed(profileLink) && isDisplayed(logoutButton);
    }

    @Step("Кликнуть по кнопке «Выход»")
    public void clickLogoutButton() {
        click(logoutButton);
    }

    @Step("Получить имя пользователя из профиля")
    public String getNameValue() {
        return getInputValueWhenLoaded(nameInput);
    }

    @Step("Получить логин (email) пользователя из профиля")
    public String getLoginValue() {
        return getInputValueWhenLoaded(loginInput);
    }

    // Данные профиля подгружаются после отрисовки, поэтому ждём непустое значение
    private String getInputValueWhenLoaded(By locator) {
        waitUntil(d -> !d.findElement(locator).getAttribute("value").isEmpty());
        return driver.findElement(locator).getAttribute("value");
    }
}
