package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(Long id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("FROM Produit", Produit.class).list();
        }
    }

    // 1) Afficher liste des produits par catégorie
    public List<Produit> produitsParCategorie(Long categorieId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "SELECT p FROM Produit p WHERE p.categorie.id = :cid",
                    Produit.class
            ).setParameter("cid", categorieId).list();
        }
    }

    // 2) Produits commandés entre deux dates (sans doublons)
    public List<Produit> produitsCommandesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "SELECT DISTINCT l.produit " +
                                    "FROM LigneCommandeProduit l " +
                                    "WHERE l.commande.date BETWEEN :d1 AND :d2",
                            Produit.class
                    ).setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    // 3) Afficher les produits commandés dans une commande donnée (format demandé)
    public void afficherDetailsCommande(Long commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            // (a) infos commande
            Object[] info = s.createQuery(
                            "SELECT c.id, c.date FROM Commande c WHERE c.id = :id",
                            Object[].class
                    ).setParameter("id", commandeId)
                    .uniqueResult();

            if (info == null) {
                System.out.println("Commande introuvable !");
                return;
            }

            Long id = (Long) info[0];
            LocalDate date = (LocalDate) info[1];

            DateTimeFormatter fr = DateTimeFormatter.ofPattern("dd MMMM yyyy");

            System.out.println("Commande : " + id + "     Date : " + date.format(fr));
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\t\tQuantité");

            // (b) lignes
            List<Object[]> lignes = s.createQuery(
                    "SELECT p.reference, p.prix, l.quantite " +
                            "FROM LigneCommandeProduit l " +
                            "JOIN l.produit p " +
                            "WHERE l.commande.id = :id",
                    Object[].class
            ).setParameter("id", commandeId).list();

            for (Object[] row : lignes) {
                String ref = (String) row[0];
                float prix = (float) row[1];
                int qte = (int) row[2];
                System.out.println(ref + "\t\t" + prix + " DH\t" + qte);
            }
        }
    }

    // 4) Produits dont prix > 100 DH (requête nommée)
    public List<Produit> produitsPrixSuperieurA(float prix) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.prixSuperieurA", Produit.class)
                    .setParameter("prix", prix)
                    .list();
        }
    }
}