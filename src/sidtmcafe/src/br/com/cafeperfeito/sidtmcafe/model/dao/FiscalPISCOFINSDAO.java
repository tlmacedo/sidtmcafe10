package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalPISCOFINSVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalPISCOFINSDAO extends BuscaBandoDados {
    ResultSet rs;

    String comandoSql = "";
    FiscalPISCOFINSVO fiscalPISCOFINSVO;
    List<FiscalPISCOFINSVO> fiscalPISCOFINSVOList;

    public FiscalPISCOFINSVO getFiscalPISCOFINSVO(int id) {
        buscaFiscalPISCOFINSVO(id);
        return fiscalPISCOFINSVO;
    }

    public List<FiscalPISCOFINSVO> getFiscalPISCOFINSVOList() {
        buscaFiscalPISCOFINSVO(0);
        return fiscalPISCOFINSVOList;
    }

    void buscaFiscalPISCOFINSVO(int id) {
        comandoSql = "SELECT id, descricao ";
        comandoSql += "FROM fiscalPISCOFINS ";
        if (id > 0) comandoSql += "WHERE id = '" + id + "' ";
        comandoSql += "ORDER BY id ";

        if (id == 0) fiscalPISCOFINSVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                fiscalPISCOFINSVO = new FiscalPISCOFINSVO();
                fiscalPISCOFINSVO.setId(rs.getInt("id"));
                fiscalPISCOFINSVO.setDescricao(rs.getString("descricao"));

                if (id == 0) fiscalPISCOFINSVOList.add(fiscalPISCOFINSVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
