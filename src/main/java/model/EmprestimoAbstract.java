package Model;

public abstract class EmprestimoAbstract {

    
    private int ide;
    private int idc;
    private int idf;
    private int quantidade;

   
    public EmprestimoAbstract() {
    }

   
    public EmprestimoAbstract(int ide, int idc, int idf, int quantidade) {
        this.ide = ide;
        this.idc = idc;
        this.idf = idf;
        this.quantidade = quantidade;
    }

   
    public int getIde() {
        return ide;
    }

    public void setIde(int ide) {
        this.ide = ide;
    }

    public int getIdc() {
        return ide;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
