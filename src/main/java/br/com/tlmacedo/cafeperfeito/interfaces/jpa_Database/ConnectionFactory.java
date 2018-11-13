package br.com.tlmacedo.cafeperfeito.interfaces.jpa_Database;

import br.com.tlmacedo.cafeperfeito.service.ServiceAbreVariaveisSistema;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ConnectionFactory {
    private static final String UNIT_NAME = "sidtmcafePU";
//    private static final String DB_DRIVER = ServiceAbreVariaveisSistema.tConfig.getSis().getConnectDB().getDbDriver();
//    private static final String DB_URL = ServiceAbreVariaveisSistema.tConfig.getSis().getConnectDB().getDbUrl();
//    private static final String DB_USER = ServiceAbreVariaveisSistema.tConfig.getSis().getConnectDB().getDbUser();
//    private static final String DB_PASS = ServiceAbreVariaveisSistema.tConfig.getSis().getConnectDB().getDbPass();

    private EntityManagerFactory emf;
    private EntityManager em;

    public EntityManager getEntityManager() {
        Map<String, String> properties = new HashMap<String, String>();
//        properties.put("javax.persistence.jdbc.driver", DB_DRIVER);
//        properties.put("javax.persistence.jdbc.url", DB_URL);
//        properties.put("javax.persistence.jdbc.user", DB_USER);
//        properties.put("javax.persistence.jdbc.password", DB_PASS);

        if (emf == null)
            emf = Persistence.createEntityManagerFactory(UNIT_NAME, properties);
        if (em == null)
            em = emf.createEntityManager();
        return em;
    }

}
