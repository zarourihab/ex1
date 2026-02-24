package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
@NamedQuery(
        name = "Produit.prixSuperieurA",
        query = "SELECT p FROM Produit p WHERE p.prix > :prix"
)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @Column(nullable = false)
    private float prix;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Produit() {}

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public Long getId() { return id; }
    public String getReference() { return reference; }
    public float getPrix() { return prix; }
    public Categorie getCategorie() { return categorie; }

    public void setReference(String reference) { this.reference = reference; }
    public void setPrix(float prix) { this.prix = prix; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference='" + reference + "', prix=" + prix +
                ", categorie=" + (categorie != null ? categorie.getLibelle() : null) + "}";
    }
}