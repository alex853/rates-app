package rates.app

class MainController {

    def index() {
        DailyRateSet day1 = new DailyRateSet(date: new Date(), usdRate: 60.0, eurRate: 70)
        DailyRateSet day2 = new DailyRateSet(date: new Date().minus(1), usdRate: 61, eurRate: 71)
        DailyRateSet day3 = new DailyRateSet(date: new Date().minus(2), usdRate: 59, eurRate: 79)

        respond(dailyRateSets: [day1, day2, day3], usd: 60, eur: 70)
    }
}
