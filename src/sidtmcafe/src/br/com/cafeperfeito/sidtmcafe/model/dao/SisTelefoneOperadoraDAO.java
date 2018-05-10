package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTelefoneOperadoraDAO extends BuscaBandoDados {

    ResultSet rs;

    String comandoSql = "";
    SisTelefoneOperadoraVO sisTelefoneOperadoraVO;
    List<SisTelefoneOperadoraVO> sisTelefoneOperadoraVOList;

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO(int id) {
        buscaSisTelefoneOperadoraVO(id);
        return sisTelefoneOperadoraVO;
    }

    public List<SisTelefoneOperadoraVO> getSisTelefoneOperadoraVOList() {
        buscaSisTelefoneOperadoraVO(0);
        return sisTelefoneOperadoraVOList;
    }

    void buscaSisTelefoneOperadoraVO(int id) {
        comandoSql = "SELECT id, descricao, tipo, ddd " +
                "FROM sisTelefoneOperadora ";
        if (id > 0) comandoSql += "WHERE id ='" + id + "' ";
        comandoSql += "ORDER BY tipo DESC, descricao ";

        if (id == 0) sisTelefoneOperadoraVOList = new ArrayList<>();
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                sisTelefoneOperadoraVO = new SisTelefoneOperadoraVO();
                sisTelefoneOperadoraVO.setId(rs.getInt("id"));
                sisTelefoneOperadoraVO.setDescricao(rs.getString("descricao"));
                sisTelefoneOperadoraVO.setTipo(rs.getInt("tipo"));
                sisTelefoneOperadoraVO.setCodigoDDD(rs.getInt("ddd"));

                if (id == 0) sisTelefoneOperadoraVOList.add(sisTelefoneOperadoraVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

}
