package tests

import geb.Browser
import geb.junit5.GebReportingTest
import helpers.ManualSwiftCodesHelper
import helpers.NavigationHelper
import helpers.SessionHelper
import io.github.bonigarcia.seljup.SeleniumJupiter
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.chrome.ChromeDriver
import pages.MainPageCbs

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
    void loginIsSuccessful() {
        SessionHelper.fillLoginField("cbs-admin", browser)
        SessionHelper.fillPasswordField("123_Qwerty", browser)
        SessionHelper.pressLoginButton(browser)
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(60) { mainMenu.topToolBar }
        })
    }

    @Test
    void createSwiftCode() {
        SessionHelper.authorizeInCbs("cbs-admin", "123_Qwerty", browser)
        ManualSwiftCodesHelper.openManualSwiftCodesWindow(browser)
        NavigationHelper.pressButtonInWindow("SWIFT codes manual", "Add", browser)
        NavigationHelper.windowIsDisplayed("Add SWIFT code", browser)
        NavigationHelper.selectFieldAndSetValue("Code", "BNS123ZASXX", "Add SWIFT code", browser)
    }

}
