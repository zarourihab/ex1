package ma.projet.classes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Commande() {}

    public Commande(LocalDate date) {
        this.date = date;
    }

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public List<LigneCommandeProduit> getLignes() { return lignes; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + "}";
    }
}