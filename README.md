# ChatSystemProject

## Presentation

Projet COO - POO de INSA Toulouse - 4A IR
Un système qui utilise les protocoles UDP et TCP pour discuter en ligne.

Objectif
Création d'un projet maven d'un système de chat dans le cadre du cours COO - POO de la 4A GEI-IR INSA Toulouse

Collaborateurs
Benjamin Abonneau, Yu Duan et Junjiang Guo

## Installation

1. Cloner ce répertoire quelque part :

    ```bash
	git clone https://github.com/IncredibleYu/ChatSystemProject.git
	```

## Utilisation

1. Clean, Compile and Test

    ```bash
	mvn clean package
	```

2. Lancer l'application, trois solutions sont possibles :

	1.
	```bash
	mvn exec:java -Dexec.mainClass="fr.insa.gei.ChatSystemProject.Main"
	```
	
	2.
	```bash
	java -cp target/classes fr.insa.gei.ChatSystemProject.Main
	```
	
	3.
	```bash
	java -jar target/ChatYourFriends-jar-with-dependencies.jar
	```

La troisième solution est la meilleure façon d'être sûr que l'application se lancera correctement.

## Distribution

1. Clean, Compile and Test

    ```bash
	mvn clean package
	```
	
2. Distrubuer le fichier situé ici aux utilisateurs :

    ```bash
	target/ChatYourFriends-jar-with-dependencies.jar
	```
	
3. Les utilisateurs peuvent alors lancer l'application à l'aide de la commande suivante :

    ```bash
	java -jar ChatYourFriends-jar-with-dependencies.jar
	```
