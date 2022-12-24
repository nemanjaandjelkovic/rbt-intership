package rs.rbt.internship.admin.service

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors

@Service
class AdminService {

    fun getDaysBetweenDate(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        var days: Int = 0
        if (dateStart.year == dateEnd.year) {
            daysWithOutWeekend = getDaysBetweenDateSameYear(dateStart, dateEnd)
        } else {
            daysWithOutWeekend = getDaysBetweenDateDifferentYear(dateStart, dateEnd)
            println("razliciti")
        }
        return daysWithOutWeekend
    }

    fun getDaysBetweenDateSameYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var dates: MutableList<LocalDate> = mutableListOf()
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        if (!dateStart.equals(dateEnd)) {
            dates = dateStart.datesUntil(dateEnd.plusDays(1)).collect(Collectors.toList())
            println(dates)
            daysWithOutWeekend.set(dateStart.year.toString(), dates.count())
        } else {
            dates.add(dateStart)
            daysWithOutWeekend.set(dateStart.year.toString(), dates.count())
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
                var days: Int = daysPerYear.get(it.year.toString())!!
                daysPerYear.replace(it.year.toString(), --days)
            }
        }
        return daysPerYear
    }

    fun getDaysBetweenDateDifferentYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        val middleYear: LocalDate = LocalDate.of(dateEnd.year, 1, 1)
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()

        //Generisanje lista datuma
        val datesLastYear: MutableList<LocalDate> = dateStart.datesUntil(middleYear).collect(Collectors.toList())
        val datesNextYear: MutableList<LocalDate> = middleYear.datesUntil(dateEnd.plusDays(1)).collect(Collectors.toList())

        //Spajanje u jednu listu
        val mergedDates: MutableList<LocalDate> = mutableListOf()
        mergedDates.addAll(datesLastYear)
        mergedDates.addAll(datesNextYear)

        daysWithOutWeekend.set(dateStart.year.toString(), datesLastYear.count())
        daysWithOutWeekend.set(dateEnd.year.toString(), datesNextYear.count())

        daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, mergedDates)

        return daysWithOutWeekend

    }

}