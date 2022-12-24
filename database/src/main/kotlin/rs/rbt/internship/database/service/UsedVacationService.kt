package rs.rbt.internship.database.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.Employee
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.repository.UsedVacationRepository
import java.time.LocalDate

@Service
class UsedVacationService() {

    @Autowired
    lateinit var usedVacationRepository: UsedVacationRepository

    fun saveUsedVacations(usedVacation: MutableList<UsedVacation>) {
        usedVacationRepository.saveAll(usedVacation)
    }

    fun dates(dateStart:LocalDate,dateEnd:LocalDate,employee:Employee):MutableList<UsedVacation>
    {
        return usedVacationRepository.findAllByDateStartGreaterThanEqualAndDateEndLessThanEqualAndEmployeeLike(dateStart,dateEnd,employee)
    }
    fun dates2(dateStart:LocalDate,dateEnd:LocalDate,employee:Employee):MutableList<UsedVacation>
    {
        return usedVacationRepository.findAllByDateStartGreaterThanEqualAndDateEndLessThanEqual(dateStart,dateEnd)
    }
}