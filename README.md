
# Tempus
<p align="center">
  <img width="357" height="233" src="images/Tempus.png">
</p>

[![Java CI with Maven](https://github.com/AcademyHeig-vd/PRO-Project/actions/workflows/maven.yml/badge.svg)](https://github.com/AcademyHeig-vd/PRO-Project/actions/workflows/maven.yml)
[![Documentation Status](https://readthedocs.org/projects/ansicolortags/badge/?version=latest)](http://ansicolortags.readthedocs.io/?badge=latest)
[![GitHub tag](https://img.shields.io/github/tag/Naereen/StrapDown.js.svg)](https://GitHub.com/Naereen/StrapDown.js/tags/)
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)
[![Open Source Love svg2](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)



Google drive : https://drive.google.com/drive/folders/16hgVjggeGgG7rjfjVIXN8Mohfn_XSWHI

## Installation 
Une fois que vous avez clone le projet, il faut tout d'abord lancer la connexion à la base de données SQL. Pour ce faire, nous avons utilisé un conteneur Docker afin de faciliter son lancement. 

Si vous êtes sur Windows, vous pouvez directement lancer le script Docker à la racine du projet avec la commande:
````bash
./start.sh
````
Si vous êtes sur Linux/macOS, il faut effectuer ces commandes:
````bash
cd Docker
sudo docker-compose up --build
````
Une fois le conteneur construit et lancé, vous pouvez vous connecter à la base de donnéee via votre navigateur à l'adresse: `http://localhost:6060` en entrant comme identifiant `root` et comme mot de passe `root`.
/!\ Si vous voulez changer de username ou mot de passe, il faudra modifier le fichier du projet `dbConnexion` qui se trouve dans le package `connexion`. /!\

Une fois la connexion effectuée, vous pouvez ajouter la base de donnée qui se trouve également dans le projet.

A présent l'installation de l'application est terminée et vous pouvez lancer l'application via l'IDE.
