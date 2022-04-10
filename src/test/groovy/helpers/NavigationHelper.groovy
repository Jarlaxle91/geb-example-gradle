package helpers

import geb.Browser
import geb.navigator.Navigator
import io.qameta.allure.Step
import org.assertj.core.matcher.AssertionMatcher
import pages.MainPageCbs
import org.assertj.core.api.Assertions

class NavigationHelper {

    @Step(value = "Window {0} is displayed")
    static void windowIsDisplayed(String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = window(windowTitle)
            assert !window.empty
        })
    }

    @Step(value = "Go to window by path {0}")
    static void openWindowByPath(String pathToWindow, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(60) { mainMenu.topToolBar }
            MainMenuHelper.openMenuItem(pathToWindow, browser)
        })
    }

    @Step(value = "Press {1} button in window {0}")
    static void pressButtonInWindow(String windowTitle, String buttonTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = waitFor { window(windowTitle) }
            button(window, buttonTitle).click()
        })
    }

    @Step(value = "Window {0} was closed automatically")
    static void windowWasClosedAutomatically(String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator actualWindow = window(windowTitle)
            sleep(500)
            actualWindow = window(windowTitle)
            Assertions.assertThat(actualWindow.size() == 0).isTrue()
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

    @Step(value = "In window {0} displayed {1} records")
    static void inWindowDisplayedRecords(String windowTitle, int amountOfRecords, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = waitFor(30) { window(windowTitle) }
            waitFormLoading(window, browser)

            buttonIsVisible(window, "Refresh", browser)

            assert 1 == checkGrid(window, browser)
        })
    }

    static void waitFormLoading(Navigator window, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            if (!xmask(window).isEmpty()) {
                waitFor(10) { !xmaskVisible(window).isEmpty() }
            }
        })
    }


    static void buttonIsVisible(Navigator window, String buttonTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor(10) {
                !windowButton(window, buttonTitle).isEmpty()
            }
        })
    }

    static checkGrid(Navigator window, Browser browser) {
        Integer result = null
        Browser.drive(browser, {
            at MainPageCbs
            waitFor { amountElementsInGrid(window) }
            Integer amountRecords = amountElementsInGrid(window).size()
            result = amountRecords
        })
        return result
    }
}
