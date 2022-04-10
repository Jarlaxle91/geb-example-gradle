package rest.rest_clients

@Singleton
class SepaRestClient extends ExtendedRestClient {
    String host = System.getProperty('sepa.host')
    String login = System.getProperty('sepa.login')
    String password = System.getProperty('sepa.password')
}
