package dao;

import model.Ferramenta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Classe responsável pelo acesso e manipulação dos dados da entidade {@link Ferramenta}
 * no banco de dados. Implementa operações CRUD e inicialização da tabela.
 *
 * Esta classe herda de {@link BaseDAO} para gerenciar a conexão com o banco.
 *
 * @author Artur Azambuja
 * @author Gustavo Russeff
 * @version 1.0
 * @since 1.0
 */
public class FerramentaDAO extends BaseDAO {
    /** Logger para registrar mensagens e erros da classe. */
    private static final Logger logger = Logger.getLogger(FerramentaDAO.class.getName());

    /** Lista que armazena as ferramentas carregadas do banco de dados. */
    private ArrayList<Ferramenta> listaFerramentas = new ArrayList<>();

    /**
     * Construtor padrão. Inicializa a conexão e cria a tabela {@code tb_ferramenta} se necessário.
     */
    public FerramentaDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    /**
     * Construtor utilizado em testes para injetar uma conexão específica.
     *
     * @param testConnection conexão de banco de dados usada para testes
     */
    public FerramentaDAO(Connection testConnection) {
        super(testConnection);
    }

    /**
     * Cria a tabela {@code tb_ferramenta} no banco de dados, caso ainda não exista.
     * A tabela contém colunas para ID, nome, marca, valor, setor e estoque.
     */
    public void inicializaBanco() {
        if (this.connection == null) return;
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS tb_ferramenta (" +
                    "idf INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "marca TEXT, " +
                    "valor REAL, " +
                    "setor TEXT, " +
                    "estoque INTEGER)");
        } catch (SQLException e) {
            logger.severe("Erro ao inicializar banco tb_ferramenta: " + e.getMessage());
        }
    }

    /**
     * Retorna o maior ID registrado na tabela {@code tb_ferramenta}.
     *
     * @return maior identificador de ferramenta
     * @throws SQLException caso ocorra erro na consulta
     */
    public int maiorID() throws SQLException {
        return maiorID("tb_ferramenta", "idf");
    }

    /**
     * Retorna uma lista com todas as ferramentas registradas no banco.
     *
     * @return lista de objetos {@link Ferramenta}
     */
    public ArrayList<Ferramenta> getListaFerramenta() {
        listaFerramentas.clear();
        if (this.connection == null) return listaFerramentas;
        String sql = "SELECT * FROM tb_ferramenta";
        try (Statement stmt = this.connection.createStatement(); ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                int idf = res.getInt("idf");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                double valor = res.getDouble("valor");
                String setor = res.getString("setor");
                int estoque = res.getInt("estoque");
                Ferramenta objeto = new Ferramenta(idf, nome, marca, valor, setor, estoque);
                listaFerramentas.add(objeto);
            }
        } catch (SQLException ex) {
            logger.severe("Erro ao listar ferramentas: " + ex.getMessage());
        }
        return listaFerramentas;
    }

    /**
     * Insere uma nova ferramenta no banco de dados.
     *
     * @param objeto ferramenta a ser inserida
     * @return {@code true} se a inserção for bem-sucedida
     */
    public boolean InsertFerramentaBD(Ferramenta objeto) {
        if (this.connection == null) return false;
        String sql = "INSERT INTO tb_ferramenta(nome, marca, valor, setor, estoque) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getValor());
            stmt.setString(4, objeto.getSetor());
            stmt.setInt(5, objeto.getEstoque());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    objeto.setIdf(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserção falhou, nenhum ID gerado.");
                }
            }
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    /**
     * Exclui uma ferramenta do banco de dados com base no ID informado.
     *
     * @param idf identificador da ferramenta a ser excluída
     * @return {@code true} se a exclusão for bem-sucedida
     */
    public boolean DeleteFerramentaBD(int idf) {
        if (this.connection == null) {
            return false;
        }

        String sql = "DELETE FROM tb_ferramenta WHERE idf = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idf);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao deletar ferramenta no banco de dados: {0}", erro.getMessage());
            return false;
        }
    }


    /**
     * Atualiza os dados de uma ferramenta existente no banco de dados.
     *
     * @param objeto ferramenta com os dados atualizados
     * @return {@code true} se a atualização for bem-sucedida
     */
    public boolean UpdateFerramentaBD(Ferramenta objeto) {
        if (this.connection == null) {
            return false;
        }

        String sql = "UPDATE tb_ferramenta SET nome = ?, marca = ?, valor = ?, setor = ?, estoque = ? WHERE idf = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getValor());
            stmt.setString(4, objeto.getSetor());
            stmt.setInt(5, objeto.getEstoque());
            stmt.setInt(6, objeto.getIdf());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException erro) {
            logger.log(Level.SEVERE, "Erro ao atualizar ferramenta no banco de dados: {0}", erro.getMessage());
            return false;
        }
    }


    /**
     * Carrega uma ferramenta específica do banco de dados com base no ID.
     *
     * @param idf identificador da ferramenta a ser carregada
     * @return objeto {@link Ferramenta} correspondente ou uma ferramenta vazia se não encontrada
     */
    public Ferramenta carregaFerramenta(int idf) {
        Ferramenta objeto = new Ferramenta();
        if (this.connection == null) return objeto;
        String sql = "SELECT * FROM tb_ferramenta WHERE idf = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idf);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setIdf(res.getInt("idf"));
                    objeto.setNome(res.getString("nome"));
                    objeto.setMarca(res.getString("marca"));
                    objeto.setValor(res.getDouble("valor"));
                    objeto.setSetor(res.getString("setor"));
                    objeto.setEstoque(res.getInt("estoque"));
                } else {
                    objeto.setIdf(0);
                }
            }
        } catch (SQLException erro) {
            logger.severe("Erro ao carregar ferramenta: " + erro.getMessage());
        }
        return objeto;
    }
}
