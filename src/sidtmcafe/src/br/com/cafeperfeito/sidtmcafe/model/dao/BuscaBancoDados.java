package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscaBancoDados {

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;

    ResultSet getResultadosBandoDados(String instrucaoSql) {
        con = ConnectionFactory.getConnection();
        try {
            rs = (stmt = con.prepareStatement(instrucaoSql)).executeQuery();
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
        if (rs.wasNull()) return 0;
        return rs.getInt("LAST_INSERT_ID()");
    }

    void getDeleteBancoDados(Connection conn, String comandoSql) throws SQLException {
        (stmt = conn.prepareStatement(comandoSql)).execute();
    }


}
