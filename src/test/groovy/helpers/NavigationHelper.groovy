package helpers

import geb.Browser
import geb.navigator.Navigator
import pages.MainPageCbs

class NavigationHelper {

    static void windowIsDisplayed(String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            window.isDisplayed()
        })
    }

    static void openWindow(String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(60) { mainMenu.topToolBar }

            mainMenu.dictionaries.click()
            mainMenu.swiftCodesMenu.click()
            mainMenu.swiftCodesManual.click()
            Navigator window = window(windowTitle)
            window.isDisplayed()
        })
    }

    static void pressButtonInWindow(String windowTitle, String buttonTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            button(window, buttonTitle).click()
        })
    }

    static selectFieldAndSetValue(String inputName, String valueField, String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            Navigator currentField = waitFor { InputsHelper.checkInputName(inputName, window, browser) } as Navigator
            InputsHelper.checkInputField(inputName, valueField, currentField, browser)
        })
    }



    static setInputField(Navigator selector, String value, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            selector.value(value)
            ''
        })
    }



//    static setFormField(Navigator selector, String value, Browser browser) {
//        Browser.drive(browser, {
//            at MainPageCbs
//            selector.$('input').value(value)
//        })
//    }
}
