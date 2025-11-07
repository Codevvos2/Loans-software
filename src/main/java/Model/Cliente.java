package Model;

import java.util.*;
import DAO.ClienteDAO;
import java.sql.SQLException;

public class Cliente extends Pessoa {

    // Atributos
    private String endereco;
    private String telefone;
    private final ClienteDAO dao; 

    // Método Construtor de Objeto Vazio
    public Cliente() {
        this.dao = new ClienteDAO(); // inicializado não importa em qual construtor
    }

    // Método Construtor de Objeto, inserindo dados
    public Cliente(String endereco, String telefone) {
        this.endereco = endereco;
        this.telefone = telefone;
        this.dao = new ClienteDAO(); // inicializado não importa em qual construtor
    }

    // Método Construtor usando também o construtor da SUPERCLASSE
    public Cliente(int idc,String nome,String email,String endereco,String telefone) {
        super(idc, nome, email);
        this.endereco = endereco;
        this.telefone = telefone;
        this.dao = new ClienteDAO(); // inicializado não importa em qual construtor
    }

    // Métodos GET e SET
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Override necessário para poder retornar os dados de Pessoa no toString para Cliente.
    @Override
    public String toString() {
        return "\n ID: " + this.getIdc()
                + "\n Nome: " + this.getNome()
                + "\n Email: " + this.getEmail()
                + "\n Endereco: " + this.getEndereco()
                + "\n Telefone:" + this.getTelefone()
                + "\n -----------";
    }

    /*
    
        ABAIXO OS M�TODOS PARA USO JUNTO COM O DAO
        SIMULANDO A ESTRUTURA EM CAMADAS PARA USAR COM BANCOS DE DADOS.
    
     */
    // Retorna a Lista de Cliente(objetos)
    public ArrayList getListaCliente() {
        //return AlunoDAO.MinhaLista;
        return dao.getListaCliente();
    }

    // Cadastra novo Cliente

    public boolean InsertClienteBD(String nome, String email, String endereco, String telefone) throws SQLException {
        int idc = this.maiorID() + 1;
        Cliente objeto = new Cliente(idc,nome,email,endereco,telefone);
//        ClienteDAO.ListaCliente.add(objeto);
        dao.InsertClienteBD(objeto);
        return true;

    }

    // Deleta um cliente específico pelo seu campo ID
    public boolean DeleteClienteBD(int idc) {
//        int indice = this.procuraIndice(id);
//        ClienteDAO.ListaCliente.remove(indice);
        dao.DeleteClienteBD(idc);
        return true;
    }

    // Edita um Cliente específico pelo seu campo ID
    public boolean UpdateClienteBD(int idc,String nome,String email,String endereco,String telefone) {
        Cliente objeto = new Cliente(idc, nome, email, endereco,telefone);
//        int indice = this.procuraIndice(id);
//        ClienteDAO.ListaCliente.set(indice, objeto);
        dao.UpdateClienteBD(objeto);
        return true;
    }

    // procura o INDICE de objeto da ListaCliente que contem o ID enviado.
//    private int procuraIndice(int id) {
//        int indice = -1;
//        for (int i = 0; i < ClienteDAO.ListaCliente.size(); i++) {
//            if (ClienteDAO.ListaCliente.get(i).getId() == id) {
//                indice = i;
//            }
//        }
//        return indice;
//    }

    // carrega dados de um cliente especéfico pelo seu ID
    public Cliente carregaCliente(int idc) {
//        int indice = this.procuraIndice(id);
//        return ClienteDAO.ListaCliente.get(indice);
        dao.carregaCliente(idc);
        return null;
    }
    
    // retorna o maior ID da nossa base de dados
        public int maiorID() throws SQLException{
//    public int maiorID(){
//        return ClienteDAO.maiorID();
        return dao.maiorID();
    }   
}

