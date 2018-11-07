import br.com.cafeperfeito.sidtmcafe.service.ServiceSocketDinamico;
import br.com.cafeperfeito.sidtmcafe.service.ServiceXmlUtil;
import br.inf.portalfiscal.xsd.cte.cte.TCTe;
import br.inf.portalfiscal.xsd.cte.procCTe.CteProc;
import br.inf.portalfiscal.xsd.nfe.consStatServ.TConsStatServ;
import br.inf.portalfiscal.wsdl.nfe.hom.NfeStatusServico4.NfeStatusServico4Stub;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNfeProc;

import java.io.FileInputStream;

import org.apache.commons.httpclient.protocol.Protocol;

public class TestesXml {

    public static void main(String[] args) throws Exception {
        lerXML();
    }

    private static void lerXML() throws Exception {
        FileInputStream arquivo = new FileInputStream(System.getProperty("user.home") + "/Desktop/35180906981833000248550010000000931027894094-nfe.xml");
        TNfeProc tNfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(arquivo), TNfeProc.class);
        System.out.println("natOp: [" + tNfeProc.getNFe().getInfNFe().getIde().getNatOp() + "]");
        //CteProc cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(diretorio), CteProc.class);
        //System.out.println("natOp: [" + cteProc.getCTe().getInfCte().getIde().getNatOp() + "]");

    }


}
