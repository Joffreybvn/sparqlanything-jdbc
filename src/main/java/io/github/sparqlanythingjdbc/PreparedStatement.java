package io.github.sparqlanythingjdbc;

import io.github.sparqlanythingjdbc.utils.LoggingConfig;
import org.apache.jena.query.Dataset;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PreparedStatement extends Statement implements java.sql.PreparedStatement {

    private static final Logger LOGGER = LoggingConfig.getLogger();
    private final String sql;
    private final Map<Integer, Object> parameters = new HashMap<>();

    public PreparedStatement(Connection connection, Dataset dataset, String sql) {
        super(connection, dataset);
        LOGGER.finest("PreparedStatement created with SQL: " + sql);
        this.sql = sql;
    }

    /**
     * Replaces the '?' placeholders with the set parameter values.
     */
    private String prepareSQL() throws SQLException {
        String preparedSql = this.sql;
        for (Map.Entry<Integer, Object> entry : this.parameters.entrySet()) {
            String replacement = (entry.getValue() != null) ? entry.getValue().toString() : "NULL";
            preparedSql = preparedSql.replaceFirst("\\?", replacement);
        }
        LOGGER.finest("Prepared SQL: " + preparedSql);
        return preparedSql;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        String finalSQL = prepareSQL();
        return super.executeQuery(finalSQL);
    }

    @Override
    public int executeUpdate() throws SQLException {
        String finalSQL = prepareSQL();
        return super.executeUpdate(finalSQL);
    }

    @Override
    public boolean execute() throws SQLException {
        String finalSQL = prepareSQL();
        return super.execute(finalSQL);
    }

    @Override
    public void addBatch() throws SQLException {

    }

    @Override
    public void setCharacterStream(int i, Reader reader, int i1) throws SQLException {

    }

    @Override
    public void setRef(int i, Ref ref) throws SQLException {

    }

    @Override
    public void setBlob(int i, Blob blob) throws SQLException {

    }

    @Override
    public void setClob(int i, Clob clob) throws SQLException {

    }

    @Override
    public void setArray(int i, Array array) throws SQLException {

    }

    @Override
    public void setInt(int parameterIndex, int value) throws SQLException {
        this.parameters.put(parameterIndex, value);
    }

    @Override
    public void setLong(int i, long l) throws SQLException {

    }

    @Override
    public void setFloat(int i, float v) throws SQLException {

    }

    @Override
    public void setString(int parameterIndex, String value) throws SQLException {
        this.parameters.put(parameterIndex, "'" + value + "'");
    }

    @Override
    public void setBytes(int i, byte[] bytes) throws SQLException {

    }

    @Override
    public void setDouble(int parameterIndex, double value) throws SQLException {
        this.parameters.put(parameterIndex, value);
    }

    @Override
    public void setBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {

    }

    @Override
    public void setBoolean(int parameterIndex, boolean value) throws SQLException {
        this.parameters.put(parameterIndex, value);
    }

    @Override
    public void setByte(int i, byte b) throws SQLException {

    }

    @Override
    public void setShort(int i, short i1) throws SQLException {

    }

    @Override
    public void setDate(int parameterIndex, Date date) throws SQLException {
        parameters.put(parameterIndex, "'" + date.toString() + "'");
    }

    @Override
    public void setTime(int i, Time time) throws SQLException {

    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp) throws SQLException {

    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    @Override
    public void setUnicodeStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        parameters.put(parameterIndex, x);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.parameters.put(parameterIndex, "NULL");
    }

    @Override
    public void clearParameters() throws SQLException {
        this.parameters.clear();
    }

    @Override
    public void setObject(int i, Object o, int i1) throws SQLException {

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMetaData is not supported");
    }

    @Override
    public void setDate(int i, Date date, Calendar calendar) throws SQLException {

    }

    @Override
    public void setTime(int i, Time time, Calendar calendar) throws SQLException {

    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar) throws SQLException {

    }

    @Override
    public void setNull(int i, int i1, String s) throws SQLException {

    }

    @Override
    public void setURL(int i, URL url) throws SQLException {

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("getParameterMetaData is not supported");
    }

    @Override
    public void setRowId(int i, RowId rowId) throws SQLException {

    }

    @Override
    public void setNString(int i, String s) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void setNClob(int i, NClob nClob) throws SQLException {

    }

    @Override
    public void setClob(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void setBlob(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void setNClob(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {

    }

    @Override
    public void setObject(int i, Object o, int i1, int i2) throws SQLException {

    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void setCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setCharacterStream(int i, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int i, Reader reader) throws SQLException {

    }

    @Override
    public void setClob(int i, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int i, Reader reader) throws SQLException {

    }

    @Override
    public void close() throws SQLException {
        super.close();
        parameters.clear();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return super.isClosed();
    }
}