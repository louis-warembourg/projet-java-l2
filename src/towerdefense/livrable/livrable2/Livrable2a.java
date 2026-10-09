package towerdefense.livrable.livrable2;
import java.util.*;

import towerdefense.Balloon;
import towerdefense.Board;
import towerdefense.Cell;
import towerdefense.Cellable;

/**A class for second deliverable */
public class Livrable2a {

    /**
     * Main method that executes the balloon simulation.
     * 
     * @param args Command line arguments: width, height, and balloon count.
     * @throws IllegalArgumentException if the number of arguments is not exactly 3.
     */
    public static void main(String[] args) throws IllegalArgumentException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }
        Random rand = new Random();
        
        // ÉTAPE 1: Création du plateau de jeu avec un chemin aléatoire
        Board b = Board.createBoardRandom(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        ArrayList<Cell> path = b.getPath();

        if (path == null || path.isEmpty()) {
            System.out.println("Pas de chemin généré");
            return;
        }
        System.out.println("--- Plateau créer ---");

        // ÉTAPE 2: Préparation de la cellule de départ (spawn)
        Cell spawnCell = path.get(0);
        Cellable spawnCellable = (Cellable) spawnCell;
        ArrayList<Balloon> balloons = new ArrayList<>();

        // ÉTAPE 3: Création des ballons avec des vitesses aléatoires
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            int speed = rand.nextInt(40) + 1;
            Balloon balloon = new Balloon(speed, 5, path);
            spawnCellable.addBalloon(balloon);
            balloons.add(balloon);
        }

        System.out.println("--- Balloon Créés et placés ---");

        int currentTick = 0;
        Map<Balloon, Integer> exitTimes = new HashMap<>();

        System.out.println("Début simulation...");

        // ÉTAPE 4: Boucle principale de simulation
        while (!balloons.isEmpty()) {
            currentTick++;
            if (currentTick > 10000) { 
                System.out.println("ERREUR: Boucle infinie détectée après " + currentTick + " ticks");
                break;
            }
            Iterator<Balloon> it = balloons.iterator();
            
            while (it.hasNext()) {
                Balloon balloon = it.next();
                
                // ÉTAPE 5: Vérification si le ballon est arrivé à la fin du chemin
                Cell lastCell = path.get(path.size() - 1);
                if (balloon.getX() == lastCell.getX() && balloon.getY() == lastCell.getY() && balloon.isAtEndProgress()) {
                    
                    exitTimes.put(balloon, currentTick);
                    System.out.println("Ballon sorti au tick " + currentTick + " à la position (" + balloon.getX() + "," + balloon.getY() + ")");
                    
                    Cell currentCell = b.getCell(balloon.getX(), balloon.getY());
                    if (currentCell instanceof Cellable) {
                        ((Cellable) currentCell).removeBalloon(balloon);
                    }
                    
                    it.remove();
                    continue;
                }

                // ÉTAPE 6: Progression du ballon dans sa cellule actuelle
                int oldProgress = (int) balloon.progress();

                // ÉTAPE 7: Déplacement vers la cellule suivante si progression terminée
                if (balloon.isAtEndProgress()) {
                    System.out.println("Tick " + currentTick + ": Ballon progresse de " + oldProgress + " à " + balloon.progress());

                    Cell oldCell = b.getCell(balloon.getX(), balloon.getY());
                    if (oldCell instanceof Cellable) {
                        ((Cellable) oldCell).removeBalloon(balloon);
                    }
                    balloon.moveRandomBalloon();

                    System.out.println("Ballon déplacé à (" + balloon.getX() + "," + balloon.getY() + ")");

                    Cell newCell = b.getCell(balloon.getX(), balloon.getY());
                    if (newCell instanceof Cellable) {
                        ((Cellable) newCell).addBalloon(balloon);
                    }
                }
            }
        }

        // ÉTAPE 8: Vérification finale et affichage des résultats
        if (!balloons.isEmpty()) {
            System.out.println("ATTENTION: " + balloons.size() + " ballons restants non sortis");
        }

        b.printBoard();

        System.out.println("=== Résultats ===");
        System.out.println("Taille du plateau: " + args[0] + "x" + args[1]);
        System.out.println("Nombre de ballons: " + args[2]);
        System.out.println("Longueur du chemin: " + path.size());
        System.out.println("\nTemps de sortie des ballons:");
        
        int index = 1;
        for (Map.Entry<Balloon, Integer> entry : exitTimes.entrySet()) {
            System.out.println("Ballon " + index + " vitesse=" + entry.getKey().getSpeed() + " Temps (en tick)=" + entry.getValue());
            index++;
        }
    }
}