package rest.proxy

import rest.EntityProxy
import rest.rest_clients.IpsRestClient

@Singleton
class SwiftProxy implements EntityProxy {
    static IpsRestClient ipsRest = IpsRestClient.instance

    @Override
    String getType() {
        'SWIFT'
    }

    @Override
    List<Map> getRecords(Map filter) {
        ipsRest.getRequestWithToken('api/dictionary/swift-codes/manual', filter).list
    }

    @Override
    void delete(List ids) {
        ipsRest.postRequestWithToken('api/dictionary/swift-codes/delete', ids)
    }

    @Override
    Map createRecord(Map data) {
        List swiftCodes = getRecords([FilterComposer.singleFilter('code', 'LIKE', data.code)])

        if (!swiftCodes) {
            if (data.enabled == null) {
                data << ['enabled': "true"]
            }
            data << ['id': 0]
            ipsRest.postRequestWithToken('api/dictionary/swift-codes/manual/save', [data]) as Map
            getRecords([FilterComposer.singleFilter('code', 'LIKE', data.code)]).list.first()
        } else {
            throw new RuntimeException("${getType()} you're trying to create already exists")
        }
    }

    @Override
    Map editRecord(Map data) {

    }
}
