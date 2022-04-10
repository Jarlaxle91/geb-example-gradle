package helpers

import io.qameta.allure.Step
import rest.Api

class ApiHelper {

    @Step (value="Проверка существования записи" )
    static getExistingSwiftCodeManual(String swiftCode) {


        Map data = [property: "code", operator: "LIKE", value: "BNS123ZASXX"]

        def proxy = Api.getProxyByType("SWIFT").getRecords(data)


    }

    static class ApiFilter {
        String property
        String operator
        String value
    }
}
