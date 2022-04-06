import org.openqa.selenium.chrome.ChromeDriver

waiting {
    timeout = 2
}

environments {
    driver = { new ChromeDriver() }

//    chrome {
//        driver = { new ChromeDriver() }
//    }

//    chromeHeadless {
//        driver = {
//            ChromeOptions o = new ChromeOptions()
//            o.addArguments('headless')
//            new ChromeDriver(o)
//        }
//    }
}
reportsDir = new File("target/runtime_reports_dir")
baseUrl = "https://test.bocbs.cardpay-test.com/"

