package pages;

import org.openqa.selenium.WebDriver;

public class ProjectsPage extends Page {
    private static final String PROJECTS_MARKER =
            "//*[contains(normalize-space(.), 'Проекты') or contains(normalize-space(.), 'работ') or contains(normalize-space(.), 'Работа')]";
    private static final String FIRST_PROJECT =
            "(//a[contains(@href, '/projects/') and not(contains(@href, '/projects/?')) and string-length(normalize-space(.)) > 5])[1]";
    private static final String PROGRAMMING_CATEGORY = "(//a[contains(normalize-space(.), 'Программирование')])[1]";
    private static final String PROGRAMMING_TEXT = "//*[contains(normalize-space(.), 'Программирование')]";
    private static final String PROJECT_CARD_MARKER =
            "//*[contains(normalize-space(.), 'Откликнуться') or contains(normalize-space(.), 'Опубликовал') or contains(normalize-space(.), 'Бюджет')]";
    private static final String PROJECT_TITLE =
            "//*[self::h1 or self::h2][string-length(normalize-space(.)) > 5]";
    private static final String PROJECT_DESCRIPTION =
            "//*[contains(normalize-space(.), 'Описание') or contains(normalize-space(.), 'Требуется') or contains(normalize-space(.), 'Необходимо') or contains(normalize-space(.), 'Нужно')]";
    private static final String RESPOND_BUTTON =
            "//*[self::a or self::button][contains(normalize-space(.), 'Откликнуться') or contains(normalize-space(.), 'Оставить отклик') or contains(normalize-space(.), 'Предложить')]";
    private static final String AUTH_REQUIRED =
            "//*[contains(normalize-space(.), 'Вход') or contains(normalize-space(.), 'Регистрация') or contains(normalize-space(.), 'Авториз') or contains(normalize-space(.), 'Зарегистр')]";
    private static final String PROJECT_SEARCH_INPUT =
            "(//input[contains(@placeholder, 'Поиск') or contains(@placeholder, 'поиск') or contains(@placeholder, 'работ') or contains(@placeholder, 'проект') or @type='search'])[1]";
    private static final String HEADER_SEARCH_BUTTON =
            "(//*[self::button or self::a][contains(@aria-label, 'Поиск') or contains(normalize-space(.), 'Поиск')])[1]";
    private static final String SEARCH_RESULTS =
            "//*[contains(normalize-space(.), 'java') or contains(normalize-space(.), 'Java') or contains(normalize-space(.), 'Найден') or contains(normalize-space(.), 'Результат') or contains(normalize-space(.), 'Проекты')]";

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    public void openProjectsPage() {
        open("/projects/");
    }

    public void checkProjectsPageLoaded() {
        assertLocationContains("/projects/");
        assertElementPresent(PROJECTS_MARKER);
    }

    public void openProgrammingCategory() {
        clickByXpath(PROGRAMMING_CATEGORY);
    }

    public void checkProgrammingCategoryLoaded() {
        waitForElementPresent(PROGRAMMING_TEXT);
        assertElementPresent(PROGRAMMING_TEXT);
    }

    public void openFirstProject() {
        waitForElementPresent(FIRST_PROJECT);
        clickByXpath(FIRST_PROJECT);
    }

    public void checkProjectCardLoaded() {
        waitForElementPresent(PROJECT_CARD_MARKER);
        assertElementPresent(PROJECT_CARD_MARKER);
    }

    public void checkProjectDetailsLoaded() {
        assertElementPresent(PROJECT_TITLE);
        assertAnyElementPresent(PROJECT_DESCRIPTION, PROJECT_CARD_MARKER);
        assertAnyElementPresent(RESPOND_BUTTON, PROJECT_CARD_MARKER);
    }

    public void respondToProject() {
        clickFirstAvailable(RESPOND_BUTTON, PROJECT_CARD_MARKER);
    }

    public void checkAuthorizationRequired() {
        assertElementPresent(AUTH_REQUIRED);
    }

    public void searchProjects(String query) {
        openProjectsPage();
        if (!isElementPresent(PROJECT_SEARCH_INPUT)) {
            clickFirstAvailable(HEADER_SEARCH_BUTTON);
        }
        typeFirstAvailable(query, true, PROJECT_SEARCH_INPUT);
    }

    public void checkSearchResultsVisible() {
        assertAnyElementPresent(SEARCH_RESULTS, PROJECTS_MARKER, FIRST_PROJECT);
    }
}
