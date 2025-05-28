<div align="center"> <h1>Quarkus Social (Projeto de Estudo)</h1></div>

<div align="center">
<h3>API para Interações Sociais</h3>
  <p><i>Projeto desenvolvido para explorar o ecossistema Quarkus e construir uma API RESTful.</i></p>
</div>

---

## 🧠 Objetivo
Este projeto é uma API RESTful desenvolvida com o intuito de aplicar e consolidar conhecimentos em:
- **Java** com **Quarkus** (Framework Java Supersonic Subatomic)
- Desenvolvimento de **APIs REST** com Jakarta REST (JAX-RS)
- Persistência de dados com **Hibernate ORM + Panache**
- Uso de **Data Transfer Objects (DTOs)** para comunicação com a API

---

## 🚀 Funcionalidades Principais
A API permite gerenciar interações básicas de uma rede social:

### Gerenciamento de Seguidores
- **Seguir um usuário**: Permite que um usuário siga outro.
  - `PUT /users/{userId}/followers`
- **Listar seguidores de um usuário**: Retorna a lista de usuários que seguem um determinado usuário.
  - `GET /users/{userId}/followers`
- **Deixar de seguir um usuário**: Remove a relação de seguidor.
  - `DELETE /users/{userId}/followers?followerId={followerId}`

*(Observação: O projeto contém um `PostResponse.java`, sugerindo futuras funcionalidades relacionadas a posts.)*

---

## 🛠 Tecnologias
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Quarkus](https://img.shields.io/badge/Quarkus-4695EB?style=for-the-badge&logo=quarkus&logoColor=white)](https://quarkus.io/)
[![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-76BEF0?style=for-the-badge&logo=jakarta-ee&logoColor=white)](https://jakarta.ee/)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/orm/)

*Ferramentas e bibliotecas complementares:*
- Lombok
- GraalVM (para build nativo)

---

## ⚙️ Como Executar

### Modo de Desenvolvimento
Você pode executar a aplicação em modo de desenvolvimento, que habilita live coding, usando:
```shell script
./mvnw quarkus:dev
