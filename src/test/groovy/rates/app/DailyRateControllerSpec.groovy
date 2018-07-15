package rates.app

import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest

class DailyRateControllerSpec extends HibernateSpec implements ControllerUnitTest<DailyRateController> {

    static doWithSpring = {
        jsonSmartViewResolver(JsonViewResolver)
    }

    def setup() {
        DailyRate.saveAll(
                new DailyRate(currency: "USD", date: new Date(2018-1900, 0, 1), rate: 60),
                new DailyRate(currency: "USD", date: new Date(2018-1900, 0, 10), rate: 65),
                new DailyRate(currency: "USD", date: new Date(2018-1900, 0, 20), rate: 63),

                new DailyRate(currency: "EUR", date: new Date(2018-1900, 0, 1), rate: 70),
                new DailyRate(currency: "EUR", date: new Date(2018-1900, 0, 10), rate: 72)
        )
    }

    def cleanup() {
    }

    void "test list of usd rates"() {
        when: "list of usd rates called"
        controller.list("USD")

        then: "3 daily rate records found"
        response.json.size() == 3
    }
}
