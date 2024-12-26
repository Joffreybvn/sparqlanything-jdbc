package io.github.sparqlanythingjdbc;
import java.math.BigDecimal;
import java.sql.*;
import io.github.sparqlanything.engine.FacadeX;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.main.QC;

public class Main {
    public static void main(String[] args) {
        testNumber1();
        //mainDriver();
        //mainSparqlAnything();
    }

    public static void testNumber1() {
        // Test with different types
        testAndPrint(42); // Integer
        testAndPrint(42L); // Long
        testAndPrint(42.99); // Double
        testAndPrint(42.99f); // Float
        testAndPrint(new BigDecimal("42.99")); // BigDecimal
        testAndPrint(true); // Boolean
        testAndPrint("42"); // String
    }

    public static void testAndPrint(Object value) {
        System.out.println("Input: " + value + " (Type: " + value.getClass().getSimpleName() + ")");
        try {
            float result = testNumber(value);
            System.out.println("Converted to float: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("---------------------------------");
    }

    public static float testNumber(Object value) {
        String typeName = value.getClass().getName();

        try {
            return switch (value) {
                case Float floatValue -> floatValue;
                case Number numberValue -> numberValue.floatValue();
                case Boolean boolValue -> boolValue ? 1.0f : 0.0f;
                case String stringValue -> Float.parseFloat(stringValue);
                default -> throw new IllegalArgumentException("Unsupported object type: " + typeName);
            };
        } catch (Exception e) {
            throw new RuntimeException("Object type '" + typeName + "' cannot be converted to Float.", e);
        }
    }

    public static void mainDriver() {
        try {
            Class.forName("io.github.sparqlanythingjdbc.Driver");

            Connection conn = (Connection) DriverManager.getConnection("jdbc:sparql-anything://localhost");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("PREFIX xyz: <http://sparql.xyz/facade-x/data/>" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                    "PREFIX fx: <http://sparql.xyz/facade-x/ns/>" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                    "SELECT" +
                    "    ?name" +
                    "    (xsd:boolean(?isHeathlyRaw) AS ?isHeathly)" +
                    "    (xsd:float(?priceRaw) AS ?price)" +
                    "    (xsd:integer(?fatRaw) AS ?fat)" +
                    "    (xsd:date(?productionDateRaw) AS ?productionDate)" +
                    "WHERE {" +
                    "    SERVICE <x-sparql-anything:data.xml> {" +
                    "        ?food xyz:nameFr ?name ;" +
                    "               xyz:isHeathly ?isHeathlyRaw ;" +
                    "               rdf:_1 ?priceField ;" +
                    "               rdf:_2 ?fatField ;" +
                    "               rdf:_3 ?productionDateField ." +
                    "    ?priceField rdf:_1 ?priceRaw ." +
                    "    ?fatField rdf:_1 ?fatRaw ." +
                    "    ?productionDateField rdf:_1 ?productionDateRaw ." +
                    "    }" +
                    "}");

            while (rs.next()) {
                System.out.println("Result: " + rs.getString("name"));
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

    public static void mainSparqlAnything(){

        // Set FacadeX OpExecutor as default executor factory
        QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);

        // Execute the query by using standard Jena ARQ's API
        Dataset kb = DatasetFactory.createGeneral();

        Query query = QueryFactory.create(
                "PREFIX xyz: <http://sparql.xyz/facade-x/data/>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX fx: <http://sparql.xyz/facade-x/ns/>" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                "SELECT" +
                "    ?name" +
                "    (xsd:boolean(?isHeathlyRaw) AS ?isHeathly)" +
                "    (xsd:float(?priceRaw) AS ?price)" +
                "    (xsd:integer(?fatRaw) AS ?fat)" +
                "    (xsd:date(?productionDateRaw) AS ?productionDate)" +
                "WHERE {" +
                "    SERVICE <x-sparql-anything:data.xml> {" +
                "        ?food xyz:nameFr ?name ;" +
                "               xyz:isHeathly ?isHeathlyRaw ;" +
                "               rdf:_1 ?priceField ;" +
                "               rdf:_2 ?fatField ;" +
                "               rdf:_3 ?productionDateField ." +
                "    ?priceField rdf:_1 ?priceRaw ." +
                "    ?fatField rdf:_1 ?fatRaw ." +
                "    ?productionDateField rdf:_1 ?productionDateRaw ." +
                "    }" +
                "}"
        );

        org.apache.jena.query.ResultSet results = QueryExecutionFactory.create(query, kb).execSelect();
        ResultSetFormatter.outputAsJSON(results);
        String stringResults = ResultSetFormatter.asText(results);

        System.out.println(stringResults);
    }
}
