package dao;

import model.Cliente;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

public class ClienteDAO extends BaseDAO {
    private static final Logger logger = Logger.getLogger(ClienteDAO.class.getName());
    private ArrayList<Cliente> listaClientes = new ArrayList<>();

    public ClienteDAO() {
        super();
        if (this.connection != null) {
            inicializaBanco();
        }
    }

    public ClienteDAO(Connection testConnection) {
        super(testConnection);
    }

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

    public int maiorID() throws SQLException {
        return maiorID("tb_cliente", "idc");
    }

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

    public boolean InsertClienteBD(Cliente objeto) {
        if (this.connection == null) return true;
        String sql = "INSERT INTO tb_cliente(idc,nome,email,endereco,telefone) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdc());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getEmail());
            stmt.setString(4, objeto.getEndereco());
            stmt.setString(5, objeto.getTelefone());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean DeleteClienteBD(int idc) {
        if (this.connection == null) return true;
        String sql = "DELETE FROM tb_cliente WHERE idc = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, idc);
            stmt.executeUpdate();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean UpdateClienteBD(Cliente objeto) {
        if (this.connection == null) return true;
        String sql = "UPDATE tb_cliente SET nome=?, email=?, endereco=?, telefone=? WHERE idc=?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getEmail());
            stmt.setString(3, objeto.getEndereco());
            stmt.setString(4, objeto.getTelefone());
            stmt.setInt(5, objeto.getIdc());
            stmt.execute();
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }

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