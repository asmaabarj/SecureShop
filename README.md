# 🛒 secureShop

Un projet pour sécuriser et déployer une application web de gestion de produits, catégories et utilisateurs, avec authentification **stateful** et intégration CI/CD via Docker et Jenkins.

---

## 🌟 **Contexte du projet**

Le projet **secureShop** permet de sécuriser les APIs, conteneuriser et déployer l'application. Les fonctionnalités incluent :
- Gestion des produits et catégories.
- Authentification et gestion des utilisateurs avec rôles.
- Déploiement via Docker et Jenkins.
- Documentation technique complète et formation des équipes.

---

## 🗂️ **Entités Principales**

### 🛍️ **Produit**
- `designation` (String)
- `prix` (Double)
- `quantite` (Integer)

### 📂 **Catégorie**
- `nom` (String)
- `description` (String)

### 👤 **Utilisateur**
- `login` (String)
- `password` (String, crypté avec BCrypt)
- `active` (Boolean)
- `roles` (Collection de `Role`)

### 🛑 **Role**
- `name` (String)

Relations :
- Une catégorie peut contenir plusieurs produits.
- Un produit appartient à une seule catégorie.

---

## ✨ **Fonctionnalités**

### 📦 **Gestion des Produits**
- Lister, rechercher, filtrer avec pagination et tri. (USER, ADMIN)
- Ajouter, modifier, supprimer. (ADMIN uniquement)
- Endpoints :
  - `/api/user/products/**`
  - `/api/admin/products/**`

### 📂 **Gestion des Catégories**
- Lister, rechercher, filtrer avec pagination et tri. (USER, ADMIN)
- Ajouter, modifier, supprimer. (ADMIN uniquement)
- Endpoints :
  - `/api/user/categories/**`
  - `/api/admin/categories/**`

### 👥 **Gestion des Utilisateurs**
- Authentification et enregistrement :
  - `POST /api/auth/login`
  - `POST /api/auth/register`
- Gestion des utilisateurs (ADMIN uniquement) :
  - `GET /api/admin/users`
  - `PUT /api/admin/users/{id}/roles`

---

## 🔐 **Sécurité**

- Authentification **stateful** via `JdbcAuthentication` (Spring Security).
- Cryptage des mots de passe avec `BCryptPasswordEncoder`.
- Protection des endpoints :
  - `/api/admin/*` : Rôle **ADMIN** requis.
  - `/api/user/*` : Rôle **USER** requis.

### 🔄 **Profils**
- **dev** : Sécurité désactivée pour simplifier le développement.
- **prod** : Authentification via `JdbcAuthentication`.

---

## 🛠️ **Couches Applicatives**

Le projet est structuré en plusieurs couches :
- **Controller** : Gestion des endpoints REST.
- **Service** : Logique métier.
- **Repository** : Accès aux données.
- **DTO et Mapper** : Transformation et mapping des entités.
- **Validation** : Validation des données en entrée.
- **Tests** : Tests unitaires et d'intégration.
- **Gestion des exceptions** : Utilisation de `@ControllerAdvice`.

---

## 📋 **Technologies et Concepts**

- **Backend** : Spring Boot, Spring Data JPA, Spring Security.
- **Base de données** : MariaDB ou OracleXE.
- **Conteneurisation** : Docker.
- **CI/CD** : Jenkins.
- **Tests** : JUnit, Mockito.
- **Documentation API** : Swagger.
- **Outils** : Lombok, SonarLint, Postman, Git, JIRA.

---

## 🚀 **Installation**

### 🐳 **Avec Docker**
1. Construire l'image Docker :
   ```bash
   docker build -t secureshop-app .
```

2. Lancer les conteneurs :
   ```bash
docker-compose up -d
```
### 🔧 ** Manuellement **
1. Cloner le dépôt :
   ```bash
git clone https://github.com/asmaabarj/secureshop.git
cd secureshop
```
2. Configurer la base de données dans application-dev.properties et application-prod.properties.
3. Lancer l'application :
   ```bash
mvn spring-boot:run
```
## 📚 **Usage**
### ⚙️ ** Endpoints Utiles **
##### Produits :  
- Lister : GET /api/user/products?page=1&size=10
- Ajouter (ADMIN) : POST /api/admin/products
##### Catégories :
- Lister : GET /api/user/categories
- Supprimer (ADMIN) : DELETE /api/admin/categories/{id}
##### Authentification :
- Login : POST /api/auth/login
- Enregistrement : POST /api/auth/register

## 🛡️ **Tests**
1. Exécuter les tests unitaires :
```bash
mvn test
```
2. Exécuter les tests de sécurité :
```bash
mvn verify
```
