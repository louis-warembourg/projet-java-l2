# l2s4-projet-2026

Vous devez *forker* ce projet dans votre espace de travail Gitlab (bouton `Fork`) et vidéo sur le [portail](https://www.fil.univ-lille.fr/portail/index.php?dipl=L&sem=S4&ue=Projet&label=Documents)
Un unique fork doit être réalisé par équipe.

Une fois cela réalisé, supprimer ces premières lignes et remplir les noms des membres de votre équipe.
N'oubliez pas d'ajouter les autres membres de votre équipe aux membres du projet, ainsi que votre enseignant·e (statut Maintainer).

# Equipe

- Arsene SIKUBWABO
- Louis WAREMBOURG
- Ilyes BENFERHAT
- Michael TANKEU

# Sujet

[Le sujet 2026](https://www.fil.univ-lille.fr/~varre/portail/l2s4-projet/sujet2026.pdf)

# Livrables

Les paragraphes concernant les livrables doivent être remplis avant la date de rendu du livrable. A chaque fois on décrira l'état du projet par rapport aux objectifs du livrable. Il est attendu un texte de plusieurs lignes qui explique la modélisation choisie, et/ou les algorithmes choisis et/ou les modifications apportées à la modélisation du livrable précédent.

Un lien vers une image de l'UML doit être fourni (une photo d'un diagramme UML fait à la main est suffisant).

## Livrable 1

### Atteinte des objectifs

La génération du plateau pour les deux modes ainsi que la création aléatoire du chemin pour le mode 1 sont opérationnelles, correctement compilées et testées.

Il a été choisi pour cela de créer une classe abstraite *Cell* qui composera les sous-classes *MixCell* (pour le mode 2), *TowerCell* et *BalloonCell* (pour le mode 1), le chemin des ballons pour le terrain du 1er mode de jeu est généré de manière aléatoire à l'aide de la classe *Board*, et des spécifications de la classe *StartEndChooser*, mais comporte tout de meme de l'optimisation par rapport à la longueur maximale qu'il peut atteindre. L'algorithme de la création du chemin aléatoire est le suivant : On part du point de départ défini de manière aléatoire, puis on choisit une direction. La direction a 50% de chance d'être celle vers la fin du chemin et 50% de chance d'être aléatoire. Cela permet d'éviter d'avoir trop de chemins qui finissent bloqués. On avance de 1 dans la direction et on recommence tant qu'on n'a pas atteint la fin, ou qu'on soit bloqué, ou que l'algorithme a effectué trop de tours, c'est-à-dire que le chemin est potentiellement trop long.
    
Nous avons également fait des UML concernant les classes précédemment citées (voir dossier /img), et entamé la conception théorique de la classe *Balloon*, en accord avec les objectifs du prochain livrable.

### Difficultés restant à résoudre

Nous avons décidé d'exprimer la génération du chemin des ballons dans le mode 2 en fonction du point d'apparition de chaque Ballon, d'ou l'utilité de générer le terrain avec des MixCell : toutes les cellules peuvent etre des chemins. Il va falloir finir la classe Balloon pour avoir un rendu finale de leur déplacement.

## Livrable 2

### Atteinte des objectifs

Cette phase a consisté à concevoir les ballons et à structurer leur apparition sur le plateau. Nous avons choisi de modéliser la construction de chaque ballon en fonction du mode de jeu; ce qui fait qu'un ballon pourrait avoir certains de ses attributs définis dans un mode et non définis dans un autre. Le ballon dispose de la capacité de mémoriser les coordonées de la case dans laquelle il se trouve, ce qui fait qu'à chaque déplacement, il abandonne les coordonées de la case actuelle pour adopter celles de la case suivante. A sa création, le ballon dispose aussi d'une vitesse de déplacement et d'une resistance aux tirs lancés par une tour. Il dispose aussi d'une mesure du progrès fait à chaque déplacement qu'il effectue.

Dans le Makefile, se trouvent toutes les commandes nécessaires pour la compilation et l'execution des sources et tests, la génération de la documentation, la création et l'éxecution de fichier JAR.
    
Pour Exécuter le Livrable2a :
```make Livrable2a```
    
Pour Exécuter le Livrable2b :
```make Livrable2b```
    
Pour Exécuter les tests :
```make executeTest```
    
Pour Créer le jar Exécutable du Livrable2a :
```make Livrable2aJAR```
    
Pour Créer le jar Exécutable du Livrable2b :
```make Livrable2bJAR```

Pour Exécuter le jar du livrable2a :
```make executeLivrable2aJAR```

Pour Exécuter le jar du livrable2b :
```make executeLivrable2bJAR```

Pour générer la documentation :
```make docs```

Nous disposons aussi d'un diagramme UML(voir dossier /img) représentant la modélisation complète.

### Difficultés restant à résoudre

Il nous reste encore à implémenter correctement la gestion du temps en fonction du déplacement des ballons.

## Livrable 3

### Atteinte des objectifs

Cette phase à consister à créer les tours du jeu, de gérer la portée des tours, d'identifier les cibles des tours et leurs faire perdre de la vie. 
Nous avons séparé la logique de gestion (ciblage, temps de recharge) de la tour elle-même dans ManageTower pour respecter le principe de responsabilité unique.
On a choisi une Map pour représenter les cellules dans la portée avec un pourcentage (100% au centre, moins sur les bords) pour permettre un ciblage plus précis des ballons qui sont à cheval entre deux cellules.
On a choisi de stocker temporairement les ballons à supprimer pour éviter les ConcurrentModificationException lors de l'itération sur la liste principale.
On a implémenté un système de cooldown basé sur des ticks plutôt qu'un timer réel pour simplifier la simulation et la rendre déterministe.
On a choisi de représenté la portée de la tour sous la forme d'un carré qui peut prendre une cellule entière comme un pourcentage d'une cellule(voir dossier img) .Pour pouvoir implémenter plus tard les upgrades en pourcentages.

Pour Exécuter le Livrable3a :
```make execute3a```
    
Pour Exécuter le Livrable3b :
```make execute3b```
    
Pour Exécuter les tests :
```make runtests```
    
Pour Créer le jar Exécutable du Livrable3 (a et b) :
```make Livrable3JAR```

Pour Exécuter le jar du livrable3 (a et b) :
```make jar```

    Le programme est fonctionnel mais il manque encore certaines méthodes de tests.

### Difficultés restant à résoudre
    Il nous reste à résoudre le système de cible multiple d'une tour. Une tour peut faire des dégats de "zone". La tour prend un ballon cible et les ballons les plus proches sont aussi touché. Donc on doit encore implémenter ce système.

## Livrable 4

### Atteinte des objectifs
    
Cette phase consistait à implémenter différents types d'évolutions pour les tours et à les mettre en application pour chaque tour. Il fallait aussi intégrer un principe de plusieurs manches dans la boucle principale du jeu. Nous avons défini les évolutions, leurs natures, leurs modes de fonctionnement et le coût de leur application sur une tour. En ce qui concerne plus précisément la boucle principale du jeu, nous avons créé une superclasse qui regroupe les points commmuns entre le mode aléatoire et le mode classique. Ainsi, chaque mode dispose de sa propre boucle principale mais qui est moins chargée car elle implémente moins de fonctionnalités et importe le reste depuis la superclasse.

Pour compiler :
```make classes```

Pour Exécuter le Livrable4a : 
```make execute4a```

Pour Exécuter le Livrable4b : 
```make execute4b```

Pour compiler les tests :
```make test```

Pour Exécuter les tests : 
```make runtests```

Pour Créer le jar Exécutable du Livrable4a : 
```make Livrable4aJAR```

Pour Créer le jar Exécutable du Livrable4b : 
```make Livrable4bJAR```

Pour Exécuter le jar du Livrable4(a et b): 
```make jar```

pour générer la documentation:
```make docs```

### Difficultés restant à résoudre
Il nous reste encore à améliorer l'affichage de la grille de jeu sur la sortie standard.

## Livrable 5

### Atteinte des objectifs
Cette phase consistait à gérer les actions effectuées par le joueur telles que les placements et les ventes de tours, l'application 
et l'annulation des évolutions de tours, la visualisation du terrain pour les deux modes de jeu, ainsi que le lancement des rounds.
Nous nous sommes pour cela aidés de la classe `ListChooser.java` afin de simuler un choix entre plusieurs options possibles, que nous 
avons concrétisées davantage en des menus définis d'avance et qui s'adaptent à la situation actuelle de la partie (présence ou absence de tours et/ou d'évolutions) au sein de la classe `Menu.java`. 

Pour compiler :
```make classes```

Pour Exécuter le Livrable5
```make execute5```

Pour compiler les tests :
```make test```

Pour Exécuter les tests : 
```make runtests```

Pour Créer le jar Exécutable du Livrable5 : 
```make Livrable5JAR```

Pour Exécuter le jar du Livrable4(a et b): 
```make jar```

pour générer la documentation:
```make docs```

### Difficultés restant à résoudre
Quelques problèmes d'éxecution pour les tests du mode classique.

## Livrable 6

### Atteinte des objectifs
cette phase finale de réalisation du projet towerdefense a consisté en une optimisation du système de Menu, de sorte à pouvoir circuler plus librement entre les différents choix possibles pour le joueur, tout en lui ajoutant des options supplémentaires (le choix de l'intéractivité du jeu, et des évolutions à appliquer aux tours)
nous avons également résolus certains problèmes qui entravaient la concordance de l'éxécution du code avec le sujet, notamment la correction et l'implémentation complète des bombes et de leur système de zone d'impact, le début du chemin du mode random qui débute sur le bord gauche, la correction des Upgrades, et enfin la complétion de la documentation et des tests.

Pour compiler :
```make classes```

Pour compiler les tests :
```make tests```

Pour Exécuter les tests : 
```make runtests```

Pour créer le jar du LivrableFinal (a ou b, interactif ou random) 
```make jar```

pour générer la documentation:
```make doc```

pour nettoyer les classes : 
```make clean```

pour exécuter les jar : 
```java -jar towerdefense-a-interactive.jar```
```java -jar towerdefense-b-interactive.jar```
```java -jar towerdefense-a-random.jar```
```java -jar towerdefense-b-random.jar```

### Difficultés restant à résoudre

# Journal de bord

Le journal de bord doit être rempli à la fin de chaque séance encadrée, et **avant** de quitter la salle. 

Pour chaque semaine on y trouvera :
- ce qui a été réalisé, les difficultés rencontrées et comment elles ont été surmontées (on attend du contenu, pas uniquement une phrase du type "tous les objectifs ont été atteints")
- la liste des objectifs à réaliser d'ici à la prochaine séance encadrée


## Semaine 1

### Ce qui a été réalisé
L'algorithme du chemin aléatoire a été conçu. Nous avons commencé à réfléchir à l'algorithme de chemin classique.
Nous avons défini l'UML des cases (Cell) (voir photo **UMLséance_1** du dossier IMG). Nous avons également fait un UML pour le choix des modes de jeux. 

### Difficultés rencontrées
Difficulté pour la mise en place du chemin de la version classique.

### Objectifs pour la semaine et répartition du travail par membre
Ajouter des méthodes dans l'UML, et commencer le code des classes.


## Semaine 2

### Ce qui a été réalisé
Nous avons finalisé l'UML de la classe Cell et de ses sous classes associées, ainsi qu'un concept pour les futures classes Player et Game (voir photo **UMLSéance_2** du dossier IMG). Finalisation de la classe Board en Java pour le 1er mode de jeu avec les Tests associés à la création du chemin,et ébauche pour le 2e mode de Jeu. Et nous avons commencé à Coder La classe Cell, et les sous-lasses TowerCell, BalloonCell et MixCell en Java.

### Difficultés rencontrées
Mise en place des chemins pour le 2e mode de jeu

### Objectifs pour la semaine et répartition du travail par membre
Finalisation de l'écriture des classes pour les deux modes de jeu, et mise en place d'un UML pour le reste du jeu
    Louis : Test pour la classe Board
    Ilyes : écriture de l'UML et de la classe Cell
    Michael : test de la classe Cell et ses Pacquetages
    Arsène : Création de l'énum Direction

## Semaine 3

### Ce qui a été réalisé
Nous avons mis en place la structure de la classe `Balloon` (voir photo **UMLSéance_3** du dossier IMG). Nous avons aussi réfléchi à l'algorithme du déplacement du ballon dans les deux modes de jeu.
Nous avons terminé la création des chemins pour le mode aléatoire du jeu. Pour le chemin du mode classique, ce sera la classe `Balloon` qui générera le déplacement.

### Difficultés rencontrées
mise en place du concept de création de la classe `Balloon`.

### Objectifs pour la semaine et répartition du travail par membre
    Implémentation de la classe `Balloon` et des méthodes associés pour les deux modes de jeu.
    Arsène : Création de la classe Balloon et du déplacement en suivant un chemin dans le premier mode.
    Ilyes : Création de la methode responsable de la direction du ballon dans le deuxieme mode.
    Michael : création de la méthode pour verifier si le ballon est à la fin.
    Louis : les tests de la classe Balloon.

## Semaine 4

### Ce qui a été réalisé
Nous avons ajouté le système de progression d'un ballon au sein d'une cellule. Nous avons réfléchi à l'implémentation du temps dans    le  jeu. Nous nous sommes renseigné sur une bibliothèque Time avec  les classes Clock, Duration etc. pour une potentielle utilisation pour le temps.

### Difficultés rencontrées
Implementation du temps, quelle classe utiliser.

### Objectifs pour la semaine et répartition du travail par membre
    Michael : Méthode move dans les deux modes.
    Arsène : Création des nouveaux tests, de la documentation.
    Ilyes : UML et Création du temps.
    Louis : Création et implémentation du temps.

## Semaine 5

### Ce qui a été réalisé
Nous nous sommes concertés sur la mise en place de la classe Tower et de ses différentes fonctionnalités, en accord avec les attributs et les méthodes mentionnées dans l'UML. Nous avons Discuté de la façon de gérer la portée des tours, avec un système de division de la cellule en coin de la portée, de sorte à mettre en place un bord "diagonal" qui va prendre un ballon comme potentielle cible en fonction de sa progression au sein de la cellule concernée, celle avant, et celle après

### Difficultés rencontrées
Mise en place concrète de ce bord "diagonal" de la portée des tours

### Objectifs pour la semaine et répartition du travail par membre et des sous-classes (les différentes tours dans le jeu)
    Louis : Mise en place du système de Tir et de l'identification de la cible des tours
    Arsène : Architecture globale de la classe Tower, et mise en place de la portée
    Ilyes : gestion de la liste des évolutions et des projectiles actuels et disponibles, et mise à jour de l'UML
    Michael : Implémentation de l'immobilisation/ralentissement des ballons 

## Semaine 6

### Ce qui a été réalisé
Nous avons réfléchi pour une nouvelle implémentation et conception de la portée de la tour en mettant en place une gestion de la progression du ballon en fonction d'un pourcentage de la cellule (voir dossier img).
Nous avons continué l'implémentation de la méthode identifyTarget et théorisé sur la mise en place de la méthode shoot.  

### Difficultés rencontrées
Nous avons eu des difficultés sur la conception de la portée de la tour en gérant les coins.  

### Objectifs pour la semaine et répartition du travail par membre
    Louis : Gérer le temps des tours 
    Arsène : Implémentations des projectiles et des upgrades de chaques tours 
    Ilyes : Mise à jour de l'UML et implémentation de la portée
    Michael : Méthode shoot

## Semaine 7

### Ce qui a été réalisé
Correction des tests que nous avons adaptés à l'implémentation de la classe ManageTower. Et début de la mise en place des évolutions des tours et de leur gestion. Nous avons également réalisé la classe ManageBalloon pour que le board ne gère plus les ballons.

### Difficultés rencontrées

Présence de problèmes au sein du dernier livrable. En raison des FreezeTower et SlowTower, car les ballons restent freeze ou slow de manière continue(cela crée donc une boucle infini)

### Objectifs pour la semaine et répartition du travail par membre
    Arsène : Mise à jour de l'UML, et correction des Freeze et Slow Tower
    Michael : Correction des problèmes liées aux upgrades, ajoût du système d'argent au sein des upgrades 
    ilyes : Réalisation des tests pour l'upgrade
    Louis : Factorisation des longues méthodes. Et réalisation des tests des ManageTower et ManageBalloon

## Semaine 8

### Ce qui a été réalisé
nous avons entamé la correction de certains problèmes concernant le système des FreezeTower et des SlowTower : les effets qu'elles engendrent ne s'annulent pas, ce qui peut amener à une boucle infini pour le jeu, particulièrement en mode classic. Nous avons par contre pu implémenter le gameMode en paramètres de certaines méthodes supplémentaires qui en avaient besoin, et continué avec l'amélioration des Upgrades.

### Difficultés rencontrées
le problème des FreezeTower et slowTower n'est toujours pas réglé, et la boucle infini fait toujours effet.

### Objectifs pour la semaine et répartition du travail par membre
    Ilyes : Tests de l'Upgrade, implémentation de la range des bombes de la BombTower, et recherche pour l'implémentation d'une interface graphique 
    Arsène : Mise à jour de l'UML, Documentation et test du ManageBalloon
    Louis : tests du ManageTower et correction du Freeze et du Slow
    Michael : correction du MakeUpgrade et commencement des tests du Game
    
## Semaine 9

### Ce qui a été réalisé
Nous avons commencé la réalisation du Livrable 4 qui comprend la gestion des manches et l'application des bonus et le retrait des bonus. La version aléatoire est achevée. Nous avons complété les sous-classes de Tower en spécifiant les évolutions possibles pour chaque sous-classe.  

### Difficultés rencontrées
Il y a eu des complications concernant le choix aléatoire des tours, ainsi que l'application des évolutions pour une tour donnée, car il arrivait que certaines ne pouvaient avoir d'évolutions.

### Objectifs pour la semaine et répartition du travail par membre
    Arsène: ajouter l'affichage pour décrire les évolutions
    Louis: faire le livrable 4b
    Ilyes: completer la documentation et mise à jour de l'UML
    Michael: completer les tests

## Semaine 10

### Ce qui a été réalisé
Nous avons effectué la correction de certaines classes de tests, notamment l'UpgradeTest, le ProjectileTest, et le CellTest, et commencé l'implémentation du ListChooser dans la calsse Ask, dans l'optique de la réalisation du livrable5.

### Difficultés rencontrées
problèmes notables sur le ManageTowerTest

### Objectifs pour la semaine et répartition du travail par membre
    Arsène : correction du ManageTowerTest
    Louis : mise à jour de l'UML, et réalistaion des Tets du Game
    Ilyes : poursuite de l'implémentation du ListChooser
    Michael : mise en place des codes à éxécuter pour chaque choix du ListChooser

## Semaine 11

### Ce qui a été réalisé
Nous avons corrigé un certain nombre de tests qui échouaient, et nous en avons rajouté d'autres plus pertinents. Nous avons aussi initialisé la classe Player qui représente le joueur, et nous avons complété certaines de ses actions. Nous avons aussi créé une autre classe d'initialisation pour alléger la classe Game.

### Difficultés rencontrées
erreurs dans les tests de Game.

### Objectifs pour la semaine et répartition du travail par membre
    Louis: poursuite de l'implementation de l'Init, et affichage Tower et Upgrade dans les livrables
    Arsène: correction des tests de Game
    Ilyes : poursuite de l'implémentation du ListChooser et mise à jour de l'UML
    Michael : poursuite de la mise en place des codes à éxécuter

## Semaine 12

### Ce qui a été réalisé

### Difficultés rencontrées

### Objectifs pour finaliser le projet et répartition du travail par membre
