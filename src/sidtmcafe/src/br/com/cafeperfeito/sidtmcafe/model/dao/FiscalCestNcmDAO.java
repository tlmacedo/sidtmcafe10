package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalCestNcmVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalCestNcmDAO extends BuscaBancoDados {

    ResultSet rs;
    FiscalCestNcmVO fiscalCestNcmVO;
    List<FiscalCestNcmVO> fiscalCestNcmVOList;
    boolean returnList = false;

    public FiscalCestNcmVO getFiscalCestNcmVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE id = %d", id), false);
        return fiscalCestNcmVO;
    }

    public FiscalCestNcmVO getFiscalCestNcmVO(String ncm) {
        getResultSet(String.format("SELECT * FROM fiscalCestNcm %s ", ncm != null ?
                String.format("WHERE ncm = '%s'", ncm) : ""), false);
        if (fiscalCestNcmVO == null)
            getResultSet(String.format("SELECT * FROM fiscalCestNcm %s ", ncm != null ?
                    String.format("WHERE ncm = '%s'", ncm.substring(0, 4)) : ""), false);
        return fiscalCestNcmVO;
    }

    public List<FiscalCestNcmVO> getFiscalCestNcmVOList(String ncm) {
        try {
            fiscalCestNcmVOList = new ArrayList<>();
            getResultSet(String.format("SELECT * FROM fiscalCestNcm %s ", (ncm != null && !ncm.equals("")) ?
                    String.format("WHERE ncm LIKE '%s'", ncm + "%") : ""), true);
            if (fiscalCestNcmVOList.size() == 0)
                if (ncm.length() >= 4)
                    getResultSet(String.format("SELECT * FROM fiscalCestNcm %s ", (ncm != null && !ncm.equals("")) ?
                            String.format("WHERE ncm LIKE '%s'", ncm.substring(0, 4) + "%") : ""), true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fiscalCestNcmVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(String.format("%s ORDER BY ncm, cest", comandoSql));
        try {
            while (rs.next()) {
                fiscalCestNcmVO = new FiscalCestNcmVO();
                fiscalCestNcmVO.setId(rs.getInt("id"));
                fiscalCestNcmVO.setSegmento(rs.getString("segmento"));
                fiscalCestNcmVO.setDescricao(rs.getString("descricao"));
                fiscalCestNcmVO.setCest(rs.getString("cest").replaceAll("\\D", ""));
                fiscalCestNcmVO.setNcm(rs.getString("ncm").replaceAll("\\D", ""));
                if (returnList) fiscalCestNcmVOList.add(fiscalCestNcmVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
