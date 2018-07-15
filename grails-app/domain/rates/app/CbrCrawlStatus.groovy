package rates.app

import org.grails.datastore.gorm.GormEntity

import java.sql.Timestamp
import java.time.LocalDate

class CbrCrawlStatus implements GormEntity<CbrCrawlStatus> {

    LocalDate date
    Timestamp lastCrawlDt

    static constraints = {
    }
}
