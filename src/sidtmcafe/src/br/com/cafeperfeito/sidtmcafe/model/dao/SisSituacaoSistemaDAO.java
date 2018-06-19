package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisSituacaoSistemaVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisSituacaoSistemaDAO extends BuscaBancoDados {

    ResultSet rs;
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;
    boolean returnList = false;

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO(int id) {
        getResultSet(String.format("SELECT * FROM sisSituacaoSistema WHERE id = %d ORDER BY descricao", id), false);
        return sisSituacaoSistemaVO;
    }

    public List<SisSituacaoSistemaVO> getSisSituacaoSistemaVOList() {
        sisSituacaoSistemaVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisSituacaoSistema ORDER BY descricao"), true);
        return sisSituacaoSistemaVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisSituacaoSistemaVO = new SisSituacaoSistemaVO();
                sisSituacaoSistemaVO.setId(rs.getInt("id"));
                sisSituacaoSistemaVO.setDescricao(rs.getString("descricao"));
                sisSituacaoSistemaVO.setClassificacao(rs.getInt("classificacao"));
                if (returnList) sisSituacaoSistemaVOList.add(sisSituacaoSistemaVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
