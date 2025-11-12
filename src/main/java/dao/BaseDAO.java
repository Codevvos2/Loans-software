package dao;

import java.sql.*;

public abstract class BaseDAO {

    protected Connection connection;

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
            System.out.println("Não foi possível conectar ao SQLite: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    protected int maiorID(String tabela, String campoID) throws SQLException {
        int result = 0;
        if (this.connection == null) return result;


        if (!isSafeName(tabela) || !isSafeName(campoID)) {
            throw new IllegalArgumentException("Tabela ou campoID inválido");
        }

        String sql = "SELECT MAX(" + campoID + ") AS max_id FROM " + tabela;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                result = rs.getInt("max_id");
            }
        }

        return result;
    }

    private boolean isSafeName(String name) {
        return name != null && name.matches("[a-zA-Z0-9_]+");
    }
}
