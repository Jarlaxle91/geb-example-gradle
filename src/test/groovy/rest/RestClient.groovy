package rest

import rest.rest_clients.SsoRestClient
import wslite.rest.RESTClient

abstract class RestClient {
    abstract String getHost()
    static SsoRestClient ssoRest = SsoRestClient.instance

    abstract RESTClient getRestClient()

    def getRequest(path, query) {
        getRestClient().get(path: path, query: query)
                .parsedResponseContent.json
    }

    def postRequest(path, body) {
        getRestClient().post(path: path) {
            type ContentType.JSON
            json body
        }.parsedResponseContent.json
    }

    def postMultipartRequest(path, file, fileType, fileName) {
        getRestClient().post(path: path) {
            multipart 'file', file.bytes, fileType, fileName
        }.parsedResponseContent.json
    }

    def putRequest(path, body) {
        getRestClient().put(path: path) {
            type ContentType.JSON
            json body
        }.parsedResponseContent.json
    }

    def deleteRequest(path, query) {
        getRestClient().delete(path: path, query: query)
                .parsedResponseContent.json
    }

    def getRequestWithToken(path, query) {
        def auth = [Authorization: "Bearer ${ssoRest.getToken()}"]
        getRestClient().get(path: path, query: query, headers: auth)
                .parsedResponseContent.json
    }

    def postRequestWithToken(path, body) {
        def auth = [Authorization: "Bearer ${ssoRest.getToken()}"]
        getRestClient().post(path: path, headers: auth) {
            type ContentType.JSON
            json body
        }.parsedResponseContent.json
    }
}