package rest.rest_clients

@Singleton
class CbsRestClient extends ExtendedRestClient {
    String host = "https://test-sso.bocbs.cardpay-test.com"
    String login = "cbs-admin"
    String password = "123_Qwerty"
}