package helpers

class Store {
    static vars = new HashMap<String, Object>()

    def static setVar(key, value) {
        vars.put(key, value)
    }

    static def getVar(path) {
        { keys, values ->
            keys.size() == 1 ?
                    values.get(keys.head(), keys.head()) :
                    call(keys.tail(), values.get(keys.head()))
        }(path.split(" > "), vars)
    }
}
