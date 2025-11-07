package DAO;

import Model.Emprestimo;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Calendar.DATE;

public class EmprestimoDAO {

    public static ArrayList<Emprestimo> ListaEmprestimo = new ArrayList<Emprestimo>();

    public EmprestimoDAO() {
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(ide) ide FROM tb_emprestimo");
            res.next();
            maiorID = res.getInt("ide");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public Connection getConexao() {

        Connection connection = null;  

        try {

            
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            
            String server = "localhost"; 
            String database = "db_loans_software";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "123456";

            connection = DriverManager.getConnection(url, user, password);

            
            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: N�O CONECTADO!");
            }

            return connection;

        } catch (ClassNotFoundException e) { 
            System.out.println("O driver nao foi encontrado. " + e.getMessage() );
            return null;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
    }

    
    public ArrayList getListaEmprestimo() {
        
        ListaEmprestimo.clear(); 

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimo");
            while (res.next()) {

                int ide = res.getInt("ide");
                int quantidade = res.getInt("quantidade");
                String dataloc = res.getString("dataloc");
                String datadev = res.getString("datadev");
                String status = res.getString("status");
                int idc = res.getInt("idc");
                int idf = res.getInt("idf");
                Emprestimo objeto = new Emprestimo(ide, quantidade, dataloc, datadev, status, idc, idf);

                ListaEmprestimo.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return ListaEmprestimo;
    }

    // Cadastra novo aluno
    public boolean InsertEmprestimoBD(Emprestimo objeto) {
        String sql = "INSERT INTO tb_emprestimo(ide,quantidade,dataloc,datadev,status, idc,idf) VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getIde());
            stmt.setInt(2, objeto.getQuantidade());
            stmt.setString(3, objeto.getDataloc());
            stmt.setString(4, objeto.getDatadev());
            stmt.setString(5, objeto.getStatus());
            stmt.setInt(6, objeto.getIdc());
            stmt.setInt(7, objeto.getIdf());
            
            
            
            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Deleta um aluno espec�fico pelo seu campo ID
    public boolean DeleteEmprestimoBD(int ide) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_emprestimo WHERE ide = " + ide);
            stmt.close();            
            
        } catch (SQLException erro) {
        }
        
        return true;
    }

    
    public boolean UpdateEmprestimoBD(Emprestimo objeto) {

        String sql = "UPDATE tb_emprestimo set quantidade = ?,dataloc = ?, datadev =?,status = ?, idc = ?, idf = ? WHERE ide = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            
            stmt.setInt(1, objeto.getQuantidade());
            stmt.setString(2, objeto.getDataloc());
            stmt.setString(3, objeto.getDatadev());
            stmt.setString(4, objeto.getStatus());
            stmt.setInt(5, objeto.getIdc());
            stmt.setInt(6, objeto.getIdf());
            stmt.setInt(7, objeto.getIde());
            

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Emprestimo carregaEmprestimo(int ide) {
        
        Emprestimo objeto = new Emprestimo();
        objeto.setIde(ide);
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimo WHERE ide = " + ide);
            res.next();

          
            objeto.setQuantidade(res.getInt("quantidade"));
            objeto.setDataloc(res.getString("dataloc"));
            objeto.setDatadev(res.getString("datadev"));
            objeto.setDatadev(res.getString("status"));
            objeto.setIdc(res.getInt("idc"));
            objeto.setIdf(res.getInt("idf"));
            stmt.close();            
            
        } catch (SQLException erro) {
        }
        return objeto;
    }
}