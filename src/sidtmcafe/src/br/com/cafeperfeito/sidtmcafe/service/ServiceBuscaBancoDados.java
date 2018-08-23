package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import javafx.util.Pair;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceBuscaBancoDados {

    public Connection connection = null;
    public PreparedStatement stmt = null;
    public ResultSet rs = null;
    public byte[] bytes0;
    public byte[] bytes1;
    public List<Pair<String, String>> listParametro = new ArrayList<>();

    public void getResultadosBandoDados(String sql) {
        try {
            stmt = (connection = ConnectionFactory.getConnection()).prepareStatement(sql);
            rs = loadParametro().executeQuery();
        } catch (SQLException e) {
            rs = null;
            e.printStackTrace();
        }
    }

    public void getUpdateBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
    }

    public int getInsertBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
        rs = conn.prepareStatement("SELECT LAST_INSERT_ID()").executeQuery();
        if (rs.next())
            return rs.getInt("LAST_INSERT_ID()");
        return 0;
    }

    public void getDeleteBancoDados(Connection conn, String sql) throws SQLException {
        stmt = conn.prepareStatement(sql);
        loadParametro().execute();
    }

    public void addParametro(Pair<String, String> parametro) {
        listParametro.add(parametro);
    }

    public void addNewParametro(Pair<String, String> parametro) {
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
                    case "blob0":
                        Blob blob = new SerialBlob(bytes0);
                        stmt.setBlob(i + 1, blob);
                        break;
                    case "blob10":
                        Blob blob1 = new SerialBlob(bytes1);
                        stmt.setBlob(i + 1, blob1);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }
}