package io.github.sparqlanythingjdbc;

import io.github.sparqlanythingjdbc.utils.JDBCTypeMap;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.sparql.core.Var;

import java.sql.SQLException;
import java.util.List;


public class ResultSetMetaData implements java.sql.ResultSetMetaData {

    private final List<Var> columns;
    private final List<LiteralLabel> values;

    public ResultSetMetaData(List<Var> columns, List<LiteralLabel> values) {
        this.columns = columns;
        this.values = values;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return this.columns.size();
    }

    @Override
    public String getColumnLabel(int index) throws SQLException {
        return this.getColumnName(index);
    }

    @Override
    public String getColumnName(int index) throws SQLException {
        return this.columns.get(index - 1).getVarName();
    }

    public int getColumnIndex(String label) throws SQLException {
        int index = this.columns.indexOf(Var.alloc(label));
        if (index == -1) {
            throw new SQLException("Column '" + label + "' not found");
        }
        return index;
    }

    @Override
    public boolean isAutoIncrement(int index) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int index) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int index) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int index) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int index) throws SQLException {
        return java.sql.ResultSetMetaData.columnNoNulls;
    }

    @Override
    public boolean isSigned(int index) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int index) throws SQLException {
        // Not possible to determine display size
        return -1;
    }

    @Override
    public String getSchemaName(int index) throws SQLException {
        return "";
    }

    @Override
    public int getPrecision(int index) throws SQLException {
        return 0;
    }

    @Override
    public int getScale(int index) throws SQLException {
        return 0;
    }

    @Override
    public String getTableName(int index) throws SQLException {
        return "bindings";
    }

    @Override
    public String getCatalogName(int index) throws SQLException {
        return "";
    }

    @Override
    public int getColumnType(int index) throws SQLException {
        RDFDatatype datatype = this.values.get(index).getDatatype();

        Class<?> javaClass = datatype.getJavaClass();
        if (javaClass != null) {
            return JDBCTypeMap.getTypeInt(javaClass);
        } else {
            return JDBCTypeMap.getTypeInt(datatype.getURI());
        }
    }

    @Override
    public String getColumnTypeName(int index) throws SQLException {
        int typeInt = this.getColumnType(index);
        return JDBCTypeMap.getTypeLabel(typeInt);
    }

    @Override
    public boolean isReadOnly(int index) throws SQLException {
        return true;
    }

    @Override
    public boolean isWritable(int index) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int index) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int index) throws SQLException {
        return "";
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
