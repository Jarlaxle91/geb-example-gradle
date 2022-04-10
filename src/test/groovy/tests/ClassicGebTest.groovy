package tests

import geb.Browser
import geb.junit5.GebReportingTest
import helpers.FilterHelper
import helpers.NavigationHelper
import helpers.SessionHelper
import io.github.bonigarcia.seljup.SeleniumJupiter
import io.qameta.allure.Description
import io.qameta.allure.Story
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.chrome.ChromeDriver
import pages.MainPageCbs

@DisplayName(value = "CBS tests")
@ExtendWith(SeleniumJupiter.class)
class ClassicGebTest extends GebReportingTest {
    public Browser browser
    public ChromeDriver driver

    @BeforeEach
    void classLevelSetup() {
        browser = new Browser()
        driver = new ChromeDriver()
        browser.setDriver(driver)
    }

    @AfterEach
    void teardown() {
        browser.quit()
    }

    @Test
    @DisplayName(value = "Login in CBS")
    @Story(value = "Test for login with valid credentials")
    @Description("Тест на успешную авторизацию. Ожидается загрузка главной страницы.")
    void loginIsSuccessful() {
        SessionHelper.fillLoginField("cbs-admin", browser)
        SessionHelper.fillPasswordField("123_Qwerty", browser)
        SessionHelper.pressLoginButton(browser)
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(90) { mainMenu.topToolBar }
        })
    }

    @Test
    @DisplayName(value = "Login in CBS with invalid username")
    @Story(value = "Test for login with invalid username")
    @Description("Тест на отображение ошибки о неверных пользовательских данных.")
    void loginWithInvalidUsername() {
        SessionHelper.fillLoginField("invalid_username", browser)
        SessionHelper.fillPasswordField("123_Qwerty", browser)
        SessionHelper.pressLoginButtonWithoutVerificationPage(browser)
        SessionHelper.errorIsDisplayed(browser)
    }

    @Test
    @DisplayName(value = "Login in CBS with invalid password")
    @Story(value = "Test for login with invalid password")
    @Description("Тест на отображение ошибки о неверных пользовательских данных.")
    void loginWithInvalidPassword() {
        SessionHelper.fillLoginField("cbs-admin", browser)
        SessionHelper.fillPasswordField("invalid_password", browser)
        SessionHelper.pressLoginButtonWithoutVerificationPage(browser)
        SessionHelper.errorIsDisplayed(browser)
    }

    @Test
    @DisplayName(value = "Create SWIFT code")
    @Story(value = "Test for creation manual swift code")
    @Description("Тест на создание новой записи в Manual SWIFT codes")
    void createSwiftCode() {
        SessionHelper.authorizeInCbs("cbs-admin", "123_Qwerty", browser)
        NavigationHelper.openWindowByPath("Dictionaries > SWIFT codes > SWIFT codes manual", browser)
        NavigationHelper.pressButtonInWindow("SWIFT codes manual", "Add", browser)
        NavigationHelper.windowIsDisplayed("Add SWIFT code", browser)
        NavigationHelper.selectFieldAndSetValue("Code", "BNS123ZASXX", "Add SWIFT code", browser)
        NavigationHelper.selectFieldAndSetValue("Bank name", "Test bank", "Add SWIFT code", browser)
        NavigationHelper.selectFieldOfDropdownListAndSetValue("Country", "Greece", "Add SWIFT code", browser)
        NavigationHelper.selectFieldAndSetValue("Address", "Test address", "Add SWIFT code", browser)
        NavigationHelper.selectFieldOfDropdownListAndSetValue("Enabled", "N", "Add SWIFT code", browser)
        NavigationHelper.pressButtonInWindow("Add SWIFT code", "Save", browser)
        NavigationHelper.windowWasClosedAutomatically("Add SWIFT code", browser)
        FilterHelper.applyFilterInWindow("Code", "LIKE", "BNS123ZASXX","SWIFT codes manual", browser)
        NavigationHelper.inWindowDisplayedRecords("SWIFT codes manual", 1, browser)
    }
}
