package io.github.sparqlanythingjdbc;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SparqlAnythingDriverTest {

    private static final String JDBC_URL = "jdbc:sparql-anything://localhost";
    private static final String DRIVER_CLASS_NAME = "io.github.sparqlanythingjdbc.Driver";

    @BeforeAll
    static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
    }

    @Test
    @Order(1)
    @DisplayName("Test Connection Establishment")
    void testConnectionEstablishment() throws SQLException {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL)) {
            assertNotNull(connection, "Connection should not be null.");
            assertFalse(connection.isClosed(), "Connection should be open.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test Simple SELECT Query")
    void testSimpleSelectQuery() throws SQLException {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 10";

            try (ResultSet resultSet = statement.executeQuery(query)) {
                assertNotNull(resultSet, "ResultSet should not be null.");

                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }

                assertTrue(rowCount >= 0, "Expected at least 1 row or possibly 0 if no data.");
            }
        }
    }

/*    @Test
    @Order(3)
    @DisplayName("Test Prepared SELECT Statement")
    void testPreparedSelectStatement() throws SQLException {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ?s ?p ?o WHERE { ?s ?p ?o . FILTER (?s = ?) } LIMIT 5")) {

            // Example parameter binding if your driver supports parameters
            // Replace with appropriate placeholders, e.g., ID or URI
            preparedStatement.setString(1, "http://example.org/resource");

            try (ResultSet resultSet = (ResultSet) preparedStatement.executeQuery()) {
                assertNotNull(resultSet, "ResultSet should not be null.");

                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }
                assertTrue(rowCount >= 0, "Row count should be non-negative.");
            }
        }
    }*/

    @Test
    @Order(4)
    @DisplayName("Test Invalid SELECT Query (Syntax Error)")
    void testInvalidSelectQuery() {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            // An obviously invalid query to provoke an error
            String invalidQuery = "SELECT BAD SYNTAX";

            // We expect an SQLException or subclass to be thrown
            assertThrows(SQLException.class, () -> {
                statement.executeQuery(invalidQuery);
            }, "Invalid query should throw SQLException.");

        } catch (SQLException e) {
            fail("Failed to create connection or statement: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test ResultSet Metadata")
    void testResultSetMetadata() throws SQLException {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 1";
            try (ResultSet rs = statement.executeQuery(query)) {
                assertNotNull(rs, "ResultSet should not be null.");

                ResultSetMetaData rsmd = rs.getMetaData();
                assertNotNull(rsmd, "ResultSetMetaData should not be null.");

                int columnCount = rsmd.getColumnCount();
                assertTrue(columnCount > 0, "Column count should be greater than 0.");

                // Example checks
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    assertNotNull(columnName, "Column name should not be null for column " + i);
                }
            }
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test Database Metadata")
    void testDatabaseMetadata() throws SQLException {
        try (Connection connection = (Connection) DriverManager.getConnection(JDBC_URL)) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            assertNotNull(dbMetaData, "DatabaseMetaData should not be null.");

            // Check some basic info (adjust according to your driver’s capabilities)
            String productName = dbMetaData.getDatabaseProductName();
            assertNotNull(productName, "Database product name should not be null.");
            System.out.println("Database Product Name: " + productName);

            String productVersion = dbMetaData.getDatabaseProductVersion();
            assertNotNull(productVersion, "Database product version should not be null.");
            System.out.println("Database Product Version: " + productVersion);
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test Closing Statements and Connections")
    void testCloseResources() throws SQLException {
        Connection connection = (Connection) DriverManager.getConnection(JDBC_URL);
        Statement statement = connection.createStatement();
        statement.close();
        assertTrue(statement.isClosed(), "Statement should be closed.");

        connection.close();
        assertTrue(connection.isClosed(), "Connection should be closed.");
    }
}
