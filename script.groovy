def buildApp() {
    echo 'building the application...'
    def response = httpRequest "https://calendarific.com/api/v2/holidays?&api_key=758f54db8c52c2b500c928282fe83af1b1aa2be8&country=IN&year=2020"
    println('Status: '+response.status)
    println('Response: '+response.content)
} 

return this
