package DAO;

import java.sql.*;
import java.util.Set;

public abstract class BaseDAO {

    protected Connection connection;

    private static final Set<String> ALLOWED_TABLES = Set.of(
            "tb_cliente",
            "tb_emprestimo",
            "tb_ferramenta"
    );

    private static final Set<String> ALLOWED_COLUMNS = Set.of(
            "idc",
            "ide",
            "idf"
    );

    public BaseDAO() {
        this.connection = getConexao();
    }

    public BaseDAO(Connection conn) {
        this.connection = conn;
    }

    protected Connection getConexao() {
        try {
            if (connection != null && !connection.isClosed()) return connection;
            String url = "jdbc:sqlite:db_loans_software.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Status: Conectado ao SQLite!");
        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar ao SQLite: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    protected int maiorID(String tabela, String campoID) throws SQLException {
        if (!ALLOWED_TABLES.contains(tabela) || !ALLOWED_COLUMNS.contains(campoID)) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        int result = 0;
        if (this.connection == null) return result;

        String sql = "SELECT MAX(" + campoID + ") AS max_id FROM " + tabela;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                result = rs.getInt("max_id");
            }
        }
        return result;
    }
}
