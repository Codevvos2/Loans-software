package Model;

import java.util.*;
import DAO.FerramentaDAO;
import java.sql.SQLException;
import java.sql.Connection;

public class Ferramenta extends FerramentaAbstract {

  
    private double valor;
    private String setor;
    private int estoque;
    private final FerramentaDAO dao; 

    
    public Ferramenta() {   
        this.dao = new FerramentaDAO(); 
    }

   
    public Ferramenta(double valor, String setor, int estoque) {
        this.valor = valor;
        this.setor = setor;
        this.estoque = estoque;
        this.dao = new FerramentaDAO();
    }

  
    public Ferramenta(int idf,String nome,String marca,double valor,String setor,int estoque) {
        super(idf, nome, marca);
        this.valor = valor;
        this.setor = setor;
        this.estoque = estoque;
        this.dao = new FerramentaDAO(); 
    }

    public Ferramenta(Connection testConnection) {
        this.dao = new FerramentaDAO(testConnection);
    }


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
     public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    
    @Override
    public String toString() {
        return "\n ID: " + this.getIdf()
                + "\n Nome: " + this.getNome()
                + "\n Marca: " + this.getMarca()
                + "\n Valor: " + this.getValor()
                + "\n Setor:" + this.getSetor()
                + "\n Estoque:" + this.getEstoque()
                + "\n -----------";
    }

    /*
    
    
     */
   
    public ArrayList getListaFerramenta() {
      
        return dao.getListaFerramenta();
    }

   

    public boolean InsertFerramentaBD(String nome,String marca,double valor,String setor,int estoque ) throws SQLException {
        int idf = this.maiorID() + 1;
        Ferramenta objeto = new Ferramenta(idf,nome,marca,valor,setor,estoque);

        dao.InsertFerramentaBD(objeto);
        return true;

    }

  
    public boolean DeleteFerramentaBD(int idf) {


        dao.DeleteFerramentaBD(idf);
        return true;
    }

    
    public boolean UpdateFerramentaBD(int idf,String nome,String marca,double valor,String setor,int estoque) {
        Ferramenta objeto = new Ferramenta(idf,nome,marca,valor,setor,estoque);

        dao.UpdateFerramentaBD(objeto);
        return true;
    }


    public Ferramenta carregaFerramenta(int idf) {
        return dao.carregaFerramenta(idf);
    }



    public int maiorID() throws SQLException{

        return dao.maiorID();
    }   
}