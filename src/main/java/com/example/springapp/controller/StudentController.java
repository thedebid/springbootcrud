package com.example.springapp.controller;

import com.example.springapp.entity.Student;
import com.example.springapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return  new ResponseEntity<Student>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }
}
