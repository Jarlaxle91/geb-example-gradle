package helpers

import geb.Browser
import geb.navigator.Navigator
import pages.MainPageCbs

class FiltersHelper {


    static void expandFilter(Navigator window, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            filterTrigger(window)
            waitFor() {filterTrigger(window).isDisplayed() }
            sleep(100)
            filterTrigger(window).click()
            if (filterPanel(window).last().size() == 0) {
                filterTrigger(window).click()
                waitFor() { page.filterPanel(window).last().size() == 1 }
            }
        })
    }

    static void clearFilter(Navigator window, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor() { !clearFilterButton(window).empty }
            waitFor() { clearFilterButton(window).isDisplayed() }
            clearFilterButton(window).click()
        })
    }

    static void selectValueOfPropertyField(Navigator window, String property, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            filterPropertyTrigger(window).click()
            elementPropertyList(property).click()
        })
    }

    static void selectValueOfOperatorField(Navigator window, String operator, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            filterOperatorTrigger(window).click()
            elementOperatorList(operator).click()
        })
    }

    static void fillValueOfFilter(Navigator window, Object primValue, Object secondValue = null, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            if (!filterPrimValueTrigger(window).empty) {
                filterPrimValueTrigger(window).click()
                waitFor() { !elementPrimValueList(primValue).empty }
                sleep(100)
                waitFor() { elementPrimValueList(primValue).isDisplayed() }
                sleep(100)
                elementPrimValueList(primValue).click()
            } else {
                firstFilterValueField(window).value(Store.getVar(primValue))
            }

            if (!secondFilterValueField(window).empty) {
                secondFilterValueField(window) value(Store.getVar(secondValue))
            }
        })
    }

    static void applyFilter(Navigator window, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor() { !applyFilterButton(window).isEmpty() }
            waitFor() { applyFilterButton(window).isDisplayed() }
            applyFilterButton(window).click()
        })
    }

    static void collapseFilter(Navigator window, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            waitFor() { collapseFilterButton(window).isEmpty() }
            waitFor() { collapseFilterButton(window).isDisplayed() }
            collapseFilterButton(window).click()
        })
    }

}
