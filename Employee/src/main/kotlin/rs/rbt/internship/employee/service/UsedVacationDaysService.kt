package rs.rbt.internship.employee.service

import java.time.LocalDate
import java.util.stream.Collectors

class UsedVacationDaysService {
    fun getDaysBetweenDate(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        var days: Int = 0
        daysWithOutWeekend = if (dateStart.year == dateEnd.year) {
            getDaysBetweenDateSameYear(dateStart, dateEnd)
        } else {
            getDaysBetweenDateDifferentYear(dateStart, dateEnd)
        }
        return daysWithOutWeekend
    }

    private fun getDaysBetweenDateSameYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var dates: MutableList<LocalDate> = mutableListOf()
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        if (!dateStart.equals(dateEnd)) {
            dates = dateStart.datesUntil(dateEnd.plusDays(1)).collect(Collectors.toList())
           // println(dates)
            daysWithOutWeekend.set(dateStart.year.toString(), dates.count())
        } else {
            dates.add(dateStart)
           // println(dates)
            daysWithOutWeekend.set(dateStart.year.toString(), dates.count())
        }
        println(dates)
        daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, dates)
        // println(daysWithOutWeekend)
        return daysWithOutWeekend
    }

   private fun checkDaysWithoutWeekend(
        daysPerYear: MutableMap<String, Int>,
        dates: MutableList<LocalDate>
    ): MutableMap<String, Int> {
        dates.forEach {
            if (it.dayOfWeek.toString() == "SATURDAY" || it.dayOfWeek.toString() == "SUNDAY") {
                var days: Int = daysPerYear.get(it.year.toString())!!
                println(days);
                daysPerYear.replace(it.year.toString(), --days)
            }
           // println(daysPerYear)
        }
        return daysPerYear
    }

    private fun getDaysBetweenDateDifferentYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
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
        println(mergedDates)

        return daysWithOutWeekend

    }
}