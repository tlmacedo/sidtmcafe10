package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisSituacaoSistemaVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisSituacaoSistemaDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO(int id) {
        buscaSisSituacaoSistema(id);
        return sisSituacaoSistemaVO;
    }

    public List<SisSituacaoSistemaVO> getSisSituacaoSistemaVOList() {
        buscaSisSituacaoSistema(0);
        return sisSituacaoSistemaVOList;
    }

    void buscaSisSituacaoSistema(int id) {
        comandoSql = String.format("SELECT id, descricao, classificacao FROM sisSituacaoSistema %sORDER BY descricao",
                id > 0 ? String.format("WHERE id = %d ", id) : "");
        if (id == 0) sisSituacaoSistemaVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisSituacaoSistemaVO = new SisSituacaoSistemaVO();
                sisSituacaoSistemaVO.setId(rs.getInt("id"));
                sisSituacaoSistemaVO.setDescricao(rs.getString("descricao"));
                sisSituacaoSistemaVO.setClassificacao(rs.getInt("classificacao"));

                if (id == 0) sisSituacaoSistemaVOList.add(sisSituacaoSistemaVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
