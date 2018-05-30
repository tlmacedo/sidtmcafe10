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
        //System.out.println("BuscaBancoDados_getResultado ==>> " + instrucaoSql + "\n");
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
        //System.out.println("BuscaBancoDados_update ==>> " + comandoSql + "\n");
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();
    }

    int getInsertBancoDados(Connection conn, String comandoSql) throws SQLException {
        //System.out.println("BuscaBancoDados_insert ==>> " + comandoSql + "\n");
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();

        rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
        if (rs.next())
            return rs.getInt("LAST_INSERT_ID()");
        return 0;
    }

    void getDeleteBancoDados(Connection conn, String comandoSql) throws SQLException {
        //System.out.println("BuscaBancoDados_delete ==>> " + comandoSql + "\n");
        stmt = conn.prepareStatement(comandoSql);
        stmt.execute();
    }


}
