package net.dg.practice;

import net.dg.practice.model.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);

        Employee emp1 = new Employee(1L, "Alex", "DG", 300D, "SALES");
        Employee emp2 = new Employee(1L, "Alex", "DG", 300D, "SALES");

        if(emp1.equals(emp2))
            System.out.println("Equals");
    }

}
