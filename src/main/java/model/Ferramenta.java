package model;

import dao.FerramentaDAO;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Representa uma ferramenta cadastrada no sistema.
 * <p>
 * Esta classe herda de {@link FerramentaAbstract} e adiciona atributos
 * específicos, como valor, setor e quantidade em estoque.
 * Também encapsula a interação com o banco de dados por meio de {@link FerramentaDAO}.
 * </p>
 *
 * <p>Faz parte da camada de modelo (Model) da aplicação,
 * seguindo o padrão de arquitetura em camadas.</p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public class Ferramenta extends FerramentaAbstract {

    /** Valor monetário da ferramenta. */
    private double valor;

    /** Setor onde a ferramenta está alocada. */
    private String setor;

    /** Quantidade disponível em estoque. */
    private int estoque;

    /** Objeto DAO responsável pelas operações no banco de dados. */
    private final FerramentaDAO dao;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public Ferramenta() {
        this.dao = new FerramentaDAO();
    }

    /**
     * Construtor que inicializa os atributos específicos da ferramenta.
     *
     * @param valor Valor monetário da ferramenta.
     * @param setor Setor ao qual pertence.
     * @param estoque Quantidade disponível em estoque.
     */
    public Ferramenta(double valor, String setor, int estoque) {
        this.valor = valor;
        this.setor = setor;
        this.estoque = estoque;
        this.dao = new FerramentaDAO();
    }

    /**
     * Construtor completo que também utiliza o construtor da superclasse {@link FerramentaAbstract}.
     *
     * @param idf Identificador da ferramenta.
     * @param nome Nome da ferramenta.
     * @param marca Marca da ferramenta.
     * @param valor Valor monetário.
     * @param setor Setor de alocação.
     * @param estoque Quantidade disponível em estoque.
     */
    public Ferramenta(int idf, String nome, String marca, double valor, String setor, int estoque) {
        super(idf, nome, marca);
        this.valor = valor;
        this.setor = setor;
        this.estoque = estoque;
        this.dao = new FerramentaDAO();
    }

    /**
     * Construtor utilizado em testes unitários, permitindo injetar uma conexão de banco.
     *
     * @param testConnection Conexão de banco de dados de teste.
     */
    public Ferramenta(Connection testConnection) {
        this.dao = new FerramentaDAO(testConnection);
    }

    /** @return o valor monetário da ferramenta. */
    public double getValor() {
        return valor;
    }

    /** @param valor define o valor monetário da ferramenta. */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /** @return o setor onde a ferramenta está alocada. */
    public String getSetor() {
        return setor;
    }

    /** @param setor define o setor de alocação da ferramenta. */
    public void setSetor(String setor) {
        this.setor = setor;
    }

    /** @return a quantidade disponível em estoque. */
    public int getEstoque() {
        return estoque;
    }

    /** @param estoque define a quantidade disponível em estoque. */
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    /**
     * Retorna uma representação textual da ferramenta.
     *
     * @return String formatada contendo todos os dados da ferramenta.
     */
    @Override
    public String toString() {
        return "\n ID: " + this.getIdf()
                + "\n Nome: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Valor: " + this.getValor()
                + "\n Setor:" + this.getSetor()
                + "\n Estoque:" + this.getEstoque()
                + "\n -----------";
    }

    /**
     * Retorna a lista de todas as ferramentas cadastradas no banco de dados.
     *
     * @return {@link ArrayList} de objetos {@link Ferramenta}.
     */
    public ArrayList getListaFerramenta() {
        return dao.getListaFerramenta();
    }

    /**
     * Insere uma nova ferramenta no banco de dados.
     *
     * @param nome Nome da ferramenta.
     * @param marca Marca da ferramenta.
     * @param valor Valor monetário.
     * @param setor Setor de alocação.
     * @param estoque Quantidade em estoque.
     * @return true se a inserção for bem-sucedida.
     * @throws SQLException se ocorrer erro no acesso ao banco de dados.
     */
    public boolean InsertFerramentaBD(String nome, String marca, double valor, String setor, int estoque) throws SQLException {
        int idf = this.maiorID() + 1;
        Ferramenta objeto = new Ferramenta(idf, nome, marca, valor, setor, estoque);
        dao.InsertFerramentaBD(objeto);
        return true;
    }

    /**
     * Remove uma ferramenta específica do banco de dados pelo seu ID.
     *
     * @param idf ID da ferramenta.
     * @return true se a remoção for bem-sucedida.
     */
    public boolean DeleteFerramentaBD(int idf) {
        dao.DeleteFerramentaBD(idf);
        return true;
    }

    /**
     * Atualiza os dados de uma ferramenta no banco de dados.
     *
     * @param idf ID da ferramenta.
     * @param nome Nome atualizado.
     * @param marca Marca atualizada.
     * @param valor Novo valor.
     * @param setor Novo setor.
     * @param estoque Nova quantidade em estoque.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean UpdateFerramentaBD(int idf, String nome, String marca, double valor, String setor, int estoque) {
        Ferramenta objeto = new Ferramenta(idf, nome, marca, valor, setor, estoque);
        dao.UpdateFerramentaBD(objeto);
        return true;
    }

    /**
     * Carrega os dados de uma ferramenta específica pelo seu ID.
     *
     * @param idf ID da ferramenta.
     * @return Objeto {@link Ferramenta} carregado do banco de dados.
     */
    public Ferramenta carregaFerramenta(int idf) {
        return dao.carregaFerramenta(idf);
    }

    /**
     * Retorna o maior ID registrado na tabela de ferramentas.
     *
     * @return o maior ID existente.
     * @throws SQLException se ocorrer erro no acesso ao banco de dados.
     */
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
