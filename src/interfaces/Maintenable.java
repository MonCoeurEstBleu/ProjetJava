package interfaces;

/**
 * Capacité d'un élément à nécessiter un entretien périodique selon son kilométrage.
 * <p>
 * Implémentée par {@code Voiture}, {@code Moto} et {@code Utilitaire}
 * (et donc, par héritage, par {@code VehiculeLuxe}).
 */
public interface Maintenable {

    /** Nombre de kilomètres entre deux entretiens. */
    int KM_ENTRE_ENTRETIENS = 10000;

    /**
     * Fixe le kilométrage auquel le prochain entretien devra être effectué.
     *
     * @param kmProchainEntretien kilométrage du prochain entretien
     */
    void planifierEntretien(int kmProchainEntretien);

    /**
     * Indique si l'entretien est dû.
     *
     * @return {@code true} si le kilométrage actuel a atteint le kilométrage du prochain entretien
     */
    boolean necessiteEntretien();

    /**
     * Effectue l'entretien : passage en maintenance, journalisation,
     * planification du prochain entretien, puis retour à l'état disponible.
     */
    void effectuerEntretien();
}
