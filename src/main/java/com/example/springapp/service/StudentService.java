package com.example.springapp.service;

import com.example.springapp.entity.Student;
import com.example.springapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void deleteStudent(long studentId){
         studentRepository.deleteById(studentId);
    }


}
