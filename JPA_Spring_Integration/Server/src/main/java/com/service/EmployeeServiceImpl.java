package com.service;

import com.dao.EmployeeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vo.Employee;

import javax.inject.Inject;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeDao employeeDao;

    @Transactional
    public void save(Employee employee) {
       employeeDao.save(employee);
    }

    @Transactional
    public Employee find(String name) {
        return employeeDao.find(name);
    }

    @Transactional
    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    @Transactional
    public Employee saveAndFind(Employee newEmployee, String name){
        employeeDao.save(newEmployee);
        return employeeDao.find(name);
    }
}
