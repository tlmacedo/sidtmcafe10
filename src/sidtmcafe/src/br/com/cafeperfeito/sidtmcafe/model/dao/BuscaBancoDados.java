package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscaBancoDados {

    Connection connection;
    PreparedStatement stmt;
    ResultSet rs;

    ResultSet getResultadosBandoDados(String instrucaoSql) {
        connection = ConnectionFactory.getConnection();
        try {
            rs = (stmt = connection.prepareStatement(instrucaoSql)).executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    void getUpdateBancoDados(Connection conn, String comandoSql) throws SQLException {
        (stmt = conn.prepareStatement(comandoSql)).execute();
    }

    int getInsertBancoDados(Connection conn, String comandoSql) throws SQLException {
        (stmt = conn.prepareStatement(comandoSql)).execute();
        rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
        if (rs.next())
            return rs.getInt("LAST_INSERT_ID()");
        return 0;
    }

    void getDeleteBancoDados(Connection conn, String comandoSql) throws SQLException {
        (stmt = conn.prepareStatement(comandoSql)).execute();
    }


}
