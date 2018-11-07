package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.FiscalFreteSituacaoTributariaVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class FiscalFreteSituacaoTributariaDAO extends ServiceBuscaBancoDados {
    FiscalFreteSituacaoTributariaVO freteSituacaoTributariaVO = null;
    List<FiscalFreteSituacaoTributariaVO> fiscalFreteSituacaoTributariaVOList = null;

    public FiscalFreteSituacaoTributariaVO getFiscalFreteSituacaoTributariaVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM fiscalFreteSituacaoTributaria WHERE id = ? ");
        return freteSituacaoTributariaVO;
    }

    public List<FiscalFreteSituacaoTributariaVO> getFiscalFreteSituacaoTributariaVOList() {
        fiscalFreteSituacaoTributariaVOList = new ArrayList<>();
        getResultSet("SELECT * FROM fiscalFreteSituacaoTributaria ");
        return fiscalFreteSituacaoTributariaVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                freteSituacaoTributariaVO = new FiscalFreteSituacaoTributariaVO();
                freteSituacaoTributariaVO.setId(rs.getInt("id"));
                freteSituacaoTributariaVO.setDescricao(rs.getString("descricao"));
                if (fiscalFreteSituacaoTributariaVOList != null)
                    fiscalFreteSituacaoTributariaVOList.add(freteSituacaoTributariaVO);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

}
