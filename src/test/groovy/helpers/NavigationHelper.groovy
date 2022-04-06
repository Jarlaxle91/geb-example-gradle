package helpers

import geb.Browser
import geb.navigator.Navigator
import io.qameta.allure.Step
import org.openqa.selenium.By
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

    @Step(value = "Press {1} button in window {0}")
    static void pressButtonInWindow(String windowTitle, String buttonTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            button(window, buttonTitle).click()
        })
    }

    @Step(value = "Window {0} was closed automatically")
    static void windowWasClosedAutomatically(String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            window.isEmpty()
        })
    }

    @Step(value = "Select field {0} and set value {1}")
    static selectFieldAndSetValue(String inputName, String valueField, String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            def currentField = waitFor { InputsHelper.checkInputName(inputName, window, browser) }
            InputsHelper.checkInputField(inputName, valueField, currentField, browser)
        })
    }

    @Step(value = "Select field {0} and set value {1} in dropdown list")
    static selectFieldOfDropdownListAndSetValue(String inputName, String valueField, String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            Navigator currentField = valueOfField(window, inputName)
            InputsHelper.selectOption(currentField, valueField, browser)
        })
    }





}
