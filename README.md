# Projet ANC3 2324 - Groupe a05 - Sokoban

## Notes de version itération 1

Nous ne sommes pas parvenus à structurer notre code de façon à respecter notre
diagramme de classes, et nous avons rencontré des problèmes notamment pour l'import
de ressources .png. Sur la fin, nous nous sommes donc concentrés sur la remise
d'un projet fonctionnel, utilisant une classe enum CellValue, en étant conscients
que toutes les consignes n'étaient pas respectées. Nous allons reprendre le
développement de l'application depuis la base pour repartir sur une structure correcte
avant d'entamer les fonctionnalités de la deuxième itération.

### Liste des bugs connus

* dimensions du board pas adaptée à la taille du grid et pas responsive
* messages d'erreurs manquants
* ...

### Liste des fonctionnalités supplémentaires

### Divers

## Notes de version itération 2

- Note 1 : 
	Rajout des boutons Clear et Random Grid pour générer une grid simple de test, et la vider en un clic.

- Bug 1 : 
	Le mushroom n'est pas bien intégré dans une Command, il fonctionne bien tant qu'on ne l'annule pas mais si on fait un undo après avoir cliqué sur le mushroom,
	il undo le dernier move du player et pas le replacement des box. Cela peut générer une nouvelle box si le move annulé avait déplacé une box.

## Notes de version itération 3

...