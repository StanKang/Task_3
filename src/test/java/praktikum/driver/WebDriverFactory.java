package praktikum.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import praktikum.utils.Config;

import java.io.File;
import java.util.Map;

/**
 * Фабрика драйверов: создаёт WebDriver по имени браузера (chrome или yandex).
 * <p>
 * Параметры запуска (системные свойства):
 * <ul>
 *     <li>-Dbrowser=chrome|yandex - браузер, по умолчанию chrome;</li>
 *     <li>-DyandexBinary=путь - исполняемый файл Яндекс.Браузера;</li>
 *     <li>-DyandexDriver=путь - yandexdriver (обязателен для yandex);</li>
 *     <li>-Dheadless=true - запуск без окна.</li>
 * </ul>
 */
public final class WebDriverFactory {

    public static final String DEFAULT_YANDEX_BINARY = "/Applications/Yandex.app/Contents/MacOS/Yandex";

    private static final String WINDOW_SIZE = "--window-size=1440,900";

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(String browserName) {
        if (browserName == null) {
            throw new IllegalArgumentException("Не задано имя браузера. Допустимые значения: chrome, yandex");
        }
        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                return new ChromeDriver(createBaseOptions());
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException(
                        "Неизвестный браузер: '" + browserName + "'. Допустимые значения: chrome, yandex");
        }
    }

    private static WebDriver createYandexDriver() {
        String driverPath = System.getProperty("yandexDriver");
        if (driverPath == null || driverPath.isBlank()) {
            throw new IllegalStateException(
                    "Для запуска в Яндекс.Браузере укажите путь к yandexdriver: -DyandexDriver=/path/to/yandexdriver");
        }
        if (!new File(driverPath).isFile()) {
            throw new IllegalStateException("Файл yandexdriver не найден: " + driverPath);
        }
        String binaryPath = System.getProperty("yandexBinary", DEFAULT_YANDEX_BINARY);
        if (!new File(binaryPath).isFile()) {
            throw new IllegalStateException(
                    "Яндекс.Браузер не найден: " + binaryPath + ". Укажите путь через -DyandexBinary=...");
        }
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = createBaseOptions();
        options.setBinary(binaryPath);
        return new ChromeDriver(options);
    }

    private static ChromeOptions createBaseOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(WINDOW_SIZE);
        if (Config.headless()) {
            options.addArguments("--headless=new");
        }
        // Отключаем всплывающие окна менеджера паролей, они перекрывают страницу
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        return options;
    }
}
