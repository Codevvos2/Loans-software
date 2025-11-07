package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnectionFactory {

    private static final String URL_SQLITE = "jdbc:sqlite::memory:";

    public static Connection getTestConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL_SQLITE);

            createTestTables(connection);

            return connection;
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão de teste com SQLite: " + e.getMessage());
            throw e;
        }
    }

    private static void createTestTables(Connection conn) throws SQLException {
        String sqlFerramenta = "CREATE TABLE IF NOT EXISTS tb_ferramenta (" +
                "idf INTEGER PRIMARY KEY, " +
                "nome VARCHAR(255) NOT NULL, " +
                "marca VARCHAR(255), " +
                "valor REAL, " +
                "setor VARCHAR(255), " +
                "estoque INTEGER);";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sqlFerramenta);
        }
    }
}