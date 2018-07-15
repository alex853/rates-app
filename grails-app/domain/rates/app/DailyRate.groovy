package rates.app

import org.grails.datastore.gorm.GormEntity

import java.time.LocalDate

class DailyRate implements GormEntity<DailyRate> {

    String currency
    LocalDate date
    Double rate

    static constraints = {
        // todo
        currency inList: ["EUR", "USD"]
        date blank: false
        rate min: 0.0D
    }
}
