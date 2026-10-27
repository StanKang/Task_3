package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import praktikum.utils.Config;

/**
 * Главная страница - конструктор бургеров.
 */
public class MainPage extends BasePage {

    public static final String PATH = "/";

    private static final String CURRENT_TAB_CLASS = "tab_tab_type_current";

    // Заголовок виден в прокручиваемом списке ингредиентов (его верхняя граница внутри контейнера)
    private static final String IS_IN_SCROLL_VIEW_SCRIPT =
            "const el = arguments[0];"
                    + "let box = el.parentElement;"
                    + "while (box && !(box.scrollHeight > box.clientHeight"
                    + " && /(auto|scroll)/.test(getComputedStyle(box).overflowY))) { box = box.parentElement; }"
                    + "const r = el.getBoundingClientRect();"
                    + "const c = box ? box.getBoundingClientRect() : {top: 0, bottom: window.innerHeight};"
                    + "return r.top >= c.top - 5 && r.top < c.bottom;";

    private final By pageHeader = By.xpath("//h1[text()='Соберите бургер']");
    private final By bunsTab = tab("Булки");
    private final By saucesTab = tab("Соусы");
    private final By fillingsTab = tab("Начинки");
    private final By bunsHeading = By.xpath("//h2[text()='Булки']");
    private final By saucesHeading = By.xpath("//h2[text()='Соусы']");
    private final By fillingsHeading = By.xpath("//h2[text()='Начинки']");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By makeOrderButton = By.xpath("//button[text()='Оформить заказ']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    private static By tab(String name) {
        return By.xpath("//div[contains(@class,'tab_tab_')][normalize-space()='" + name + "']");
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        driver.get(Config.baseUrl() + PATH);
        waitVisible(pageHeader);
        return this;
    }

    @Step("Проверить, что открыта главная страница с конструктором")
    public boolean isOpened() {
        return waitForPath(PATH) && isDisplayed(pageHeader);
    }

    @Step("Кликнуть по кнопке «Войти в аккаунт»")
    public void clickLoginButton() {
        click(loginButton);
    }

    @Step("Проверить, что на главной есть кнопка «Оформить заказ» (пользователь авторизован)")
    public boolean isMakeOrderButtonDisplayed() {
        return isDisplayed(makeOrderButton);
    }

    @Step("Кликнуть по вкладке «Булки»")
    public void clickBunsTab() {
        click(bunsTab);
    }

    @Step("Кликнуть по вкладке «Соусы»")
    public void clickSaucesTab() {
        click(saucesTab);
    }

    @Step("Кликнуть по вкладке «Начинки»")
    public void clickFillingsTab() {
        click(fillingsTab);
    }

    @Step("Проверить, что вкладка «Булки» активна")
    public boolean isBunsTabActive() {
        return isTabActive(bunsTab);
    }

    @Step("Проверить, что вкладка «Соусы» активна")
    public boolean isSaucesTabActive() {
        return isTabActive(saucesTab);
    }

    @Step("Проверить, что вкладка «Начинки» активна")
    public boolean isFillingsTabActive() {
        return isTabActive(fillingsTab);
    }

    @Step("Проверить, что заголовок раздела «Булки» находится в зоне видимости списка")
    public boolean isBunsHeadingInView() {
        return isHeadingInView(bunsHeading);
    }

    @Step("Проверить, что заголовок раздела «Соусы» находится в зоне видимости списка")
    public boolean isSaucesHeadingInView() {
        return isHeadingInView(saucesHeading);
    }

    @Step("Проверить, что заголовок раздела «Начинки» находится в зоне видимости списка")
    public boolean isFillingsHeadingInView() {
        return isHeadingInView(fillingsHeading);
    }

    private boolean isTabActive(By tabLocator) {
        return waitUntil(d -> d.findElement(tabLocator).getAttribute("class").contains(CURRENT_TAB_CLASS));
    }

    private boolean isHeadingInView(By headingLocator) {
        return waitUntil(d -> {
            WebElement heading = d.findElement(headingLocator);
            return (Boolean) ((JavascriptExecutor) d).executeScript(IS_IN_SCROLL_VIEW_SCRIPT, heading);
        });
    }
}
