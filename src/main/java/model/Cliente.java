package model;

import java.util.*;
import dao.ClienteDAO;
import java.sql.SQLException;

/**
 * Representa um cliente do sistema, herdando atributos básicos de {@link Pessoa}
 * e adicionando informações específicas como endereço e telefone.
 * <p>
 * Esta classe também encapsula a comunicação com o banco de dados por meio do {@link ClienteDAO},
 * simulando a camada de negócio (model) na arquitetura MVC.
 * </p>
 *
 * @author Artur Azambuja
 * @author Bernardo Gaussmann
 * @version 1.0
 * @since 1.0
 */
public class Cliente extends Pessoa {

    /** Endereço do cliente. */
    private String endereco;

    /** Telefone do cliente. */
    private String telefone;

    /** Objeto de acesso aos dados (DAO) responsável pelas operações no banco de dados. */
    private final ClienteDAO dao;

    /**
     * Construtor padrão que inicializa o DAO.
     */
    public Cliente() {
        this.dao = new ClienteDAO();
    }

    /**
     * Construtor que inicializa os atributos endereço e telefone.
     *
     * @param endereco Endereço do cliente.
     * @param telefone Telefone do cliente.
     */
    public Cliente(String endereco, String telefone) {
        this.endereco = endereco;
        this.telefone = telefone;
        this.dao = new ClienteDAO();
    }

    /**
     * Construtor completo que utiliza também o construtor da superclasse {@link Pessoa}.
     *
     * @param idc ID do cliente.
     * @param nome Nome do cliente.
     * @param email E-mail do cliente.
     * @param endereco Endereço do cliente.
     * @param telefone Telefone do cliente.
     */
    public Cliente(int idc, String nome, String email, String endereco, String telefone) {
        super(idc, nome, email);
        this.endereco = endereco;
        this.telefone = telefone;
        this.dao = new ClienteDAO();
    }

    /** @return o endereço do cliente. */
    public String getEndereco() {
        return endereco;
    }

    /** @param endereco define o endereço do cliente. */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /** @return o telefone do cliente. */
    public String getTelefone() {
        return telefone;
    }

    /** @param telefone define o telefone do cliente. */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna uma representação textual do cliente.
     * Inclui também os atributos herdados de {@link Pessoa}.
     *
     * @return String formatada com todos os dados do cliente.
     */
    @Override
    public String toString() {
        return "\n ID: " + this.getIdc()
                + "\n Nome: " + this.getNome()
                + "\n Email: " + this.getEmail()
                + "\n Endereco: " + this.getEndereco()
                + "\n Telefone:" + this.getTelefone()
                + "\n -----------";
    }

    /**
     * Retorna uma lista de todos os clientes cadastrados no banco de dados.
     *
     * @return {@link ArrayList} contendo objetos do tipo {@link Cliente}.
     */
    public ArrayList getListaCliente() {
        return dao.getListaCliente();
    }

    /**
     * Insere um novo cliente no banco de dados.
     *
     * @param nome Nome do cliente.
     * @param email Email do cliente.
     * @param endereco Endereço do cliente.
     * @param telefone Telefone do cliente.
     * @return true se o cliente foi inserido com sucesso.
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados.
     */
    public boolean InsertClienteBD(String nome, String email, String endereco, String telefone) throws SQLException {
        int idc = this.maiorID() + 1;
        Cliente objeto = new Cliente(idc, nome, email, endereco, telefone);
        dao.InsertClienteBD(objeto);
        return true;
    }

    /**
     * Exclui um cliente específico pelo seu ID.
     *
     * @param idc ID do cliente a ser removido.
     * @return true se a exclusão for bem-sucedida.
     */
    public boolean DeleteClienteBD(int idc) {
        dao.DeleteClienteBD(idc);
        return true;
    }

    /**
     * Atualiza os dados de um cliente no banco de dados.
     *
     * @param idc ID do cliente.
     * @param nome Nome atualizado.
     * @param email Email atualizado.
     * @param endereco Endereço atualizado.
     * @param telefone Telefone atualizado.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean UpdateClienteBD(int idc, String nome, String email, String endereco, String telefone) {
        Cliente objeto = new Cliente(idc, nome, email, endereco, telefone);
        dao.UpdateClienteBD(objeto);
        return true;
    }

    /**
     * Carrega os dados de um cliente específico pelo seu ID.
     *
     * @param idc ID do cliente.
     * @return Objeto {@link Cliente} carregado do banco de dados.
     */
    public Cliente carregaCliente(int idc) {
        dao.carregaCliente(idc);
        return null;
    }

    /**
     * Retorna o maior ID existente na tabela de clientes.
     *
     * @return o maior ID registrado.
     * @throws SQLException se ocorrer erro no acesso ao banco de dados.
     */
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
