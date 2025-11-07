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

    @Test
    void testGetListaEmprestimo() throws Exception {
        when(connectionMock.createStatement()).thenReturn(statementMock);
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, false);
        when(resultSetMock.getInt("ide")).thenReturn(1);
        when(resultSetMock.getInt("quantidade")).thenReturn(2);
        when(resultSetMock.getString("dataloc")).thenReturn("2025-01-01");
        when(resultSetMock.getString("datadev")).thenReturn("2025-02-01");
        when(resultSetMock.getString("status")).thenReturn("ativo");
        when(resultSetMock.getInt("idc")).thenReturn(3);
        when(resultSetMock.getInt("idf")).thenReturn(4);

        ArrayList<Emprestimo> lista = dao.getListaEmprestimo();

        assertEquals(1, lista.size());
        Emprestimo e = lista.get(0);
        assertEquals("ativo", e.getStatus());
    }

    @Test
    void testInsertEmprestimoBD() throws Exception {
        Emprestimo emp = new Emprestimo(1, 5, "2025-01-01", "2025-02-01", "ativo", 2, 3);

        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        boolean result = dao.InsertEmprestimoBD(emp);

        assertTrue(result);
        verify(preparedStatementMock, times(1)).execute();
        verify(preparedStatementMock, times(1)).close();
    }

    @Test
    void testDeleteEmprestimoBD() throws Exception {
        when(connectionMock.createStatement()).thenReturn(statementMock);
        boolean result = dao.DeleteEmprestimoBD(5);
        assertTrue(result);
        verify(statementMock, times(1)).executeUpdate(contains("DELETE FROM tb_emprestimo"));
    }

    @Test
    void testUpdateEmprestimoBD() throws Exception {
        Emprestimo emp = new Emprestimo(1, 2, "2025-01-01", "2025-02-01", "ativo", 1, 2);
        when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
        boolean result = dao.UpdateEmprestimoBD(emp);

        assertTrue(result);
        verify(preparedStatementMock, times(1)).execute();
    }
    
    @Test
    void testCarregaEmprestimo() throws Exception {
        when(connectionMock.createStatement()).thenReturn(statementMock);
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt("quantidade")).thenReturn(5);
        when(resultSetMock.getString("dataloc")).thenReturn("2025-01-01");
        when(resultSetMock.getString("datadev")).thenReturn("2025-02-01");
        when(resultSetMock.getString("status")).thenReturn("ativo");
        when(resultSetMock.getInt("idc")).thenReturn(2);
        when(resultSetMock.getInt("idf")).thenReturn(3);

        Emprestimo e = dao.carregaEmprestimo(10);

        assertEquals(10, e.getIde());
        assertEquals(5, e.getQuantidade());
        assertEquals("2025-01-01", e.getDataloc());
    }
}
