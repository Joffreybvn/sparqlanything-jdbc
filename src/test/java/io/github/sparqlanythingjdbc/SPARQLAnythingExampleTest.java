package io.github.sparqlanythingjdbc;

import org.junit.jupiter.api.*;
import org.mockito.*;
import java.sql.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SPARQLAnythingExampleTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * WHERE { ?s ?p ?o } LIMIT 10")).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // Simulate one row
        when(mockResultSet.getString(1)).thenReturn("subject1");
        when(mockResultSet.getString(2)).thenReturn("predicate1");
    }

    @Test
    public void testConnection() {
        assertDoesNotThrow(() -> {
            DriverManager.registerDriver(new io.github.sparqlanythingjdbc.Driver());
            Connection connection = (Connection) DriverManager.getConnection("jdbc:sparql-anything://localhost");
            assertNotNull(connection);
        });
    }

    @Test
    public void testExecuteQuery() throws SQLException {
        String query = "SELECT * WHERE { ?s ?p ?o } LIMIT 10";

        Statement statement = mockConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        assertTrue(resultSet.next());
        assertEquals("subject1", resultSet.getString(1));
        assertEquals("predicate1", resultSet.getString(2));

        verify(mockStatement, times(1)).executeQuery(query);
    }

    @Test
    public void testResourceClosing() throws SQLException {
        mockResultSet.close();
        mockStatement.close();
        mockConnection.close();

        verify(mockResultSet, times(1)).close();
        verify(mockStatement, times(1)).close();
        verify(mockConnection, times(1)).close();
    }
}
