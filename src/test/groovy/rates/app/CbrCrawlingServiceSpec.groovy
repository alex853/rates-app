package rates.app

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

import java.time.LocalDate

class CbrCrawlingServiceSpec extends HibernateSpec implements ServiceUnitTest<CbrCrawlingService> {

    def setup() {
    }

    def cleanup() {
    }

    void "crawlDate"() {
        when: "crawl today"
        def DailyRateSet set = service.crawlDate(LocalDate.now())

        then:
        set.usdRate > 0
        set.eurRate > 0
    }

    void "convertDouble"() {
        when: "double with dot"
        def double result = service.convertDouble("123.4567")

        then: "value converted correctly"
        result == 123.4567D

        when: "double with comma"
        result = service.convertDouble("321,4567")

        then: "value converted correctly"
        result == 321.4567D
    }
}
