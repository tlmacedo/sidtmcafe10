import br.com.tlmacedo.cafeperfeito.service.ServiceXmlUtil;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNfeProc;

import java.io.FileInputStream;

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
