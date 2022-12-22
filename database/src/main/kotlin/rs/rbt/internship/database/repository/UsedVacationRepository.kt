package rs.rbt.internship.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.rbt.internship.database.model.UsedVacation

@Repository
interface UsedVacationRepository:JpaRepository<UsedVacation,Long> {
    override fun <S : UsedVacation?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }
}