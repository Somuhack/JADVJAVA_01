package com.example.MicroservicesLearn.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MicroservicesLearn.entity.Employee;
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}