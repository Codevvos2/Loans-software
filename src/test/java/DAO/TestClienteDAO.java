package DAO;

import Model.Cliente;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestClienteDAO {

    static ClienteDAO dao;
    static int novoId;

    @BeforeAll
    static void setup() throws SQLException {
        dao = new ClienteDAO();
        novoId = dao.maiorID() + 1;
    }

    @Test
    @Order(1)
    void testInsertClienteBD() {
        Cliente cliente = new Cliente(novoId, "João Teste", "joao@teste.com", "Rua Teste 123", "9999-9999");
        boolean resultado = dao.InsertClienteBD(cliente);
        assertTrue(resultado, "Falha ao inserir cliente no banco");
    }

    @Test
    @Order(2)
    void testGetListaCliente() {
        ArrayList<Cliente> lista = dao.getListaCliente();
        assertNotNull(lista, "A lista de clientes não deve ser nula");
        assertTrue(lista.size() > 0, "A lista de clientes deve conter registros");
    }

}