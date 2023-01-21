package java8.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 20.01.2023
 */
public class Util {
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/lms_task",
                    "postgres",
                    "postgres"
            );
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
