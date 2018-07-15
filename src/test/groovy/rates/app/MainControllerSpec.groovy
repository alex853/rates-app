package rates.app

import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest

import java.time.LocalDate

class MainControllerSpec extends HibernateSpec implements ControllerUnitTest<MainController> {

    def setup() {
        LocalDate today = LocalDate.now()

        DailyRate.saveAll(
                new DailyRate(currency: "USD", date: today.minusDays(1), rate: 60),
                new DailyRate(currency: "USD", date: today.minusDays(3), rate: 63),
                new DailyRate(currency: "USD", date: today.minusDays(2), rate: 65),

                new DailyRate(currency: "EUR", date: today.minusDays(0), rate: 70),
                new DailyRate(currency: "EUR", date: today.minusDays(1), rate: 72)
        )

        sessionFactory.currentSession.getTransaction().commit()
    }

    def cleanup() {
    }

    void "index"() { //todo it does not work when response is not set to json O_o
        when: "index is called"
        controller.index()

        then: "list of daily rate sets is correct"
        def result = response.json.getAt("dailyRateSets")

        def row0 = result.opt(0)
        row0.usdRate == null
        row0.eurRate == 70

        def row1 = result.opt(1)
        row1.usdRate == 60
        row1.eurRate == 72

        def row2 = result.opt(2)
        row2.usdRate == 65
        row2.eurRate == null

        def row3 = result.opt(3)
        row3.usdRate == 63
        row3.eurRate == null

        def row4 = result.opt(4)
        row4.usdRate == null
        row4.eurRate == null
    }
}
