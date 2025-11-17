package dao;

import model.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Classe responsável pelo acesso e manipulação dos dados da tabela {@code tb_cliente} no banco de dados.
 * <p>
 * Implementa operações CRUD (Create, Read, Update, Delete) para a entidade {@link Cliente}.
 * Esta classe estende {@link BaseDAO} para utilizar a conexão com o banco de dados.
 * </p>
 *
 * @author Artur Azambuja
 * @author Gustavo Russeff
 * @version 1.0
 * @since 1.0
 */
public class ClienteDAO extends BaseDAO {

    /**
     * Logger para registro de mensagens e erros durante as operações no banco de dados.
     */
    private static final Logger logger = Logger.getLogger(ClienteDAO.class.getName());

    /**
     * Lista utilizada para armazenar os objetos {@link Cliente} recuperados do banco de dados.
     */
    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    /**
     * Construtor padrão que inicializa a conexão com o banco e cria a tabela {@code tb_cliente} se necessário.
     */
    public ClienteDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    /**
     * Construtor utilizado para testes, recebendo uma conexão específica.
     *
     * @param testConnection Conexão de banco de dados utilizada nos testes
     */
    public ClienteDAO(Connection testConnection) {
        super(testConnection);
    }

    /**
     * Cria a tabela {@code tb_cliente} no banco de dados, caso ela ainda não exista.
     * A tabela contém as colunas: idc, nome, email, endereco e telefone.
     */
    public void inicializaBanco() {
        if (this.connection == null) return;
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS tb_cliente (" +
                            "idc INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "nome TEXT NOT NULL, " +
                            "email TEXT, " +
                            "endereco TEXT, " +
                            "telefone TEXT)"
            );
        } catch (SQLException e) {
            logger.severe("Erro ao inicializar banco tb_cliente: " + e.getMessage());
        }
    }

    /**
     * Retorna o maior ID existente na tabela {@code tb_cliente}.
     *
     * @return O maior valor de ID encontrado
     * @throws SQLException Se ocorrer um erro de acesso ao banco de dados
     */
    public int maiorID() throws SQLException {
        return maiorID("tb_cliente", "idc");
    }

    /**
     * Retorna a lista de todos os clientes cadastrados na tabela {@code tb_cliente}.
     *
     * @return Uma lista de objetos {@link Cliente}
     */
    public ArrayList<Cliente> getListaCliente() {
        listaClientes.clear();
        if (this.connection == null) return listaClientes;
        String sql = "SELECT * FROM tb_cliente";
        try (Statement stmt = this.connection.createStatement(); ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                int idc = res.getInt("idc");
                String nome = res.getString("nome");
                String email = res.getString("email");
                String endereco = res.getString("endereco");
                String telefone = res.getString("telefone");
                Cliente objeto = new Cliente(idc, nome, email, endereco, telefone);
                listaClientes.add(objeto);
            }
        } catch (SQLException ex) {
            logger.severe("Erro ao buscar lista de clientes: " + ex.getMessage());
        }
        return listaClientes;
    }

    /**
     * Insere um novo cliente no banco de dados.
     *
     * @param objeto Objeto {@link Cliente} a ser inserido
     * @return {@code true} se a inserção for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro ao inserir o cliente
     */
    public boolean InsertClienteBD(Cliente objeto) {
        if (this.connection == null) {
            return false;
        }
        String sql = "INSERT INTO tb_cliente(idc, nome, email, endereco, telefone) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdc());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getEmail());
            stmt.setString(4, objeto.getEndereco());
            stmt.setString(5, objeto.getTelefone());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            System.err.println("Erro ao inserir cliente: " + erro.getMessage());
            return false;
        }
    }

    /**
     * Exclui um cliente da tabela {@code tb_cliente} com base em seu ID.
     *
     * @param idc ID do cliente a ser excluído
     * @return {@code true} se a exclusão for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro durante a exclusão
     */
    public boolean DeleteClienteBD(int idc) {
        if (this.connection == null) {
            return false;
        }

        String sql = "DELETE FROM tb_cliente WHERE idc = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idc);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            System.err.println("Erro ao deletar cliente: " + erro.getMessage());
            return false;
        }
    }


    /**
     * Atualiza os dados de um cliente existente no banco de dados.
     *
     * @param objeto Objeto {@link Cliente} contendo os novos dados
     * @return {@code true} se a atualização for bem-sucedida
     * @throws RuntimeException Se ocorrer um erro durante a atualização
     */
    public boolean UpdateClienteBD(Cliente objeto) {
        if (this.connection == null) {
            return false;
        }

        String sql = "UPDATE tb_cliente SET nome=?, email=?, endereco=?, telefone=? WHERE idc=?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getEmail());
            stmt.setString(3, objeto.getEndereco());
            stmt.setString(4, objeto.getTelefone());
            stmt.setInt(5, objeto.getIdc());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }


    /**
     * Carrega um cliente específico do banco de dados com base em seu ID.
     *
     * @param idc ID do cliente a ser carregado
     * @return Um objeto {@link Cliente} preenchido com os dados encontrados
     */
    public Cliente carregaCliente(int idc) {
        Cliente objeto = new Cliente();
        if (this.connection == null) return objeto;
        String sql = "SELECT * FROM tb_cliente WHERE idc=?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idc);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setIdc(res.getInt("idc"));
                    objeto.setNome(res.getString("nome"));
                    objeto.setEmail(res.getString("email"));
                    objeto.setEndereco(res.getString("endereco"));
                    objeto.setTelefone(res.getString("telefone"));
                } else {
                    objeto.setIdc(0);
                }
            }
        } catch (SQLException erro) {
            logger.severe("Erro ao carregar cliente: " + erro.getMessage());
        }
        return objeto;
    }
}