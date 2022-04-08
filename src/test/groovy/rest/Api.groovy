package rest

import rest.proxy.SwiftProxy

class Api {
    static final List<EntityProxy> proxies = [
            SwiftProxy.getInstance()
    ]

    static EntityProxy getProxyByType(String type) {
        proxies.find { it.getType() == type }
    }
}
