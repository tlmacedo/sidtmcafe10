package br.com.cafeperfeito.sidtmcafe.interfaces.database;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;

import java.sql.*;

public class ConnectionFactory implements Constants {

    public static Connection getConnection() {
        try {
            Class.forName(BD_DRIVER);
            return DriverManager.getConnection(BD_URL, BD_USER, BD_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na minha conexão: ", ex);
        }
    }

    public static void closeConnection(Connection conexao) {
        try {
            if (conexao != null) conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void closeConnection(Connection conexao, PreparedStatement pstmt) {
        closeConnection(conexao);
        try {
            if (pstmt != null) pstmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection(Connection conexao, PreparedStatement pstmt, ResultSet rs) {
        closeConnection(conexao, pstmt);
        try {
            if (rs != null) rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
