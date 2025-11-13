package dao;

import java.sql.*;
import java.util.Map;
import java.util.Set;

/**
 * Classe abstrata que fornece os métodos e estruturas básicas para acesso ao banco de dados.
 *
 * Todas as classes DAO do sistema herdam desta classe para reutilizar a conexão e funções
 * comuns, como o cálculo do maior ID de uma tabela.
 *
 * <p>Esta implementação utiliza o banco de dados SQLite.</p>
 *
 * @author Gustavo Russeff
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseDAO {

    /** Conexão ativa com o banco de dados SQLite. */
    protected Connection connection;

    /**
     * Construtor padrão. Tenta estabelecer uma conexão com o banco de dados SQLite.
     */
    public BaseDAO() {
        this.connection = getConexao();
    }

    /**
     * Construtor alternativo que permite injetar uma conexão específica,
     * geralmente usada em testes.
     *
     * @param conn conexão existente que será utilizada pelo DAO
     */
    public BaseDAO(Connection conn) {
        this.connection = conn;
    }

    /**
     * Cria (ou reutiliza) uma conexão com o banco de dados SQLite.
     *
     * @return objeto {@link Connection} conectado ao banco, ou {@code null} em caso de falha
     */
    protected Connection getConexao() {
        try {
            if (connection != null && !connection.isClosed()) return connection;
            String url = "jdbc:sqlite:db_loans_software.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Status: Conectado ao SQLite!");
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao SQLite: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    /**
     * Retorna o maior valor do campo ID de uma tabela específica.
     *
     * <p>Este método é útil para determinar o próximo identificador a ser utilizado
     * em novas inserções.</p>
     *
     * @param tabela nome da tabela no banco de dados
     * @param campoID nome do campo de identificação (geralmente a chave primária)
     * @return maior valor do campo informado ou 0 se não houver registros
     * @throws SQLException se ocorrer erro durante a consulta
     * @throws IllegalArgumentException se o nome da tabela ou campo for inválido
     */

    private static final Map<String, Set<String>> ALLOWED_IDENTIFIERS = Map.of(
            "tb_emprestimo", Set.of("ide", "quantidade", "dataloc", "datadev", "status", "idc", "idf"),
            "tb_ferramenta",  Set.of("idf", "nome", "marca", "valor", "setor", "estoque")
    );

    public int maiorID(String tabela, String campoID) throws SQLException {
        Set<String> allowedCols = ALLOWED_IDENTIFIERS.get(tabela);
        if (allowedCols == null || !allowedCols.contains(campoID)) {
            throw new IllegalArgumentException("Tabela ou campoID não permitido");
        }

        String sql = "SELECT MAX(" + campoID + ") AS max_id FROM " + tabela;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("max_id");
            }
            return 0;
        }
    }



    /**
     * Verifica se o nome informado (de tabela ou campo) é seguro para uso em SQL,
     * evitando possíveis injeções.
     *
     * @param name nome da tabela ou campo a ser validado
     * @return {@code true} se o nome for seguro e válido
     */
    private boolean isSafeName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9_]+$");
    }
}
