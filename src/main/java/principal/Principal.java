package principal;

import view.TelaPrincipal;

/**
 * Classe responsável por iniciar a aplicação.
 * <p>
 * Contém o método {@code main}, que é o ponto de entrada do sistema.
 * Ao ser executado, inicializa a interface gráfica principal
 * {@link view.TelaPrincipal}.
 * </p>
 *
 * <p>Faz parte do pacote {@code principal}, que contém a inicialização da aplicação.</p>
 *
 * @author Artur Azambuja
 * @version 1.0
 * @since 1.0
 */
public class Principal {

    /**
     * Método principal que executa a aplicação.
     * <p>
     * Cria uma instância da tela principal e a torna visível ao usuário.
     * </p>
     *
     * @param args argumentos passados pela linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        TelaPrincipal objetotela = new TelaPrincipal();
        objetotela.setVisible(true);
    }
}
