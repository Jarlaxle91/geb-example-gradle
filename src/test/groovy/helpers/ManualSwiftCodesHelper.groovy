package helpers

import geb.Browser
import geb.navigator.Navigator
import io.qameta.allure.Step
import pages.MainPageCbs

class ManualSwiftCodesHelper {

    @Step(value = "Open manual swift codes window")
    static void openManualSwiftCodesWindow(Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(60) { mainMenu.topToolBar }

            mainMenu.dictionaries.click()
            mainMenu.swiftCodesMenu.click()
            mainMenu.swiftCodesManual.click()
            Navigator window = window("SWIFT codes manual")
            window.isDisplayed()
        })
    }

}
