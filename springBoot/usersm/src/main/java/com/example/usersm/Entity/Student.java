package com.example.usersm.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns =
                    @JoinColumn(name = "student_id"),
            inverseJoinColumns =
                    @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    
}
