package test;

import clients.Client;
import clients.Entreprise;
import clients.Particulier;
import location.AgenceLocation;
import location.ContratLocation;
import vehicules.Moto;
import vehicules.Utilitaire;
import vehicules.Vehicule;
import vehicules.VehiculeLuxe;
import vehicules.Voiture;

/**
 * Scénario complet de l'agence (étape 5) : ajout, location, entretien,
 * retour, statistiques.
 */
public class TestAgence {

    public static void main(String[] args) {

        AgenceLocation agence = new AgenceLocation(10, 10, 20);

        // ---------------------------------------------------------------- ajouts
        titre("1. Ajout des véhicules et des clients");

        Voiture clio = new Voiture("AA-111-BB", "Renault", "Clio", 35.0, 5, "Essence", false);
        Moto mt07 = new Moto("AA-222-BB", "Yamaha", "MT-07", 30.0, 689);
        Utilitaire master = new Utilitaire("AA-333-BB", "Renault", "Master", 60.0, 12.0, 1200);
        VehiculeLuxe classeS = new VehiculeLuxe("AA-444-BB", "Mercedes", "Classe S", 250.0, 4,
                "Essence", true, true, new String[]{"WiFi", "Bar"});
        Voiture golf = new Voiture("AA-555-BB", "Volkswagen", "Golf", 40.0, 5, "Diesel", true);
        golf.rouler(12000);   // dépasse le seuil d'entretien (10 000 km)

        System.out.println("Ajout Clio      : " + agence.ajouterVehicule(clio));
        System.out.println("Ajout MT-07     : " + agence.ajouterVehicule(mt07));
        System.out.println("Ajout Master    : " + agence.ajouterVehicule(master));
        System.out.println("Ajout Classe S  : " + agence.ajouterVehicule(classeS));
        System.out.println("Ajout Golf      : " + agence.ajouterVehicule(golf));

        // Doublon : même immatriculation que la Clio -> refusé
        Voiture doublon = new Voiture("AA-111-BB", "Renault", "Clio", 35.0, 5, "Essence", false);
        System.out.println("Ajout doublon (attendu false) : " + agence.ajouterVehicule(doublon));

        Particulier alice = new Particulier("Durand", "Alice", "PERMIS-200", "0622222222", 1998, true);
        Entreprise transExpress = new Entreprise("Bernard", "Luc", "PERMIS-300", "0633333333", 1980,
                "TransExpress SARL", "12345678900012");

        System.out.println("Ajout Alice        : " + agence.ajouterClient(alice));
        System.out.println("Ajout TransExpress : " + agence.ajouterClient(transExpress));

        // Doublon : même numéro de permis -> refusé
        Client doublonClient = new Client("Autre", "Nom", "PERMIS-200", "0600000000", 1990);
        System.out.println("Ajout client doublon (attendu false) : " + agence.ajouterClient(doublonClient));

        // ---------------------------------------------------------------- affichages
        titre("2. Flotte complète (toString polymorphe)");
        agence.afficherFlotte();

        titre("3. Comptage et catalogue par catégorie");
        agence.compterVehiculesParCategorie();
        agence.afficherCatalogueParCategorie();

        // ---------------------------------------------------------------- recherches
        titre("4. Recherches");
        System.out.println("Recherche AA-333-BB : " + agence.rechercherParImmatriculation("AA-333-BB"));
        System.out.println("Recherche ZZ-000-ZZ (attendu null) : " + agence.rechercherParImmatriculation("ZZ-000-ZZ"));
        System.out.println("Recherche PERMIS-300 : " + agence.rechercherClientParPermis("PERMIS-300"));
        System.out.println("Recherche PERMIS-999 (attendu null) : " + agence.rechercherClientParPermis("PERMIS-999"));

        // ---------------------------------------------------------------- locations
        titre("5. Locations");
        ContratLocation c1 = agence.louerVehicule(alice, clio, 10, 7);
        if (c1 != null) {
            System.out.printf("Contrat Clio / Alice : %.2f %s%n", c1.calculerMontantTotal(), Vehicule.DEVISE);
        }

        ContratLocation c2 = agence.louerVehicule(transExpress, classeS, 12, 3);
        if (c2 != null) {
            System.out.printf("Contrat Classe S / TransExpress : %.2f %s%n", c2.calculerMontantTotal(), Vehicule.DEVISE);
        }

        // Le véhicule est déjà loué : la location doit être refusée (null) sans plantage
        ContratLocation refuse = agence.louerVehicule(transExpress, clio, 15, 2);
        System.out.println("Location d'un véhicule déjà loué refusée : " + (refuse == null));

        titre("6. Véhicules disponibles (la Clio et la Classe S ne doivent plus apparaître)");
        agence.afficherVehiculesDisponibles();

        // ---------------------------------------------------------------- entretien
        titre("7. Entretien");
        agence.afficherVehiculesAEntretenir();   // la Golf doit apparaître
        System.out.println("Entretien Golf : " + agence.effectuerEntretien("AA-555-BB"));
        agence.afficherVehiculesAEntretenir();   // la Golf ne doit plus apparaître

        // ---------------------------------------------------------------- retours
        titre("8. Retour des véhicules");
        System.out.println("Retour Clio : " + agence.retournerVehicule("AA-111-BB"));
        System.out.println("Retour véhicule non loué (attendu false) : " + agence.retournerVehicule("AA-222-BB"));
        agence.afficherVehiculesDisponibles();

        // ---------------------------------------------------------------- bilan
        titre("9. Statistiques (CA = contrats clôturés uniquement)");
        agence.afficherStatistiques();

        titre("10. Historique des clients");
        agence.afficherHistoriqueClient("PERMIS-200");
        agence.afficherHistoriqueClient("PERMIS-300");
    }

    /** Affiche un titre de section. */
    private static void titre(String texte) {
        System.out.println();
        System.out.println("===== " + texte + " =====");
    }
}
