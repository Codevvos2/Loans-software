package DAO;

import Model.Emprestimo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestEmprestimoDAO {

    private EmprestimoDAO dao;
    private Connection connectionMock;
    private Statement statementMock;
    private PreparedStatement preparedStatementMock;
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() throws Exception {
        dao = new EmprestimoDAO();
        connectionMock = mock(Connection.class);
        statementMock = mock(Statement.class);
        preparedStatementMock = mock(PreparedStatement.class);
        resultSetMock = mock(ResultSet.class);

        EmprestimoDAO spyDao = Mockito.spy(dao);
        doReturn(connectionMock).when(spyDao).getConexao();
        dao = spyDao;
    }

    @Test
    void testMaiorID() throws Exception {
        when(connectionMock.createStatement()).thenReturn(statementMock);
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt("ide")).thenReturn(10);

        int result = dao.maiorID();

        assertEquals(10, result);
        verify(statementMock, times(1)).executeQuery("SELECT MAX(ide) ide FROM tb_emprestimo");
    }
}
