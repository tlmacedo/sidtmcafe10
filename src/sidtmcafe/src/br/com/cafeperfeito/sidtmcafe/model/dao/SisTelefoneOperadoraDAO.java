package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;

import java.sql.Connection;
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

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO_WS(String wsOperadora) {
        String[] wsDetalhes = wsOperadora.replace("|", ";").split(";");

        getResultSet(String.format("SELECT * FROM sisTelefoneOperadora WHERE codWsPortabilidadeCelular LIKE '%s' ORDER BY tipo DESC, descricao", ("%" + wsDetalhes[0] + "%")), false);
        if (sisTelefoneOperadoraVO == null) {
            sisTelefoneOperadoraVO = new SisTelefoneOperadoraVO(wsDetalhes[3], wsDetalhes[0]);
            try {
                sisTelefoneOperadoraVO.setId(insertSisTelefoneOperadoraVO(ConnectionFactory.getConnection(), sisTelefoneOperadoraVO));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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

    public int insertSisTelefoneOperadoraVO(Connection conn, SisTelefoneOperadoraVO telefoneOperadora) throws SQLException {
        String comandoSql = String.format("INSERT INTO sisTelefoneOperadora (descricao, tipo, ddd, codWsPortabilidadeCelular)" +
                        "VALUES('%s', %d, %d, '%s')",
                telefoneOperadora.getDescricao(), telefoneOperadora.getTipo(), telefoneOperadora.getCodigoDDD(),
                telefoneOperadora.getCodigoWs());
        return getInsertBancoDados(conn, comandoSql);
    }

}
