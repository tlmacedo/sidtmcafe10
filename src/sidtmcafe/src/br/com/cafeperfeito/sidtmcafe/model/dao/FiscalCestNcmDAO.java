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
        String comandoSql = "SELECT * FROM fiscalCestNcm ";
        if (!ncm.equals("")) {
            comandoSql += "WHERE ncm = ? ";
            addNewParametro(new Pair<>("String", ncm));
        }
        getResultSet(comandoSql);
        if (fiscalCestNcmVO == null && ncm.length() >= 4) {
            addNewParametro(new Pair<>("String", ncm.substring(0, 4) + "%"));
            getResultSet(comandoSql);
        }
        return fiscalCestNcmVO;
    }

    public List<FiscalCestNcmVO> getFiscalCestNcmVOList(String ncm) {
        fiscalCestNcmVOList = new ArrayList<>();
        String comandoSql = "SELECT * FROM fiscalCestNcm ";
        if (!ncm.equals("") || ncm != null) {
            addNewParametro(new Pair<String, String>("String", ncm + "%"));
            comandoSql += "WHERE ncm LIKE ? ";
        }
        getResultSet(comandoSql);
        if (fiscalCestNcmVOList.size() == 0 && ncm.length() >= 4) {
            addNewParametro(new Pair<String, String>("String", ncm.substring(0, 4) + "%"));
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
