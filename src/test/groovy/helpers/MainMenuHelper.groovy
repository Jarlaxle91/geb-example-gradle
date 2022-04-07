package helpers

import geb.Browser
import geb.navigator.Navigator
import pages.MainPageCbs
import static org.assertj.core.api.Assertions.*;

class MainMenuHelper {

    static openMenuItem(String menuPath, Browser browser) {
        Browser.drive(browser, {
            at MainPageCbs
            List <String> pathElements = menuPath.split('>').collect { it.trim() }
            def clickItem
            clickItem = { scope, path ->
                if (scope.empty) {
                    return
                }

                Navigator item = scope.$('a', innerHTML: contains(path.head() + '<'))
                assertThat(!item.empty).isTrue()
                waitFor { item.isDisplayed() }
                sleep(100)
                item.click()

                if (path.size() > 1) {
                    item.attr('aria-owns')
                            .with { id -> waitFor { $("#$id" as String) } }
                            .with { clickItem(it, path.tail()) }
                }
            }
            clickItem(topPanel, pathElements)

        })
    }



}


