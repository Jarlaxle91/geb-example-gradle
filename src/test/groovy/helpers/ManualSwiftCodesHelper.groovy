package helpers

import geb.Browser
import geb.navigator.Navigator
import pages.MainPageCbs

class ManualSwiftCodesHelper {

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

    static void pressAddButton (Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window("SWIFT codes manual")
            button(window, "Add").click()
        })
    }

}
