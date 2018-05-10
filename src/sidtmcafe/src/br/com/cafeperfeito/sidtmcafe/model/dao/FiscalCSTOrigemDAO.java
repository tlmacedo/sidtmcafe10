package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalCSTOrigemVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FiscalCSTOrigemDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    FiscalCSTOrigemVO fiscalCSTOrigemVO;
    List<FiscalCSTOrigemVO> fiscalCSTOrigemVOList;

    public FiscalCSTOrigemVO getFiscalCSTOrigemVO(int id) {
        buscaFiscalCSTOrigemVO(id);
        return fiscalCSTOrigemVO;
    }

    public List<FiscalCSTOrigemVO> getFiscalCSTOrigemVOList() {
        buscaFiscalCSTOrigemVO(0);
        return fiscalCSTOrigemVOList;
    }

    void buscaFiscalCSTOrigemVO(int id) {
        comandoSql = "SELECT id, descricao ";
        comandoSql += "FROM fiscalCSTOrigem ";
        if (id > 0) comandoSql += "WHERE id = " + id + " ";
        comandoSql += "ORDER BY id ";

        if (id == 0) fiscalCSTOrigemVOList = new ArrayList<FiscalCSTOrigemVO>();
        rs = getResultadosBandoDados(comandoSql);

        try {
            while (rs.next()) {
                fiscalCSTOrigemVO = new FiscalCSTOrigemVO();
                fiscalCSTOrigemVO.setId(rs.getInt("id"));
                fiscalCSTOrigemVO.setDescricao(rs.getString("descricao"));

                if (id == 0) fiscalCSTOrigemVOList.add(fiscalCSTOrigemVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
