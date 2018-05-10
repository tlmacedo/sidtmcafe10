package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuscaBandoDados {

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    int idInclusao = 0;

    ResultSet getResultadosBandoDados(String instrucaoSql) {
        con = ConnectionFactory.getConnection();
        try {
            stmt = con.prepareStatement(instrucaoSql);
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    void getUpdateBancoDados(Connection conn, String comandoSql) throws SQLException {
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();

    }

    int getInsertBancoDados(Connection conn, String comandoSql) throws SQLException {
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();

        rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
        if (rs.next())
            idInclusao = rs.getInt("LAST_INSERT_ID()");
        return idInclusao;
    }

    void getDeleteBancoDados(Connection conn, String comandoSql) throws SQLException {
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();

    }


}
