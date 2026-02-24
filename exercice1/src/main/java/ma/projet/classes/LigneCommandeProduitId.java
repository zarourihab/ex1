package ma.projet.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitId implements Serializable {

    private Long commandeId;
    private Long produitId;

    public LigneCommandeProduitId() {}

    public LigneCommandeProduitId(Long commandeId, Long produitId) {
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    public Long getCommandeId() { return commandeId; }
    public Long getProduitId() { return produitId; }

    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitId)) return false;
        LigneCommandeProduitId that = (LigneCommandeProduitId) o;
        return Objects.equals(commandeId, that.commandeId) &&
                Objects.equals(produitId, that.produitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandeId, produitId);
    }
}