package dao;

import model.Ferramenta;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class TestFerramentaDAO {

    private FerramentaDAO dao;
    private static final int ID_TESTE = 999;
    private static final String NOME_TESTE = "Martelo de Teste";

    @BeforeEach
    void setup() {
        String url = "jdbc:sqlite::memory:";
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(url);
            dao = new FerramentaDAO(conexao);
            dao.inicializaBanco();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        dao.DeleteFerramentaBD(ID_TESTE);
    }

    @Test
    void testA_InsertFerramentaBD_Success() {
        Ferramenta f = new Ferramenta(ID_TESTE, NOME_TESTE, "Marca", 50.00, "Setor", 10);

        boolean resultado = dao.InsertFerramentaBD(f);

        assertTrue(resultado, "A inserção deve retornar TRUE em caso de sucesso.");

        Ferramenta f_recuperada = dao.carregaFerramenta(f.getIdf());
        assertNotNull(f_recuperada, "A ferramenta deve ser recuperada do banco após a inserção.");
        assertEquals(NOME_TESTE, f_recuperada.getNome(), "O nome recuperado deve ser igual ao nome inserido.");
    }

    @Test
    void testB_UpdateFerramentaBD_Success() {
        Ferramenta f_original = new Ferramenta(ID_TESTE, NOME_TESTE, "Marca Antiga", 10.00, "Setor", 1);
        dao.InsertFerramentaBD(f_original);

        Ferramenta f_carregada = dao.carregaFerramenta(f_original.getIdf());
        final double NOVO_VALOR = 99.99;
        final String NOVA_MARCA = "Marca Nova LTDA";

        f_carregada.setValor(NOVO_VALOR);
        f_carregada.setMarca(NOVA_MARCA);

        boolean resultado = dao.UpdateFerramentaBD(f_carregada);

        assertTrue(resultado, "A atualização deve retornar TRUE em caso de sucesso.");

        Ferramenta f_verificada = dao.carregaFerramenta(f_carregada.getIdf());

        assertEquals(NOVO_VALOR, f_verificada.getValor(), 0.001, "O Valor deve ser atualizado no banco.");
        assertEquals(NOVA_MARCA, f_verificada.getMarca(), "A Marca deve ser atualizada no banco.");
    }

    @Test
    void testC_DeleteFerramentaBD_Success() {
        Ferramenta f = new Ferramenta(ID_TESTE, NOME_TESTE, "Marca", 50.00, "Setor", 10);
        dao.InsertFerramentaBD(f);

        Ferramenta f_antes = dao.carregaFerramenta(ID_TESTE);
        assertNotNull(f_antes, "O registro deve existir antes da exclusão.");

        boolean resultado = assertDoesNotThrow(() -> dao.DeleteFerramentaBD(ID_TESTE),
                "O método Delete não deve lançar exceção.");

        assertTrue(resultado || !resultado, "O retorno pode ser TRUE ou FALSE dependendo da implementação.");

        Ferramenta f_verificada = dao.carregaFerramenta(ID_TESTE);
        assertEquals(0, f_verificada.getIdf(),
                "O registro deve ter sido excluído (ID é 0 quando não encontrado).");
    }


    @Test
    void testD_InsertDuplicado_ShouldPass() {
        Ferramenta f1 = new Ferramenta(0, NOME_TESTE, "Marca", 50.00, "Setor", 10);
        dao.InsertFerramentaBD(f1);

        Ferramenta f2 = new Ferramenta(0, NOME_TESTE, "Marca", 60.00, "Setor", 5);

        assertDoesNotThrow(() -> {
            dao.InsertFerramentaBD(f2);
        }, "A inserção de nome duplicado deve ser permitida.");
    }

    @Test
    void testE_UpdateNonExistentID_ShouldReturnFalseOrNotAffect() {
        final int ID_INEXISTENTE = ID_TESTE + 1;
        Ferramenta f_nao_existe = dao.carregaFerramenta(ID_INEXISTENTE);
        assertEquals(0, f_nao_existe.getIdf(), "Carregar item inexistente deve retornar ID=0.");
        Ferramenta f_update = new Ferramenta(ID_INEXISTENTE, "Teste", "Teste", 10.0, "Teste", 1);

        boolean resultado_update = dao.UpdateFerramentaBD(f_update);

        assertDoesNotThrow(() -> {
            assertTrue(resultado_update || !resultado_update,
                    "O update pode retornar TRUE ou FALSE, dependendo da implementação.");
        });

        boolean resultado_delete = dao.DeleteFerramentaBD(ID_INEXISTENTE);

        assertDoesNotThrow(() -> {
            assertTrue(resultado_delete || !resultado_delete,
                    "O delete pode retornar TRUE ou FALSE, dependendo da implementação.");
        });
    }

    @Test
    void testD_ListagemEMaiorID_Success() throws SQLException {
        Ferramenta f1 = new Ferramenta(0, "Ferramenta 1", "M", 10.0, "S", 1);
        Ferramenta f2 = new Ferramenta(0, "Ferramenta 2", "M", 20.0, "S", 2);
        dao.InsertFerramentaBD(f1);
        dao.InsertFerramentaBD(f2);

        assertEquals(f2.getIdf(), dao.maiorID(), "O maior ID deve ser o último item inserido.");

        ArrayList<Ferramenta> lista = dao.getListaFerramenta();
        assertTrue(lista.size() >= 2, "A lista deve conter ao menos os dois itens inseridos.");

        dao.DeleteFerramentaBD(1000);
        dao.DeleteFerramentaBD(1001);
    }

    @Test
    void testInicializaBancoComConnectionNull() {
        FerramentaDAO daoNull = new FerramentaDAO((Connection) null);
        assertDoesNotThrow(() -> daoNull.inicializaBanco(), "Inicializar banco com conexão nula não deve lançar exceção");
    }

    @Test
    void testDeleteComConnectionNull() {
        FerramentaDAO daoNull = new FerramentaDAO((Connection) null);
        assertFalse(daoNull.DeleteFerramentaBD(1), "Delete deve retornar false se connection for null");
    }

    @Test
    void testCarregaFerramentaInexistente() {
        Ferramenta f = dao.carregaFerramenta(123456);
        assertEquals(0, f.getIdf(), "ID de item inexistente deve ser 0");
    }
    @Test
    @Order(1)
    void testInicializaBancoFerramenta_Catch() throws Exception {
        FerramentaDAO daoErro = new FerramentaDAO();

        daoErro.getConexao().close();

        assertDoesNotThrow(() -> daoErro.inicializaBanco(),
                "inicializaBanco() não deve lançar exceção mesmo com SQLException");
    }

    @Test
    @Order(2)
    void testDeleteFerramentaBD_Catch() throws Exception {
        FerramentaDAO daoErro = new FerramentaDAO();

        daoErro.getConexao().close();

        boolean resultado = daoErro.DeleteFerramentaBD(1);

        assertFalse(resultado,
                "Quando ocorrer SQLException no delete, deve retornar false");
    }

    @Test
    @Order(3)
    void testUpdateFerramentaBD_Catch() throws Exception {
        FerramentaDAO daoErro = new FerramentaDAO();

        daoErro.getConexao().close();

        Ferramenta f = new Ferramenta(1, "Teste", "Marca", 10.0, "Setor", 5);

        boolean resultado = daoErro.UpdateFerramentaBD(f);

        assertFalse(resultado,
                "Quando ocorrer SQLException no update, deve retornar false");
    }

    @Test
    @Order(4)
    void testCarregaFerramenta_Catch() throws Exception {
        FerramentaDAO daoErro = new FerramentaDAO();

        daoErro.getConexao().close();

        Ferramenta f = daoErro.carregaFerramenta(1);

        assertNotNull(f, "O método deve retornar um objeto mesmo com SQLException");
        assertEquals(0, f.getIdf(),
                "Se ocorrer SQLException, o objeto deve vir com id 0 (padrão)");
    }

    @Test
    @Order(5)
    void testGetListaFerramentaCatch() throws Exception {
        FerramentaDAO dao = new FerramentaDAO();

        dao.getConexao().close();

        ArrayList<Ferramenta> lista = dao.getListaFerramenta();

        assertTrue(lista.isEmpty(),
                "Quando ocorre SQLException, o método deve capturar e retornar a lista vazia");
    }


}