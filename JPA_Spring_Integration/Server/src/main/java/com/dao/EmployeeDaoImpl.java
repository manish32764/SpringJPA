package com.dao;

import com.vo.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    public Employee find(String name) {
        List<Employee> resultList = entityManager.createQuery("select e from Employee e where e.name like :name").setParameter("name", name).getResultList();
        if(resultList != null && !resultList.isEmpty()){
            return resultList.get(0);
        }
        return null;
    }

    public void delete(Employee employee) {
        entityManager.remove(employee);
    }
}
