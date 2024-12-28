package io.github.sparqlanythingjdbc;

import io.github.sparqlanythingjdbc.utils.JDBCTypeMap;
import io.github.sparqlanythingjdbc.utils.LoggingConfig;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.sparql.core.Var;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ResultSetMetaData implements java.sql.ResultSetMetaData {

    private static final Logger LOGGER = LoggingConfig.getLogger();
    private final List<Var> columns;
    private final List<LiteralLabel> values;

    public ResultSetMetaData(List<Var> columns, List<LiteralLabel> values) {
        LOGGER.finest("Calling ResultSetMetaData constructor with columns=..., values=...");
        this.columns = columns;
        this.values = values;
    }

    @Override
    public int getColumnCount() throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnCount()");
        return this.columns.size();
    }

    @Override
    public String getColumnLabel(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnLabel(int index=" + index + ")");
        return this.getColumnName(index);
    }

    @Override
    public String getColumnName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnName(int index=" + index + ")");
        // Indices in JDBC are 1-based, so we do index - 1
        return this.columns.get(index - 1).getVarName();
    }

    public int getColumnIndex(String label) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnIndex(String label=" + label + ")");
        int index = this.columns.indexOf(Var.alloc(label));
        if (index == -1) {
            throw new SQLException("Column '" + label + "' not found");
        }
        return index + 1;
    }

    @Override
    public boolean isAutoIncrement(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isAutoIncrement(int index=" + index + ")");
        return false;
    }

    @Override
    public boolean isCaseSensitive(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isCaseSensitive(int index=" + index + ")");
        return false;
    }

    @Override
    public boolean isSearchable(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isSearchable(int index=" + index + ")");
        return false;
    }

    @Override
    public boolean isCurrency(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isCurrency(int index=" + index + ")");
        return false;
    }

    @Override
    public int isNullable(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isNullable(int index=" + index + ")");
        // Not sure if columns can be null or not, but let's keep the original behavior
        return java.sql.ResultSetMetaData.columnNoNulls;
    }

    @Override
    public boolean isSigned(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isSigned(int index=" + index + ")");
        return false;
    }

    @Override
    public int getColumnDisplaySize(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnDisplaySize(int index=" + index + ")");
        // Not possible to determine display size
        return -1;
    }

    @Override
    public String getSchemaName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getSchemaName(int index=" + index + ")");
        return "";
    }

    @Override
    public int getPrecision(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getPrecision(int index=" + index + ")");
        return 0;
    }

    @Override
    public int getScale(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getScale(int index=" + index + ")");
        return 0;
    }

    @Override
    public String getTableName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getTableName(int index=" + index + ")");
        // A placeholder value, since there's no real table
        return "bindings";
    }

    @Override
    public String getCatalogName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getCatalogName(int index=" + index + ")");
        return "";
    }

    @Override
    public int getColumnType(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnType(int index=" + index + ")");
        RDFDatatype datatype = this.values.get(index - 1).getDatatype();

        Class<?> javaClass = datatype.getJavaClass();
        if (javaClass != null) {
            return JDBCTypeMap.getTypeInt(javaClass);
        } else {
            return JDBCTypeMap.getTypeInt(datatype.getURI());
        }
    }

    @Override
    public String getColumnTypeName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnTypeName(int index=" + index + ")");
        int typeInt = this.getColumnType(index);
        return JDBCTypeMap.getTypeLabel(typeInt);
    }

    @Override
    public boolean isReadOnly(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isReadOnly(int index=" + index + ")");
        return true;
    }

    @Override
    public boolean isWritable(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isWritable(int index=" + index + ")");
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isDefinitelyWritable(int index=" + index + ")");
        return false;
    }

    @Override
    public String getColumnClassName(int index) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.getColumnClassName(int index=" + index + ")");
        // Placeholder or an actual Java class name could be returned
        return "";
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.unwrap(iface=" + iface + ")");
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        LOGGER.finest("Calling ResultSetMetaData.isWrapperFor(iface=" + iface + ")");
        return false;
    }
}
