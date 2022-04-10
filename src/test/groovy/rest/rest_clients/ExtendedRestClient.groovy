package rest.rest_clients

import rest.RestClient
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

abstract class ExtendedRestClient extends RestClient {
    abstract String getLogin()

    abstract String getPassword()
    RESTClient client = null

    @Override
    RESTClient getRestClient() {
        if (client == null) {
            client = {
                RESTClient clientBuilder = new RESTClient(host)
                clientBuilder.httpClient.sslTrustAllCerts = true

                if (login && password) {
                    clientBuilder.authorization = new HTTPBasicAuthorization(login, password)
                }
                clientBuilder
            }()
        }
        return client
    }
}
