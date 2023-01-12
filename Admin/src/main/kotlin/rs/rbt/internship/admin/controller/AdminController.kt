package rs.rbt.internship.admin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.exception.CustomException
import rs.rbt.internship.admin.exception.CustomResponseEntity
import rs.rbt.internship.admin.service.AdminService

@RestController
@RequestMapping("/admin")
class AdminController {
    @Autowired
    lateinit var adminService: AdminService


    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile): ResponseEntity<CustomResponseEntity> {
        val employees: CustomResponseEntity = adminService.uploadEmployees(file)
        return ResponseEntity(employees,employees.statusCode)
    }

    @PostMapping("/upload/used-vacation")
    @ResponseBody
    fun uploadUsedVacation(@RequestParam("file") file: MultipartFile): ResponseEntity<CustomResponseEntity> {
        val usedVacations: CustomResponseEntity =
            adminService.uploadUsedVacations(file)
        return ResponseEntity(usedVacations, usedVacations.statusCode)

    }

    @PostMapping("/upload/vacations")
    fun uploadVacationDaysPerYear(@RequestParam("file") file: MutableList<MultipartFile>): ResponseEntity<CustomResponseEntity> {
        val vacationDaysPerYear: CustomResponseEntity = adminService.uploadVacationDaysPerYear(file)
        return ResponseEntity(vacationDaysPerYear,vacationDaysPerYear.statusCode)
    }

    @DeleteMapping("/deleteall")
    fun deleteAll() {
        adminService.deleteAll()
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<CustomResponseEntity> {
        val response = CustomResponseEntity(exception.status, exception.message, exception.objects)
        return ResponseEntity(response, exception.status)
    }

}