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

    public List<FiscalCestNcmVO> getFiscalCestNcmVOList(String ncm) {
        fiscalCestNcmVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE ncm LIKE '%s' ORDER BY id", ncm), true);
        if (fiscalCestNcmVOList.size() == 0)
            getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE ncm LIKE = '%s' ORDER BY id", ncm.substring(0, 4)), true);
        return fiscalCestNcmVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                fiscalCestNcmVO = new FiscalCestNcmVO();
                fiscalCestNcmVO.setId(rs.getInt("id"));
                fiscalCestNcmVO.setSegmento(rs.getString("segmento"));
                fiscalCestNcmVO.setDescricao(rs.getString("descricao"));
                fiscalCestNcmVO.setCest(rs.getString("cest"));
                fiscalCestNcmVO.setNcm(rs.getString("ncm"));
                if (returnList) fiscalCestNcmVOList.add(fiscalCestNcmVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }
}
