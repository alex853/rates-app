package rates.app

class DailyRateController {
    static responseFormats = ['json']

    def list(String currency) {
        def query = DailyRate.where {
            currency == currency
        }

        respond query.list()
    }

    def listUsd() {
        respond list("USD")
    }

}
