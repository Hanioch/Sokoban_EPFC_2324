# Projet ANC3 2324 - Groupe a05 - Sokoban

## Description
Ce projet est une implémentation du célèbre jeu **Sokoban** en Java, avec des fonctionnalités étendues pour la création, la sauvegarde, le chargement et la génération de terrains. Il permet également de jouer en déplaçant des caisses sur des cibles, avec des fonctionnalités supplémentaires comme la visualisation de champignons spéciaux et la possibilité de revenir en arrière dans les mouvements.

## Fonctionnalités

### Création de la Map
<img width="1068" alt="Capture d’écran 2024-09-15 à 11 41 38" src="https://github.com/user-attachments/assets/c226152d-074c-446c-b3e1-705222dbb7a0">

- **Création manuelle de la map** : 
  - Ajoutez des murs, des cibles et des caisses.
  - Gestion d'erreurs avec des messages explicatifs :
    1. Il doit y avoir un **personnage** (ni plus, ni moins d'un).
    2. Il faut au moins **une caisse**.
    3. Il faut au moins **une cible**.
    4. Le nombre de **caisses** doit être égal au nombre de **cibles**.
    5. Le nombre total d'éléments sur la carte doit être inférieur à la **moitié du nombre total de cases** disponibles.

<img width="1112" alt="Capture d’écran 2024-09-15 à 11 42 08" src="https://github.com/user-attachments/assets/2580ba12-01df-441f-b289-6c25b785b947">

- **Sauvegarde de la configuration** : 
  - Vous pouvez sauvegarder la configuration actuelle de la map.
  
- **Chargement d'une configuration** :
  - Chargez une map sauvegardée pour la modifier ou jouer dessus.

- **Génération de map aléatoire** :
  - Générez automatiquement un terrain aléatoire avec des murs, des caisses et des cibles placés de manière aléatoire.

### Jouer à Sokoban
<img width="1112" alt="Capture d’écran 2024-09-15 à 11 42 39" src="https://github.com/user-attachments/assets/d36f39e4-6428-406d-9209-d2b85fef3d40">

- **Déplacement du joueur** :
  - Déplacez le personnage sur la map en poussant les caisses.
  
- **Déplacement des caisses** :
  - Poussez les caisses sur les cibles pour terminer le niveau.
  <img width="1112" alt="Capture d’écran 2024-09-15 à 11 43 04" src="https://github.com/user-attachments/assets/984006ee-ad78-43b5-a8d1-0e7361fda580">

- **Passage à travers les cibles** :
  - Le personnage peut passer à travers les cibles sans problème.
  
- **Comptabilisation des mouvements** :
  - Chaque déplacement est comptabilisé et affiché.

- **Visualisation du champignon ("Show Mushroom")** :
  - Le jeu se met en pause et affiche l'emplacement d'un **champignon** sur la map.
  <img width="1112" alt="Capture d’écran 2024-09-15 à 11 43 14" src="https://github.com/user-attachments/assets/04a55498-366b-4299-bd82-47cabc81406b">

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

