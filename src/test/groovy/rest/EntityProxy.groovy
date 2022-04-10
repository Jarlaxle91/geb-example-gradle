package rest

interface EntityProxy {
    String getType()
    List<Map> getRecords(Map filters)
    void delete(List ids)
    Map createRecord(Map data)
    Map editRecord(Map data)
}
