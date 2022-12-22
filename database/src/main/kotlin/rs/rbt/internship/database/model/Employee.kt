package rs.rbt.internship.database.model

import jakarta.persistence.*

@Entity
@Table(name = "employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    var id: Long = 0,
    @Column(name = "email")
    var email: String = "",
    @Column(name = "password")
    var password: String = "",
    @OneToMany(mappedBy = "employee")
    var usedVacationList: MutableList<UsedVacation> = mutableListOf(),
    @Column(name = "vacation_day_per_year")
    @ElementCollection
    var vacationDayPerYear: MutableMap<String, Int> = mutableMapOf()
)