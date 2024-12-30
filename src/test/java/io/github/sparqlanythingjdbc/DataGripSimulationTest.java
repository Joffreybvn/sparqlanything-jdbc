package io.github.sparqlanythingjdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataGripSimulationTest {

    private static final String JDBC_URL = "jdbc:sparql-anything://localhost";
    private static final String DRIVER_CLASS_NAME = "io.github.sparqlanythingjdbc.Driver";
    private static final String QUERY = """
            PREFIX xyz: <http://sparql.xyz/facade-x/data/>
            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
            PREFIX fx: <http://sparql.xyz/facade-x/ns/>
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            SELECT
                ?name
                (xsd:boolean(?isHeathlyRaw) AS ?isHeathly)
                (xsd:float(?priceRaw) AS ?price)
                (xsd:integer(?fatRaw) AS ?fat)
                (xsd:date(?productionDateRaw) AS ?productionDate)
            WHERE {
                SERVICE <x-sparql-anything:sample.xml> {
                    ?food xyz:nameFr ?name ;
                           xyz:isHeathly ?isHeathlyRaw ;
                           rdf:_1 ?priceField ;
                           rdf:_2 ?fatField ;
                           rdf:_3 ?productionDateField .
                    ?priceField rdf:_1 ?priceRaw .
                    ?fatField rdf:_1 ?fatRaw .
                    ?productionDateField rdf:_1 ?productionDateRaw .
                }
            }
            """;

    @BeforeAll
    static void loadDriver() throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
    }

    @Test
    public void simulateDataGripUsage() throws SQLException {

        try (Connection conn = (Connection) DriverManager.getConnection(JDBC_URL)) {
            assertNotNull(conn, "Connection should not be null.");

            // 1. Simulate some checks DataGrip does
            boolean initiallyClosed = conn.isClosed();
            assertFalse(initiallyClosed, "Connection should be open initially.");

            DatabaseMetaData metaData = conn.getMetaData();
            assertNotNull(metaData, "DatabaseMetaData should not be null.");

            metaData.getDriverName();
            metaData.getDriverVersion();
            metaData.getDatabaseProductName();
            metaData.getDatabaseProductVersion();
            metaData.getDatabaseMajorVersion();
            metaData.getDatabaseMinorVersion();
            metaData.getDriverMajorVersion();
            metaData.getDriverMinorVersion();
            metaData.getCatalogTerm();
            metaData.getSchemaTerm();

            conn.getClientInfo("ApplicationName");
            conn.setClientInfo("ApplicationName", "DataGrip 2024.3.3");

            conn.isValid(20);
            conn.getAutoCommit();
            conn.setAutoCommit(true);
            conn.getWarnings();
            conn.clearWarnings();
            conn.setReadOnly(false);
            conn.getWarnings();
            conn.clearWarnings();
            conn.setCatalog("");
            conn.setSchema("");
            conn.getAutoCommit();

            // 2. Create Statement
            try (Statement stmt = conn.createStatement()) {
                stmt.setEscapeProcessing(false);
                assertFalse(stmt.isClosed());
                stmt.getWarnings();
                stmt.clearWarnings();
                stmt.setFetchSize(100);

                // 3. Execute the query
                boolean hasResultSet = stmt.execute(QUERY);
                assertTrue(hasResultSet, "Query should return a result set.");

                // 4. Retrieve the results
                try (ResultSet rs = stmt.getResultSet()) {
                    List<String> rows = new ArrayList<>();
                    while (rs.next()) {
                        String name = rs.getString("name");
                        boolean isHeathly = (boolean) rs.getObject("isHeathly");
                        float price = (float) rs.getObject("price");
                        int fat = (int) rs.getObject("fat");
                        Date productionDate = rs.getDate("productionDate");

                        String row = String.format("%s,%b,%.1f,%d,%s", name, isHeathly, price, fat, productionDate);
                        rows.add(row);
                    }

                    // Ensure we got 3 rows matching the data, compare them to a CSV output
                    // 1,Frites,true,4.5,540,2024-06-25
                    // 2,Gaufres,true,3.5,330,2024-06-25
                    // 3,Eau,false,1.2000000476837158,0,2024-06-25
                    assertEquals(3, rows.size(), "Should retrieve exactly 3 rows.");
                    assertTrue(rows.stream().anyMatch(r -> r.contains("Frites,true,4.5,540,2024-06-25")));
                    assertTrue(rows.stream().anyMatch(r -> r.contains("Gaufres,true,3.5,330,2024-06-25")));
                    assertTrue(rows.stream().anyMatch(r -> r.contains("Eau,false,1.2,0,2024-06-25")));
                }
                stmt.getMoreResults();
                stmt.getUpdateCount();
                assertFalse(stmt.isClosed());
                stmt.getWarnings();
                stmt.clearWarnings();
                assertFalse(stmt.isClosed());
                stmt.getWarnings();
                stmt.clearWarnings();

                // 5. Close the statement
                stmt.close();
                assertTrue(stmt.isClosed(), "Statement should be closed after close().");
            }
            conn.getCatalog();
            conn.getSchema();
            conn.isClosed();
        }
    }
}
