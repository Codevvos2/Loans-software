package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestEmprestimo {

    private Emprestimo emprestimo;

    @BeforeEach
    void setUp() throws SQLException {
        emprestimo = new Emprestimo();
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
    void testGetListaEmprestimo() throws SQLException {
        ArrayList<Emprestimo> lista = emprestimo.getListaEmprestimo();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    void testInsertEmprestimoBD() throws SQLException {
        boolean result = emprestimo.InsertEmprestimoBD(1, "2025-10-01", "2025-11-01", "ativo", 1, 1);
        assertTrue(result);
    }

    @Test
    void testDeleteEmprestimoBD() throws SQLException {
        boolean result = emprestimo.DeleteEmprestimoBD(emprestimo.maiorID());
        assertTrue(result);
    }

    @Test
    void testUpdateEmprestimoBD() throws SQLException {
        boolean result = emprestimo.UpdateEmprestimoBD(emprestimo.maiorID(), 1, "2025-10-01", "2025-11-01", "ativo", 1, 1);
        assertTrue(result);
    }


    @Test
    void testMaiorID() throws SQLException {
        int id = emprestimo.maiorID();
        assertTrue(id >= 0);
    }
}