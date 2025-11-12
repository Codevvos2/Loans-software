package dao;

import model.Emprestimo;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmprestimoDAO {

    static EmprestimoDAO dao;
    static int novoId;

    @BeforeAll
    static void setup() throws SQLException {
        dao = new EmprestimoDAO();
        dao.inicializaBanco();
        novoId = dao.maiorID() + 1;
    }

    @Test
    @Order(1)
    void testInsertEmprestimoBD() {
        Emprestimo emp = new Emprestimo(novoId, 5, "2025-01-01", "2025-02-01", "ativo", 1, 1);
        boolean resultado = dao.InsertEmprestimoBD(emp);
        assertTrue(resultado);
    }

    @Test
    @Order(2)
    void testGetListaEmprestimo() {
        ArrayList<Emprestimo> lista = dao.getListaEmprestimo();
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
    }

    @Test
    @Order(3)
    void testCarregaEmprestimo() {
        Emprestimo emp = dao.carregaEmprestimo(novoId);
        assertEquals(5, emp.getQuantidade());
        assertEquals("ativo", emp.getStatus());
    }

    @Test
    @Order(4)
    void testUpdateEmprestimoBD() {
        Emprestimo empAtualizado = new Emprestimo(novoId, 10, "2025-01-01", "2025-02-01", "finalizado", 1, 1);
        boolean resultado = dao.UpdateEmprestimoBD(empAtualizado);
        assertTrue(resultado);

        Emprestimo empBanco = dao.carregaEmprestimo(novoId);
        assertEquals(10, empBanco.getQuantidade());
        assertEquals("finalizado", empBanco.getStatus());
    }

    @Test
    @Order(5)
    void testDeleteEmprestimoBD() {
        boolean resultado = dao.DeleteEmprestimoBD(novoId);
        assertTrue(resultado);

        Emprestimo empBanco = dao.carregaEmprestimo(novoId);
        assertEquals(0, empBanco.getIde());
    }
}
