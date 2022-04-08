package helpers

import java.security.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateTimeUtil {
    static DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern('yyyyMMddHHmmss')

    static Long timestampToCustom(Long sqlTimestamp) {
        LocalDateTime.ofInstant(new Timestamp(sqlTimestamp).toInstant(), ZoneOffset.UTC)
                .format(customFormatter) as Long
    }

    static Long timestampToCustom(String sqlTimestamp) {
        timestampToCustom(sqlTimestamp as Long)
    }

    static String customTimeToIsoDateTime(String customTime) {
        LocalDateTime.parse(customTime, customFormatter).format(DateTimeFormatter.ISO_DATE_TIME)
    }

    static String IsoDateTimeToCustom(LocalDateTime date) {
        date.format(customFormatter)
    }

//    static String getServerDate() {
//        CbsEntity.cbsRest.getRequest('api/getSysdate', []).value
//    }

    static String getDateToday(String day) {
        String requiredDay = null
        if (day == 'Today(start)' || day == 'Today' || day.contains('Today ')) {
            requiredDay = getServerDate().substring(0, 8) + '000000'
        } else if (day == 'Today(end)') {
            requiredDay = getServerDate().substring(0, 8) + '235959'
        } else if (day == 'Today(simple date format)') {
            requiredDay = getServerDate().substring(0, 8) + '000000'
        }
        requiredDay
    }

    static String getDayInSimpleDateFormat(String dateInCustomFormat) {
        String day
        String month
        String year
        String dateInSimpleDateFormat = null
        if (dateInCustomFormat == 'Today') {
            day = getDateToday(dateInCustomFormat).substring(6, 8)
            month = getDateToday(dateInCustomFormat).substring(4, 6)
            year = getDateToday(dateInCustomFormat).substring(0, 4)
            dateInSimpleDateFormat = day + '.' + month + '.' + year
        } else if (dateInCustomFormat.contains('Today ')) {
            String calculatedDateInCustomFormat = findRequiredDay(dateInCustomFormat)
            day = calculatedDateInCustomFormat.substring(6, 8)
            month = calculatedDateInCustomFormat.substring(4, 6)
            year = calculatedDateInCustomFormat.substring(0, 4)
            dateInSimpleDateFormat = day + '.' + month + '.' + year
        }
        dateInSimpleDateFormat
    }

    static String caclucateOperdayForFilter(String date) {
        String dateInCustomFormat = findRequiredDay(date)
        String day, month, year, calculatedDay
        day = dateInCustomFormat.substring(6, 8)
        month = dateInCustomFormat.substring(4, 6)
        year = dateInCustomFormat.substring(0, 4)
        calculatedDay = year + '-' + month + '-' + day
    }

    static String getNameOperday() {
        LocalDateTime.now().toLocalDate() as String
    }

    static String calculateNameOperday(String name) {
        LocalDateTime requiredDate
        def amountDays = name.split(' ')[4] as Integer
        String calculatedNameOperday
        LocalDateTime dateInLocalDateTime
        if (name.contains('+')) {
            String dateToday = getDateToday(name.split('\\+')[0].substring(0, 25))
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.plusDays(amountDays)
            calculatedNameOperday = (requiredDate as String).substring(0, 10)
        } else {
            String dateToday = getDateToday(name.split('-')[0].substring(0, 25))
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.minusDays(amountDays)
            calculatedNameOperday = (requiredDate as String).substring(0, 10)
        }
        calculatedNameOperday
    }

    static String findRequiredDay(String date) {
        LocalDateTime requiredDate
        Integer amountDays = date.split(' ')[2] as Integer
        LocalDateTime dateInLocalDateTime
        if (date.contains('+')) {
            String dateToday = getDateToday((date.split('\\+')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.plusDays(amountDays)
        } else {
            String dateToday = getDateToday((date.split('-')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.minusDays(amountDays)
        }
        IsoDateTimeToCustom(requiredDate)
    }

    static String findRequiredMonth(String date) {
        LocalDateTime requiredDate
        Integer amountMonths = date.split(' ')[2] as Integer
        LocalDateTime dateInLocalDateTime
        if (date.contains('+')) {
            String dateToday = getDateToday((date.split('\\+')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.plusMonths(amountMonths)
        } else {
            String dateToday = getDateToday((date.split('-')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.minusMonths(amountMonths)
        }
        IsoDateTimeToCustom(requiredDate)
    }

    static String findDateForCard(String date) {
        def requiredDate
        Integer amountMonths = date.split(' ')[2] as Integer
        LocalDateTime dateInLocalDateTime
        if (date.contains('+')) {
            String dateToday = getDateToday((date.split('\\+')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.plusMonths(amountMonths).toString().substring(0, 7).split('-')[0] +
                    '/' + dateInLocalDateTime.plusMonths(amountMonths).toString().substring(0, 7).split('-')[1]
        } else {
            String dateToday = getDateToday((date.split('-')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.minusMonths(amountMonths).toString().substring(0, 7).split('-')[0] +
                    '/' + dateInLocalDateTime.minusMonths(amountMonths).toString().substring(0, 7).split('-')[1]
        }
        requiredDate
    }

    static String convertDateToFilter(String date) {
        LocalDateTime requiredDate
        Integer amountDays = date.split(' ')[2] as Integer
        LocalDateTime dateInLocalDateTime
        if (date.contains('+')) {
            String dateToday = getDateToday((date.split('\\+')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.plusDays(amountDays)
        } else {
            String dateToday = getDateToday((date.split('-')[0]).split(' ')[0])
            dateInLocalDateTime = LocalDateTime.parse(dateToday, customFormatter)
            requiredDate = dateInLocalDateTime.minusDays(amountDays)
        }
        IsoDateTimeToCustom(requiredDate)
    }
}
