package praktikum.utils;

/**
 * Настройки запуска, читаются из системных свойств (-Dимя=значение).
 */
public final class Config {

    public static final String DEFAULT_BASE_URL = "https://qa-stellarburgers.education-services.ru";
    public static final String DEFAULT_BROWSER = "chrome";

    private Config() {
    }

    /** Базовый URL приложения без завершающего слэша: -DbaseUrl=... */
    public static String baseUrl() {
        String url = System.getProperty("baseUrl", DEFAULT_BASE_URL).trim();
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    /** Имя браузера: -Dbrowser=chrome|yandex. */
    public static String browser() {
        return System.getProperty("browser", DEFAULT_BROWSER).trim();
    }

    /** Запуск браузера без окна: -Dheadless=true. */
    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
