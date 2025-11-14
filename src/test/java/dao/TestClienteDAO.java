package dao;

import model.Cliente;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Test
    @Order(3)
    void testCarregaCliente() {
        Cliente cliente = dao.carregaCliente(novoId);
        assertEquals("João Teste", cliente.getNome(), "Nome não corresponde");
        assertEquals("joao@teste.com", cliente.getEmail(), "Email não corresponde");
    }

    @Test
    @Order(4)
    void testUpdateClienteBD() {
        Cliente clienteAtualizado = new Cliente(novoId, "João Atualizado", "joao@novoemail.com", "Rua Nova 456", "8888-8888");
        boolean resultado = dao.UpdateClienteBD(clienteAtualizado);
        assertTrue(resultado, "Falha ao atualizar cliente");

        Cliente clienteBanco = dao.carregaCliente(novoId);
        assertEquals("João Atualizado", clienteBanco.getNome(), "O nome não foi atualizado corretamente");
    }

    @Test
    @Order(5)
    void testDeleteClienteBD() {
        boolean resultado = dao.DeleteClienteBD(novoId);
        assertTrue(resultado, "Falha ao deletar cliente");

        Cliente clienteBanco = dao.carregaCliente(novoId);
        assertNull(clienteBanco.getNome(), "Cliente não foi removido corretamente");
    }

    @Test
    @Order(6)
    void testInsertClienteBD_Catch() throws Exception {
        ClienteDAO daoErro = new ClienteDAO();

        daoErro.getConexao().close();

        Cliente cliente = new Cliente(1, "Teste", "teste@teste.com", "Rua X", "9999-9999");

        boolean resultado = daoErro.InsertClienteBD(cliente);

        assertFalse(resultado,
                "Quando ocorrer SQLException, InsertClienteBD deve retornar false");
    }

    @Test
    @Order(7)
    void testGetListaCliente_Catch() throws Exception {
        ClienteDAO daoErro = new ClienteDAO();

        daoErro.getConexao().close();

        ArrayList<Cliente> lista = daoErro.getListaCliente();

        assertTrue(lista.isEmpty(),
                "Quando ocorrer SQLException em getListaCliente(), deve retornar lista vazia");
    }

    @Test
    @Order(8)
    void testCarregaCliente_Catch() throws Exception {
        ClienteDAO daoErro = new ClienteDAO();

        daoErro.getConexao().close();

        Cliente cliente = daoErro.carregaCliente(1);

        assertNotNull(cliente, "O método deve retornar um objeto Cliente, mesmo em erro");
        assertEquals(0, cliente.getIdc(),
                "Em caso de SQLException, o cliente deve vir com id 0 (objeto padrão)");
    }

    @Test
    @Order(9)
    void testInicializaBanco_Catch() throws Exception {
        ClienteDAO daoErro = new ClienteDAO();

        daoErro.getConexao().close();

        assertDoesNotThrow(() -> daoErro.inicializaBanco(),
                "inicializaBanco() não deve lançar exceção mesmo com SQLException");
    }

    @Test
    @Order(10)
    void testUpdateClienteBDCatch() throws Exception {
        ClienteDAO dao = new ClienteDAO();

        dao.getConexao().close();

        Cliente cli = new Cliente(1, "Teste", "t@t.com", "Rua X", "9999");

        assertThrows(RuntimeException.class,
                () -> dao.UpdateClienteBD(cli),
                "O método deve relançar RuntimeException quando ocorre SQLException");
    }

    @Test
    @Order(11)
    void testConstrutorChamaInicializaBancoQuandoConexaoNaoEhNull() {
        ClienteDAO dao = new ClienteDAO();

        assertNotNull(dao.getConexao(), "Conexão não deveria ser null no construtor");

        try (Statement st = dao.getConexao().createStatement()) {
            st.executeQuery("SELECT 1 FROM tb_cliente LIMIT 1;");
        } catch (SQLException e) {
            fail("inicializaBanco() NÃO foi chamado pois tb_cliente não existe!");
        }
    }


    @Test
    @Order(12)
    void testGetListaClienteConnectionNull() throws Exception {

        ClienteDAO dao = new ClienteDAO();

        Field field = dao.getClass().getSuperclass().getDeclaredField("connection");
        field.setAccessible(true);
        field.set(dao, null);

        ArrayList<Cliente> lista = dao.getListaCliente();

        assertNotNull(lista, "Lista não deve ser null quando connection é null");
        assertEquals(0, lista.size(), "Lista deve estar vazia quando connection é null");
    }



    @Test
    @Order(13)
    void testInsertClienteBDConnectionNull() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        dao.getConexao().close();

        Cliente cli = new Cliente(1, "X", "x@x.com", "Rua", "999");

        boolean resultado = dao.InsertClienteBD(cli);

        assertFalse(resultado,
                "Quando connection == null, InsertClienteBD deve retornar false");
    }

    @Test
    @Order(14)
    void testDeleteClienteBDConnectionNull() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        dao.getConexao().close();

        boolean resultado = dao.DeleteClienteBD(1);

        assertFalse(resultado,
                "Quando connection == null, DeleteClienteBD deve retornar false");
    }

    @Test
    @Order(15)
    void testUpdateClienteBDConnectionNull() throws Exception {

        ClienteDAO dao = new ClienteDAO();

        Field field = ClienteDAO.class.getSuperclass().getDeclaredField("connection");
        field.setAccessible(true);

        field.set(dao, null);

        Cliente cli = new Cliente();

        boolean resultado = dao.UpdateClienteBD(cli);

        assertFalse(resultado);
    }


    @Test
    @Order(16)
    void testCarregaClienteConnectionNull() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        dao.getConexao().close();

        Cliente cli = dao.carregaCliente(1);

        assertNotNull(cli);
        assertEquals(0, cli.getIdc(),
                "Quando connection == null, carregaCliente deve retornar objeto vazio");
    }
}