package DAO;

import Model.Cliente;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {

    public static ArrayList<Cliente> ListaCliente = new ArrayList<Cliente>();

    public ClienteDAO() {
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idc) idc FROM tb_cliente");
            res.next();
            maiorID = res.getInt("idc");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public Connection getConexao() {

        Connection connection = null;  //inst�ncia da conex�o

        try {

            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            String server = "localhost"; //caminho do MySQL
            String database = "db_loans_software";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: N�O CONECTADO!");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver n�o encontrado
            System.out.println("O driver nao foi encontrado. " + e.getMessage() );
            return null;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
    }

    public ArrayList getListaCliente() {
        
        ListaCliente.clear();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_cliente");
            while (res.next()) {

                int idc = res.getInt("idc");
                String nome = res.getString("nome");
                String email = res.getString("email");
                String endereco = res.getString("endereco");
                String telefone = res.getString("telefone");

                Cliente objeto = new Cliente(idc,nome,email,endereco,telefone);

                ListaCliente.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return ListaCliente;
    }

    public boolean InsertClienteBD(Cliente objeto) {
        String sql = "INSERT INTO tb_cliente(idc,nome,email,endereco,telefone) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getIdc());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getEmail());
            stmt.setString(4, objeto.getEndereco());
            stmt.setString(5, objeto.getTelefone());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public boolean DeleteClienteBD(int idc) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_cliente WHERE idc = " + idc);
            stmt.close();            
            
        } catch (SQLException erro) {
        }
        
        return true;
    }

    public boolean UpdateClienteBD(Cliente objeto) {

        String sql = "UPDATE tb_cliente set nome = ? ,email = ? ,endereco = ? ,telefone = ? WHERE idc = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getEmail());
            stmt.setString(3, objeto.getEndereco());
            stmt.setString(4, objeto.getTelefone());
            stmt.setInt(5, objeto.getIdc());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Cliente carregaCliente(int idc) {
        
        Cliente objeto = new Cliente();
        objeto.setIdc(idc);
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_cliente WHERE idc = " + idc);
            res.next();

            objeto.setNome(res.getString("nome"));
            objeto.setEmail(res.getString("email"));
            objeto.setEndereco(res.getString("endereco"));
            objeto.setTelefone(res.getString("telefone"));

            stmt.close();            
            
        } catch (SQLException erro) {
        }
        return objeto;
    }
}

