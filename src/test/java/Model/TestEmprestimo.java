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
}