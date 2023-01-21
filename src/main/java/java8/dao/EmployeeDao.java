package java8.dao;

import java8.model.Employee;
import java8.model.Job;

import javax.swing.event.ListDataEvent;
import java.util.List;
import java.util.Map;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 20.01.2023
 */
public interface EmployeeDao {


    void createEmployee();
    void addEmployee(Employee employee);
    void dropTable();
    void cleanTable();
    void updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);
}
