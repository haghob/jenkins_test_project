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


http://localhost:8081