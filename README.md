# KeySpring Backend / API  

<p align="center">
  <img src="./assets/Logo_Rectangle.png" alt="Header Cover" style="width: 100%; height: auto;">
</p>

KeySpring Backend is the core authentication API for the KeySpring system. Built with Java Spring Boot, it handles secure credential storage, authentication logic, and integration with client applications.  

## ✨ Features  
- Secure authentication and credential management.  
- API for registering applications and generating client credentials.  
- Stateless architecture with future support for token revocation and app secret rotation.  

## 📚 Tech Stack  
- **Backend Framework**: Java Spring Boot ☕  
- **Database**: PostgreSQL 🐘  
- **Security**: Spring Security 🔒  

## ⚙️ Setup  

To get the KeySpring Backend/API up and running, follow these steps:  

### 📃 Prerequisites  
- Install **Java Development Kit (JDK 17 or later)** ☕  
- Install **Gradle** 🛠️  
- Set up a PostgreSQL database 🐘

### ⬇️ Installation  
1. **Clone the repository:**  
   ```bash  
   git clone https://github.com/majiinB/KeySpring-API.git  
   cd KeySpring-API

### 🛠️ Configuration  

1. **Create the `application.properties` file**:  
   In the `src/main/resources` directory, create a file named `application.properties` and configure the following fields:  
   ```properties
   # Application Name
   spring.application.name=KeySpring

   # JWT
   jwt.secret.key=your_jwt_secret_key

   # Database connection
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   spring.datasource.url=jdbc:postgresql://localhost:5432/keyspring

   # JPA and Hibernate
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.properties.hibernate.format_sql=true

   # Flyway
   spring.flyway.baseline-on-migrate=true
   spring.flyway.locations=classpath:db/migration

   # Jackson
   spring.jackson.deserialization.fail-on-unknown-properties=false

## 🤝 Contributing  
We welcome contributions from the community! To contribute:  
1. **Fork the repository** 🍴  
2. **Create a new branch** 🛤️:  
   ```bash  
   git checkout -b feature/YourFeature  
   ```  
3. **Commit your changes** 📝:  
   ```bash  
   git commit -m 'Add some feature'  
   ```  
4. **Push to the branch** ⬆️:  
   ```bash  
   git push origin feature/YourFeature  
   ```  
5. **Open a Pull Request** 📬  

---

## 📜 License  

KeySpringg is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.  

