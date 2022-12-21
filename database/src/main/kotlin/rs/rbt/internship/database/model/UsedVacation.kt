package rs.rbt.internship.database.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name="used_vacation")
class UsedVacation(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id:Long=0,
        @Column(name="date_start")
        var dateStart:LocalDate,
        @Column(name="date_end")
        var dateEnd:LocalDate,
        @ManyToOne
        @JoinColumn(name = "employee_id")
        var employee:Employee

) {

}
