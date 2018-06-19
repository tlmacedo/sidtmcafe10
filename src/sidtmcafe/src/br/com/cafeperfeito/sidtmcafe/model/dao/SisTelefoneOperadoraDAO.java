package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTelefoneOperadoraDAO extends BuscaBancoDados {

    ResultSet rs;
    SisTelefoneOperadoraVO sisTelefoneOperadoraVO;
    List<SisTelefoneOperadoraVO> sisTelefoneOperadoraVOList;
    boolean returnList = false;

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO(int id) {
        getResultSet(String.format("SELECT * FROM sisTelefoneOperadora WHERE id = %d ORDER BY tipo DESC, descricao", id), false);
        return sisTelefoneOperadoraVO;
    }

    public List<SisTelefoneOperadoraVO> getSisTelefoneOperadoraVOList() {
        sisTelefoneOperadoraVOList = new ArrayList<>();
        getResultSet(String.format("SELECT * FROM sisTelefoneOperadora ORDER BY tipo DESC, descricao"), true);
        return sisTelefoneOperadoraVOList;
    }

    void getResultSet(String comandoSql, boolean returnList) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisTelefoneOperadoraVO = new SisTelefoneOperadoraVO();
                sisTelefoneOperadoraVO.setId(rs.getInt("id"));
                sisTelefoneOperadoraVO.setDescricao(rs.getString("descricao"));
                sisTelefoneOperadoraVO.setTipo(rs.getInt("tipo"));
                sisTelefoneOperadoraVO.setCodigoDDD(rs.getInt("ddd"));
                if (returnList) sisTelefoneOperadoraVOList.add(sisTelefoneOperadoraVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
}
