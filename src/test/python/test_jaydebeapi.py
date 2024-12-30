import glob
import os
import pytest
import jaydebeapi

QUERY = """
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
"""

class TestJayDeBeApi:
    """
    This class demonstrates how to test SparqlAnything via JayDeBeAPI in a pytest context.
    It checks that the query returns exactly the three expected rows.
    """

    @pytest.fixture(scope="class")
    def db_connection(self):
        """
        Creates a JayDeBeAPI connection to the SparqlAnything JDBC driver.
        """

        conn = jaydebeapi.connect(
            "io.github.sparqlanythingjdbc.Driver",
            "jdbc:sparql-anything://localhost",
            # Note: you must execute `make bundle` before running below line
            jars=glob.glob(os.path.join("./build/bundle", "*.jar"))
        )

        yield conn
        conn.close()

    def test_sparql_anything_query(self, db_connection):
        curs = db_connection.cursor()
        curs.execute(QUERY)
        rows = curs.fetchall()
        curs.close()

        # Expected exact result rows
        expected_rows = [
            ("Frites", True, 4.5, 540, "2024-06-25"),
            ("Gaufres", True, 3.5, 330, "2024-06-25"),
            ("Eau", False, 1.2000000476837158, 0, "2024-06-25")
        ]

        # Assert the entire result set matches exactly
        assert rows == expected_rows, f"Expected rows:\n{expected_rows}\nBut got:\n{rows}"
