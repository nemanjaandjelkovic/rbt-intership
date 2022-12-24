package rs.rbt.internship.employee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["rs.rbt.internship.employee","rs.rbt.internship.database"]
)
@EnableJpaRepositories(
    "rs.rbt.internship.database.repository"
)
@EntityScan(
    "rs.rbt.internship.database.model"
)
class EmployeeApplication

fun main(args: Array<String>) {
    runApplication<EmployeeApplication>(*args)
}
