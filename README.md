# Tower Defense Game - Java (POO)

Un jeu de **Tower Defense** développé en Java dans le cadre du projet de Licence 2 Informatique à l'Université de Lille. 

Le projet met en œuvre les principes fondamentaux de la **programmation orientée objet (POO)**, le respect de la responsabilité unique (`ManageTower`, `ManageBalloon`, `ManageProjectile`), ainsi que les tests unitaires JUnit et documentation Javadoc.

---

## Fonctionnalités

* **Modes de jeu & Pathfinding :**
  * **Mode Aléatoire (Mode 0) :** Génération procédurale du terrain et de chemins aléatoires optimisés sans blocage via la classe `Board` et `StartEndChooser`.
  * **Mode Classique (Mode 1) :** Plateaux prédéfinis avec entrées/sorties sur les bords et calcul de progression au sein de chaque case.
* **Tours & Portées :**
  * Spécialisations variées : `DartMonkeyTower`, `BombMonkeyTower`, `EliteMonkeyTower`, `FreezeTower`, `SlowTower`, `GorillaTower`, `NeedleMonkeyTower`.
  * Dégâts de zone pour les bombes (`ManageProjectile`) et effets de statut temporaires (Gél, Ralentissement).
  * Algorithme de ciblage priorisant le ballon le plus proche de la sortie (`identifyTarget`).
* **Économie & Améliorations :**
  * Système d'upgrades dynamiques (puissance, portée, vitesse de tir, changement de projectiles comme `ExtraBomb`).
  * Gestion des ressources par la classe `Player` (achats/ventes de tours et remboursements d'upgrades).
* **Interface & Interaction :**
  * Architecture de menu dynamique (`Menu.java`) s'adaptant à l'état de la partie.
  * Prise en charge des choix interactifs en console (`InteractiveListChooser`) et de simulations automatiques (`RandomListChooser`).

---

## Architecture

```text
projet-java-l2/
├── documents/            # Sujet du projet, diaporama de présentation et journal des différents livrables
├── img/                  # Images et visuels d'illustration
├── lib/                  # Bibliothèques externes (junit-console.jar)
├── src/                  # Code source du projet
│   └── towerdefense/
│       ├── cell/         # Types de cases (BalloonCell, MixedCell, TowerCell)
│       ├── tower/        # Classes des tours (BombMonkeyTower, FreezeTower, etc.)
│       ├── projectile/   # Munitions et projectiles (Bomb, ExtraBomb, Needle, etc.)
│       ├── util/         # Menus, sélecteurs et IHM (Ask, Menu, InteractiveListChooser)
│       ├── exception/    # Exceptions personnalisées (TowerTypeException)
│       └── *.java        # Classes cœurs (Game, Board, Balloon, Player, ManageTower...)
├── test/                 # Tests unitaires JUnit
│   └── towerdefense/     # Classes de test (BalloonTest, BoardTest, CellTest...)
├── uml/                  # Diagrammes et images UML
├── .gitignore            # Fichiers et dossiers à ignorer par Git
├── Makefile              # Script de compilation et d'exécution
└── README.md             # Présentation générale du projet


```

---

## Tests Unitaires

Le projet inclut une couverture de tests automatisés via JUnit 5 :
* Tests des algorithmes de trajectoire et de déplacement des ballons (BalloonTest).
* Tests des générations de grilles, chemins et sorties (BoardTest).
* Tests des états des cases et placements (CellTest).
* Tests du ciblage, des portées et de l'application des dégâts des tours (ManageTowerTest, UpgradeTest).

---

## Compilation et Exécution

Le projet s'appuie sur un Makefile à la racine.

Prérequis
* JDK 17 ou supérieur
* Make

1. Compilation des sources
    ```bash
    make classes
    ```

2. Exécution des tests unitaires
    * Sur Linux / macOS :
        ```bash
        make runtests
        ```

    * Sur Windows :
        ```bash
        make runtestsWindows
        ```

3. Génération et lancement des fichiers JAR
    * Générer l'ensemble des exécutables
        ```bash
        make jar
        ```
    * Lancer la version Classique Interactive (2 paramètres à fournir : longueur et largeur du tableau)
        ```bash
        java -jar towerdefense-a-interactive.jar
        ```
    * Lancer la version Aléatoire Interactive (2 paramètres à fournir : longueur et largeur du tableau)
        ```bash
        java -jar towerdefense-b-interactive.jar
        ```

4. Génération de la Javadoc
    ```bash
    make doc
    ```

## Équipe de développement

Projet réalisé en équipe de 4 étudiants (L2 Informatique, Université de Lille) :
* Louis WAREMBOURG
* Arsène SIKUBWABO
* Ilyes BENFERHAT
* Michaël TANKEU
