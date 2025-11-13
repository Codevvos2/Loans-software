package dao;

import model.Emprestimo;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Classe responsável pelo acesso e manipulação dos dados da tabela {@code tb_emprestimo} no banco de dados.
 * <p>
 * Implementa operações CRUD (Create, Read, Update, Delete) para a entidade {@link Emprestimo}.
 * Esta classe estende {@link BaseDAO}, utilizando seus recursos de conexão com o banco de dados.
 * </p>
 *
 * <p>Revisado e documentado de acordo com o padrão Javadoc.</p>
 *
 * @author Artur Azambuja
 * @author Gustavo Russeff
 * @version 1.0
 * @since 1.0
 */
public class EmprestimoDAO extends BaseDAO {

    /**
     * Logger utilizado para registrar mensagens e erros durante as operações com o banco de dados.
     */
    private static final Logger logger = Logger.getLogger(EmprestimoDAO.class.getName());

    /**
     * Lista utilizada para armazenar os objetos {@link Emprestimo} recuperados do banco de dados.
     */
    private ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();

    /**
     * Construtor padrão que inicializa a conexão e cria a tabela {@code tb_emprestimo} se necessário.
     */
    public EmprestimoDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    /**
     * Construtor utilizado em testes, recebendo uma conexão específica.
     *
     * @param testConnection Conexão de banco de dados usada para testes
     */
    public EmprestimoDAO(Connection testConnection) {
        super(testConnection);
    }

    /**
     * Cria a tabela {@code tb_emprestimo} no banco de dados caso ela ainda não exista.
     * <p>
     * A tabela contém as colunas:
     * <ul>
     *   <li>ide — identificador do empréstimo</li>
     *   <li>quantidade — quantidade de itens emprestados</li>
     *   <li>dataloc — data de locação</li>
     *   <li>datadev — data de devolução</li>
     *   <li>status — situação atual do empréstimo</li>
     *   <li>idc — identificador do cliente</li>
     *   <li>idf — identificador do funcionário</li>
     * </ul>
     */
    public void inicializaBanco() {
        if (this.connection == null) return;
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS tb_emprestimo (" +
                            "ide INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "quantidade INTEGER, " +
                            "dataloc TEXT, " +
                            "datadev TEXT, " +
                            "status TEXT, " +
                            "idc INTEGER, " +
                            "idf INTEGER)"
            );
        } catch (SQLException e) {
            logger.severe("Erro ao inicializar banco tb_emprestimo: " + e.getMessage());
        }
    }

    /**
     * Retorna o maior ID existente na tabela {@code tb_emprestimo}.
     *
     * @return O maior valor de ID encontrado
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados
     */
    public int maiorID() throws SQLException {
        return maiorID("tb_emprestimo", "ide");
    }

    /**
     * Retorna uma lista de todos os empréstimos cadastrados na tabela {@code tb_emprestimo}.
     *
     * @return Uma lista contendo objetos {@link Emprestimo}
     */
    public ArrayList<Emprestimo> getListaEmprestimo() {
        listaEmprestimos.clear();
        if (this.connection == null) return listaEmprestimos;
        String sql = "SELECT * FROM tb_emprestimo";
        try (Statement stmt = this.connection.createStatement(); ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                int ide = res.getInt("ide");
                int quantidade = res.getInt("quantidade");
                String dataloc = res.getString("dataloc");
                String datadev = res.getString("datadev");
                String status = res.getString("status");
                int idc = res.getInt("idc");
                int idf = res.getInt("idf");
                Emprestimo objeto = new Emprestimo(ide, quantidade, dataloc, datadev, status, idc, idf);
                listaEmprestimos.add(objeto);
            }
        } catch (SQLException ex) {
            logger.severe("Erro ao listar empréstimos: " + ex.getMessage());
        }
        return listaEmprestimos;
    }

    /**
     * Insere um novo empréstimo no banco de dados.
     *
     * @param objeto Objeto {@link Emprestimo} a ser inserido
     * @return {@code true} se a inserção for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro durante a operação
     */
    public boolean InsertEmprestimoBD(Emprestimo objeto) {
        if (this.connection == null) {
            return false;
        }

        String sql = "INSERT INTO tb_emprestimo (ide, quantidade, dataloc, datadev, status, idc, idf) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIde());
            stmt.setInt(2, objeto.getQuantidade());
            stmt.setString(3, objeto.getDataloc());
            stmt.setString(4, objeto.getDatadev());
            stmt.setString(5, objeto.getStatus());
            stmt.setInt(6, objeto.getIdc());
            stmt.setInt(7, objeto.getIdf());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return false;
        }
    }


    /**
     * Exclui um empréstimo do banco de dados com base no ID informado.
     *
     * @param ide ID do empréstimo a ser removido
     * @return {@code true} se a exclusão for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro durante a exclusão
     */
    public boolean DeleteEmprestimoBD(int ide) {
        if (this.connection == null) {
            return false;
        }

        String sql = "DELETE FROM tb_emprestimo WHERE ide = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ide);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return false;
        }
    }


    /**
     * Atualiza os dados de um empréstimo existente no banco de dados.
     *
     * @param objeto Objeto {@link Emprestimo} contendo os novos valores
     * @return {@code true} se a atualização for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro durante a atualização
     */
    public boolean UpdateEmprestimoBD(Emprestimo objeto) {
        if (this.connection == null) {
            return false;
        }

        String sql = "UPDATE tb_emprestimo SET quantidade=?, dataloc=?, datadev=?, status=?, idc=?, idf=? WHERE ide=?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getQuantidade());
            stmt.setString(2, objeto.getDataloc());
            stmt.setString(3, objeto.getDatadev());
            stmt.setString(4, objeto.getStatus());
            stmt.setInt(5, objeto.getIdc());
            stmt.setInt(6, objeto.getIdf());
            stmt.setInt(7, objeto.getIde());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            erro.printStackTrace();
            return false;
        }
    }


    /**
     * Carrega um empréstimo específico do banco de dados com base em seu ID.
     *
     * @param ide ID do empréstimo a ser carregado
     * @return Objeto {@link Emprestimo} preenchido com os dados encontrados, ou um objeto vazio se não existir
     */
    public Emprestimo carregaEmprestimo(int ide) {
        Emprestimo objeto = new Emprestimo();
        if (this.connection == null) return objeto;
        String sql = "SELECT * FROM tb_emprestimo WHERE ide = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ide);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setIde(res.getInt("ide"));
                    objeto.setQuantidade(res.getInt("quantidade"));
                    objeto.setDataloc(res.getString("dataloc"));
                    objeto.setDatadev(res.getString("datadev"));
                    objeto.setStatus(res.getString("status"));
                    objeto.setIdc(res.getInt("idc"));
                    objeto.setIdf(res.getInt("idf"));
                } else {
                    objeto.setIde(0);
                }
            }
        } catch (SQLException erro) {
            logger.severe("Erro ao carregar empréstimo: " + erro.getMessage());
        }
        return objeto;
    }
}