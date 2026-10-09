package towerdefense.livrable.livrable2;
import java.util.*;

import towerdefense.Balloon;
import towerdefense.Board;
import towerdefense.Cell;
import towerdefense.Cellable;
import towerdefense.util.Direction;
import towerdefense.util.StartEndChooser;

/**A class for second deliverable */
public class Livrable2b {

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
        
        // ÉTAPE 1: Création du plateau de jeu classique composé de MixedCell
        Board b = Board.createBoardClassic(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("--- Plateau créer ---");

        // ÉTAPE 2: Préparation de la cellule de départ (spawn)
        

        // ÉTAPE 3: Création des ballons avec des vitesses aléatoires
        ArrayList<Balloon> balloons = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            int randX = rand.nextInt(Integer.parseInt(args[0]));
            int randY = rand.nextInt(Integer.parseInt(args[1]));
            Direction dir = StartEndChooser.getStartingBorderDirection(randX, randY, b.getBoard());
            boolean isABorder = StartEndChooser.isValidBorder(randY, randX, b.getBoard());
            while(!(isABorder)) {
                randX = rand.nextInt(Integer.parseInt(args[0]));
                randY = rand.nextInt(Integer.parseInt(args[1]));
                dir = StartEndChooser.getStartingBorderDirection(randX, randY, b.getBoard());
                isABorder = StartEndChooser.isValidBorder(randY, randX, b.getBoard());
            }
            Cell spawnCell = b.getCell(randX, randY);
            Cellable spawnCellable = (Cellable) spawnCell;
            int speed = rand.nextInt(40) + 1;
            Balloon balloon = new Balloon(speed, 5, spawnCell.getX(), spawnCell.getY(), dir);
            spawnCellable.addBalloon(balloon);
            balloons.add(balloon);
        }

        System.out.println("--- Balloon Créés et placés ---");

        int currentTick = 0;
        Map<Balloon, Integer> exitTimes = new HashMap<>();

        System.out.println("Début simulation...");

        // ÉTAPE 4: Boucle principale de simulation
        while (!balloons.isEmpty()) {
            b.printBoard();
            currentTick++;
            if (currentTick > 10000) { 
                System.out.println("ERREUR: Boucle infinie détectée après " + currentTick + " ticks");
                break;
            }
            Iterator<Balloon> it = balloons.iterator();
            
            while (it.hasNext()) {
                Balloon balloon = it.next();
                
                // ÉTAPE 6: Progression du ballon dans sa cellule actuelle (on incrémente d'abord la progression)
                int newProgress = balloon.progress();
                int oldProgress = newProgress - balloon.getSpeed();

                // ÉTAPE 5: Vérification si le ballon est arrivé à la fin du chemin (après incrément)
                Cell lastCell = b.getEndClassicPath(balloon);
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

                // ÉTAPE 7: Déplacement vers la cellule suivante si progression terminée
                if (balloon.isAtEndProgress()) {
                    System.out.println("Tick " + currentTick + ": Ballon progresse de " + oldProgress + " à " + newProgress);

                    Cell oldCell = b.getCell(balloon.getX(), balloon.getY());
                    if (oldCell instanceof Cellable) {
                        ((Cellable) oldCell).removeBalloon(balloon);
                    }
                    balloon.moveClassicBalloon();

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
        System.out.println("\nTemps de sortie des ballons:");
        
        int index = 1;
        for (Map.Entry<Balloon, Integer> entry : exitTimes.entrySet()) {
            System.out.println("Ballon " + index + " vitesse=" + entry.getKey().getSpeed() + " Temps (en tick)=" + entry.getValue());
            index++;
        }
    }
}