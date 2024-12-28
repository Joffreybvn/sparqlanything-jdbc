
# SPARQL Anything JDBC Driver

[![Build status](https://img.shields.io/github/actions/workflow/status/joffreybvn/sparqlanything-jdbc/ci.yml?branch=master)](https://github.com/Joffreybvn/sparqlanything-jdbc/actions/workflows/ci.yml?query=branch%3Amaster)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[SPARQL Anything](https://sparql-anything.cc/) embedded as a JDBC Driver, to allow Java programs to run SPARQL queries on pretty much anything. _Currently in work in progress, expect breaking changes._

## Quickstart

➡️ See [Usage](#Usage) to set up the driver in [DataGrip](#DataGrip setup and usage), DBeaver, JayDeBeApi (Python) and Airflow.

1. **Add Dependency** (if using Maven/Gradle) or drop the JAR into your classpath.
2. **Copy the [sample.xml](./sample.xml)** data locally
3. **Run a query**:

   ```java
    Class.forName("io.github.sparqlanythingjdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:sparql-anything://localhost");
    Statement statement = connection.createStatement();
   
    ResultSet resultSet = statement.executeQuery(
        "PREFIX xyz: <http://sparql.xyz/facade-x/data/>" +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
        "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
        "SELECT" +
        "    ?name" +
        "    (xsd:boolean(?isHeathlyRaw) AS ?isHeathly)" +
        "    (xsd:float(?priceRaw) AS ?price)" +
        "    (xsd:integer(?fatRaw) AS ?fat)" +
        "    (xsd:date(?productionDateRaw) AS ?productionDate)" +
        "WHERE {" +
        "    SERVICE <x-sparql-anything:sample.xml> {" +
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
    
    int columnCount = resultSet.getMetaData().getColumnCount();
    while (resultSet.next()) {
        for (int index = 1; index <= columnCount; index++) {
            String columnLabel = resultSet.getMetaData().getColumnLabel(index);
            String value = resultSet.getString(index);
            System.out.println(columnLabel + ": " + value);
        }
        System.out.println();
    }
    connection.close();

## Features

### Supported query types

Currently, this driver focuses on SPARQL `SELECT` queries.

| Query Type    | Supported? | Description                                                                                   |
|---------------|:----------:|-----------------------------------------------------------------------------------------------|
| **SELECT**    |     ✅      | Run `SELECT` queries to find and return data matching certain patterns.                       |
| **CONSTRUCT** |     ❌      | Create/transform data based on existing data. _Not implemented yet_.                          |
| **ASK**       |     ❌      | Return `true`/`false` indicating whether a pattern exists in the data. _Not implemented yet_. |
| **DESCRIBE**  |     ❌      | Return RDF data describing a resource. _Not implemented yet_.                                 |

## Usage

### DataGrip setup and usage

### DBeaver setup and usage

### JayDeBeApi setup and usage

### Airflow setup and usage
