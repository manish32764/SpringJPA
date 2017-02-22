package com.run.server;

import com.pool.ThreadPoolSave;
import com.vo.Department;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.vo.Employee;

import java.util.concurrent.*;

public class StartServer {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);

        CountDownLatch latch = new CountDownLatch(3);

        ThreadPoolSave task1 = createTask1(context, latch, "Manish", 22, "Physics");
        ThreadPoolSave task2 = createTask1(context, latch, "Devesh", 33, "Chemistry");
        ThreadPoolSave task3 = createTask1(context, latch, "satpal", 33, "Math");

        ExecutorService service = Executors.newFixedThreadPool(3);



        try{
            Future<Employee> e1 = service.submit(task1);
            Future<Employee> e2 = service.submit(task2);
            Future<Employee> e3 = service.submit(task3);

            Employee manish1 = e1.get();
            System.out.println("5 " + manish1);

            Employee manish2 = e2.get();
            System.out.println("6 " + manish2);

            Employee manish3 = e3.get();
            System.out.println("7 " + manish3);

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

    private static ThreadPoolSave createTask1(AnnotationConfigApplicationContext context, CountDownLatch latch, String name, int salary, String departmentName) {
        Employee devesh = createEmployee(name, salary);
        Department chemistry = createDepartment(departmentName);
        return new ThreadPoolSave(devesh, chemistry, context, latch);
    }

    private static ThreadPoolSave createTask1(AnnotationConfigApplicationContext context, CountDownLatch latch) {
        Employee manish = createEmployee("Manish", 23);
        Department physics = createDepartment("Physics");
        return new ThreadPoolSave(manish, physics, context, latch);
    }

    private static Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return department;
    }

    private static Employee createEmployee(String name, int salary) {
        Employee e = new Employee();
        e.setName(name);
        e.setSalary(salary);
        return e;
    }
}
