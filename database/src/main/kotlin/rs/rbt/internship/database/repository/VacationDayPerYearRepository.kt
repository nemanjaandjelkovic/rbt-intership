package rs.rbt.internship.database.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import rs.rbt.internship.database.model.VacationDayPerYear

@Repository
interface VacationDayPerYearRepository : JpaRepository<VacationDayPerYear, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
        "UPDATE vacation_day_per_year SET days= days-:days WHERE employee_id= :employee_id AND year= :year",
        nativeQuery = true
    )
    fun updateVacationDayPerYear(
        @Param("days") days: Int,
        @Param("year") year: String,
        @Param("employee_id") employeeId: Long
    )

    fun findByYearEqualsAndEmployeeIdEquals(year:String, employeeId:Long):VacationDayPerYear

    fun findAllByEmployeeIdEquals(employeeId:Long):MutableList<VacationDayPerYear>

    fun existsByYearAndEmployeeId(year:String, employeeId:Long):Boolean
}