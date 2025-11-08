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
    void testGetListaCliente() {
        ArrayList<Cliente> lista = new ArrayList<>();
        lista.add(new Cliente(1, "João Teste", "email@teste.com", "Rua Teste", "99999999"));

        when(daoMock.getListaCliente()).thenReturn(lista);

        ArrayList result = cliente.getListaCliente();

        assertEquals(lista, result);
        verify(daoMock, times(1)).getListaCliente();
    }
    @Test
    void testInsertClienteBD() throws SQLException {
        when(daoMock.maiorID()).thenReturn(5);

        boolean result = cliente.InsertClienteBD("João da Silva", "joao@email.com", "Rua das Flores, 123", "(11) 98765-4321");

        assertTrue(result);
        verify(daoMock, times(1)).InsertClienteBD(any(Cliente.class));
        verify(daoMock, times(1)).maiorID();
    }
    @Test
    void testDeleteClienteBD() {
        boolean result = cliente.DeleteClienteBD(10);

        assertTrue(result);
        verify(daoMock, times(1)).DeleteClienteBD(10);
    }
    @Test
    void testUpdateClienteBD() {
        boolean result = cliente.UpdateClienteBD(1, "Maria", "maria@email.com", "Av. Brasil", "(21) 91234-5678");

        assertTrue(result);
        verify(daoMock, times(1)).UpdateClienteBD(any(Cliente.class));
    }
    @Test
    void testCarregaCliente() {
        cliente.carregaCliente(15);
        verify(daoMock, times(1)).carregaCliente(15);
    }

    @Test
    void testMaiorID() throws SQLException {
        when(daoMock.maiorID()).thenReturn(8);
        assertEquals(8, cliente.maiorID());
        verify(daoMock, times(1)).maiorID();
    }
}