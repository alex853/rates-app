package rates.app

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainController {
    DailyRatesService dailyRatesService
    CbrCrawlingService cbrCrawlingService
    ExecutorService executor = Executors.newSingleThreadExecutor()

    def index() {
        executor.execute { // todo how to not create multiple tasks?
            CbrCrawlStatus.withNewSession {session ->
                cbrCrawlingService.crawl()
            }
        }

        def sets = dailyRatesService.lastMonth()

        respond(dailyRateSets: sets)
    }
}
