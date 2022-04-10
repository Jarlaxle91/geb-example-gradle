package helpers

import geb.Browser
import geb.navigator.Navigator
import org.openqa.selenium.By
import pages.MainPageCbs

class FilterHelper {

    static applyFilterInWindow(String property, String operator, String value, String windowTitle, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = waitFor(10) { window(windowTitle) }
            expandFilter(window, browser)
            clearFilter(window, browser)
            selectValueOfPropertyField(window, property, browser)
            selectValueOfOperatorField(window, operator, browser)

            if (property == 'Payment details') {
                fillValueOfFilter(window, Store.getVar(value), browser)
            } else if (value.contains(' > ')) {
                fillValueOfFilter(window, Store.getVar(value.split('\\ > ')[0])
                        .getAt(value.split('\\ > ')[1]) as String, browser)
            } else {
                fillValueOfFilter(window, value, browser)
            }
            applyFilter(window, browser)
            collapseFilter(window, browser)
        })
    }

    static setMultifilter(String windowTitle, Collection<Filter> data, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            Navigator window = waitFor(10) { window(windowTitle) }
            Navigator filterFirstValueTrigger = filterPrimValueTrigger

            expandFilter(window, browser)
            clearFilter(window, browser)
            def caclulatedDate
            data.each {
                selectValueOfPropertyField(window, it.name as String, browser)
                selectValueOfOperatorField(window, it.operator as String, browser)

                if (!filterFirstValueTrigger.isEmpty()) {
                    filterFirstValueTrigger.click()
                    if (it.primValue == 'Today') {
                        caclulatedDate = DateTimeUtil.getNameOperday()
                        def calculatedCell = waitFor { $('div.x-boundlist-item', text: caclulatedDate) }
                        calculatedCell.click()
                    } else if (it.primValue.contains('Today ')) {
                        caclulatedDate = DateTimeUtil.caclucateOperdayForFilter(it.primValue)
                        def calculatedCell = waitFor { page.$('div.x-boundlist-item', text: caclulatedDate) }
                        calculatedCell.click()
                    } else
                        filterFirstValueTrigger.value(Store.getVar(it.primValue))

                } else {
                    if (it.primValue == 'Today') {
                        caclulatedDate = DateTimeUtil.getNameOperday()
                        firstFilterValueField.value(caclulatedDate)
                    } else if (it.primValue.contains('Today ')) {
                        caclulatedDate = DateTimeUtil.caclucateOperdayForFilter(it.primValue)
                        firstFilterValueField.value(caclulatedDate)
                    } else
                        firstFilterValueField.value(Store.getVar(it.primValue))
                }

                if (!secondFilterValueField(window).empty) {
                    if (it.secondValue == 'Today') {
                        caclulatedDate = DateTimeUtil.getNameOperday()
                        secondFilterValueField(window).value(caclulatedDate)
                    } else if (it.secondValue.contains('Today ')) {
                        caclulatedDate = DateTimeUtil.caclucateOperdayForFilter(it.secondValue)
                        secondFilterValueField(window).value(caclulatedDate)
                    } else
                        secondFilterValueField(window).value(Store.getVar(it.secondValue))
                }
            }
            applyFilter(window, browser)
            collapseFilter(window, browser)
        })
    }


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
            filterOperatorTrigger.click()
            elementOperatorList(operator).click()
        })
    }

    static void fillValueOfFilter(Navigator window, Object primValue, Object secondValue = null, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            if (!filterPrimValueTrigger(window).empty) {
                filterPrimValueTrigger.click()
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
            waitFor(10) { !collapseFilterButton(window).isEmpty() }
            waitFor(10) { collapseFilterButton(window).isDisplayed() }
            collapseFilterButton(window).click()
        })
    }

    static class Filter {
        String name
        String operator
        String primValue
        String secondValue = ""
    }

}
