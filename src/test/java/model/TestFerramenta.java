package model;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertNotNull(f);

        f.setIdf(ID_PADRAO);
        f.setNome(NOME_PADRAO);
        f.setMarca(MARCA_PADRAO);
        f.setValor(VALOR_PADRAO);
        f.setSetor(SETOR_PADRAO);
        f.setEstoque(ESTOQUE_PADRAO);

        assertEquals(ID_PADRAO, f.getIdf());
        assertEquals(NOME_PADRAO, f.getNome());
        assertEquals(MARCA_PADRAO, f.getMarca());
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001);
        assertEquals(SETOR_PADRAO, f.getSetor());
        assertEquals(ESTOQUE_PADRAO, f.getEstoque());

        String representacao = f.toString();
        assertTrue(representacao.contains(NOME_PADRAO));

        assertNotNull(f.getListaFerramenta());
        f.maiorID();
        f.carregaFerramenta(ID_PADRAO);
        f.InsertFerramentaBD("Novo", "M", 10.0, "S", 1);
        f.DeleteFerramentaBD(ID_PADRAO);
        f.UpdateFerramentaBD(ID_PADRAO, "NovoNome", "M", 10.0, "S", 1);
    }

    @Test
    void testConstrutorCompleto() {
        Ferramenta f = new Ferramenta(ID_PADRAO, NOME_PADRAO, MARCA_PADRAO, VALOR_PADRAO, SETOR_PADRAO, ESTOQUE_PADRAO);
        assertEquals(ID_PADRAO, f.getIdf());
        assertEquals(NOME_PADRAO, f.getNome());
        assertEquals(MARCA_PADRAO, f.getMarca());
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001);
        assertEquals(SETOR_PADRAO, f.getSetor());
        assertEquals(ESTOQUE_PADRAO, f.getEstoque());
    }
}