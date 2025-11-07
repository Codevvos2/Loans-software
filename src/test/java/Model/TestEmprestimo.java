package Model;

import DAO.EmprestimoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestEmprestimo {

    private EmprestimoDAO daoMock;
    private Emprestimo emprestimo;

    @BeforeEach
    void setUp() throws Exception {
        daoMock = Mockito.mock(EmprestimoDAO.class);
        emprestimo = new Emprestimo();

        Field daoField = Emprestimo.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(emprestimo, daoMock);
    }

    @Test
    void testGettersAndSetters() {
        emprestimo.setDataloc("2025-10-01");
        emprestimo.setDatadev("2025-11-01");
        emprestimo.setStatus("ativo");

        assertEquals("2025-10-01", emprestimo.getDataloc());
        assertEquals("2025-11-01", emprestimo.getDatadev());
        assertEquals("ativo", emprestimo.getStatus());
    }

    @Test
    void testToStringContainsFields() {
        emprestimo.setDataloc("2025-10-01");
        emprestimo.setDatadev("2025-11-01");
        emprestimo.setStatus("ativo");

        String texto = emprestimo.toString();
        assertTrue(texto.contains("Dataloc"));
        assertTrue(texto.contains("Datadev"));
        assertTrue(texto.contains("Status"));
    }

    @Test
    void testConstrutorComParametros() {
        Emprestimo e = new Emprestimo("2025-01-01", "2025-02-01", "ativo");
        assertEquals("2025-01-01", e.getDataloc());
        assertEquals("2025-02-01", e.getDatadev());
        assertEquals("ativo", e.getStatus());
    }

    @Test
    void testGetListaEmprestimo() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Emprestimo 1");

        when(daoMock.getListaEmprestimo()).thenReturn(lista);

        ArrayList result = emprestimo.getListaEmprestimo();

        assertEquals(lista, result);
        verify(daoMock, times(1)).getListaEmprestimo();
    }

    @Test
    void testInsertEmprestimoBD() throws SQLException {
        when(daoMock.maiorID()).thenReturn(10);

        boolean result = emprestimo.InsertEmprestimoBD(5, "2025-10-01", "2025-11-01", "ativo", 2, 3);

        assertTrue(result);
        verify(daoMock, times(1)).InsertEmprestimoBD(any(Emprestimo.class));
        verify(daoMock, times(1)).maiorID();
    }

    @Test
    void testDeleteEmprestimoBD() {
        boolean result = emprestimo.DeleteEmprestimoBD(7);

        assertTrue(result);
        verify(daoMock, times(1)).DeleteEmprestimoBD(7);
    }

    @Test
    void testUpdateEmprestimoBD() {
        boolean result = emprestimo.UpdateEmprestimoBD(1, 3, "2025-10-01", "2025-11-01", "ativo", 2, 3);

        assertTrue(result);
        verify(daoMock, times(1)).UpdateEmprestimoBD(any(Emprestimo.class));
    }

    @Test
    void testCarregaEmprestimo() {
        emprestimo.carregaEmprestimo(10);
        verify(daoMock, times(1)).carregaEmprestimo(10);
    }

    @Test
    void testMaiorID() throws SQLException {
        when(daoMock.maiorID()).thenReturn(7);
        assertEquals(7, emprestimo.maiorID());
        verify(daoMock, times(1)).maiorID();
    }
}