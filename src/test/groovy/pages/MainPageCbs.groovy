package pages

import geb.Page
import geb.navigator.Navigator
import modules.MainPageCbsModule
import modules.SwiftCodeCreationWindow
import modules.SwiftCodesManualWindowModule
import org.openqa.selenium.By

class MainPageCbs extends Page {

    static at = { waitFor { title == "CBS"} }

    static content = {
        mainMenu { module MainPageCbsModule }
        swiftCodesManualWindowModule { module SwiftCodesManualWindowModule }
        swiftCodeCreationWindow { module SwiftCodeCreationWindow }

        //mainMenu
        topPanel { $('#topToolbar') }
        menuItem { Navigator menu, String title -> menu.$('a', innerHTML: contains(title + '<')) }

        //windows
        window { String titleName ->
            def header = $('body.x-body').$('div.x-window').$('div.x-title-text', text: contains(titleName))
            header.empty ? header : header.parents('div.x-window')
        }

        //buttons
        button { Navigator window, String buttonTitle -> window.$('.x-btn').has('.x-btn-inner', innerHTML: contains(buttonTitle)) }

        //fields
        textAreaByName { Navigator window, String inputName -> window.$('div.x-field', text: contains(inputName)).$('textarea') }
        inputByName { Navigator window, String inputName -> window.$('div.x-field', text: contains(inputName)).$('input') }
        valueOfField { Navigator window, String inputName -> window.$('div.x-field', text: contains(inputName)) }


        //filters

        filterPanel { Navigator window -> window.$(" div.x-panel-body-default-collapsed") }
        clearFilterButton { Navigator window -> window.$('a.clearButton') }
        applyFilterButton { Navigator window -> window.$('a.findButton') }

        filterTrigger { Navigator window -> window.$("div", role: 'button')
                .has("div.x-tool-expand-bottom") }

        collapseFilterButton { Navigator window ->
            window.$("div", role: 'button').has("div.x-tool-collapse-top") }

        filterPropertyTrigger { Navigator window ->
            window.$(By.xpath('(//div[contains(@class, "x-container-default")]' +
                    '//a[contains(@class, "filterCheckbox")]/../../..)[last()]' +
                    '//input[contains(@name, "filterField")]/../..//div[contains(@class, "x-form-trigger")]'))[0] }

        elementPropertyList { String property ->
            $(By.xpath('//div[contains(@class, "x-boundlist-floating")][last()]' +
                    '//ul[contains(@class, "x-list-plain")]//li[text()="' + property + '"]')) }

        filterOperatorTrigger { Navigator window ->
            window.$(By.xpath('//input[contains(@name, "filterField")]/..' +
                    '//input[contains(@aria-owns, "boundlist")]/../../../../..//' +
                    'div[contains(@class, "FilterOperator")]//div[contains(@class, "x-form-arrow-trigger")]')).last() }

        elementOperatorList { Navigator operator ->
            $(By.xpath('//div[contains(@class, "x-boundlist-floating")][last()]//ul[contains(@class, "x-list-plain")]' +
                    '//li[text()="' + operator + '"]')) }

        filterPrimValueTrigger { Navigator window ->
            window.$(By.xpath('//div[contains(@class, "FilterForm")][last() - 1]' +
                    '//div[contains(@class, "FilterValue")]//div[contains(@class, "x-form-arrow-trigger")]')) }

        elementPrimValueList { Navigator primValue ->
            $(By.xpath('//div[contains(@class, "x-boundlist-floating")][last()]' +
                    '//ul[contains(@class, "x-list-plain")]//li[text()="' + primValue + '"]')) }

        firstFilterValueField { Navigator window ->
            window.$(By.xpath('//input[contains(@name, "filterField")]' +
                    '/..//input[contains(@aria-owns, "boundlist")]/../../../../..' +
                    '//div[contains(@class, "FilterValue")]//div[contains(@class,"x-field")][1]//input')).last() }

        secondFilterValueField { window ->
            window.$(By.xpath('//input[contains(@name, "filterField")]/..' +
                    '//input[contains(@aria-owns, "boundlist")]/../../../../..' +
                    '//div[contains(@class, "FilterValue")]//div[contains(@class,"x-field")][2]//input')).last() }
    }
}