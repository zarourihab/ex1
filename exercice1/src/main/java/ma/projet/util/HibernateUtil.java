package ma.projet.util;

import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties props = new Properties();
                try (InputStream is = HibernateUtil.class.getClassLoader()
                        .getResourceAsStream("application.properties")) {
                    if (is == null) throw new RuntimeException("application.properties introuvable !");
                    props.load(is);
                }

                Configuration cfg = new Configuration();
                cfg.setProperties(props);

                // Enregistrer les classes annotées
                cfg.addAnnotatedClass(Categorie.class);
                cfg.addAnnotatedClass(Produit.class);
                cfg.addAnnotatedClass(Commande.class);
                cfg.addAnnotatedClass(LigneCommandeProduit.class);
                cfg.addAnnotatedClass(LigneCommandeProduitId.class);

                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());

                sessionFactory = cfg.buildSessionFactory(builder.build());
            } catch (Exception e) {
                throw new RuntimeException("Erreur SessionFactory: " + e.getMessage(), e);
            }
        }
        return sessionFactory;
    }
}