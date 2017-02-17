## Mes petits Exercices### Description de l applicationNotre application est une application Android integrant le Math widget developpe par MyScript. Elle donne acces a des exercices d algebre a resoudre par un utilisateur de niveau ecole primaire. L application permet aussi de collecter les donnees utilisateurs afin de les analyser et de permettre ainsi de produire des informations sur le niveau d un utilisateur (pour chaque exercice realise, le nombre de tentatives, le nombre de reponses fausses et vraies, ou encore l evolution en fonction du temps du niveau d un utilisateur). Notre application integre donc egalement un serveur et une base de donnees permettant de traiter cette partie collecte de donnees.Nous pouvons diviser notre application en 3 grandes parties :- L application Android integrant le math widget, les exercices et l interaction avec l utilisateur et permettant l envoie de donnees utilisateur- Une base de donnees permettant de stocker les donnees utilisateurs- Un serveur permettant de faire l�interface entre l application et la base de donnees.### Presentation des technologies utiliseesAu niveau de l application **Android** (cote client donc), nous utilisons la bibliotheque **OkHttp** quipermet d envoyer des donnees en utilisant le protocole HTTP.Cote serveur, nous avons developpe une API REST en utilisant **Node.js** et le module **Express** pour permettre l echange de donnees entre l application et la base de donnees. L architecture REST respecte un certain nombre de regles et utilise les methodes traditionnelles (que nous avons donc impl�ment�es) du protocole HTTP :- GET : pour la lecture des donn�es- POST : pour lՎcriture de nouvelles donn�es- PUT : pour la modification des donn�es- DELETE : pour la suppression des donn�esNotre serveur est d�ploy� sur un cloud, nous utilisons le service **Heroku**. C�t� base de donn�es, nous utilisons MongoDB, qui est un SGBD de type NoSQL. Les bases de donn�es Mongo reposent sur des collections de documents au format JSON et sont particuli�rement adapt�es � notre cas, �tant donn� que la donn�e produite par notre application est au format JSON. La base de donn�es Mongo est h�berg�e par le service MLab.Nous allons expliquer dans l�exemple ci-dessous le fonctionnement de cette architecture client-serveur.