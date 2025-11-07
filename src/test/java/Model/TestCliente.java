package Model;

import DAO.ClienteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestCliente {

    private ClienteDAO daoMock;
    private Cliente cliente;

    @BeforeEach
    void setUp() throws Exception {
        daoMock = Mockito.mock(ClienteDAO.class);
        cliente = new Cliente();


        Field daoField = Cliente.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(cliente, daoMock);
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
}