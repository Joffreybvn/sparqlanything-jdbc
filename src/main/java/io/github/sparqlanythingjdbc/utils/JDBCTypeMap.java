package io.github.sparqlanythingjdbc.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JDBCTypeMap {
    private static final Map<Class<?>, Integer> javaClassMap = new HashMap<>();
    private static final Map<String, Integer> uriMap = new HashMap<>();
    private static final Map<Integer, String> labelMap = new HashMap<>();
    private static final Integer defaultType = Types.NULL;

    // XSD types defined with JavaClass in org.apache.jena.datatypes.xsd.XSDDatatype
    static {
        javaClassMap.put(Float.class, Types.FLOAT);
        javaClassMap.put(Double.class, Types.DOUBLE);
        javaClassMap.put(Integer.class, Types.INTEGER);
        javaClassMap.put(Long.class, Types.BIGINT);
        javaClassMap.put(Short.class, Types.SMALLINT);
        javaClassMap.put(Byte.class, Types.BINARY);
        javaClassMap.put(BigDecimal.class, Types.DECIMAL);
        javaClassMap.put(BigInteger.class, Types.BIGINT);
        javaClassMap.put(Boolean.class, Types.BOOLEAN);
        javaClassMap.put(String.class, Types.VARCHAR);
        javaClassMap.put(URI.class, Types.VARCHAR);
    }

    // XSD types defined without JavaClass, and only with XSD uri in org.apache.jena.datatypes.xsd.XSDDatatype
    static {
        uriMap.put("http://www.w3.org/2001/XMLSchema#date", Types.DATE);
        uriMap.put("http://www.w3.org/2001/XMLSchema#dateTime", Types.TIMESTAMP);
        uriMap.put("http://www.w3.org/2001/XMLSchema#time", Types.TIME);
    }

    static {
        labelMap.put(Types.ARRAY, "ARRAY");
        labelMap.put(Types.BIGINT, "BIGINT");
        labelMap.put(Types.BINARY, "BINARY");
        labelMap.put(Types.BIT, "BIT");
        labelMap.put(Types.BLOB, "BLOB");
        labelMap.put(Types.BOOLEAN, "BOOLEAN");
        labelMap.put(Types.CHAR, "CHAR");
        labelMap.put(Types.CLOB, "CLOB");
        labelMap.put(Types.DATE, "DATE");
        labelMap.put(Types.DECIMAL, "DECIMAL");
        labelMap.put(Types.DOUBLE, "DOUBLE");
        labelMap.put(Types.FLOAT, "FLOAT");
        labelMap.put(Types.INTEGER, "INTEGER");
        labelMap.put(Types.NVARCHAR, "NVARCHAR");
        labelMap.put(Types.REAL, "REAL");
        labelMap.put(Types.SMALLINT, "SMALLINT");
        labelMap.put(Types.TIME, "TIME");
        labelMap.put(Types.TIMESTAMP, "TIMESTAMP");
        labelMap.put(Types.VARCHAR, "VARCHAR");
        labelMap.put(Types.NULL, "NULL");
    }

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
