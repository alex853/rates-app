package rates.app

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainController {
    DailyRatesService dailyRatesService
    CbrCrawlingService cbrCrawlingService
    ExecutorService executor = Executors.newSingleThreadExecutor()

    def index() {
        executor.execute {
            CbrCrawlStatus.withNewSession {session ->
                cbrCrawlingService.crawl()
            }
        }

        def sets = dailyRatesService.lastMonth()

        def double usdMinRate = Integer.MAX_VALUE
        def double usdMaxRate = Integer.MIN_VALUE

        def double eurMinRate = Integer.MAX_VALUE
        def double eurMaxRate = Integer.MIN_VALUE

        for (DailyRateSet set : sets) {
            if (set.usdRate != null) {
                usdMinRate = Math.min(usdMinRate, set.usdRate)
                usdMaxRate = Math.max(usdMaxRate, set.usdRate)
            }
            if (set.eurRate != null) {
                eurMinRate = Math.min(eurMinRate, set.eurRate)
                eurMaxRate = Math.max(eurMaxRate, set.eurRate)
            }
        }

        respond(dailyRateSets: sets, usdMinRate: usdMinRate, usdMaxRate: usdMaxRate, eurMinRate: eurMinRate, eurMaxRate: eurMaxRate)
    }
}
