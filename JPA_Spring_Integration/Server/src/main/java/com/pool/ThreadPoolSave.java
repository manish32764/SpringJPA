package com.pool;

import com.service.DepartmentService;
import com.service.EmployeeService;
import com.vo.Department;
import com.vo.Employee;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ThreadPoolSave implements Callable<Employee> {

    private final CountDownLatch latch;
    private final Employee newEmployee;
    private final Department newDepartment;
    private final ApplicationContext context;

    public ThreadPoolSave(Employee newEmployee, Department newDepartment, ApplicationContext context, CountDownLatch latch) {
        this.newEmployee = newEmployee;
        this.newDepartment = newDepartment;
        this.context = context;
        this.latch = latch;
    }

    public Employee call() throws Exception {
        Employee manish = null;
        try {
            EmployeeService bean = context.getBean(EmployeeService.class);
            //bean.save(newEmployee);
            System.out.println(String.format("1 ThreadName %s, Employee-Name %s", Thread.currentThread().getName(), newEmployee.getName()));
            latch.countDown();

            manish = bean.saveAndFind(newEmployee, "Manish");
            System.out.println(String.format("2 ThreadName %s, Employee-Name %s", Thread.currentThread().getName(), newEmployee));

            context.getBean(DepartmentService.class).save(newDepartment);
            System.out.println(String.format("3 ThreadName %s, Department-Name %s", Thread.currentThread().getName(), newDepartment.getName()));
        }catch(Throwable t){
            t.printStackTrace();
        }
        return manish;
    }
}
