package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabTelefoneVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabTelefoneDAO extends BuscaBancoDados {

    ResultSet rs;
    TabTelefoneVO telefoneVO;

    public TabTelefoneVO getTabTelefoneVO(int id) {
        getResultSet(String.format("SELECT * FROM tabTelefone WHERE id = %d", id));
        addObjetosPesquisa(telefoneVO);
        return telefoneVO;
    }

    void getResultSet(String comandoSql) {
        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                telefoneVO = new TabTelefoneVO();
                telefoneVO.setId(rs.getInt("id"));
                telefoneVO.setDescricao(rs.getString("descricao"));
                telefoneVO.setSisTelefoneOperadora_id(rs.getInt("sisTelefoneOperadora_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabTelefoneVO telefoneVO) {
        telefoneVO.setSisTelefoneOperadoraVO(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(telefoneVO.getSisTelefoneOperadora_id()));
    }

    public void updateTabTelefoneVO(Connection conn, TabTelefoneVO telefone) throws SQLException {
        String comandoSql = String.format("UPDATE tabTelefone SET descricao = '%s', sisTelefoneOperadora_id = %d " +
                        "WHERE id = %d", telefone.getDescricao().replaceAll("//D", ""),
                telefone.getSisTelefoneOperadora_id(), telefone.getId());
        getInsertBancoDados(conn, comandoSql);
    }

    public int insertTabTelefoneVO(Connection conn, TabTelefoneVO telefone, int empresa_id, int contato_id) throws SQLException {
        String comandoSql = String.format("INSERT INTO tabTelefone (descricao, sisTelefoneOperadora_id) VALUES('%s', %d)",
                telefone.getDescricao().replaceAll("//D", ""), telefone.getSisTelefoneOperadora_id());
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
        String comandoSql = String.format("DELETE FROM tabTelefone WHERE id = %d", telefone_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
