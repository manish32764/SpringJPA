package com.service;

import com.dao.DepartmentDao;
import com.vo.Department;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Inject
    private DepartmentDao departmentDao;

    @Transactional
    public void save(Department department){
        departmentDao.save(department);
    }
}
