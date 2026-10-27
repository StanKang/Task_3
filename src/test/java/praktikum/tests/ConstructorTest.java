package praktikum.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import praktikum.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Конструктор: переходы к разделам")
class ConstructorTest extends BaseTest {

    private MainPage mainPage;

    @BeforeEach
    void openMainPage() {
        mainPage = new MainPage(driver).open();
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    void saucesTabOpensSaucesSection() {
        mainPage.clickSaucesTab();

        assertTrue(mainPage.isSaucesTabActive(), "Вкладка «Соусы» не стала активной");
        assertTrue(mainPage.isSaucesHeadingInView(), "Заголовок раздела «Соусы» не виден в списке ингредиентов");
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    void fillingsTabOpensFillingsSection() {
        mainPage.clickFillingsTab();

        assertTrue(mainPage.isFillingsTabActive(), "Вкладка «Начинки» не стала активной");
        assertTrue(mainPage.isFillingsHeadingInView(), "Заголовок раздела «Начинки» не виден в списке ингредиентов");
    }

    @Test
    @DisplayName("Переход к разделу «Булки» после другого раздела")
    void bunsTabOpensBunsSection() {
        mainPage.clickFillingsTab();
        assertTrue(mainPage.isFillingsTabActive(), "Предусловие не выполнено: вкладка «Начинки» не стала активной");

        mainPage.clickBunsTab();

        assertTrue(mainPage.isBunsTabActive(), "Вкладка «Булки» не стала активной");
        assertTrue(mainPage.isBunsHeadingInView(), "Заголовок раздела «Булки» не виден в списке ингредиентов");
    }
}
