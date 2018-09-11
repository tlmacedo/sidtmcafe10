package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalCestNcmVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalCestNcmDAO extends ServiceBuscaBancoDados {

    FiscalCestNcmVO fiscalCestNcmVO = null;
    List<FiscalCestNcmVO> fiscalCestNcmVOList = null;

    public FiscalCestNcmVO getFiscalCestNcmVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalCestNcm WHERE id = ? ");
        return fiscalCestNcmVO;
    }

    public FiscalCestNcmVO getFiscalCestNcmVO(String ncm) {
        String value = ncm.replaceAll("\\D", "");
        String comandoSql = "SELECT * FROM fiscalCestNcm ";
        if (!value.equals("")) {
            comandoSql += "WHERE ncm = ? ";
            addNewParametro(new Pair<>("String", value));
        }
        getResultSet(comandoSql);
        if (fiscalCestNcmVO == null && value.length() >= 4) {
            addNewParametro(new Pair<>("String", value.substring(0, 4) + "%"));
            getResultSet(comandoSql);
        }
        return fiscalCestNcmVO;
    }

    public List<FiscalCestNcmVO> getFiscalCestNcmVOList(String ncm) {
        String value = ncm.replaceAll("\\D", "");
        fiscalCestNcmVOList = new ArrayList<>();
        String comandoSql = "SELECT * FROM fiscalCestNcm ";
        if (!value.equals("") || value != null) {
            addNewParametro(new Pair<String, String>("String", value + "%"));
            comandoSql += "WHERE ncm LIKE ? ";
        }
        getResultSet(comandoSql);
        if (fiscalCestNcmVOList.size() == 0 && value.length() >= 4) {
            addNewParametro(new Pair<String, String>("String", value.substring(0, 4) + "%"));
            getResultSet(comandoSql);
        }
        return fiscalCestNcmVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY ncm ");
        try {
            while (rs.next()) {
                fiscalCestNcmVO = new FiscalCestNcmVO();
                fiscalCestNcmVO.setId(rs.getInt("id"));
                fiscalCestNcmVO.setSegmento(rs.getString("segmento"));
                fiscalCestNcmVO.setDescricao(rs.getString("descricao"));
                fiscalCestNcmVO.setCest(rs.getString("cest").replaceAll("\\D", ""));
                fiscalCestNcmVO.setNcm(rs.getString("ncm").replaceAll("\\D", ""));
                if (fiscalCestNcmVOList != null) fiscalCestNcmVOList.add(fiscalCestNcmVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
