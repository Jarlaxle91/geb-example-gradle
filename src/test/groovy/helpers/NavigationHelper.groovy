package helpers

import geb.Browser
import geb.navigator.Navigator
import io.qameta.allure.Step
import pages.MainPageCbs

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

    static applyFilterInWindow(String property, String operator, String value, String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = waitFor(10) { window(windowTitle) }
            FiltersHelper.expandFilter(window, browser)
            FiltersHelper.clearFilter(window, browser)
            FiltersHelper.selectValueOfPropertyField(window, property, browser)
            FiltersHelper.selectValueOfOperatorField(window, operator, browser)

            if (property == 'Payment details') {
                FiltersHelper.fillValueOfFilter(window, Store.getVar(value), browser)
            } else if (value.contains(' > ')) {
                FiltersHelper.fillValueOfFilter(window, Store.getVar(value.split('\\ > ')[0])
                        .getAt(value.split('\\ > ')[1]) as String, browser)
            } else {
                FiltersHelper.fillValueOfFilter(window, value, browser)
            }
            FiltersHelper.applyFilter(window, browser)
            FiltersHelper.collapseFilter(window, browser)

        })
    }

}
