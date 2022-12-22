package rs.rbt.internship.database.service

import org.springframework.stereotype.Service
import rs.rbt.internship.database.model.UsedVacation
import rs.rbt.internship.database.repository.UsedVacationRepository

@Service
class UsedVacationService(var usedVacationRepository: UsedVacationRepository) {

    fun saveUsedVacations(usedVacation: MutableList<UsedVacation>) {
        usedVacationRepository.saveAll(usedVacation)
    }
}