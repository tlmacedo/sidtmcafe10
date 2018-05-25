package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisSituacaoSistemaVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisSituacaoSistemaDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    SisSituacaoSistemaVO sisSituacaoSistemaVO;
    List<SisSituacaoSistemaVO> sisSituacaoSistemaVOList;

    public SisSituacaoSistemaVO getSisSituacaoSistemaVO(int id) {
        buscaSisSituacaoSistemaVO(id);
        return sisSituacaoSistemaVO;
    }

    public List<SisSituacaoSistemaVO> getSisSituacaoSistemaVOList() {
        buscaSisSituacaoSistemaVO(0);
        return sisSituacaoSistemaVOList;
    }

    void buscaSisSituacaoSistemaVO(int id) {
        comandoSql = "SELECT id, descricao, classificacao " +
                "FROM sisSituacaoSistema ";
        if (id != 0) comandoSql += "WHERE id = " + id + " ";
        comandoSql += "ORDER BY descricao ";

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
