package rates.app

import grails.gorm.transactions.Transactional
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CbrCrawlingService {

    def crawl() {
        LocalDate curr = LocalDate.now();
        for (int i = 0; i < 31; i++) {
            def crawlStatusList = CbrCrawlStatus.where {
                date == curr
            }.list()

            def CbrCrawlStatus crawlStatus = !crawlStatusList.empty ? crawlStatusList.get(0) : null
            def needToCrawl = (crawlStatus == null) || (crawlStatus.date == curr && crawlStatus.lastCrawlDt.getTime() + 3600000 < System.currentTimeMillis())

            if (needToCrawl) {
                def DailyRateSet set = crawlDate(curr)
                saveRates(curr, crawlStatus, set)
            }

            curr = curr.minusDays(1)
        }
    }

    def DateTimeFormatter urlDateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")

    def crawlDate(LocalDate date) {
        log.info("Crawling CBR data for " + date)

        def url = "https://www.cbr.ru/currency_base/daily/?date_req=" + urlDateFormatter.format(date)
        def content = new URL(url).getText()

        def Document doc = Jsoup.parse(content)

        def Element dataTable = doc.select("table.data").get(0)
        def Elements rows = dataTable.selectFirst("tbody").select("tr")

        def DailyRateSet set = new DailyRateSet(date: date)
        for (int i = 0; i < rows.size(); i++) {
            def Element row = rows.get(i)
            def cells = row.select("td")
            if (cells.isEmpty())
                continue

            def Element currencyCode = cells.get(1)
            def Element currencyRate = cells.get(4)

            if (currencyCode.text() == "USD") {
                set.usdRate = convertDouble(currencyRate.text())
            } else if (currencyCode.text() == "EUR") {
                set.eurRate = convertDouble(currencyRate.text())
            }
        }

        set
    }

    def double convertDouble(String s) {
        def DecimalFormat df = new DecimalFormat("#0.0000")
        def DecimalFormatSymbols dfs = df.getDecimalFormatSymbols()
        dfs.setDecimalSeparator('.' as char)
        df.setDecimalFormatSymbols(dfs)
        s = s.replace(',', '.')
        df.parse(s)
    }

    @Transactional
    def saveRates(LocalDate date, CbrCrawlStatus cbrCrawlStatus, DailyRateSet dailyRateSet) {
        def DailyRate usdRate = getOrCreateDailyRate("USD", date)
        def DailyRate eurRate = getOrCreateDailyRate("EUR", date)

        usdRate.rate = dailyRateSet.usdRate
        eurRate.rate = dailyRateSet.eurRate

        usdRate.save(flush: true)
        eurRate.save(flush: true)

        if (cbrCrawlStatus == null) {
            cbrCrawlStatus = new CbrCrawlStatus(date: date)
        }
        cbrCrawlStatus.lastCrawlDt = new Timestamp(System.currentTimeMillis())
        cbrCrawlStatus.save(flush: true)
    }

    private DailyRate getOrCreateDailyRate(String currency, LocalDate date) {
        def list = DailyRate.where { currency == currency && date == date }.list()
        def rate = !list.empty ? list.get(0) : null
        if (rate == null) {
            rate = new DailyRate(date: date, currency: currency)
        }
        rate
    }
}
