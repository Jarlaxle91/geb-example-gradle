package rest.rest_clients

import rest.TokenRestClient

@Singleton
class SsoRestClient extends TokenRestClient {
    String host = System.getProperty('sso.host')
}
