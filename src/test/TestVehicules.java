package test;

import clients.Client;
import interfaces.Maintenable;
import vehicules.Moto;
import vehicules.Utilitaire;
import vehicules.Vehicule;
import vehicules.VehiculeLuxe;
import vehicules.Voiture;

import java.util.Scanner;

/**
 * Tests de la hiérarchie des véhicules (étapes 1 à 4).
 * <p>
 * Depuis l'étape 4, {@code Vehicule} est abstraite : les tests des étapes 1 et 2
 * utilisent donc des {@code Voiture} à la place de {@code new Vehicule(...)}.
 */
public class TestVehicules {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ===================================================================== ÉTAPE 1
        titre("ÉTAPE 1 : constructeurs et saisie clavier");

        // Constructeur par défaut
        Voiture vDefaut = new Voiture();
        System.out.println(vDefaut);

        // Constructeur d'initialisation, marque et modèle saisis au clavier
        System.out.print("Marque du véhicule : ");
        String marque = sc.nextLine();
        System.out.print("Modèle du véhicule : ");
        String modele = sc.nextLine();
        Voiture v1 = new Voiture("AB-123-CD", marque, modele, 35.0, 5, "Essence", false);
        System.out.println(v1);

        // Trajet de 250 km
        v1.rouler(250);
        System.out.println("Après 250 km : " + v1);

        // Constructeur de copie
        Voiture vCopie = new Voiture(v1);
        System.out.println("Copie : " + vCopie);

        // Vehicule est abstraite : la ligne suivante ne compile pas (vérification de l'étape 4)
        // Vehicule interdit = new Vehicule("XX-000-XX", "Test", "Test", 10.0);

        // ===================================================================== ÉTAPE 2
        titre("ÉTAPE 2 : encapsulation, static, equals/hashCode");

        // Validations des setters : les valeurs négatives sont refusées
        v1.setTarifJournalier(-10);
        v1.setKilometrage(-5);
        System.out.println("Après tentatives invalides : " + v1);

        // == compare les références, equals() compare l'immatriculation
        Voiture a = new Voiture("ZZ-999-ZZ", "Peugeot", "208", 30.0, 5, "Diesel", false);
        Voiture b = new Voiture("ZZ-999-ZZ", "Peugeot", "208", 30.0, 5, "Diesel", false);
        System.out.println("a == b              : " + (a == b));            // false
        System.out.println("a.equals(b)         : " + a.equals(b));         // true
        System.out.println("hashCode identiques : " + (a.hashCode() == b.hashCode())); // true

        // Attributs statiques
        System.out.println("Devise : " + Vehicule.DEVISE);
        System.out.println("Nombre de véhicules créés : " + Vehicule.getCompteurVehicules());

        // Tableau de 3 véhicules parcouru avec for-each
        Vehicule[] trois = {
                new Voiture("AA-001-AA", "Renault", "Clio", 35.0, 5, "Essence", false),
                new Voiture("AA-002-AA", "Citroën", "C3", 32.5, 5, "Diesel", false),
                new Voiture("AA-003-AA", "Toyota", "Yaris", 38.0, 5, "Hybride", true)
        };
        for (Vehicule v : trois) {
            System.out.println(v);
        }
        System.out.println("Nombre de véhicules créés : " + Vehicule.getCompteurVehicules());

        // ===================================================================== ÉTAPE 3
        titre("ÉTAPE 3 : héritage et polymorphisme");

        // Tableau polymorphe (exemple 7.4 du sujet)
        Vehicule[] flotte = {
                new Voiture("AA-111-BB", "Peugeot", "308", 45.0, 5, "Essence", false),
                new Moto("AA-222-BB", "Yamaha", "MT-07", 30.0, 689),
                new Utilitaire("AA-333-BB", "Renault", "Master", 60.0, 12.0, 1200),
                new VehiculeLuxe("AA-444-BB", "Mercedes", "Classe S", 250.0, 4, "Essence", true,
                        true, new String[]{"WiFi", "Bar"})
        };

        for (Vehicule v : flotte) {
            System.out.println(v);   // toString() du type RÉEL de l'objet
            if (v instanceof Moto) {
                Moto m = (Moto) v;   // downcast explicite, sûr grâce à instanceof
                System.out.println("   Permis A2/A requis : " + m.necessitePermisA2OuA());
            } else if (v instanceof Utilitaire) {
                Utilitaire u = (Utilitaire) v;
                System.out.println("   Peut transporter 1000 kg : " + u.peutTransporter(1000));
            }
        }

        // ===================================================================== ÉTAPE 4
        titre("ÉTAPE 4 : classes abstraites et interfaces");

        // Méthodes abstraites : chaque catégorie a son propre calcul
        System.out.println("--- Tarifs de location (3 jours / 7 jours) ---");
        for (Vehicule v : flotte) {
            System.out.printf("%-12s %-10s : 3 j = %8.2f %s | 7 j = %8.2f %s%n",
                    v.getCategorie(), v.getImmatriculation(),
                    v.calculerTarifLocation(3), Vehicule.DEVISE,
                    v.calculerTarifLocation(7), Vehicule.DEVISE);
        }

        // Interface Assurable
        System.out.println("--- Assurable ---");
        Vehicule voiture = flotte[0];
        System.out.println("Assurée avant souscription : " + voiture.estAssure());
        voiture.souscrireAssurance(500.0);
        System.out.println("Assurée après souscription : " + voiture.estAssure());
        System.out.printf("Prime journalière : %.2f %s%n", voiture.calculerPrimeAssurance(), Vehicule.DEVISE);

        // Interface Reservable
        System.out.println("--- Reservable ---");
        Client client = new Client("Dupont", "Jean", "PERMIS-001", "0601020304", 1995);
        Vehicule moto = flotte[1];
        System.out.println("Disponible avant réservation : " + moto.estDisponiblePourReservation());
        moto.reserver(client, 3);
        System.out.println("Disponible après réservation : " + moto.estDisponiblePourReservation());
        moto.libererReservation();
        System.out.println("Disponible après libération  : " + moto.estDisponiblePourReservation());

        // Interface Maintenable : filtrage avec instanceof
        System.out.println("--- Maintenable ---");
        flotte[2].rouler(Maintenable.KM_ENTRE_ENTRETIENS + 500);   // l'utilitaire dépasse le seuil
        for (Vehicule v : flotte) {
            if (v instanceof Maintenable) {
                Maintenable m = (Maintenable) v;
                if (m.necessiteEntretien()) {
                    System.out.println("Entretien nécessaire : " + v);
                    m.effectuerEntretien();
                    System.out.println("Après entretien, encore nécessaire ? " + m.necessiteEntretien());
                }
            }
        }

        sc.close();
    }

    /** Affiche un titre de section. */
    private static void titre(String texte) {
        System.out.println();
        System.out.println("===== " + texte + " =====");
    }
}
