package com.example.Student_Library_Management_System.Controllers;

import com.example.Student_Library_Management_System.DTOs.StudentUpdateMobRequestDto;
import com.example.Student_Library_Management_System.Models.Student;
import com.example.Student_Library_Management_System.Repositories.StudentRepository;
import com.example.Student_Library_Management_System.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public String createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @GetMapping("/get_user")
    public String findNameByEmail(@RequestParam("email") String email){
        return studentService.findNameByEmail(email);
    }

    @PutMapping("/update_mob")
    public String updateMobNo(@RequestBody StudentUpdateMobRequestDto studentUpdateMobRequestDto){
        return studentService.updateMobNo(studentUpdateMobRequestDto);
    }

    @GetMapping("/get_students")
    public List<String> findByCountry(@RequestParam("country") String country){
        return studentService.findByCountry(country);
    }

}
