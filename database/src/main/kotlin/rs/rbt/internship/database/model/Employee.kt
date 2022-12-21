package rs.rbt.internship.database.model

import jakarta.persistence.*
import org.hibernate.mapping.Join

@Entity
@Table(name="employee")
data class Employee(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        var id:Long=0,
        @Column(name="email")
        var email:String="",
        @Column(name="password")
        var password:String="",
        @OneToMany(mappedBy = "employee")
        var usedVacationList: MutableList<UsedVacation> = mutableListOf(),
        @Column(name="vacation_day_per_year")
        @MapKey(name="year")
        @ElementCollection
        @CollectionTable(name="vacation_day_per_year", joinColumns = [JoinColumn(name="employee_id")])
        var vacationDayPerYear: MutableMap<String,Int> = mutableMapOf()
) {
}