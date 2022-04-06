package helpers;

import geb.Browser;
import geb.navigator.Navigator;
import pages.MainPageCbs;

public class InputsHelper {

    static checkInputName(String inputName, Navigator window, Browser browser) {

        Browser.drive(browser, {
            at MainPageCbs
            def namesOfTextArea = ['Description', 'Other', 'License description', 'Comment', 'Residence address', 'Content']
            def actualInput = null
            if (window._args[0] == 'Add Incoming transaction' || window._args[0] == "Edit Incoming transaction") {
                namesOfTextArea << ['Notes']
            }
            namesOfTextArea.each {
                if (inputName.contains(it)) {
                    actualInput = textAreaByName(window, inputName)
                }

                if (actualInput == null) {
                    actualInput = inputByName(window, inputName)
                }
            }
            actualInput
        })
    }

    static checkInputField(String inputName, String valueField, Navigator currentField, Browser browser) {
        boolean needCurrentValue = true
        if (needCurrentValue) {
            NavigationHelper.setInputField(currentField, valueField, browser)
        }


    }
}
