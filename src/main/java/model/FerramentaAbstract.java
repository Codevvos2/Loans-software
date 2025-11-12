package model;

/**
 * Classe abstrata que representa a estrutura básica de uma ferramenta.
 * <p>
 * Esta classe define os atributos e métodos comuns a todas as ferramentas,
 * servindo como base para classes concretas como {@link Ferramenta}.
 * </p>
 *
 * <p>Faz parte da camada de modelo (Model) da aplicação.</p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public abstract class FerramentaAbstract {

    /** Identificador único da ferramenta. */
    private int idf;

    /** Nome da ferramenta. */
    private String nome;

    /** Marca da ferramenta. */
    private String marca;

    /**
     * Construtor padrão.
     * <p>
     * Utilizado quando não há necessidade de inicializar os atributos no momento da criação.
     * </p>
     */
    public FerramentaAbstract() {
    }

    /**
     * Construtor completo da classe abstrata.
     *
     * @param idf Identificador único da ferramenta.
     * @param nome Nome da ferramenta.
     * @param marca Marca da ferramenta.
     */
    public FerramentaAbstract(int idf, String nome, String marca) {
        this.idf = idf;
        this.nome = nome;
        this.marca = marca;
    }

    /**
     * Retorna o identificador único da ferramenta.
     *
     * @return ID da ferramenta.
     */
    public int getIdf() {
        return idf;
    }

    /**
     * Define o identificador único da ferramenta.
     *
     * @param idf ID da ferramenta.
     */
    public void setIdf(int idf) {
        this.idf = idf;
    }

    /**
     * Retorna o nome da ferramenta.
     *
     * @return nome da ferramenta.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da ferramenta.
     *
     * @param nome nome da ferramenta.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a marca da ferramenta.
     *
     * @return marca da ferramenta.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Define a marca da ferramenta.
     *
     * @param marca marca da ferramenta.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
}
