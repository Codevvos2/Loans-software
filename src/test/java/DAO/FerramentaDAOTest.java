package DAO;

import Model.Ferramenta;
import org.junit.jupiter.api.*;;
import java.sql.SQLException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FerramentaDAOTest {

    private FerramentaDAO dao;
    private static final int ID_TESTE = 999;
    private static final String NOME_TESTE = "Martelo de Teste";

    @BeforeEach
    void setup() throws SQLException {
        Connection testConn = new FerramentaDAO().getConexao();
        dao = new FerramentaDAO(testConn);
        dao.DeleteFerramentaBD(ID_TESTE);
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

        Ferramenta f_recuperada = dao.carregaFerramenta(ID_TESTE);
        assertNotNull(f_recuperada, "A ferramenta deve ser recuperada do banco após a inserção.");
        assertEquals(NOME_TESTE, f_recuperada.getNome(), "O nome recuperado deve ser igual ao nome inserido.");
    }

    @Test
    void testB_UpdateFerramentaBD_Success() {
        Ferramenta f_original = new Ferramenta(ID_TESTE, NOME_TESTE, "Marca Antiga", 10.00, "Setor", 1);
        dao.InsertFerramentaBD(f_original);

        Ferramenta f_carregada = dao.carregaFerramenta(ID_TESTE);
        final double NOVO_VALOR = 99.99;
        final String NOVA_MARCA = "Marca Nova LTDA";

        f_carregada.setValor(NOVO_VALOR);
        f_carregada.setMarca(NOVA_MARCA);

        boolean resultado = dao.UpdateFerramentaBD(f_carregada);

        assertTrue(resultado, "A atualização deve retornar TRUE em caso de sucesso.");

        Ferramenta f_verificada = dao.carregaFerramenta(ID_TESTE);

        assertEquals(NOVO_VALOR, f_verificada.getValor(), 0.001, "O Valor deve ser atualizado no banco.");
        assertEquals(NOVA_MARCA, f_verificada.getMarca(), "A Marca deve ser atualizada no banco.");
    }

    @Test
    void testC_DeleteFerramentaBD_Success() {
        Ferramenta f = new Ferramenta(ID_TESTE, NOME_TESTE, "Marca", 50.00, "Setor", 10);
        dao.InsertFerramentaBD(f);

        Ferramenta f_antes = dao.carregaFerramenta(ID_TESTE);
        assertNotNull(f_antes, "O registro deve existir antes da exclusão.");

        boolean resultado = dao.DeleteFerramentaBD(ID_TESTE);

        assertTrue(resultado, "A exclusão deve retornar TRUE em caso de sucesso.");

        Ferramenta f_verificada = dao.carregaFerramenta(ID_TESTE);
        assertEquals(0, f_verificada.getIdf(), "O registro deve ter sido excluído (ID é 0 quando não encontrado).");
    }
}