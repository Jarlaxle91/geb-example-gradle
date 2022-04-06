package pages

import geb.Browser
import geb.Page
import geb.navigator.Navigator
import modules.MainPageCbsModule
import modules.SwiftCodeCreationWindow
import modules.SwiftCodesManualWindowModule

class MainPageCbs extends Page {

    static at = { waitFor { title == "CBS"} }

    static content = {
        mainMenu { module MainPageCbsModule }
        swiftCodesManualWindowModule { module SwiftCodesManualWindowModule }
        swiftCodeCreationWindow { module SwiftCodeCreationWindow }

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
    }













    void goToInstitutionsWindow() {
        Browser.drive(getBrowser(), {
            getBrowser().to(this)
            MainPageCbsModule.documents.click()
        })
    }
}