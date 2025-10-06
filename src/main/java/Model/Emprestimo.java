package Model;

import java.util.*;
import DAO.EmprestimoDAO;
import java.sql.SQLException;

public class Emprestimo extends EmprestimoAbstract {

  
    
    private String dataloc;
    private String datadev;
    private String status;
    private final EmprestimoDAO dao; 

    
    public Emprestimo() {
        this.dao = new EmprestimoDAO(); 
    }

    
    public Emprestimo( String dataloc, String datadev,String status) {
       
        this.dataloc = dataloc;
        this.datadev = datadev;
        this.status = status;
        this.dao = new EmprestimoDAO(); 
    }

  
    public Emprestimo(int ide,int quantidade,String dataloc,String datadev,String status,int idc,int idf) {
        super(ide,idc,idf,quantidade);
       
        this.dataloc = dataloc;
        this.datadev = datadev;
        this.status = status;
        this.dao = new EmprestimoDAO();
    }

    
    public String getDataloc() {
        return dataloc;
    }

    public void setDataloc(String dataloc) {
        this.dataloc = dataloc;
    }
     public String getDatadev() {
        return datadev;
    }

    public void setDatadev(String datadev) {
        this.datadev = datadev;
    }
      public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   
    @Override
    public String toString() {
        return "\n ID: " + this.getIde()
                + "\n Quantidade: " + this.getQuantidade()
                + "\n Dataloc:" + this.getDataloc()
                + "\n Datadev:" + this.getDatadev()
                + "\n Status:" + this.getStatus()
                + "\n Idc: " + this.getIdc()
                + "\n Idf: " + this.getIdf()
                + "\n -----------";
    }

    /*
    
     
    
     */
 
    public ArrayList getListaEmprestimo() {
       
        return dao.getListaEmprestimo();
    }

   

    public boolean InsertEmprestimoBD(int quantidade,String dataloc,String datadev,String status,int idc,int idf ) throws SQLException {
        int ide = this.maiorID() + 1;
        Emprestimo objeto = new Emprestimo(ide,quantidade, dataloc,datadev,status,idc,idf);

        dao.InsertEmprestimoBD(objeto);
        return true;

    }

  
    public boolean DeleteEmprestimoBD(int ide) {

        dao.DeleteEmprestimoBD(ide);
        return true;
    }

   
    public boolean UpdateEmprestimoBD(int ide,int quantidade,String dataloc,String datadev,String status,int idc,int idf){
        Emprestimo objeto = new Emprestimo(ide,quantidade,dataloc,datadev,status,idc,idf);

        dao.UpdateEmprestimoBD(objeto);
        return true;
    }

   
    public Emprestimo carregaEmprestimo(int ide) {

        dao.carregaEmprestimo(ide);
        return null;
    }
    
   
        public int maiorID() throws SQLException{

        return dao.maiorID();
    }   
}
