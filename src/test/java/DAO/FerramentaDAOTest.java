package DAO;

import Model.Ferramenta;
import org.junit.jupiter.api.*;;

import static org.junit.jupiter.api.Assertions.*;

public class FerramentaDAOTest {

    private FerramentaDAO dao;
    private static final int ID_TESTE = 999;
    private static final String NOME_TESTE = "Martelo de Teste";

    @BeforeEach
    void setup() {
        dao = new FerramentaDAO();
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
}