1) décompresser les deux dossiers (libs et MenuNavigationMyscript) dans un dossier

2) Ouvrez avec android SDK le projet MenuNavigationMyscript

3) Exécutez le projet 

(si cela ne marche pas à cause du certificat, recréez un nouveau certificat : 
l'id de ce certificat est le nom du package dans androidManifest.xml)




Dans ce projet avec les onglets :

1) Je n'ai pas réussi à mettre plusieurs onglets avec plusieurs widgets de MyScript 
(c'est à cause de problèmes concurrents : 2 widgets ne peuvent pas s'éxécuter sur la même thread. Il faudrait donc gérer cela autrement)

2) Aller dans l'onglet "Exercices" afin de consulter les différents exercices

3) Pour changer d'exercice, appuyer sur changer : on génère un exercice différent aléatoire

4) Tous les champs commençant par @ sont des "Android annotations". Tu peux chercher sur google ou sur le document qui est sur le drive 
(Serge tahé - Introduction à la programmation de Tablettes android....)