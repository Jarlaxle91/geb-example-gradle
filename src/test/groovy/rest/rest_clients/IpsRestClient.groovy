package rest.rest_clients

@Singleton
class IpsRestClient extends ExtendedRestClient {
    String host = "https://test-ips.bocbs.cardpay-test.com"
    String login = "cbs-admin"
    String password = "123_Qwerty"
}
