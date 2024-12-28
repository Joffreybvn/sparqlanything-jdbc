package io.github.sparqlanythingjdbc.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Types;
import java.util.Map;

public class JDBCTypeMap {

    // XSD types defined with JavaClass (in org.apache.jena.datatypes.xsd.XSDDatatype - jena-core)
    private static final Map<Class<?>, Integer> javaClassMap = Map.ofEntries(
            Map.entry(Float.class, Types.FLOAT),
            Map.entry(Double.class, Types.DOUBLE),
            Map.entry(Integer.class, Types.INTEGER),
            Map.entry(Long.class, Types.BIGINT),
            Map.entry(Short.class, Types.SMALLINT),
            Map.entry(Byte.class, Types.BINARY),
            Map.entry(BigDecimal.class, Types.DECIMAL),
            Map.entry(BigInteger.class, Types.BIGINT),
            Map.entry(Boolean.class, Types.BOOLEAN),
            Map.entry(String.class, Types.VARCHAR),
            Map.entry(URI.class, Types.VARCHAR)
    );

    // XSD types defined without JavaClass, and only with XSD uri (in org.apache.jena.datatypes.xsd.XSDDatatype - jena-core)
    private static final Map<String, Integer> uriMap = Map.of(
            "http://www.w3.org/2001/XMLSchema#date", Types.DATE,
            "http://www.w3.org/2001/XMLSchema#dateTime", Types.TIMESTAMP,
            "http://www.w3.org/2001/XMLSchema#time", Types.TIME
    );

    private static final Map<Integer, String> labelMap = Map.ofEntries(
            Map.entry(Types.ARRAY, "ARRAY"),
            Map.entry(Types.BIGINT, "BIGINT"),
            Map.entry(Types.BINARY, "BINARY"),
            Map.entry(Types.BIT, "BIT"),
            Map.entry(Types.BLOB, "BLOB"),
            Map.entry(Types.BOOLEAN, "BOOLEAN"),
            Map.entry(Types.CHAR, "CHAR"),
            Map.entry(Types.CLOB, "CLOB"),
            Map.entry(Types.DATE, "DATE"),
            Map.entry(Types.DECIMAL, "DECIMAL"),
            Map.entry(Types.DOUBLE, "DOUBLE"),
            Map.entry(Types.FLOAT, "FLOAT"),
            Map.entry(Types.INTEGER, "INTEGER"),
            Map.entry(Types.NVARCHAR, "NVARCHAR"),
            Map.entry(Types.REAL, "REAL"),
            Map.entry(Types.SMALLINT, "SMALLINT"),
            Map.entry(Types.TIME, "TIME"),
            Map.entry(Types.TIMESTAMP, "TIMESTAMP"),
            Map.entry(Types.VARCHAR, "VARCHAR"),
            Map.entry(Types.NULL, "NULL")
    );

    private static final Integer defaultType = Types.NULL;

    public static int getTypeInt(Class<?> javaClass) {
        return javaClassMap.getOrDefault(javaClass, defaultType);
    }

    public static int getTypeInt(String uri) {
        return uriMap.getOrDefault(uri, defaultType);
    }

    public static String getTypeLabel(int typeInt) {
        return labelMap.get(typeInt);
    }
}
