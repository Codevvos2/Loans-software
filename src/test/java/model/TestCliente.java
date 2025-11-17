package model;

import dao.ClienteDAO;
import dao.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestCliente {

    private Cliente cliente;
    private ClienteDAO dao;

    @BeforeEach
    void setUp() throws SQLException {
        dao = new ClienteDAO(Database.getConnection());
        cliente = new Cliente();
    }

    @Test
    void testGettersAndSetters() {
        cliente.setIdc(1);
        cliente.setNome("João da Silva");
        cliente.setEmail("joao@email.com");
        cliente.setEndereco("Rua das Flores, 123");
        cliente.setTelefone("(11) 98765-4321");

        assertEquals(1, cliente.getIdc());
        assertEquals("João da Silva", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("Rua das Flores, 123", cliente.getEndereco());
        assertEquals("(11) 98765-4321", cliente.getTelefone());
    }

    @Test
    void testToStringContainsFields() {
        cliente.setNome("João da Silva");
        cliente.setEmail("joao@email.com");
        cliente.setEndereco("Rua das Flores, 123");
        cliente.setTelefone("(11) 98765-4321");

        String texto = cliente.toString();
        assertTrue(texto.contains("Nome"));
        assertTrue(texto.contains("Email"));
        assertTrue(texto.contains("Endereco"));
        assertTrue(texto.contains("Telefone"));
    }

    @Test
    void testConstrutorComParametros() {
        Cliente c = new Cliente(1, "Maria Oliveira", "maria@email.com", "Av. Brasil, 456", "(21) 91234-5678");
        assertEquals(1, c.getIdc());
        assertEquals("Maria Oliveira", c.getNome());
        assertEquals("maria@email.com", c.getEmail());
        assertEquals("Av. Brasil, 456", c.getEndereco());
        assertEquals("(21) 91234-5678", c.getTelefone());
    }

    @Test
    void testGetListaCliente() throws SQLException {
        ArrayList<Cliente> lista = cliente.getListaCliente();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    void testInsertClienteBD() throws SQLException {
        boolean result = cliente.InsertClienteBD("Teste", "teste@email.com", "Rua Teste, 1", "12345678");
        assertTrue(result);
    }

    @Test
    void testDeleteClienteBD() throws SQLException {
        boolean result = cliente.DeleteClienteBD(cliente.maiorID());
        assertTrue(result);
    }

    @Test
    void testUpdateClienteBD() throws SQLException {
        boolean result = cliente.UpdateClienteBD(cliente.maiorID(), "Atualizado", "atual@email.com", "Rua Atual, 2", "87654321");
        assertTrue(result);
    }


    @Test
    void testMaiorID() throws SQLException {
        int id = cliente.maiorID();
        assertTrue(id >= 0);
    }

    @Test
    @Order(1)
    void testClienteConstrutorEnderecoTelefone() {
        Cliente c = new Cliente("Rua Teste", "9999-9999");

        assertEquals("Rua Teste", c.getEndereco(), "Endereço deve ser atribuído corretamente");
        assertEquals("9999-9999", c.getTelefone(), "Telefone deve ser atribuído corretamente");
    }

    @Test
    @Order(2)
    void testClienteCarregaClienteSempreRetornaNull() {
        Cliente cliente = new Cliente("Rua Teste", "9999-9999");

        Cliente resultado = cliente.carregaCliente(1);

        assertNull(resultado,
                "O método carregaCliente do model Cliente sempre deve retornar null");
    }
}