package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnectionFactory {

    private static Connection connection;

    public static Connection getTestConnection() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite::memory:");

            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tb_cliente (" +
                        "idc INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL, " +
                        "email TEXT NOT NULL UNIQUE, " +
                        "endereco TEXT, " +
                        "telefone TEXT)");

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tb_ferramenta (" +
                        "idf INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL, " +
                        "marca TEXT, " +
                        "valor REAL NOT NULL, " +
                        "setor TEXT, " +
                        "estoque INTEGER NOT NULL)");

                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tb_emprestimo (" +
                        "ide INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "quantidade INTEGER NOT NULL, " +
                        "dataloc TEXT NOT NULL, " +
                        "datadev TEXT, " +
                        "status TEXT NOT NULL, " +
                        "idc INTEGER NOT NULL, " +
                        "idf INTEGER NOT NULL, " +
                        "FOREIGN KEY (idc) REFERENCES tb_cliente (idc), " +
                        "FOREIGN KEY (idf) REFERENCES tb_ferramenta (idf))");

                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_idc ON tb_emprestimo (idc)");
                stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_idf ON tb_emprestimo (idf)");
            }

            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found.", e);
        }
    }
}
