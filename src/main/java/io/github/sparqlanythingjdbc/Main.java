package io.github.sparqlanythingjdbc;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("io.github.sparqlanythingjdbc.Driver");

            Connection conn = (Connection) DriverManager.getConnection("jdbc:sparql-anything://localhost");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mock_table");

            while (rs.next()) {
                System.out.println("Result: " + rs.getString("column1"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
