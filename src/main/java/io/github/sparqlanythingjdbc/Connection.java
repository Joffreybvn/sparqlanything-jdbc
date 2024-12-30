package io.github.sparqlanythingjdbc;

import io.github.sparqlanything.engine.FacadeX;
import io.github.sparqlanythingjdbc.utils.LoggingConfig;
import org.apache.jena.query.ARQ;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.sparql.engine.main.QC;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

public class Connection implements java.sql.Connection {

    private static final Logger LOGGER = LoggingConfig.getLogger();
    private final Dataset dataset;
    private boolean isClosed = false;

    public Connection() {
        LOGGER.finest("Calling Connection constructor");
        // Set FacadeX OpExecutor as default executor factory
        QC.setFactory(ARQ.getContext(), FacadeX.ExecutorFactory);
        // Execute the query by using standard Jena ARQ's API
        this.dataset = DatasetFactory.createGeneral();
    }

    @Override
    public Statement createStatement() throws SQLException {
        LOGGER.finest("Calling Connection.createStatement()");
        return new Statement(this, this.dataset);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql = " + sql + ")");
        return new PreparedStatement(this, this.dataset, sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        LOGGER.finest("Calling Connection.prepareCall(String sql = " + sql + ")");
        return null;
    }

    @Override
    public void close() throws SQLException {
        LOGGER.finest("Calling Connection.close()");
        this.isClosed = true;
        System.out.println("Connection: Connection closed.");
    }

    @Override
    public boolean isClosed() throws SQLException {
        LOGGER.finest("Calling Connection.isClosed()");
        return this.isClosed;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        LOGGER.finest("Calling Connection.getMetaData()");
        return new DatabaseMetaData();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        LOGGER.finest("Calling Connection.nativeSQL(String sql = " + sql + ")");
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        LOGGER.finest("Calling Connection.setAutoCommit(boolean autoCommit = " + autoCommit + ")");
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        LOGGER.finest("Calling Connection.getAutoCommit()");
        return false;
    }

    @Override
    public void commit() throws SQLException {
        LOGGER.finest("Calling Connection.commit()");
    }

    @Override
    public void rollback() throws SQLException {
        LOGGER.finest("Calling Connection.rollback()");
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        LOGGER.finest("Calling Connection.setReadOnly(boolean readOnly = " + readOnly + ")");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        LOGGER.finest("Calling Connection.isReadOnly()");
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        LOGGER.finest("Calling Connection.setCatalog(String catalog = " + catalog + ")");
    }

    @Override
    public String getCatalog() throws SQLException {
        LOGGER.finest("Calling Connection.getCatalog()");
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        LOGGER.finest("Calling Connection.setTransactionIsolation(int level = " + level + ")");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        LOGGER.finest("Calling Connection.getTransactionIsolation()");
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        LOGGER.finest("Calling Connection.getWarnings()");
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        LOGGER.finest("Calling Connection.clearWarnings()");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        LOGGER.finest("Calling Connection.createStatement(int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ")");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql=" + sql + ", int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ")");
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        LOGGER.finest("Calling Connection.prepareCall(String sql=" + sql + ", int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ")");
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        LOGGER.finest("Calling Connection.getTypeMap()");
        return Map.of();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        LOGGER.finest("Calling Connection.setTypeMap(Map<String, Class<?>> map = " + map + ")");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        LOGGER.finest("Calling Connection.setHoldability(int holdability = " + holdability + ")");
    }

    @Override
    public int getHoldability() throws SQLException {
        LOGGER.finest("Calling Connection.getHoldability()");
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        LOGGER.finest("Calling Connection.setSavepoint()");
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        LOGGER.finest("Calling Connection.setSavepoint(String name = " + name + ")");
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        LOGGER.finest("Calling Connection.rollback(Savepoint savepoint = " + savepoint + ")");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        LOGGER.finest("Calling Connection.releaseSavepoint(Savepoint savepoint = " + savepoint + ")");
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        LOGGER.finest("Calling Connection.createStatement(int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ", int holdability=" + resultSetHoldability + ")");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql=" + sql + ", int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ", int holdability=" + resultSetHoldability + ")");
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        LOGGER.finest("Calling Connection.prepareCall(String sql=" + sql + ", int type=" + resultSetType + ", int concurrency=" + resultSetConcurrency + ", int holdability=" + resultSetHoldability + ")");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql=" + sql + ", int autoGeneratedKeys=" + autoGeneratedKeys + ")");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql=" + sql + ", int[] columnIndexes=...)");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        LOGGER.finest("Calling Connection.prepareStatement(String sql=" + sql + ", String[] columnNames=...)");
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        LOGGER.finest("Calling Connection.createClob()");
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        LOGGER.finest("Calling Connection.createBlob()");
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        LOGGER.finest("Calling Connection.createNClob()");
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        LOGGER.finest("Calling Connection.createSQLXML()");
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        LOGGER.finest("Calling Connection.isValid(int timeout = " + timeout + ")");
        return true;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        LOGGER.finest("Calling Connection.setClientInfo(String name=" + name + ", String value=" + value + ")");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        LOGGER.finest("Calling Connection.setClientInfo(Properties properties = " + properties + ")");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        LOGGER.finest("Calling Connection.getClientInfo(String name = " + name + ")");
        return "";
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        LOGGER.finest("Calling Connection.getClientInfo()");
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        LOGGER.finest("Calling Connection.createArrayOf(String typeName=" + typeName + ", Object[] elements=...)");
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        LOGGER.finest("Calling Connection.createStruct(String typeName=" + typeName + ", Object[] attributes=...)");
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        LOGGER.finest("Calling Connection.setSchema(String schema=" + schema + ")");
    }

    @Override
    public String getSchema() throws SQLException {
        LOGGER.finest("Calling Connection.getSchema()");
        return "";
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        LOGGER.finest("Calling Connection.abort(Executor executor=" + executor + ")");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        LOGGER.finest("Calling Connection.setNetworkTimeout(Executor executor=" + executor + ", int milliseconds=" + milliseconds + ")");
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        LOGGER.finest("Calling Connection.getNetworkTimeout()");
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        LOGGER.finest("Calling Connection.unwrap(Class<T> iface = " + iface + ")");
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        LOGGER.finest("Calling Connection.isWrapperFor(Class<?> iface = " + iface + ")");
        return false;
    }
}
