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
            daysWithOutWeekend=getDaysBetweenDateDifferentYear(dateStart,dateEnd)
            println("razliciti")
        }
        return daysWithOutWeekend
    }

    fun getDaysBetweenDateSameYear(dateStart: LocalDate, dateEnd: LocalDate): MutableMap<String, Int> {
        var dates: MutableList<LocalDate> = mutableListOf()
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()
        if (dateStart.year == dateEnd.year) {
            if (!dateStart.equals(dateEnd)) {
                dates = dateStart.datesUntil(dateEnd).collect(Collectors.toList())
                daysWithOutWeekend.set(dateStart.year.toString(), dates.count() + 1)
            } else {
                dates.add(dateStart)
                daysWithOutWeekend.set(dateStart.year.toString(), dates.count())

            }
        }
        else{
            daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, dates)

        }
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
        val NextYearDate: LocalDate = LocalDate.of(dateEnd.year, 1, 1)
        //Ova dva sluze da bi bio tacan broj dana, zato ako stavim od 1.1 a odmor je do 5.1 napisace samo 4
        val LastYearDate: LocalDate = LocalDate.of(dateStart.year, 12, 31)
        var daysWithOutWeekend: MutableMap<String, Int> = mutableMapOf()

        val datesLastYear: MutableList<LocalDate> = dateStart.datesUntil(NextYearDate).collect(Collectors.toList())
        val datesNextYear: MutableList<LocalDate> = dateStart.datesUntil(LastYearDate).collect(Collectors.toList())
        println(datesLastYear)
        println(datesNextYear)
        val mergedDates: MutableList<LocalDate> = mutableListOf()
        mergedDates.addAll(datesLastYear)
        mergedDates.addAll(datesNextYear)
        daysWithOutWeekend.set(dateStart.year.toString(), datesLastYear.count())
        daysWithOutWeekend.set(dateEnd.year.toString(), datesNextYear.count())

        daysWithOutWeekend = checkDaysWithoutWeekend(daysWithOutWeekend, mergedDates)

        return daysWithOutWeekend

    }

}