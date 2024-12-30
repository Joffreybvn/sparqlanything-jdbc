package io.github.sparqlanythingjdbc;

import io.github.sparqlanythingjdbc.utils.LoggingConfig;
import org.apache.jena.query.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Statement implements java.sql.Statement {

    private static final Logger LOGGER = LoggingConfig.getLogger();
    private final Connection connection;
    private final Dataset dataset;
    private final List<ResultSet> results = new ArrayList<>();
    private int position = 0;
    private boolean isClosed = false;

    public Statement(Connection connection, Dataset dataset) {
        LOGGER.finest("Calling Statement constructor with dataset=...");
        this.connection = connection;
        this.dataset = dataset;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        LOGGER.finest("Calling Statement.executeQuery(String sql=" + sql + ")");

        try {
            Query query = QueryFactory.create(sql);
            org.apache.jena.query.ResultSet resultSet = QueryExecutionFactory.create(query, this.dataset).execSelect();
            this.results.add(new ResultSet(this, resultSet));
            this.results.add(null);
            return this.results.getFirst();
        } catch (QueryParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        LOGGER.finest("Calling Statement.close()");
        // TODO: loop over results and close the ResultSets
        this.isClosed = true;
        LOGGER.info("Statement closed");
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        LOGGER.finest("Calling Statement.getMaxFieldSize()");
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        LOGGER.finest("Calling Statement.setMaxFieldSize(int max=" + max + ")");
    }

    @Override
    public int getMaxRows() throws SQLException {
        LOGGER.finest("Calling Statement.getMaxRows()");
        return 0;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        LOGGER.finest("Calling Statement.executeUpdate(String sql=" + sql + ")");
        return 0;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        LOGGER.finest("Calling Statement.execute(String sql=" + sql + ")");
        ResultSet result = this.executeQuery(sql);
        return result != null;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        LOGGER.finest("Calling Statement.getResultSet()");
        Object result = this.results.get(this.position);
        if (result instanceof ResultSet) {
            return (ResultSet) result;
        }
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        LOGGER.finest("Calling Statement.getUpdateCount()");
        return -1;
    }

    /**
     * Equivalent to ResulSet's next() method.
     */
    @Override
    public boolean getMoreResults() throws SQLException {
        LOGGER.finest("Calling Statement.getMoreResults()");
        this.position++;
        // TODO: close results obtained with getResultSet
        return this.results.get(this.position) != null;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        LOGGER.finest("Calling Statement.setFetchDirection(int direction=" + direction + ")");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        LOGGER.finest("Calling Statement.getFetchDirection()");
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        LOGGER.finest("Calling Statement.setFetchSize(int rows=" + rows + ")");
    }

    @Override
    public int getFetchSize() throws SQLException {
        LOGGER.finest("Calling Statement.getFetchSize()");
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        LOGGER.finest("Calling Statement.getResultSetConcurrency()");
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public int getResultSetType() throws SQLException {
        LOGGER.finest("Calling Statement.getResultSetType()");
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        LOGGER.finest("Calling Statement.addBatch(String sql=" + sql + ")");
    }

    @Override
    public void clearBatch() throws SQLException {
        LOGGER.finest("Calling Statement.clearBatch()");
    }

    @Override
    public int[] executeBatch() throws SQLException {
        LOGGER.finest("Calling Statement.executeBatch()");
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        LOGGER.finest("Calling Statement.getConnection()");
        return this.connection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        LOGGER.finest("Calling Statement.getMoreResults(int current=" + current + ")");
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        LOGGER.finest("Calling Statement.getGeneratedKeys()");
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        LOGGER.finest("Calling Statement.executeUpdate(String sql=" + sql + ", int autoGeneratedKeys=" + autoGeneratedKeys + ")");
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        LOGGER.finest("Calling Statement.executeUpdate(String sql=" + sql + ", int[] columnIndexes=...)");
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        LOGGER.finest("Calling Statement.executeUpdate(String sql=" + sql + ", String[] columnNames=...)");
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        LOGGER.finest("Calling Statement.execute(String sql=" + sql + ", int autoGeneratedKeys=" + autoGeneratedKeys + ")");
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        LOGGER.finest("Calling Statement.execute(String sql=" + sql + ", int[] columnIndexes=...)");
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        LOGGER.finest("Calling Statement.execute(String sql=" + sql + ", String[] columnNames=...)");
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        LOGGER.finest("Calling Statement.getResultSetHoldability()");
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        LOGGER.finest("Calling Statement.setMaxRows(int max=" + max + ")");
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        LOGGER.finest("Calling Statement.setEscapeProcessing(boolean enable=" + enable + ")");
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        LOGGER.finest("Calling Statement.getQueryTimeout()");
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        LOGGER.finest("Calling Statement.setQueryTimeout(int seconds=" + seconds + ")");
    }

    @Override
    public void cancel() throws SQLException {
        LOGGER.finest("Calling Statement.cancel()");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        LOGGER.finest("Calling Statement.getWarnings()");
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        LOGGER.finest("Calling Statement.clearWarnings()");
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        LOGGER.finest("Calling Statement.setCursorName(String name=" + name + ")");
    }

    @Override
    public boolean isClosed() throws SQLException {
        LOGGER.finest("Calling Statement.isClosed()");
        return this.isClosed;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        LOGGER.finest("Calling Statement.setPoolable(boolean poolable=" + poolable + ")");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        LOGGER.finest("Calling Statement.isPoolable()");
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        LOGGER.finest("Calling Statement.closeOnCompletion()");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        LOGGER.finest("Calling Statement.isCloseOnCompletion()");
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        LOGGER.finest("Calling Statement.unwrap(iface=" + iface + ")");
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        LOGGER.finest("Calling Statement.isWrapperFor(iface=" + iface + ")");
        return false;
    }
}
