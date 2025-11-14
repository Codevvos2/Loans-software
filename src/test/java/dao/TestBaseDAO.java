package dao;

import model.Ferramenta;
import org.junit.jupiter.api.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBaseDAO {

    static BaseDAO dao;
    static Method methodIsSafeName;

    static class BaseDAOFake extends BaseDAO {
        public BaseDAOFake() {
            super();
        }
    }

    @BeforeAll
    static void setup() throws Exception {
        dao = new BaseDAOFake();

        // Acessa o método privado via reflection
        methodIsSafeName = BaseDAO.class.getDeclaredMethod("isSafeName", String.class);
        methodIsSafeName.setAccessible(true);
    }

    @Test
    @Order(1)
    void testSafeNameValido() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, "Cliente123");
        assertTrue(result, "Nome deveria ser considerado seguro");
    }

    @Test
    @Order(2)
    void testSafeNameComUnderscore() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, "tb_emprestimo");
        assertTrue(result, "Nome com underscore deveria ser válido");
    }

    @Test
    @Order(3)
    void testSafeNameNull() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, (String) null);
        assertFalse(result, "Nome null não pode ser válido");
    }

    @Test
    @Order(4)
    void testSafeNameComEspaco() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, "tb cliente");
        assertFalse(result, "Nome com espaço não deve ser permitido");
    }

    @Test
    @Order(5)
    void testSafeNameCaracteresInvalidos() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, "DROP_TABLE;");
        assertFalse(result, "Nome com caracteres especiais deve ser inválido");
    }

    @Test
    @Order(6)
    void testSafeNameComHifen() throws Exception {
        boolean result = (boolean) methodIsSafeName.invoke(dao, "nome-invalido");
        assertFalse(result, "Nome com hífen deve ser inválido");
    }
    @Test
    @Order(7)
    void testDeleteClienteBD_geraSQLException() throws Exception {
        ClienteDAO daoErro = new ClienteDAO();
        daoErro.getConexao().close();

        boolean resultado = daoErro.DeleteClienteBD(1);

        assertFalse(resultado, "Em caso de SQLException o método deve retornar false");
    }

    @Test
    @Order(8)
    void testInsertFerramentaBDCatch() throws Exception {

        FerramentaDAO dao = new FerramentaDAO();

        dao.getConexao().close();

        Ferramenta ferramenta =
                new Ferramenta(0, "Furadeira", "Bosch", 199.99, "Eletrica", 10);

        assertThrows(RuntimeException.class,
                () -> dao.InsertFerramentaBD(ferramenta),
                "O método deve relançar RuntimeException quando ocorre SQLException");
    }


}
