package dao;
import model.Ferramenta;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

public class FerramentaDAO extends BaseDAO {
    private static final Logger logger = Logger.getLogger(FerramentaDAO.class.getName());
    private ArrayList<Ferramenta> listaFerramentas = new ArrayList<>();

    public FerramentaDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    public FerramentaDAO(Connection testConnection) {
        super(testConnection);
    }

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

    public int maiorID() throws SQLException {
        return maiorID("tb_ferramenta", "idf");
    }

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

    public boolean DeleteFerramentaBD(int idf) {
        if (this.connection == null) return true;
        String sql = "DELETE FROM tb_ferramenta WHERE idf = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idf);
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean UpdateFerramentaBD(Ferramenta objeto) {
        if (this.connection == null) return true;
        String sql = "UPDATE tb_ferramenta SET nome = ?, marca = ?, valor = ?, setor = ?, estoque = ? WHERE idf = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getValor());
            stmt.setString(4, objeto.getSetor());
            stmt.setInt(5, objeto.getEstoque());
            stmt.setInt(6, objeto.getIdf());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

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