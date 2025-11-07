package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestFerramenta {

    private final String NOME_PADRAO = "Martelo de Borracha";
    private final String MARCA_PADRAO = "Gedore";
    private final double VALOR_PADRAO = 50.50;
    private final String SETOR_PADRAO = "Montagem";
    private final int ESTOQUE_PADRAO = 25;
    private final int ID_PADRAO = 1;

    @Test
    void testConstrutorVazio() {
        Ferramenta f = new Ferramenta();
        assertNotNull(f, "O objeto Ferramenta não deve ser nulo ao usar o construtor vazio.");
    }

    @Test
    void testConstrutorCompleto() {
        Ferramenta f = new Ferramenta(
                ID_PADRAO, NOME_PADRAO, MARCA_PADRAO, VALOR_PADRAO, SETOR_PADRAO, ESTOQUE_PADRAO
        );

        assertEquals(ID_PADRAO, f.getIdf(), "O ID da Ferramenta deve ser o valor passado no construtor.");
        assertEquals(NOME_PADRAO, f.getNome(), "O Nome deve ser o valor passado no construtor.");
        assertEquals(MARCA_PADRAO, f.getMarca(), "A Marca deve ser o valor passado no construtor.");
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001, "O Valor deve ser o valor passado no construtor (tolerância 0.001).");
        assertEquals(SETOR_PADRAO, f.getSetor(), "O Setor deve ser o valor passado no construtor.");
        assertEquals(ESTOQUE_PADRAO, f.getEstoque(), "O Estoque deve ser o valor passado no construtor.");
    }

    @Test
    void testSettersEGetters() {
        Ferramenta f = new Ferramenta();

        f.setIdf(ID_PADRAO);
        f.setNome(NOME_PADRAO);
        f.setMarca(MARCA_PADRAO);
        f.setValor(VALOR_PADRAO);
        f.setSetor(SETOR_PADRAO);
        f.setEstoque(ESTOQUE_PADRAO);

        assertEquals(ID_PADRAO, f.getIdf(), "Getter de ID não corresponde ao valor setado.");
        assertEquals(NOME_PADRAO, f.getNome(), "Getter de Nome não corresponde ao valor setado.");
        assertEquals(MARCA_PADRAO, f.getMarca(), "Getter de Marca não corresponde ao valor setado.");
        assertEquals(VALOR_PADRAO, f.getValor(), 0.001, "Getter de Valor não corresponde ao valor setado.");
        assertEquals(SETOR_PADRAO, f.getSetor(), "Getter de Setor não corresponde ao valor setado.");
        assertEquals(ESTOQUE_PADRAO, f.getEstoque(), "Getter de Estoque não corresponde ao valor setado.");
    }
}