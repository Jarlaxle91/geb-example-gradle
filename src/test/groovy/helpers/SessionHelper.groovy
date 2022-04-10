package helpers

import geb.Browser
import io.qameta.allure.Step
import pages.CbsLoginPage
import pages.MainPageCbs

class SessionHelper {

    @Step(value = "Fill in login with {0}")
    static void fillLoginField(String login, Browser browser) {
        Browser.drive(browser, {
            to CbsLoginPage
            loginForm.loginField.value(login)
        })
    }

    @Step(value = "Fill in password with {0}")
    static void fillPasswordField(String password, Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            loginForm.passwordField.value(password)
        })
    }

    @Step(value = "Fill login form with login {0} and password {1}")
    static void fillLoginForm(String login, String password, Browser browser) {
        Browser.drive(browser, {
            to CbsLoginPage
            loginForm.loginField.value(login)
            loginForm.passwordField.value(password)
        })
    }

    @Step(value = "Authorization in CBS")
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

    @Step(value = "Click login button")
    static void pressLoginButton(Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            loginButton.click(MainPageCbs)
        })
    }

    @Step(value = "Click login button without verification page")
    static void pressLoginButtonWithoutVerificationPage(Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            loginButton.click()
        })
    }

    @Step(value = "Error about invalid credentials is displayed")
    static void errorIsDisplayed(Browser browser) {
        Browser.drive(browser, {
            at CbsLoginPage
            !loginForm.error.isEmpty()
        })
    }
}
