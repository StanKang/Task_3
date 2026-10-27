package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;
import java.util.function.Function;

/**
 * Базовая страница: шапка приложения и общие методы работы с элементами.
 */
public abstract class BasePage {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // Шапка приложения
    private final By constructorLink = By.xpath("//header//a[@href='/'][normalize-space()='Конструктор']");
    private final By accountLink = By.xpath("//header//a[@href='/account']");
    // Логотип - ссылка на главную без текста (внутри только svg)
    private final By logoLink = By.xpath("//header//a[@href='/' and not(normalize-space())]");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Step("Кликнуть по ссылке «Конструктор» в шапке")
    public void clickConstructorLink() {
        click(constructorLink);
    }

    @Step("Кликнуть по кнопке «Личный Кабинет» в шапке")
    public void clickAccountLink() {
        click(accountLink);
    }

    @Step("Кликнуть по логотипу Stellar Burgers в шапке")
    public void clickLogo() {
        click(logoLink);
    }

    @Step("Получить текущий URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Ждёт, пока путь текущего URL станет равен заданному. Возвращает false, если не дождались. */
    protected boolean waitForPath(String path) {
        return waitUntil(d -> URI.create(d.getCurrentUrl()).getPath().equals(path));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean isDisplayed(By locator) {
        return waitUntil(d -> {
            var elements = d.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        });
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    /** Ждёт выполнения условия, возвращает false вместо исключения по таймауту. */
    protected boolean waitUntil(Function<WebDriver, Boolean> condition) {
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            return false;
        }
    }
}
