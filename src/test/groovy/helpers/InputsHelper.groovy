package helpers;

import geb.Browser;
import geb.navigator.Navigator
import org.openqa.selenium.By;
import pages.MainPageCbs;

public class InputsHelper {

    static Navigator checkInputName(String inputName, Navigator window, Browser browser) {
    Navigator result = null
        Browser.drive(browser, {
            at MainPageCbs
            ArrayList<String> namesOfTextArea = ['Description', 'Other', 'License description', 'Comment', 'Residence address', 'Content']
            Navigator actualInput = null
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
            result = actualInput
        })
        return result
    }

    static setInputField(Navigator selector, String value, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            selector.value(value)
        })
    }

    static checkInputField(String inputName, String valueField, Navigator currentField, Browser browser) {
        boolean needCurrentValue = true
        if (needCurrentValue) {
            setInputField(currentField, valueField, browser)
        }
    }

    static void selectOption(Navigator selector, String value, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            selector.find(".x-form-trigger").click()
            def ariaOwns = selector.find("input").first().attr("aria-owns")
            waitFor { $("body.x-body").find("div.x-mask", "aria-hidden": "false").isEmpty() }
            if (selector.text().contains('R Account') || selector.text().contains('IBAN')) {
                assert !$(By.xpath("//*[@id='${ariaOwns}']//*[contains(text(), '$value')]")).isEmpty()
                def toSelect = $(By.xpath("//*[@id='${ariaOwns}']//*[contains(text(), '$value')]")).last()
                toSelect.click()
            } else {
                assert !$(By.xpath("//*[@id='${ariaOwns}']//*[text()='$value']")).isEmpty()
                def toSelect = $(By.xpath("//*[@id='${ariaOwns}']//*[text()='$value']")).last()
                toSelect.click()
            }
            if (!$('ul.x-list-plain', 'aria-hidden': 'false').isEmpty()) {
                selector.find(".x-form-trigger").click()
            }
        })
    }
    }
