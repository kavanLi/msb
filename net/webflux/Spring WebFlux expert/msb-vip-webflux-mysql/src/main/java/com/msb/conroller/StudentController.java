package com.msb.conroller;

import com.msb.bean.Student;
import com.msb.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository studentRepository;
    public StudentController (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public Flux<Student> index(){
        return studentRepository.findAll();
    }
    @RequestMapping("/save")
    public Mono<Student> save(){
        Student student = new Student();
        student.setCode("23");
        student.setName("tiany");
        student.setActive(false);
        student.setAddress("sdf");
        student.setBirthday(LocalDate.of(2022,12,23));
        student.setGender("1");

        return studentRepository.save(student);
    }

}
