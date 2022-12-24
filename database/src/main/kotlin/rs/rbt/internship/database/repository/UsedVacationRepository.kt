package rs.rbt.internship.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import java.time.LocalDate

@Repository
interface UsedVacationRepository:JpaRepository<UsedVacation,Long> {
    override fun <S : UsedVacation?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    fun findAllByDateStartGreaterThanEqualAndDateEndLessThanEqualAndEmployeeLike(dateStart: LocalDate, dateEnd:LocalDate,employee:Employee):MutableList<UsedVacation>

    fun findAllByDateStartGreaterThanEqualAndDateEndLessThanEqual(dateStart: LocalDate, dateEnd:LocalDate):MutableList<UsedVacation>



}