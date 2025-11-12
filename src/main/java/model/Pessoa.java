package model;

/**
 * Classe abstrata que representa uma pessoa genérica no sistema.
 * <p>
 * Esta classe serve como superclasse para entidades que possuem informações
 * básicas de identificação, como {@link Cliente}.
 * </p>
 *
 * <p>Faz parte da camada de modelo (Model) da aplicação.</p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public abstract class Pessoa {

    /** Identificador único da pessoa. */
    private int idc;

    /** Nome completo da pessoa. */
    private String nome;

    /** Endereço de e-mail da pessoa. */
    private String email;

    /**
     * Construtor padrão.
     * <p>
     * Utilizado para criar uma instância vazia de {@code Pessoa}.
     * </p>
     */
    public Pessoa() {
    }

    /**
     * Construtor completo da classe {@code Pessoa}.
     *
     * @param idc identificador único da pessoa.
     * @param nome nome completo da pessoa.
     * @param email endereço de e-mail da pessoa.
     */
    public Pessoa(int idc, String nome, String email) {
        this.idc = idc;
        this.nome = nome;
        this.email = email;
    }

    /**
     * Retorna o identificador único da pessoa.
     *
     * @return ID da pessoa.
     */
    public int getIdc() {
        return idc;
    }

    /**
     * Define o identificador único da pessoa.
     *
     * @param idc ID da pessoa.
     */
    public void setIdc(int idc) {
        this.idc = idc;
    }

    /**
     * Retorna o nome completo da pessoa.
     *
     * @return nome da pessoa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome completo da pessoa.
     *
     * @param nome nome da pessoa.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o e-mail da pessoa.
     *
     * @return e-mail da pessoa.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail da pessoa.
     *
     * @param email e-mail da pessoa.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
