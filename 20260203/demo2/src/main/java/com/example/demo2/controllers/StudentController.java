package com.example.demo2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo2.model.Student;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String getStudent(Model model) {
        Student student = new Student(1, "student");
        model.addAttribute("student", student);
        model.addAttribute("message", "Hello World!");
        return "student";
    }
}
