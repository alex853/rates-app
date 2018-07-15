package rates.app

import java.time.LocalDate

class DailyRatesService {

    CbrCrawlingService cbrCrawlingService

    def lastMonth() {
        cbrCrawlingService.crawl()

        def usdData = DailyRate.where {
            currency == "USD"
            // todo limit 30 days
        }.order("date", "desc").list()

        def eurData = DailyRate.where {
            currency == "EUR"
            // todo limit 30 days
        }.order("date", "desc").list()

        def usdIndex = 0
        def eurIndex = 0

        def sets = []
        LocalDate curr = LocalDate.now();
        for (int i = 0; i < 31; i++) {
            DailyRateSet set = new DailyRateSet(date: curr)

            while (usdIndex < usdData.size()) {
                def usdRate = usdData.get(usdIndex)
                if (usdRate.date.equals(curr)) {
                    set.usdRate = usdRate.rate
                    break
                } else if (usdRate.date.isAfter(curr)) {
                    usdIndex += 1
                } else { // usdRate.rate is before curr
                    break
                }
            }

            while (eurIndex < eurData.size()) {
                def eurRate = eurData.get(eurIndex)
                if (eurRate.date.equals(curr)) {
                    set.eurRate = eurRate.rate
                    break
                } else if (eurRate.date.isAfter(curr)) {
                    eurIndex += 1
                } else { // eurRate.rate is before curr
                    break
                }
            }

            sets.add(set)
            curr = curr.minusDays(1)
        }

        sets
    }
}
