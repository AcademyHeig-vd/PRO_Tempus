# PRO - Tempus

> Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon

## Méthodologie

Nous avons décidé de choisir un processus "piloté" pour notre projet; nous avons utilisé un planning par semaine avec chaque tâches à faire par personne.  Toutes nos activités ont donc été planifiées en avance et étant donné que nous avons organisé le projet en plusieurs itérations, le projet ne devrait pas subir trop de changements entre itération.

### Communication

Il est important d'avoir une très bonne communication afin que chaque personne du groupe suive le projet et comprenne chaque étape réalisée. 

Nous avons planifié d'organiser deux meetings par semaine, **mercredi et dimanche**. A chaque meeting, chaque personne du groupe doit informer le reste du groupe de l'avancée du travail personnel et si nécessaire, faire une petite démo de l'application.

Entre les meetings, il est important de communiquer toutes informations importantes via Telegram.

Si jamais, il nous est pas possible de suivre le planning prévu, il faut également en informer le groupe pour qu'on soit tous au courant et qu'on puisse si vraiment avancer le travail des autres.

### Outils

Afin d'avoir une bonne organisation de groupe, nous utilisons quelques outils pratiques:

- **Google Drive** qui regroupe tous les fichiers importants liés au projet
- **Telegram** pour les communications importantes ainsi que les questions
- **Discord** pour les calls/meetings
- **GitHub** pour l'implémentation de l'application

### Pair Programming

Nous avons mis en place cette méthode de travail car nous pensons qu'elle apporte beaucoup de points positifs pour l'implémentation d'un projet; si une personne du groupe rencontre un problème pour une partie de code ou si une personne a tout simplement une question sur l'optimisation du code, nous faisons un call et une des deux personnes partage son écran. Ainsi, nous travaillons les deux sur un seul écran et nous amenons chacun des idées différentes.

### Google Drive

Comme cité précédemment, nous utilisons Google Drive afin de partager tous les fichiers importants du projet; le planning, le cahier des charges, la base de données ... 

Au niveau de la convention de nommage, chaque dossier possède un numéro -> `01`  par exemple puis le nom du dossier `database` par exemple. A l'intérieur du dossier, chaque fichier comporte également un numéro; pour donner un exemple d'un fichier `0101_Script_SQL`.

### GitHub

A chaque fois que l'on ajoute une fonctionnalité ou que l'on modifie le projet, il est nécessaire de créer un issue avec une description de ce que l'on va faire. Le nom de l'issue doit être clair et précis. 

Dès que l'on décide de s'occuper de l'issue, il faut créer une nouvelle branche et commencer à travailler dessus.

Si possible, il serait bien de la nommer de cette manière:

- `issuexx-ajout_db`

Dès que l'on commence à travailler sur l'issue, on peut directement effectuer un commit/push afin de faire une PR. Dans le titre de la PR, il faut ajouter `[WIP]..`. 

Une fois que nous avons terminé de travailler dessus, on peut modifier le titre en ajoutant `[TOREVIEW]` et envoyer un message sur le groupe Telegram afin qu'une personne du groupe approuve les changements effectués. 

Il est important de préciser, que la personne qui effectue la PR ne doit pas merger sa propre PR. Elle doit premièrement attendre que les tests unitaires passent, puis à ce moment une autre personne peut merger les modifications sur le main.