package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalPisCofinsVO;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalPisCofinsDAO extends BuscaBancoDados {

    FiscalPisCofinsVO fiscalPisCofinsVO = null;
    List<FiscalPisCofinsVO> fiscalPisCofinsVOList = null;

    public FiscalPisCofinsVO getFiscalPisCofinsVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalPisCofins WHERE id = ? ");
        return fiscalPisCofinsVO;
    }

    public List<FiscalPisCofinsVO> getFiscalPisCofinsVOList() {
        fiscalPisCofinsVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalPisCofins ");
        return fiscalPisCofinsVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                fiscalPisCofinsVO = new FiscalPisCofinsVO();
                fiscalPisCofinsVO.setId(rs.getInt("id"));
                fiscalPisCofinsVO.setDescricao(rs.getString("descricao"));
                if (fiscalPisCofinsVOList != null) fiscalPisCofinsVOList.add(fiscalPisCofinsVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
