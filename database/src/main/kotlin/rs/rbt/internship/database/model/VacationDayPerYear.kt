package rs.rbt.internship.database.model

import jakarta.persistence.*

@Entity
@Table(name = "vacation_day_per_year")
data class VacationDayPerYear(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long=0,
    @Column(name = "year")
    var year: String="",
    @Column(name = "days")
    var day: Int=0,
    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee=Employee()
)



