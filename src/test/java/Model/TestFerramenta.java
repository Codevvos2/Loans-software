package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestFerramenta {

    private final String NOME_PADRAO = "Martelo de Borracha";
    private final String MARCA_PADRAO = "Gedore";
    private final double VALOR_PADRAO = 50.50;
    private final String SETOR_PADRAO = "Montagem";
    private final int ESTOQUE_PADRAO = 25;
    private final int ID_PADRAO = 1;

    @Test
    void testSettersEGettersECoberturaTotal() throws SQLException {

        Ferramenta f = new Ferramenta();
        assertNotNull(f, "O objeto Ferramenta não deve ser nulo.");

        f.setIdf(ID_PADRAO);
        f.setNome(NOME_PADRAO);
        f.setMarca(MARCA_PADRAO);
        f.setValor(VALOR_PADRAO);
        f.setSetor(SETOR_PADRAO);
        f.setEstoque(ESTOQUE_PADRAO);

        assertEquals(ID_PADRAO, f.getIdf(), "Getter de ID falhou.");
        assertEquals(NOME_PADRAO, f.getNome(), "Getter de Nome falhou.");
        assertEquals(MARCA_PADRAO, f.getMarca(), "Getter de Marca falhou.");
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001, "Getter de Valor falhou.");
        assertEquals(SETOR_PADRAO, f.getSetor(), "Getter de Setor falhou.");
        assertEquals(ESTOQUE_PADRAO, f.getEstoque(), "Getter de Estoque falhou.");

        String representacao = f.toString();
        assertTrue(representacao.contains(NOME_PADRAO), "O toString deve incluir o nome.");

        assertNotNull(f.getListaFerramenta(), "A lista deve ser inicializada.");
        f.maiorID();
        f.carregaFerramenta(ID_PADRAO);

        f.InsertFerramentaBD("Novo", "M", 10.0, "S", 1);
        f.DeleteFerramentaBD(ID_PADRAO);
        f.UpdateFerramentaBD(ID_PADRAO, "NovoNome", "M", 10.0, "S", 1);
    }

    @Test
    void testConstrutorCompleto() {
        Ferramenta f = new Ferramenta(
                ID_PADRAO, NOME_PADRAO, MARCA_PADRAO, VALOR_PADRAO, SETOR_PADRAO, ESTOQUE_PADRAO
        );
        assertEquals(ID_PADRAO, f.getIdf(), "ID deve ser igual.");
        assertEquals(NOME_PADRAO, f.getNome(), "Nome deve ser igual.");
        assertEquals(MARCA_PADRAO, f.getMarca(), "Marca deve ser igual.");
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001, "Valor deve ser igual.");
        assertEquals(SETOR_PADRAO, f.getSetor(), "Setor deve ser igual.");
        assertEquals(ESTOQUE_PADRAO, f.getEstoque(), "Estoque deve ser igual.");
    }
}