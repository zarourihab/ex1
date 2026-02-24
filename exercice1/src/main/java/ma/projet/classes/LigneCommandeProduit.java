
package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitId id = new LigneCommandeProduitId();

    @ManyToOne(optional = false)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(optional = false)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.id = new LigneCommandeProduitId(commande.getId(), produit.getId());
    }

    // --- GETTERS ---
    public LigneCommandeProduitId getId() { return id; }
    public Commande getCommande() { return commande; }
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }

    // --- SETTERS ---
    public void setId(LigneCommandeProduitId id) { this.id = id; }

    public void setCommande(Commande commande) {
        this.commande = commande;
        if (this.id == null) this.id = new LigneCommandeProduitId();
        if (commande != null) this.id.setCommandeId(commande.getId());
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
        if (this.id == null) this.id = new LigneCommandeProduitId();
        if (produit != null) this.id.setProduitId(produit.getId());
    }

    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return "LigneCommandeProduit{commande=" + (commande != null ? commande.getId() : null) +
                ", produit=" + (produit != null ? produit.getReference() : null) +
                ", quantite=" + quantite + "}";
    }
}