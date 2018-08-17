package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabTelefoneVO;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabTelefoneDAO extends BuscaBancoDados implements Constants {

    TabTelefoneVO tabTelefoneVO = null;

    public TabTelefoneVO getTabTelefoneVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabTelefone WHERE id = ? ");
        if (tabTelefoneVO != null)
            addObjetosPesquisa(tabTelefoneVO);
        return tabTelefoneVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
        try {
            while (rs.next()) {
                tabTelefoneVO = new TabTelefoneVO();
                tabTelefoneVO.setId(rs.getInt("id"));
                tabTelefoneVO.setDescricao(rs.getString("descricao"));
                tabTelefoneVO.setSisTelefoneOperadora_id(rs.getInt("sisTelefoneOperadora_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabTelefoneVO telefoneVO) {
        telefoneVO.setSisTelefoneOperadoraVO(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(telefoneVO.getSisTelefoneOperadora_id()));
    }

    public void updateTabTelefoneVO(Connection conn, TabTelefoneVO telefone) throws SQLException {
        addNewParametro(new Pair<>("String", telefone.getDescricao().replaceAll("\\D", "")));
        addParametro(new Pair<>("int", String.valueOf(telefone.getSisTelefoneOperadora_id())));
        addParametro(new Pair<>("int", String.valueOf(telefone.getId())));
        String comandoSql = "UPDATE tabTelefone SET descricao = ?, sisTelefoneOperadora_id = ? WHERE id = ? ";
        getInsertBancoDados(conn, comandoSql);
    }

    public int insertTabTelefoneVO(Connection conn, TabTelefoneVO telefone, int empresa_id, int contato_id) throws SQLException {
        addNewParametro(new Pair<>("String", telefone.getDescricao().replaceAll("\\D", "")));
        addParametro(new Pair<>("int", String.valueOf(telefone.getSisTelefoneOperadora_id())));
        String comandoSql = "INSERT INTO tabTelefone (descricao, sisTelefoneOperadora_id) VALUES(?, ?) ";
        int telefone_id = getInsertBancoDados(conn, comandoSql);
        if (empresa_id > 0)
            new RelEmpresaTelefoneDAO().insertRelEmpresaTelefoneVO(conn, empresa_id, telefone_id);
        if (contato_id > 0)
            new RelContatoTelefoneDAO().insertRelContatoTelefoneVO(conn, contato_id, telefone_id);
        return telefone_id;
    }

    public void deleteTabTelefoneVO(Connection conn, int telefone_id, int empresa_id, int contato_id) throws SQLException {
        if (telefone_id < 0) telefone_id = telefone_id * (-1);
        if (empresa_id > 0)
            new RelEmpresaTelefoneDAO().deleteRelEmpresaTelefoneVO(conn, empresa_id, telefone_id);
        if (contato_id > 0)
            new RelContatoTelefoneDAO().deleteRelContatoTelefoneVO(conn, contato_id, telefone_id);
        addNewParametro(new Pair<>("int", String.valueOf(telefone_id)));
        String comandoSql = "DELETE FROM tabTelefone WHERE id = ? ";
        getDeleteBancoDados(conn, comandoSql);
    }

    public TabTelefoneVO getTelefone_WsPortabilidadeCelular(String busca) {
        String retURL = "";
        if ((retURL = new BuscaWebService().getObjectWebService(WS_PORTABILIDADE_CELULAR_URL +
                busca + "&completo")) == null) {
            return new TabTelefoneVO(busca.substring(2), new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(51));
        }
        return new TabTelefoneVO(busca.substring(2), new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO_WS(retURL));
    }

}
