# arbre-de-decision
# Description :
=============

Ce projet s'agit d'une implémentation de l'algorithme des arbres de décision vu en cours. 
Construction d'un arbre de décision minimal, accompagné d'une interface graphique.


# Pour faire marcher le programme :
================================

	Il suffit de charger le projet complet dans un IDE (eclipse par exemple).
	Pour cela :
		Copier le projet dans le workspace eclipse et déziper-le
		Ouvrir votre IDE eclipse et faites :
			File -> Open projects from file system -> Directory et choisir le dossier contenant le projet
			
	Lancer la classe Test (qui contient la méthode main).
	Vous pouvez aussi lancer la classe Test en ligne de commande «terminal»
	Les fichiers de données traités par ce programme sont au format « arff » utilisé dans la plateforme weka.
	Ce projet se limite à traiter des attributs nominaux.
	
	Vous trouverez quelques fichiers de données (jeux de données utilisés) dans le dossier «fichiers»
	
	
# Fonctionnement :
===============

À partir de l'interface graphique, vous pouvez charger un fichier de données (bouton parcourir), avant qu'un fichier soit charger, les autres boutons sont désactivés. Une fois vous avez chargé un fichier de données «.arff», le chemin vers le fichier sélectionné est affiché dans le footer de l'interface graphique et les champs «profondeur» et «taux d'impureté» seront activés, cela vous donnera donc la possibilité de préciser une profondeur pour l'arbre de décision (un entier, par exemple 3) et un taux d'impureté (un entier par exemple 6 pour 6%). Ensuite il suffit de cliquer sur le bouton «générer l'arbre» pour visualiser le résulat sous forme d'un JTree java. Vous pouvez aussi consulter la sortie textuelle d'eclipse pour voir les détails (nom de la relation, nombre d'exemples, nombre d'attributs, noms des attributs et un affichage simplifié "sous forme d'un tableau" des données lu à partir du fichier arff.


# Conception du programme :
========================
Veuillez consulter le document pdf joint à ce projet
