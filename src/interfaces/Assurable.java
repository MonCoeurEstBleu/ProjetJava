package interfaces;

/**
 * Capacité d'un élément à être assuré.
 * <p>
 * Implémentée par {@code Vehicule} : tous les véhicules de l'agence sont assurables.
 */
public interface Assurable {

    /** Taux de base utilisé pour calculer la prime d'assurance journalière (3 %). */
    double TAUX_BASE_ASSURANCE = 0.03;

    /**
     * Calcule la prime d'assurance journalière.
     *
     * @return la prime journalière en euros ({@code TAUX_BASE_ASSURANCE} x tarif journalier)
     */
    double calculerPrimeAssurance();

    /**
     * Indique si une assurance a été souscrite.
     *
     * @return {@code true} si l'élément est assuré
     */
    boolean estAssure();

    /**
     * Souscrit une assurance : mémorise la franchise et rend l'élément assuré.
     *
     * @param montantFranchise montant de la franchise en euros
     */
    void souscrireAssurance(double montantFranchise);
}
