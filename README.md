## Mes petits Exercices
### Description de l'application
Notre application est une application Android intégrant le widget Math développé par MyScript. Elle donne accès à des exercices d'algèbre destinés à être résolu par un utilisateur de niveau école primaire. L'application permet aussi de collecter les données utilisateurs afin de les analyser et de permettre ainsi de produire des informations sur le niveau d'un utilisateur (pour chaque exercice réalisé, le nombre de tentatives, le nombre de réponses fausses et vraies, ou encore l'évolution en fonction du temps du niveau d'un utilisateur). Notre application intègre donc egalement un serveur et une base de données permettant de traiter cette partie collecte de données.
Nous pouvons diviser notre application en 3 grandes parties :
- L'application Android intégrant le math widget, les exercices et l'interaction avec l utilisateur et permettant l'envoi de données utilisateurs
- Une base de données permettant de stocker les données utilisateurs
- Un serveur permettant de faire l'interface entre l'application et la base de données

### Présentation des technologies utilisées
Au niveau de l'application **Android** (côté client), nous utilisons la bibliothèque **OkHttp** qui
permet d'envoyer des donnees en utilisant le protocole HTTP.
Côté serveur, nous avons developpé une API REST en utilisant **Node.js** et le module **Express** pour permettre l'échange de données entre l'application et la base de données. L architecture REST respecte un certain nombre
de règles et utilise les méthodes traditionnelles (que nous avons donc implementées) du protocole HTTP :
- GET : pour la lecture des données
- POST : pour l écriture de nouvelles données
- PUT : pour la modification des données
- DELETE : pour la suppression des données

Notre serveur est deployé sur un cloud : pour cela, nous utilisons le service **Heroku**. Afin de stocker les informations, nous utilisons MongoDB, qui est une base de données orientée objet (de type NoSQL). Les bases de données Mongo reposent sur des collections de documents au format JSON et sont particulièrement adaptées à notre cas d'utilisation : en effet, la donnée produite par notre application est au format JSON. Cette base de données est hebergée par le service **MLab**.
Nous allons expliquer dans l’exemple ci-dessous le fonctionnement de cette architecture client-serveur.

### Module d'analyse et de visualisation de données
Afin de valoriser les données collectées, nous avons décidé de mettre en pratique nos cours de visualisation de données en réalisant deux visualisations. Ces visualisations s’appuient chacune sur une collecte JSON de la base puis sur un script javascript utilisant une bibliothèque externe.
Comme visualisation, nous avons retenu une représentation des pourcentages de bonnes réponses pour chaque exercice afin de pouvoir équilibrer au besoin la difficulté des exercices.
Une courbe représentant le volume de POST journalier afin d’évaluer l’activité de l’application a également été réalisée.
