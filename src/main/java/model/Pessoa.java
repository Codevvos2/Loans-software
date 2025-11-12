package model;

public abstract class Pessoa {

 
    private int idc;
    private String nome;
    private String email;

   
    public Pessoa() {
    }

   
    public Pessoa(int idc, String nome, String email) {
        this.idc = idc;
        this.nome = nome;
        this.email = email;
    }

    
    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
