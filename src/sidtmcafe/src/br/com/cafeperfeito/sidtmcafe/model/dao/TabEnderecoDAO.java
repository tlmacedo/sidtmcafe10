package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.database.ConnectionFactory;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaBancoDados;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.SQLException;

//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class TabEnderecoDAO extends ServiceBuscaBancoDados {

    TabEnderecoVO tabEnderecoVO = null;

    public TabEnderecoVO getTabEnderecoVO(int id) {
        addNewParametro(new Pair<>("int", String.valueOf(id)));
        getResultSet("SELECT * FROM tabEndereco WHERE id = ? ");
        if (tabEnderecoVO != null)
            addObjetosPesquisa(tabEnderecoVO);
        return tabEnderecoVO;
    }

    void getResultSet(String sql) {
        getResultadosBandoDados(sql + "ORDER BY id ");
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
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
    }

    void addObjetosPesquisa(TabEnderecoVO endereco) {
        endereco.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(endereco.getSisMunicipio_id(), true));
        endereco.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(endereco.getSisTipoEndereco_id()));
    }

    public void updateTabEnderecoVO(Connection conn, TabEnderecoVO endereco) throws SQLException {
        String comandoSql = "UPDATE tabEndereco SET " +
                "sisTipoEndereco_id = ?, " +
                "cep = ?, " +
                "logradouro = ?, " +
                "numero = ?, " +
                "complemento = ?, " +
                "bairro = ?, " +
                "sisMunicipio_id = ?, " +
                "pontoReferencia = ? " +
                "WHERE id = ? ";
        addNewParametro(new Pair<>("int", String.valueOf(endereco.getSisTipoEndereco_id())));
        addParametro(new Pair<>("String", endereco.getCep().replaceAll("\\D", "")));
        addParametro(new Pair<>("String", endereco.getLogradouro()));
        addParametro(new Pair<>("String", endereco.getNumero()));
        addParametro(new Pair<>("String", endereco.getComplemento()));
        addParametro(new Pair<>("String", endereco.getBairro()));
        addParametro(new Pair<>("int", String.valueOf(endereco.getSisMunicipio_id())));
        addParametro(new Pair<>("String", endereco.getPontoReferencia()));
        addParametro(new Pair<>("int", String.valueOf(endereco.getId())));
        getUpdateBancoDados(conn, comandoSql);
    }

    public int insertTabEnderecoVO(Connection conn, TabEnderecoVO endereco, int empresa_id) throws SQLException {
        String comandoSql = "INSERT INTO tabEndereco " +
                "(sisTipoEndereco_id, " +
                "cep, " +
                "logradouro, " +
                "numero, " +
                "complemento, " +
                "bairro, " +
                "sisMunicipio_id, " +
                "pontoReferencia) " +
                "VALUES (" +
                "?, ?, ?, ?, ?, " +
                "?, ?, ?)";
        addNewParametro(new Pair<>("int", String.valueOf(endereco.getSisTipoEndereco_id())));
        addParametro(new Pair<>("String", endereco.getCep().replaceAll("\\D", "")));
        addParametro(new Pair<>("String", endereco.getLogradouro()));
        addParametro(new Pair<>("String", endereco.getNumero()));
        addParametro(new Pair<>("String", endereco.getComplemento()));
        addParametro(new Pair<>("String", endereco.getBairro()));
        addParametro(new Pair<>("int", String.valueOf(endereco.getSisMunicipio_id())));
        addParametro(new Pair<>("String", endereco.getPontoReferencia()));
        int endereco_id = getInsertBancoDados(conn, comandoSql);
        new RelEmpresa_EnderecoDAO().insertRelEmpresa_endereco(conn, empresa_id, endereco_id);
        return endereco_id;
    }

    public void deleteTabEnderecoVO(Connection conn, int endereco_id, int empresa_id) throws SQLException {
        String comandoSql = "DELETE FROM tabEndereco " +
                "WHERE id = ? ";
        if (endereco_id < 0) endereco_id = endereco_id * (-1);
        new RelEmpresa_EnderecoDAO().dedeteRelEmpresa_endereco(conn, empresa_id, endereco_id);
        addNewParametro(new Pair<>("int", String.valueOf(empresa_id)));
        getDeleteBancoDados(conn, comandoSql);
    }

}
