package praktikum.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import praktikum.driver.WebDriverFactory;
import praktikum.utils.Config;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Базовый класс UI-тестов: запускает браузер перед тестом и закрывает после.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    @Step("Запустить браузер")
    void setUpDriver() {
        driver = WebDriverFactory.createDriver(Config.browser());
        String browser = Config.browser();
        // Параметр с браузером виден в отчёте для каждого теста
        Allure.parameter("Браузер", browser);
        // historyId вычисляется Allure до появления параметра, поэтому он одинаков в разных браузерах
        // и при объединении результатов тесты слились бы в один. Делаем historyId зависимым от браузера
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setHistoryId(
                UUID.nameUUIDFromBytes((testResult.getHistoryId() + "|" + browser).getBytes(StandardCharsets.UTF_8))
                        .toString()));
    }

    @AfterEach
    @Step("Закрыть браузер")
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
