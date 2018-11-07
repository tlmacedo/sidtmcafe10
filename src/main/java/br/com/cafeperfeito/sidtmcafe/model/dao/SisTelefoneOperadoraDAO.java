package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SisTelefoneOperadoraDAO extends ServiceBuscaBancoDados {

    SisTelefoneOperadoraVO sisTelefoneOperadoraVO = null;
    List<SisTelefoneOperadoraVO> sisTelefoneOperadoraVOList = null;

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM sisTelefoneOperadora WHERE id = ? ");
        return sisTelefoneOperadoraVO;
    }

    public SisTelefoneOperadoraVO getSisTelefoneOperadoraVO_WS(String wsOperadora) {
        String[] wsDetalhes = wsOperadora.replace("|", ";").split(";");

        addNewParametro(new Pair<>("String", "%" + wsDetalhes[0] + "%"));
        getResultSet("SELECT * FROM sisTelefoneOperadora WHERE codWsPortabilidadeCelular LIKE ? ");
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
        getResultSet("SELECT * FROM sisTelefoneOperadora ");
        return sisTelefoneOperadoraVOList;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY tipo DESC, descricao ");
        try {
            while (rs.next()) {
                sisTelefoneOperadoraVO = new SisTelefoneOperadoraVO();
                sisTelefoneOperadoraVO.setId(rs.getInt("id"));
                sisTelefoneOperadoraVO.setDescricao(rs.getString("descricao"));
                sisTelefoneOperadoraVO.setTipo(rs.getInt("tipo"));
                sisTelefoneOperadoraVO.setCodigoDDD(rs.getInt("ddd"));
                if (sisTelefoneOperadoraVOList != null) sisTelefoneOperadoraVOList.add(sisTelefoneOperadoraVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    public int insertSisTelefoneOperadoraVO(Connection conn, SisTelefoneOperadoraVO telefoneOperadora) throws SQLException {
        addNewParametro(new Pair<>("String", telefoneOperadora.getDescricao()));
        addParametro(new Pair<>("int", String.valueOf(telefoneOperadora.getTipo())));
        addParametro(new Pair<>("int", String.valueOf(telefoneOperadora.getCodigoDDD())));
        addParametro(new Pair<>("String", telefoneOperadora.getCodigoWs()));
        String comandoSql = "INSERT INTO sisTelefoneOperadora (descricao, tipo, ddd, codWsPortabilidadeCelular) VALUES(?, ?, ?, ?)";
        return getInsertBancoDados(conn, comandoSql);
    }

}
