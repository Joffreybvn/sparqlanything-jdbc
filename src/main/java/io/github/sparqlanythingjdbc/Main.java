package io.github.sparqlanythingjdbc;
import java.sql.*;
import io.github.sparqlanything.engine.FacadeX;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.main.QC;

public class Main {
    public static void main(String[] args) {
        // mainDriver();
        mainSparqlAnything();
    }

    public static void mainDriver() {
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

    public static void mainSparqlAnything(){

        // Set FacadeX OpExecutor as default executor factory
        QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);

        // Execute the query by using standard Jena ARQ's API
        Dataset kb = DatasetFactory.createGeneral();

        Query query = QueryFactory.create(
                "PREFIX fx:  <http://sparql.xyz/facade-x/ns/> " +
                        "PREFIX xyz: <http://sparql.xyz/facade-x/data/> " +
                        "SELECT ?o { " +
                        "SERVICE <x-sparql-anything:> { " +
                        "fx:properties fx:content '[1,2,3]' ; " +
                        "fx:media-type 'application/json' . " +
                        "?s fx:anySlot ?o" +
                        "}}");

        System.out.println(ResultSetFormatter.asText(QueryExecutionFactory.create(query,kb).execSelect()));
    }
}
