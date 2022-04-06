package helpers

import geb.Browser
import pages.CbsLoginPage
import pages.MainPageCbs

class SessionHelper {

    static void fillLoginField(String login, Browser browser) {
        Browser.drive(browser, {
            to CbsLoginPage
            loginForm.loginField.value(login)
        })
    }

    static void fillPasswordField(String password, Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            loginForm.passwordField.value(password)
        })
    }

    static void fillLoginForm(String login, String password, Browser browser) {
        Browser.drive(browser, {
            to CbsLoginPage
            loginForm.loginField.value(login)
            loginForm.passwordField.value(password)
        })
    }

    static void authorizeInCbs(String login, String password, Browser browser) {
        Browser.drive(browser, {
            to CbsLoginPage
            loginForm.loginField.value(login)
            loginForm.passwordField.value(password)
            loginButton.click(MainPageCbs)
            at MainPageCbs
            waitFor(60) { mainMenu.topToolBar }
        })
    }

    static void pressLoginButton(Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            loginButton.click(MainPageCbs)
        })
    }
}
