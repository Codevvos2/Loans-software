package model;

/**
 * Classe abstrata que define a estrutura básica de um empréstimo.
 * <p>
 * Contém os principais atributos e métodos de acesso utilizados pelas classes concretas,
 * como {@link Emprestimo}, para representar os dados de um empréstimo entre clientes e ferramentas.
 * </p>
 *
 * <p>Esta classe segue o padrão de herança para promover o reaproveitamento de código e
 * manter a consistência entre diferentes tipos de empréstimos.</p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public abstract class EmprestimoAbstract {

    /** Identificador único do empréstimo. */
    private int ide;

    /** Identificador do cliente associado ao empréstimo. */
    private int idc;

    /** Identificador da ferramenta associada ao empréstimo. */
    private int idf;

    /** Quantidade de itens emprestados. */
    private int quantidade;

    /**
     * Construtor padrão sem parâmetros.
     * <p>Utilizado quando o objeto será preenchido posteriormente por métodos de acesso (get/set).</p>
     */
    public EmprestimoAbstract() {
    }

    /**
     * Construtor completo para inicializar todos os atributos do empréstimo.
     *
     * @param ide Identificador único do empréstimo.
     * @param idc ID do cliente.
     * @param idf ID da ferramenta.
     * @param quantidade Quantidade de itens emprestados.
     */
    public EmprestimoAbstract(int ide, int idc, int idf, int quantidade) {
        this.ide = ide;
        this.idc = idc;
        this.idf = idf;
        this.quantidade = quantidade;
    }

    /** @return o identificador do empréstimo. */
    public int getIde() {
        return ide;
    }

    /** @param ide define o identificador do empréstimo. */
    public void setIde(int ide) {
        this.ide = ide;
    }

    /** @return o identificador do cliente. */
    public int getIdc() {
        return idc;
    }

    /** @param idc define o identificador do cliente. */
    public void setIdc(int idc) {
        this.idc = idc;
    }

    /** @return o identificador da ferramenta. */
    public int getIdf() {
        return idf;
    }

    /** @param idf define o identificador da ferramenta. */
    public void setIdf(int idf) {
        this.idf = idf;
    }

    /** @return a quantidade de itens emprestados. */
    public int getQuantidade() {
        return quantidade;
    }

    /** @param quantidade define a quantidade de itens emprestados. */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
