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
    @OneToMany(mappedBy = "employee",cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    var usedVacationList: MutableList<UsedVacation> = mutableListOf(),
    @OneToMany(mappedBy = "employee",cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE], orphanRemoval = true)
    var vacationDayPerYear: MutableList<VacationDayPerYear> = mutableListOf()
)