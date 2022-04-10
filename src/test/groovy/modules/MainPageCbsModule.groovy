package modules

import geb.Module

class MainPageCbsModule extends Module{
    static content = {
        topToolBar { $(id: "topToolbar-innerCt") }
        mainPageBody { $("body.badBrowsers") }



    }
}
