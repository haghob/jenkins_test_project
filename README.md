# Lancement du service jenkins :

Pour lancer le service jenkins deux phases sont nécessaires, il est possible d'utiliser soit la commande docker soit la commande docker-compose. 

### La phase de construction de l'image docker (build) :

Cette phase n'est nécessaire qu'une seule fois pour la construction de l'image docker

Avec docker (nom de l'image jenkins-cust) :

```bash
docker build -t jenkins-cust
```

Avec docker-compose (nom sera généré en fonction du nom du service docker-compose) :

```bash
docker-compose build
```

### La phase du lancement du conteneur (run) :

Pour lancer le conteneur docker exécutant le service jenkins et ouvrant les ports 8080 et 50000 du conteneur vers les ports respectifs de ma machine locale 8081 et 50000.

Avec docker (cette commande ne tient pas compte des différents volumes nécessaires pour le reste du travail) :

```bash
docker run --rm --name myjenkins -p 8080:8081 -p 50000:50000 jenkins-cust
```

Avec docker-compose (il est nécessaire d'utiliser cette commande) :

```bash
docker-compose up -d
```

# Étapes 

### Dockerfile pour Jenkins :

création d'un Dockerfile personnalisé pour Jenkins, lui permettant d'être exécuté dans un conteneur Docker

### Configuration d'un pipeline Jenkins : 

J'ai configuré un pipeline Jenkins en utilisant un Jenkinsfile. Ce pipeline récupère le code depuis un dépôt Git, exécute des tests sur différentes versions de Node.js en parallèle, déploie l'application sur une branche de production et envoie des notifications Slack en fonction du résultat du déploiement

### Intégration de la gestion des identifiants : 

J'ai ajouté la gestion sécurisée des identifiants en utilisant les credentials de Jenkins pour stocker et récupérer les secrets nécessaires dans le pipeline

### Configuration d'un déclenchement basé sur les changements de dossier : 

J'ai configuré le pipeline pour qu'il ne s'exécute que lorsqu'un changement a lieu dans un dossier spécifique du répertoire

### Automatisation du rollback en cas d'échec de déploiement : 

J'ai ajouté une gestion des erreurs pour automatiser le rollback en cas d'échec du déploiement, pour assurer aussi une meilleure fiabilité du processus de déploiement

### Intégration de l'analyse de code statique et des rapports de couverture de tests : 

J'ai ajouté des étapes pour effectuer l'analyse de code statique et générer les rapports de couverture de tests dans le pipeline Jenkins



http://localhost:8081