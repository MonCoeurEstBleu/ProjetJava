package interfaces;

import clients.Client;

/**
 * Capacité d'un élément à être réservé par un client.
 * <p>
 * Implémentée par {@code Vehicule} : tous les véhicules de l'agence peuvent être loués.
 */
public interface Reservable {

    /**
     * Indique si l'élément peut être réservé.
     *
     * @return {@code true} si l'élément est actuellement disponible
     */
    boolean estDisponiblePourReservation();

    /**
     * Réserve l'élément pour un client (l'élément passe à l'état loué).
     *
     * @param client     client qui réserve
     * @param dureeJours durée de la réservation en jours
     */
    void reserver(Client client, int dureeJours);

    /**
     * Libère la réservation (l'élément redevient disponible).
     */
    void libererReservation();
}
