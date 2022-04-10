package rest

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

abstract class TokenRestClient extends RestClient {

    @Override
    RESTClient getRestClient() {
        RESTClient client = {
            RESTClient clientBuilder = new RESTClient(host)
            clientBuilder.httpClient.sslTrustAllCerts = true
            clientBuilder
        }()

        return client
    }


    def getToken() {
        def access_token = getToken("auth/realms/cbs/protocol/openid-connect/token",
                [grant_type: "client_credentials", scope     : "openId", client_id: "dev-cbs-backend",
                 client_secret: "d4be0ca9-2ea5-4144-be8a-b6fa1a7a2562"])
    }

    def getToken(path, body) {
        getRestClient().post(path: path) {
            type ContentType.URLENC
            urlenc body
        }.parsedResponseContent.json.access_token
    }
}
