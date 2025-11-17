package dao;

import model.Emprestimo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmprestimoDAO {

    static EmprestimoDAO dao;
    static int novoId;
    static Connection testConn;

    private void setConnectionNull(EmprestimoDAO dao) throws Exception {
        Field field = EmprestimoDAO.class.getSuperclass().getDeclaredField("connection");
        field.setAccessible(true);
        field.set(dao, null);
    }


    @BeforeAll
    static void setup() throws SQLException {
        dao = new EmprestimoDAO();
        dao.inicializaBanco();
        novoId = dao.maiorID() + 1;
        testConn = DriverManager.getConnection("jdbc:sqlite::memory:");

        testConn.createStatement().execute("""
            CREATE TABLE tb_emprestimo (
                ide INTEGER PRIMARY KEY,
                quantidade INTEGER
            )
        """);
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

    @Test
    @Order(6)
    void testDeleteEmprestimoBDCatch() throws Exception {
        EmprestimoDAO daoErro = new EmprestimoDAO();

        daoErro.getConexao().close();

        boolean resultado = daoErro.DeleteEmprestimoBD(1);

        assertFalse(resultado,
                "Quando ocorrer SQLException, o método deve retornar false");
    }

    @Test
    @Order(7)
    void testInsertEmprestimoBDCatch() throws Exception {
        EmprestimoDAO daoErro = new EmprestimoDAO();
        daoErro.getConexao().close(); // força SQLException

        boolean resultado = daoErro.InsertEmprestimoBD(null);

        assertFalse(resultado,
                "Quando ocorrer SQLException, InsertEmprestimoBD deve retornar false");
    }

    @Test
    @Order(8)
    void testUpdateEmprestimoBDCatch() throws Exception {
        EmprestimoDAO daoErro = new EmprestimoDAO();
        daoErro.getConexao().close(); // força SQLException

        boolean resultado = daoErro.UpdateEmprestimoBD(null);

        assertFalse(resultado,
                "Quando ocorrer SQLException, UpdateEmprestimoBD deve retornar false");
    }

    @Test
    @Order(9)
    void testGetListaEmprestimoCatch() throws Exception {
        EmprestimoDAO daoErro = new EmprestimoDAO();
        daoErro.getConexao().close(); // força SQLException

        assertDoesNotThrow(() -> daoErro.getListaEmprestimo(),
                "SQLException deve ser capturada dentro de getListaEmprestimo()");
    }

    @Test
    @Order(10)
    void testCarregaEmprestimoCatch() throws Exception {
        EmprestimoDAO daoErro = new EmprestimoDAO();
        daoErro.getConexao().close(); // força SQLException

        assertDoesNotThrow(() -> daoErro.carregaEmprestimo(1),
                "SQLException deve ser capturada dentro de carregaEmprestimo()");
    }

    @Test
    @Order(11)
    void testConstrutorComConexao() {
        EmprestimoDAO dao = new EmprestimoDAO(testConn);

        assertNotNull(dao, "DAO não deve ser nulo");
        assertNotNull(dao.connection, "A conexão não deve ser nula");
        assertSame(testConn, dao.connection, "A conexão passada no construtor deve ser usada pelo DAO");
    }
    @Test
    @Order(12)
    void testInicializaBancoCatch() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        dao.getConexao().close();

        assertDoesNotThrow(() -> dao.inicializaBanco(),
                "O método inicializaBanco() deve capturar SQLException e não lançar exceção");
    }

    @Test
    @Order(13)
    void testInicializaBancoConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        assertDoesNotThrow(() -> dao.inicializaBanco());
    }

    @Test
    @Order(14)
    void testGetListaEmprestimoConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        ArrayList<Emprestimo> lista = dao.getListaEmprestimo();

        assertNotNull(lista);
        assertEquals(0, lista.size());
    }

    @Test
    @Order(15)
    void testInsertEmprestimoBDConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        Emprestimo e = new Emprestimo();

        boolean resultado = dao.InsertEmprestimoBD(e);

        assertFalse(resultado);
    }

    @Test
    @Order(16)
    void testDeleteEmprestimoBDConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        boolean resultado = dao.DeleteEmprestimoBD(1);

        assertFalse(resultado);
    }

    @Test
    @Order(17)
    void testUpdateEmprestimoBDConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        Emprestimo e = new Emprestimo();

        boolean resultado = dao.UpdateEmprestimoBD(e);

        assertFalse(resultado);
    }

    @Test
    @Order(18)
    void testCarregaEmprestimoConnectionNull() throws Exception {
        EmprestimoDAO dao = new EmprestimoDAO();

        setConnectionNull(dao);

        Emprestimo e = dao.carregaEmprestimo(1);

        assertNotNull(e);
    }


}
