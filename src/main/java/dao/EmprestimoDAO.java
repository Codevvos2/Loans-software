package dao;
import model.Emprestimo;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

public class EmprestimoDAO extends BaseDAO {
    private static final Logger logger = Logger.getLogger(EmprestimoDAO.class.getName());
    private ArrayList<Emprestimo> listaEmprestimos = new ArrayList<>();

    public EmprestimoDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    public EmprestimoDAO(Connection testConnection) {
        super(testConnection);
    }

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

    public int maiorID() throws SQLException {
        return maiorID("tb_emprestimo", "ide");
    }

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

    public boolean InsertEmprestimoBD(Emprestimo objeto) {
        if (this.connection == null) return true;
        String sql = "INSERT INTO tb_emprestimo(ide,quantidade,dataloc,datadev,status,idc,idf) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIde());
            stmt.setInt(2, objeto.getQuantidade());
            stmt.setString(3, objeto.getDataloc());
            stmt.setString(4, objeto.getDatadev());
            stmt.setString(5, objeto.getStatus());
            stmt.setInt(6, objeto.getIdc());
            stmt.setInt(7, objeto.getIdf());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean DeleteEmprestimoBD(int ide) {
        if (this.connection == null) return true;
        String sql = "DELETE FROM tb_emprestimo WHERE ide = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ide);
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean UpdateEmprestimoBD(Emprestimo objeto) {
        if (this.connection == null) return true;
        String sql = "UPDATE tb_emprestimo SET quantidade=?, dataloc=?, datadev=?, status=?, idc=?, idf=? WHERE ide=?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getQuantidade());
            stmt.setString(2, objeto.getDataloc());
            stmt.setString(3, objeto.getDatadev());
            stmt.setString(4, objeto.getStatus());
            stmt.setInt(5, objeto.getIdc());
            stmt.setInt(6, objeto.getIdf());
            stmt.setInt(7, objeto.getIde());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

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