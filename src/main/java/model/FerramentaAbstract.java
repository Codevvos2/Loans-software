package Model;

public abstract class FerramentaAbstract {

    
    private int idf;
    private String nome;
    private String marca;

  
    public FerramentaAbstract() {
    }

   
    public FerramentaAbstract(int idf, String nome, String marca) {
        this.idf = idf;
        this.nome = nome;
        this.marca = marca;
    }

  
    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

}
