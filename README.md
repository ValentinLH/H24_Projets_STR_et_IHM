# IFT729_Projet_STR
 
Les sources du projet peuvent être ouverte afin IDE, nous l’avons conçu sur éclipse, voici la hiérarchie des classes :

Les classes MainCentralApplication et TimeRecordMain sont les points d'entrée de l'exécution de l'application.
Les packages dao, dao.repository et dao.service contiennent les outils d’interaction avec le systeme de gestion de base de données MongoDB.

Le package rtstest contient les trois classe de tests que nous vous avons présenté durant le rapport : 
“LoadTestTimeRecord” pour le test de charge.
“TimeExecutionPointeuse” pour le test d'exécution sur la pointeuse et
“TimeExecutionTest” pour le test d'exécution de l’application central

Le package realtime contient la classe BufferedMemory.

Après avoir ouvert l’environnement et commencé à explorer cette forêt vierge, assurez-vous que les dépendances nécessaires, telles que les bibliothèques MongoDB, sont ajoutées au chemin de construction du projet. Pour pallier à cela nous vous avons aussi livrer les jar des librairies nécessaire et elles sont contenu dans le classpath du projet. 

Configurez les paramètres d'exécution, tels que les variables d'environnement et assurez vous de la présence du fichier de configuration: application.properties. Ce fichier contient les indications d’accès au système de gestion de base de donnée dont nous allons discuter dans quelques instant : 

Vous pouvez adapter l’uri, la valeur du port et le nom de la base de donnée à votre guise.

Survolons maintenant l’installation de MongoDB, dans notre cas nous avons utiliser l’interface graphique de celui-ci MongoDB compass :


Vous pouvez télécharger Compass sur le site internet de MongoDB : https://www.mongodb.com/try/download/compass 

D’ici créez une base de donnée avec le nom indiqué dans le fichier application.properties, dans notre cas c’est str_project avec le nom de collection qui vous convient. 
Maintenant exécuter la classe StubMain qui si tout se déroule correctement vous créé en base de donnée des entreprises avec lequel nous pouvons jouer

De la si tout est beau, exécutez les classes MainCentralApplication et/ou TimeRecordMain pour démarrer les applications.


Si il y a un soucie lors de la création des collections vous pouvez le faire manuellement en créant les trois collections ‘company’, ‘employee’ et ‘department’ et importer les fichiers json correspondant que nous vous avons aussi livrés avec les sources.



Nous espérons que ce petit guide d'installation vous aidera à configurer et à exécuter le projet dans votre environnement de développement.
