package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuscaBancoDados {

    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Pair<String, String>> listParametro = new ArrayList<>();

    void getResultadosBandoDados(String sql) {
        try {
            stmt = (connection = ConnectionFactory.getConnection()).prepareStatement(sql);
            rs = loadParametro().executeQuery();
        } catch (SQLException e) {
            rs = null;
            e.printStackTrace();
        }
    }

    void getUpdateBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
    }

    int getInsertBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
        rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
        if (rs.next())
            return rs.getInt("LAST_INSERT_ID()");
        return 0;
    }

    void getDeleteBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
    }

    void addParametro(Pair<String, String> parametro) {
        listParametro.add(parametro);
    }

    void addNewParametro(Pair<String, String> parametro) {
        listParametro = new ArrayList<>();
        listParametro.add(parametro);
    }


    PreparedStatement loadParametro() {
        try {
            stmt.clearParameters();
            for (int i = 0; i < listParametro.size(); i++) {
                switch (listParametro.get(i).getKey().toLowerCase()) {
                    case "string":
                        stmt.setString(i + 1, listParametro.get(i).getValue());
                        break;
                    case "int":
                        stmt.setInt(i + 1, Integer.parseInt(listParametro.get(i).getValue()));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

}
