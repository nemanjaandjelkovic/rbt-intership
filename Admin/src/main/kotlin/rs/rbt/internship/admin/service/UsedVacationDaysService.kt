package rs.rbt.internship.admin.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors

@Service
class UsedVacationDaysService {
    fun getDaysBetweenDate(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        daysWithOutWeekend = if (dateStart.year == dateEnd.year) {
            getDaysBetweenDateSameYear(dateStart, dateEnd)
        } else {
            getDaysBetweenDateDifferentYear(dateStart, dateEnd)
        }
        return daysWithOutWeekend
    }

    fun getDaysBetweenDateSameYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var dates: MutableList<LocalDate> = mutableListOf()
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        if (dateStart != dateEnd) {
            dates = dateStart.datesUntil(dateEnd.plusDays(1)).collect(Collectors.toList())
            daysWithOutWeekend[dateStart.year.toString()] = dates.count()
        } else {
            dates.add(dateStart)
            daysWithOutWeekend[dateStart.year.toString()] = dates.count()
        }
        daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, dates)
        return daysWithOutWeekend
    }

    fun checkDaysWithoutWeekend(
        daysPerYear: MutableMap<String, Int>,
        dates: MutableList<LocalDate>
    ): MutableMap<String, Int> {
        dates.forEach {
            if (it.dayOfWeek.toString() == "SATURDAY" || it.dayOfWeek.toString() == "SUNDAY") {
                var days: Int = daysPerYear[it.year.toString()]!!
                daysPerYear.replace(it.year.toString(), --days)
            }
        }
        return daysPerYear
    }

    fun getDaysBetweenDateDifferentYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        val middleYear: LocalDate = LocalDate.of(dateEnd.year, 1, 1)
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()

        //Generisanje lista datuma
        val datesLastYear: MutableList<LocalDate> =
            dateStart.datesUntil(middleYear).collect(Collectors.toList())
        val datesNextYear: MutableList<LocalDate> =
            middleYear.datesUntil(dateEnd.plusDays(1)).collect(Collectors.toList())

        //Spajanje u jednu listu
        val mergedDates: MutableList<LocalDate> = mutableListOf()
        mergedDates.addAll(datesLastYear)
        mergedDates.addAll(datesNextYear)

        daysWithOutWeekend[dateStart.year.toString()] = datesLastYear.count()
        daysWithOutWeekend[dateEnd.year.toString()] = datesNextYear.count()

        daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, mergedDates)

        return daysWithOutWeekend
    }
}