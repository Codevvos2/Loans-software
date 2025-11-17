package model;

import dao.EmprestimoDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa um empréstimo de ferramenta, herdando os atributos genéricos de {@link EmprestimoAbstract}.
 * <p>
 * Esta classe contém informações adicionais como data de locação, data de devolução e status do empréstimo,
 * além de gerenciar as operações de persistência por meio do {@link EmprestimoDAO}.
 * </p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public class Emprestimo extends EmprestimoAbstract {

    /** Data em que o empréstimo foi realizado. */
    private String dataloc;

    /** Data prevista ou efetiva de devolução do empréstimo. */
    private String datadev;

    /** Status atual do empréstimo (ex.: "Ativo", "Devolvido", "Atrasado"). */
    private String status;

    /** Objeto DAO responsável pelas operações no banco de dados relacionadas a empréstimos. */
    private final EmprestimoDAO dao;

    /**
     * Construtor padrão que inicializa o objeto {@link EmprestimoDAO}.
     */
    public Emprestimo() {
        this.dao = new EmprestimoDAO();
    }

    /**
     * Construtor que inicializa os atributos específicos de um empréstimo.
     *
     * @param dataloc Data da locação.
     * @param datadev Data da devolução.
     * @param status Situação atual do empréstimo.
     */
    public Emprestimo(String dataloc, String datadev, String status) {
        this.dataloc = dataloc;
        this.datadev = datadev;
        this.status = status;
        this.dao = new EmprestimoDAO();
    }

    /**
     * Construtor completo que também chama o construtor da superclasse {@link EmprestimoAbstract}.
     *
     * @param ide Identificador único do empréstimo.
     * @param quantidade Quantidade de itens emprestados.
     * @param dataloc Data da locação.
     * @param datadev Data da devolução.
     * @param status Status do empréstimo.
     * @param idc ID do cliente.
     * @param idf ID da ferramenta.
     */
    public Emprestimo(int ide, int quantidade, String dataloc, String datadev, String status, int idc, int idf) {
        super(ide, idc, idf, quantidade);
        this.dataloc = dataloc;
        this.datadev = datadev;
        this.status = status;
        this.dao = new EmprestimoDAO();
    }

    /** @return a data da locação. */
    public String getDataloc() {
        return dataloc;
    }

    /** @param dataloc define a data da locação. */
    public void setDataloc(String dataloc) {
        this.dataloc = dataloc;
    }

    /** @return a data de devolução. */
    public String getDatadev() {
        return datadev;
    }

    /** @param datadev define a data de devolução. */
    public void setDatadev(String datadev) {
        this.datadev = datadev;
    }

    /** @return o status do empréstimo. */
    public String getStatus() {
        return status;
    }

    /** @param status define o status do empréstimo. */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retorna uma representação textual completa do empréstimo.
     *
     * @return String formatada com todos os dados do empréstimo.
     */
    @Override
    public String toString() {
        return "\n ID: " + this.getIde()
                + "\n Quantidade: " + this.getQuantidade()
                + "\n Dataloc: " + this.getDataloc()
                + "\n Datadev: " + this.getDatadev()
                + "\n Status: " + this.getStatus()
                + "\n Idc: " + this.getIdc()
                + "\n Idf: " + this.getIdf()
                + "\n -----------";
    }

    /**
     * Retorna a lista de todos os empréstimos registrados no banco de dados.
     *
     * @return {@link ArrayList} de objetos {@link Emprestimo}.
     */
    public ArrayList getListaEmprestimo() {
        return dao.getListaEmprestimo();
    }

    /**
     * Insere um novo empréstimo no banco de dados.
     *
     * @param quantidade Quantidade de itens emprestados.
     * @param dataloc Data da locação.
     * @param datadev Data da devolução.
     * @param status Status do empréstimo.
     * @param idc ID do cliente associado.
     * @param idf ID da ferramenta associada.
     * @return true se a inserção for bem-sucedida.
     * @throws SQLException se ocorrer erro de acesso ao banco.
     */
    public boolean InsertEmprestimoBD(int quantidade, String dataloc, String datadev, String status, int idc, int idf) throws SQLException {
        int ide = this.maiorID() + 1;
        Emprestimo objeto = new Emprestimo(ide, quantidade, dataloc, datadev, status, idc, idf);
        dao.InsertEmprestimoBD(objeto);
        return true;
    }

    /**
     * Exclui um empréstimo do banco de dados com base em seu ID.
     *
     * @param ide ID do empréstimo a ser excluído.
     * @return true se a exclusão for realizada com sucesso.
     */
    public boolean DeleteEmprestimoBD(int ide) {
        dao.DeleteEmprestimoBD(ide);
        return true;
    }

    /**
     * Atualiza os dados de um empréstimo no banco de dados.
     *
     * @param ide ID do empréstimo.
     * @param quantidade Nova quantidade de itens.
     * @param dataloc Nova data da locação.
     * @param datadev Nova data da devolução.
     * @param status Novo status do empréstimo.
     * @param idc ID do cliente associado.
     * @param idf ID da ferramenta associada.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean UpdateEmprestimoBD(int ide, int quantidade, String dataloc, String datadev, String status, int idc, int idf) {
        Emprestimo objeto = new Emprestimo(ide, quantidade, dataloc, datadev, status, idc, idf);
        dao.UpdateEmprestimoBD(objeto);
        return true;
    }

    /**
     * Carrega os dados de um empréstimo específico a partir do banco de dados.
     *
     * @param ide ID do empréstimo a ser carregado.
     * @return Objeto {@link Emprestimo} carregado.
     */
    public Emprestimo carregaEmprestimo(int ide) {
        dao.carregaEmprestimo(ide);
        return null;
    }

    /**
     * Retorna o maior ID registrado na tabela de empréstimos.
     *
     * @return O maior ID existente no banco de dados.
     * @throws SQLException se ocorrer erro ao consultar o banco.
     */
    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}
