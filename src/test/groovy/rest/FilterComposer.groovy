package rest

import groovy.json.JsonOutput
class FilterComposer {
    static Map singleFilter(String property, String operator, Object value) {
        [[property: property, operator: operator, value: value]]
                .with(JsonOutput.&toJson)
                .with { [filter: it] }
    }

    static Map filters(List<Map> rawFilters) {
        rawFilters.with(JsonOutput.&toJson)
                .with { [ filter: it ] }
    }
}
