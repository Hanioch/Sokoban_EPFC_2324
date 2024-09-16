# Projet ANC3 2324 - Groupe a05 - Sokoban

## Description
Ce projet est une implémentation du célèbre jeu **Sokoban** en Java, avec des fonctionnalités étendues pour la création, la sauvegarde, le chargement et la génération de terrains. Il permet également de jouer en déplaçant des caisses sur des cibles, avec des fonctionnalités supplémentaires comme la visualisation de champignons spéciaux et la possibilité de revenir en arrière dans les mouvements.

## Fonctionnalités

### Création de la Map
- **Création manuelle de la map** : 
  - Ajoutez des murs, des cibles et des caisses.
  - Gestion d'erreurs avec des messages explicatifs :
    1. Il doit y avoir un **personnage** (ni plus, ni moins d'un).
    2. Il faut au moins **une caisse**.
    3. Il faut au moins **une cible**.
    4. Le nombre de **caisses** doit être égal au nombre de **cibles**.
    5. Le nombre total d'éléments sur la carte doit être inférieur à la **moitié du nombre total de cases** disponibles.

- **Sauvegarde de la configuration** : 
  - Vous pouvez sauvegarder la configuration actuelle de la map.
  
- **Chargement d'une configuration** :
  - Chargez une map sauvegardée pour la modifier ou jouer dessus.

- **Génération de map aléatoire** :
  - Générez automatiquement un terrain aléatoire avec des murs, des caisses et des cibles placés de manière aléatoire.

### Jouer à Sokoban
- **Déplacement du joueur** :
  - Déplacez le personnage sur la map en poussant les caisses.
  
- **Déplacement des caisses** :
  - Poussez les caisses sur les cibles pour terminer le niveau.
  
- **Passage à travers les cibles** :
  - Le personnage peut passer à travers les cibles sans problème.
  
- **Comptabilisation des mouvements** :
  - Chaque déplacement est comptabilisé et affiché.

- **Visualisation du champignon ("Show Mushroom")** :
  - Le jeu se met en pause et affiche l'emplacement d'un **champignon** sur la map.
  
- **Interagir avec le champignon** :
  - Cliquez sur le champignon pour que toutes les caisses soient déplacées à des positions aléatoires parmi celles disponibles.

- **Annuler et refaire des mouvements** :
  - Utilisez **Ctrl + Z** pour annuler un mouvement et **Ctrl + Y** pour le refaire.

### Limites connues
- Les fonctionnalités de **Ctrl + Z / Y** ne fonctionnent pas correctement après avoir cliqué sur le champignon.

## Installation et Exécution

1. **Prérequis** :
   - Assurez-vous d'avoir Java installé sur votre machine (version 8 ou plus récente).
   
2. **Cloner le projet** :
   ```bash
   git clone https://github.com/ton-repo/sokoban-java.git
   ```

3. **Compiler et exécuter** :
   - Compilez le projet avec la commande suivante :
     ```bash
     javac Main.java
     ```
   - Ensuite, exécutez le programme :
     ```bash
     java Main
     ```

## Utilisation
- **Créer une map** : Ajoutez manuellement des murs, des cibles et des caisses, puis lancez la partie.
- **Sauvegarder/Charger une map** : Sauvegardez votre map pour y rejouer plus tard ou chargez une map existante.
- **Jouer** : Déplacez les caisses vers les cibles et utilisez le champignon pour générer des surprises.

## Améliorations futures
- Corriger le problème avec **Ctrl + Z/Y** après l'interaction avec le champignon.
- Ajouter des niveaux de difficulté ou des niveaux prédéfinis.
- Optimiser la génération aléatoire des maps.

## Auteurs
- Boubker Hani
- Zian Rayan
- Handali Ayman

