package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;

public class WsTelefoneOperadoraDAO extends ServiceBuscaWebService implements Constants {

    String retURL;
    SisTelefoneOperadoraVO wsTelefoneOperadoraVO;

    public SisTelefoneOperadoraVO getTelefoneOperadoraVO(String numeroTelefone) {
        if ((retURL = getObjectWebService(WS_PORTABILIDADE_CELULAR_URL +
                numeroTelefone + "&completo")) == null) {
            return new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(51);
        }
        wsTelefoneOperadoraVO = new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO_WS(retURL);
        if (wsTelefoneOperadoraVO == null)
            wsTelefoneOperadoraVO = new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(51);
        return wsTelefoneOperadoraVO;
    }


}
