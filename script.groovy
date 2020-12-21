def buildApp() {
    echo 'building the application...'
    
    RESTClient client = new RESTClient("https://calendarific.com/api/v2/holidays?&api_key=758f54db8c52c2b500c928282fe83af1b1aa2be8&country=IN&year=2020")
    def response
    try {
        response = client.get()
        assert response.statusCode = 200
        
        println('Response: '+response.content)
    } catch (RESTClientException e) {
        assert e?.response?.statusCode != 200
    }
    
    def response = httpRequest "https://calendarific.com/api/v2/holidays?&api_key=758f54db8c52c2b500c928282fe83af1b1aa2be8&country=IN&year=2020"
    println('Status: '+response.status)
    println('Response: '+response.content)
} 

return this
