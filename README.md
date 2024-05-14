# Musigame 
## A new way of enjoying music

[![CI Bff](https://github.com/bbmt0/Musigame/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/bbmt0/Musigame/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bbmt0_Musigame&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bbmt0_Musigame)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bbmt0_Musigame&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bbmt0_Musigame)


### Description
//TODO faire description projet 

### Presentation
Vous pouvez retrouver une vidéo de présentation du projet sur [Youtube](
https://youtu.be/VjRyPywA1zI).
Dans cette vidéo est illustrée une partie entre deux joueurs, bien que le jeu se veut être joué à minimum 3 joueurs.

### Mono-repo
Ce repository est un mono-repo, il contient plusieurs projets :
- Musigame front : Application web en React
- Musigame bff : API en Spring Boot

### Technologies
- Front : React
- BFF : Spring Boot (Java 21)

### Installation
Pour installer le projet, il suffit de cloner le repository avec la commande suivante :
```shell 
git clone https://github.com/bbmt0/Musigame.git
```
Ensuite, veuillez suivre les instructions d'installation des projets front et bff situées dans leur README respectif.
front : [README.md](./front/README.md)
bff : [README.md](./bff/README.md)


### Documentation
Nous avons mis en place des ADR pour documenter les choix d'architecture du projet. Vous pouvez les retrouver dans le dossier [docs/adr](./doc/architecture/decisions/)
Nous avons également mis en place des schémas d'architecture que vous pouvez retrouver dans le dossier [docs/architecture](./doc/architecture/schemas/)

### Livrables
Vous pouvez retrouver les livrables du projet dans le dossier [docs/livrables](./doc/livrables/)

### CI/CD
Nous avons mis en place une CI/CD avec Github Actions. Vous pouvez retrouver les workflows dans le dossier [.github/workflows](./.github/workflows/)


### SonarCloud
Nous utilisons SonarCloud pour analyser la qualité de notre code. Vous pouvez retrouver les résultats de l'analyse sur la page du projet [SonarCloud](https://sonarcloud.io/dashboard?id=bbmt0_Musigame)

