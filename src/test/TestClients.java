package test;

import clients.Client;
import clients.Entreprise;
import clients.Particulier;

/**
 * Tests de la hiérarchie des clients (étapes 1 à 3).
 */
public class TestClients {

    private static final int ANNEE_COURANTE = 2026;

    public static void main(String[] args) {

        // ===================================================================== ÉTAPE 1
        titre("ÉTAPE 1 : constructeurs");

        Client complet = new Client("Dupont", "Jean", "PERMIS-001", "0601020304", 1995);
        Client copie = new Client(complet);
        Client defaut = new Client();

        System.out.println(complet + " -> " + complet.calculerAge(ANNEE_COURANTE) + " ans");
        System.out.println(copie + " -> " + copie.calculerAge(ANNEE_COURANTE) + " ans");
        System.out.println(defaut + " -> " + defaut.calculerAge(ANNEE_COURANTE) + " ans");

        // ===================================================================== ÉTAPE 2
        titre("ÉTAPE 2 : encapsulation, static, equals/hashCode");

        // La copie est un objet distinct, mais "égal" (même numéro de permis)
        System.out.println("complet == copie      : " + (complet == copie));       // false
        System.out.println("complet.equals(copie) : " + complet.equals(copie));    // true
        System.out.println("complet.equals(defaut): " + complet.equals(defaut));   // false
        System.out.println("hashCode identiques   : " + (complet.hashCode() == copie.hashCode())); // true

        // Modifier la copie ne modifie pas l'original
        copie.setTelephone("0700000000");
        System.out.println("Original : " + complet);
        System.out.println("Copie    : " + copie);

        System.out.println("Nombre de clients créés : " + Client.getCompteurClients());

        // ===================================================================== ÉTAPE 3
        titre("ÉTAPE 3 : héritage et polymorphisme");

        Client[] clients = {
                new Client("Martin", "Paul", "PERMIS-100", "0611111111", 1990),
                new Particulier("Durand", "Alice", "PERMIS-200", "0622222222", 1998, true),
                new Entreprise("Bernard", "Luc", "PERMIS-300", "0633333333", 1980,
                        "TransExpress SARL", "12345678900012")
        };

        // Même appel calculerRemise(), résultat différent selon le type réel (0 %, 5 %, 10 %)
        for (Client c : clients) {
            System.out.println(c);
            System.out.printf("   Remise : %.0f %%%n", c.calculerRemise() * 100);
            if (c instanceof Entreprise) {
                Entreprise e = (Entreprise) c;   // downcast pour accéder aux méthodes propres
                System.out.println("   Raison sociale : " + e.getRaisonSociale());
            }
        }

        System.out.println("Nombre de clients créés : " + Client.getCompteurClients());
    }

    /** Affiche un titre de section. */
    private static void titre(String texte) {
        System.out.println();
        System.out.println("===== " + texte + " =====");
    }
}
