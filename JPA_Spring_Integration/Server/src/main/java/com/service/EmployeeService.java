package com.service;

import com.vo.Employee;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeService {

    void save(Employee employee);

    @Transactional
    Employee find(String name);

    void delete(Employee employee);

    @Transactional
    Employee saveAndFind(Employee newEmployee, String name);
}
