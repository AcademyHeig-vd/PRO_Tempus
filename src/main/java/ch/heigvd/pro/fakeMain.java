/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : fakeMain.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Pour la création du jar, on ne peut pas appeler le main du Tempus directement à cause du JavaFX,
               ce main sert donc à appeler le main principal du fichier Tempus pour tout charger
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro;

public class fakeMain {
    public static void main(String[] args) {
        Tempus.main(args);
    }
}
