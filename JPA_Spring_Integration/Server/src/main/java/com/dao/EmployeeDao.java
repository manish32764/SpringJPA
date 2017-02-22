package com.dao;

import com.vo.Employee;

public interface EmployeeDao {
    void save(Employee employee);

    Employee find(String name);

    void delete(Employee employee);
}
