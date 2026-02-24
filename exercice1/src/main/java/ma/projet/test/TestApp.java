package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;

public class TestApp {

    public static void main(String[] args) {

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cmds = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        try {

            // ====== CREATE CATEGORIE ======
            Categorie c1 = new Categorie("C1", "Informatique");
            cs.create(c1);

            // ====== CREATE PRODUIT ======
            Produit p1 = new Produit("P1", 1200f, c1);
            ps.create(p1);

            // ====== CREATE COMMANDE ======
            Commande cmd = new Commande(LocalDate.now());
            cmds.create(cmd);

            // ====== CREATE LIGNE COMMANDE ======
            LigneCommandeProduit lcp =
                    new LigneCommandeProduit(cmd, p1, 2);
            lcs.create(lcp);

            // ====== AFFICHAGE ======
            System.out.println("---- Categories ----");
            cs.findAll().forEach(System.out::println);

            System.out.println("---- Produits ----");
            ps.findAll().forEach(System.out::println);

            System.out.println("---- Commandes ----");
            cmds.findAll().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}