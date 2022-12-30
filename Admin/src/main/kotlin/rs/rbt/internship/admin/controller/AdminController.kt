package rs.rbt.internship.admin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import rs.rbt.internship.admin.service.AdminService

@RestController
@RequestMapping("/admin")
class AdminController(){
    @Autowired
    lateinit var adminService: AdminService


    @PostMapping("/upload/employee")
    fun uploadEmployee(@RequestParam("file") file: MultipartFile) {
        adminService.uploadEmployees(file)
    }

    @PostMapping("/upload/used-vacation")
    @ResponseBody
    fun uploadUsedVacation(@RequestParam("file") file: MultipartFile) {
         adminService.uploadUsedVacations(file)
    }

    @PostMapping("/upload/vacations")
    fun uploadVacationDaysPerYear(@RequestParam("file") file: MutableList<MultipartFile>) {
       adminService.uploadVacationDaysPerYear(file)
    }

    @DeleteMapping("/deleteall")
    fun deleteAll() {
        adminService.deleteAll()
    }

}