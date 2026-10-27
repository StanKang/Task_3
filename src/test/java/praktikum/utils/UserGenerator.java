package praktikum.utils;

import praktikum.models.User;

import java.util.UUID;

/**
 * Генерация уникальных тестовых пользователей.
 */
public final class UserGenerator {

    private UserGenerator() {
    }

    /** Пользователь с уникальным email и корректным паролем (10 символов). */
    public static User randomUser() {
        return randomUserWithPassword(randomPassword());
    }

    /** Пользователь с уникальным email и заданным паролем. */
    public static User randomUserWithPassword(String password) {
        String id = UUID.randomUUID().toString();
        return new User("test-" + id + "@yandex.ru", password, "Тестер " + id.substring(0, 8));
    }

    private static String randomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
