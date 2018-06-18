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

    public FiscalCestNcmVO getFiscalCestNcmVO(int id) {
        getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE id = %d", id));
        return fiscalCestNcmVO;
    }

    public List<FiscalCestNcmVO> getFiscalCestNcmVOList(String ncm) {
        getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE ncm LIKE '%s' ORDER BY id", ncm));
        if (fiscalCestNcmVOList.size() == 0)
            getResultSet(String.format("SELECT * FROM fiscalCestNcm WHERE ncm LIKE = '%s' ORDER BY id", ncm.substring(0, 4)));
        return fiscalCestNcmVOList;
    }

    void getResultSet(String comandoSql) {
        boolean returnList = false;
        rs = getResultadosBandoDados(comandoSql);
        try {
            if (rs.last())
                if (returnList = (rs.getRow() > 1))
                    fiscalCestNcmVOList = new ArrayList<>();
            rs.beforeFirst();
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
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
