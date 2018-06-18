package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class TabEnderecoDAO extends BuscaBancoDados {

    ResultSet rs;

    String comandoSql = "";
    TabEnderecoVO tabEnderecoVO;

    public TabEnderecoVO getTabEnderecoVO(int id) {
        buscaTabEnderecoVO(id);
        if (tabEnderecoVO != null)
            addObjetosPesquisa(tabEnderecoVO);
        return tabEnderecoVO;
    }

    void buscaTabEnderecoVO(int id) {
        comandoSql = "SELECT id, sisTipoEndereco_id, cep, logradouro, numero, complemento, bairro, " +
                "sisMunicipio_id, pontoReferencia " +
                "FROM TabEndereco " +
                "WHERE id = '" + id + "' ";

        rs = getResultadosBandoDados(comandoSql);
        try {
            while (rs.next()) {
                tabEnderecoVO = new TabEnderecoVO();
                tabEnderecoVO.setId(rs.getInt("id"));
                tabEnderecoVO.setSisTipoEndereco_id(rs.getInt("sisTipoEndereco_id"));
                tabEnderecoVO.setCep(rs.getString("cep"));
                tabEnderecoVO.setLogradouro(rs.getString("logradouro"));
                tabEnderecoVO.setNumero(rs.getString("numero"));
                tabEnderecoVO.setComplemento(rs.getString("complemento"));
                tabEnderecoVO.setBairro(rs.getString("bairro"));
                tabEnderecoVO.setSisMunicipio_id(rs.getInt("sisMunicipio_id"));
                tabEnderecoVO.setPontoReferencia(rs.getString("pontoReferencia"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEnderecoVO endereco) {
        endereco.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(endereco.getSisMunicipio_id()));
        endereco.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(endereco.getSisTipoEndereco_id()));
    }

    public void updateEnderecoEmpresa(Connection conn, TabEnderecoVO endereco, int empresa_id) throws SQLException {
//        new RelEmpresaEnderecoDAO().dedeteRelEmpresaEnderecoVO(conn, empresa_id, endereco.getId());
        comandoSql = String.format("UPDATE tabEndereco " +
                        "SET sisTipoEndereco_id = %d, " +
                        "cep = '%s', " +
                        "logradouro = '%s', " +
                        "numero = '%s', " +
                        "complemento = '%s', " +
                        "bairro = '%s', " +
                        "sisMunicipio_id = %d, " +
                        "pontoReferencia = '%s' " +
                        "WHERE id = %d",
                endereco.getSisTipoEndereco_id(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getSisMunicipio_id(),
                endereco.getPontoReferencia(),
                endereco.getId());
        getUpdateBancoDados(conn, comandoSql);
//        new RelEmpresaEnderecoDAO().insertrelEmpresaEnderecoVO(conn, empresa_id, endereco.getId());
    }

    public int insertEnderecoEmpresa(Connection conn, TabEnderecoVO endereco, int empresa_id) throws SQLException {
        comandoSql = String.format("INSERT INTO tabEndereco " +
                        "(sisTipoEndereco_id, cep, logradouro, numero, complemento, bairro, sisMunicipio_id, pontoReferencia) " +
                        "VALUES(%d, '%s', '%s', '%s', '%s', '%s', %d, '%s')",
                endereco.getSisTipoEndereco_id(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getSisMunicipio_id(),
                endereco.getPontoReferencia());
        int endereco_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresaEnderecoDAO().insertRelEmpresaEndereco(conn, empresa_id, endereco_id);
        return endereco_id;
    }

    public void deleteEnderecoEmpresa(Connection conn, int endereco_id, int empresa_id) throws SQLException {
        if (endereco_id < 0) endereco_id = endereco_id * (-1);
        new RelEmpresaEnderecoDAO().dedeteRelEmpresaEndereco(conn, empresa_id, endereco_id);
        comandoSql = String.format("DELETE FROM tabEndereco WHERE id = %d", endereco_id);
        getDeleteBancoDados(conn, comandoSql);
    }

}
