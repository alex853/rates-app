package rates.app

class MainController {
    DailyRatesService dailyRatesService

    def index() {
        def sets = dailyRatesService.lastMonth()

        respond(dailyRateSets: sets)
    }
}
