package java8.dao.impl;

import java8.dao.EmployeeDao;
import java8.model.Employee;
import java8.model.Job;
import java8.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 20.01.2023
 */
public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection;

    public EmployeeDaoImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createEmployee() {
        String sql = """
                CREATE TABLE IF NOT EXISTS employees(
                id serial primary key,
                first_name varchar(50) not null,
                last_name varchar(50) not null,
                age int not null,
                email varchar unique,
                job_id int references jobs(id) not null
                );
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table employee created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = ("""
                insert into employees(first_name, last_name, age, email, job_id)
                values(?,?,?,?,?);
                """);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
            System.out.println("Employee added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        String s = """
                drop table employees;
                """;
        try (Statement statement = connection.createStatement()) {
            boolean b = statement.execute(s);
            if (!b) {
                System.out.println("Drop table successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        String s = "delete from employees";
        try (Statement statement = connection.createStatement()) {
            boolean b = statement.execute(s);
            if (!b) {
                System.out.println("Table is cleaned.");
            } else {
                System.out.println("Does not exists.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String s = """
                update employees set
                first_name = ?,
                last_name = ?,
                age = ?,
                email = ?,
                job_id = ? 
                where id = ?
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(s)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("Successfully update.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    select * from employees;
                    """);
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong(1));
                employee.setFirstName(resultSet.getString(2));
                employee.setLastName(resultSet.getString(3));
                employee.setAge(resultSet.getInt(4));
                employee.setEmail(resultSet.getString(5));
                employee.setJobId(resultSet.getInt(6));
                employees.add(employee);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = """
                select * from employees where email = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Does not exists.");
            }
            Employee employee1 = new Employee();
            employee1.setId(resultSet.getLong(1));
            employee1.setFirstName(resultSet.getString(2));
            employee1.setLastName(resultSet.getString(3));
            employee1.setAge(resultSet.getInt(4));
            employee1.setEmail(resultSet.getString(5));
            employee1.setJobId(resultSet.getInt(6));
            resultSet.close();
            return employee1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> ej = new LinkedHashMap<>();
        String s = """
                select * from employees e join jobs j on e.job_id = j.id where e.id = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(s)){
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Does not exists.");
            }
            Employee employee1 = new Employee();
            employee1.setId(resultSet.getLong(1));
            employee1.setFirstName(resultSet.getString(2));
            employee1.setLastName(resultSet.getString(3));
            employee1.setAge(resultSet.getInt(4));
            employee1.setEmail(resultSet.getString(5));
            employee1.setJobId(resultSet.getInt(6));

            Long id = resultSet.getLong("id");
            String position = resultSet.getString("position");
            String profession = (resultSet.getString("profession"));
            String description = (resultSet.getString("description"));
            int experience = (resultSet.getInt("experience"));
            Job job = new Job(id, position, profession, description, experience);
            resultSet.close();
            ej.put(employee1,job);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return ej;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String s = """
        select * from employees
        join jobs j on employees.job_id = j.id where position = ?
        """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(s)){
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Does not exists.");
            }
                Employee employee1 = new Employee();
                employee1.setId(resultSet.getLong(1));
                employee1.setFirstName(resultSet.getString(2));
                employee1.setLastName(resultSet.getString(3));
                employee1.setAge(resultSet.getInt(4));
                employee1.setEmail(resultSet.getString(5));
                employee1.setJobId(resultSet.getInt(6));
                employees.add(employee1);
            resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}
