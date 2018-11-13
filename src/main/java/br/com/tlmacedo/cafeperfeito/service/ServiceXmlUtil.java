package br.com.tlmacedo.cafeperfeito.service;


import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class ServiceXmlUtil {

    private static final String STATUS = "TConsStatServ";
    private static final String SITUACAO_NFE = "TConsSitNFe";
    private static final String ENVIO_NFE = "TEnviNFe";
    private static final String DIST_DFE = "DistDFeInt";
    private static final String INUTILIZACAO = "TInutNFe";
    private static final String NFEPROC = "TNfeProc";
    private static final String EVENTO = "TEnvEvento";
    private static final String TPROCEVENTO = "TProcEvento";
    private static final String TCONSRECINFE = "TConsReciNFe";
    private static final String TConsCad = "TConsCad";
    private static final String TPROCINUT = "TProcInutNFe";
    private static final String RETORNO_ENVIO = "TRetEnviNFe";
    private static final String SITUACAO_NFE_RET = "TRetConsSitNFe";

    private static final String CTE = "TCTe";
    private static final String CTEPROC = "CteProc";

    public static String leXml(FileInputStream arquivo) {
        StringBuilder xml = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(arquivo, "UTF-8"));
            String linha;

            while ((linha = in.readLine()) != null) {
                xml.append(linha);
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Ler Xml: " + e.getMessage());
        }
        return xml.toString();
    }

    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }

    @SuppressWarnings("Duplicates")
    public static <T> String objectToXml(Object obj) throws JAXBException {

        JAXBContext context = null;
        JAXBElement<?> element = null;
        System.out.println(obj.getClass().getSimpleName());
        switch (obj.getClass().getSimpleName()) {
            case "TInformacaoBasica":
//                context = JAXBContext.newInstance(TInformacaoBasica.class);
//                element = new br.com.tlmacedo.cafeperfeito.xsd.configSistema.informacaoBasica.ObjectFactory().createConfigSidtmCafe((TInformacaoBasica) obj);
                break;

//            case STATUS:
//                context = JAXBContext.newInstance(TConsStatServ.class);
//                element = new br.inf.portalfiscal.xsd.nfe.consStatServ.ObjectFactory().createConsStatServ((TConsStatServ) obj);
//                break;
//
//            case NFEPROC:
//                context = JAXBContext.newInstance(TNfeProc.class);
//                element = new br.inf.portalfiscal.xsd.nfe.procNFe.ObjectFactory().createNfeProc((TNfeProc) obj);
//                break;
//
//            case CTEPROC:
//                context = JAXBContext.newInstance(CteProc.class);
//                element = new br.inf.portalfiscal.xsd.cte.cte.ObjectFactory().createCTe((TCTe) obj);
//                break;
//

        }
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty("jaxb.encoding", "Unicode");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();

//        if (obj.getClass().getSimpleName().equals(ENVIO_NFE) || obj.getClass().getSimpleName().equals(NFEPROC)) {
//            CDATAContentHandler cdataHandler = new CDATAContentHandler(sw, "utf-8");
//            marshaller.marshal(element, cdataHandler);
//        } else {
        marshaller.marshal(element, sw);
//        }
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(sw.toString());

//        if ((obj.getClass().getSimpleName().equals(TPROCEVENTO))) {
//            return replacesNfe(xml.toString().replaceAll("procEvento", "procEventoNFe"));
//        } else {
        return replacesNfe(xml.toString());
//        }

    }

    @SuppressWarnings("Duplicates")
    private static String replacesNfe(String xml) {
        xml = xml.replaceAll("ns2:", "");
        xml = xml.replaceAll("<!\\[CDATA\\[<!\\[CDATA\\[", "<!\\[CDATA\\[");
        xml = xml.replaceAll("\\]\\]>\\]\\]>", "\\]\\]>");
        xml = xml.replaceAll("ns3:", "");
        xml = xml.replaceAll("&lt;", "<");
        xml = xml.replaceAll("&amp;", "&");
        xml = xml.replaceAll("&gt;", ">");
        xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
        xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        xml = xml.replaceAll(" xmlns=\"\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "");
        return xml;
    }

}
