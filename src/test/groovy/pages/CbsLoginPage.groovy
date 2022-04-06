package pages

import geb.Page
import modules.CbsLoginPageModule

class CbsLoginPage extends Page {
    static url = "https://test.bocbs.cardpay-test.com/"

    static at = { title == "Log in to CBS" }

    static content = {
        loginButton { $(name: "login") }
        loginForm { module CbsLoginPageModule }
    }
}

