package DAO;

import Model.Ferramenta;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FerramentaDAO {

    public static ArrayList<Ferramenta> ListaFerramenta = new ArrayList<Ferramenta>();

    public FerramentaDAO() {
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT MAX(idf) idf FROM tb_ferramenta");
            res.next();
            maiorID = res.getInt("idf");

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
            String password = "root";

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

   
    public ArrayList getListaFerramenta() {
        
        ListaFerramenta.clear(); 

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramenta");
            while (res.next()) {

                int idf = res.getInt("idf");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                double  valor = res.getDouble("valor");
                String setor = res.getString("setor");
                int estoque = res.getInt("estoque");

                Ferramenta objeto = new Ferramenta(idf, nome, marca, valor, setor, estoque);

                ListaFerramenta.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return ListaFerramenta;
    }

   
    public boolean InsertFerramentaBD(Ferramenta objeto) {
        String sql = "INSERT INTO tb_ferramenta(idf, nome, marca, valor, setor, estoque) VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getIdf());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getMarca());
            stmt.setDouble(4, objeto.getValor());
            stmt.setString(5, objeto.getSetor());
            stmt.setInt(6, objeto.getEstoque());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Deleta um aluno espec�fico pelo seu campo ID
    public boolean DeleteFerramentaBD(int idf) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_ferramenta WHERE idf = " + idf);
            stmt.close();            
            
        } catch (SQLException erro) {
        }
        
        return true;
    }

   
    public boolean UpdateFerramentaBD(Ferramenta objeto) {

        String sql = "UPDATE tb_ferramenta set nome = ? ,marca = ? ,valor = ? ,setor = ?,estoque = ? WHERE idf = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getValor());
            stmt.setString(4, objeto.getSetor());
            stmt.setInt(5, objeto.getEstoque());
            stmt.setInt(6, objeto.getIdf());
            

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    public Ferramenta carregaFerramenta(int idf) {
        
        Ferramenta objeto = new Ferramenta();
        objeto.setIdf(idf);
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramenta WHERE idf = " + idf);
            res.next();

            objeto.setNome(res.getString("nome"));
            objeto.setMarca(res.getString("marca"));
            objeto.setValor(res.getDouble("valor"));
            objeto.setSetor(res.getString("setor"));
            objeto.setEstoque(res.getInt("estoque"));

            stmt.close();            
            
        } catch (SQLException erro) {
        }
        return objeto;
    }
}
