Welcome to the official repo for the **Skypay Technical Test 1 – Banking Service**.  
This is a Spring Boot 3.5.3 console application that simulates core banking features in a clean and object-oriented architecture.

---

## 📌 Context

> “When doing this test, think of the requirements in banking software systems.”  
> — Skypay Technical Test instructions

I followed this direction by focusing on core banking functionalities such as:

- ✅ **Sign up / Sign in**
- ✅ **Login & logout**
- ✅ **Creating Current / Saving accounts**
- ✅ **Deposits / Withdrawals**
- ✅ **Transfers between accounts**
- ✅ **Error & Exception handling**
- ✅ **Well-structured architecture with Service, DTO, Exception, and Entity layers**
- ✅ **Clean CLI interface**

using int instead of BigDecimal to respect insructions requirement and password are stored without hashing to simplify
---

![detailedClassDiagramme](https://github.com/user-attachments/assets/6888416d-291c-4b18-a731-2061f32e3f36)# 💳 JAVA SKYPAY TEST
🧬 Class Diagram
You can click it or open detailedClassDiagramme.svg included in the repo to explore the system design.


## 🧠 Technologies Used

- `Java 17`
- `Spring Boot 3.5.3`
- `Maven`
- `Lombok`
- Clean Architecture principles
- UML Design (see `detailedClassDiagramme.svg`)

---

## 📁 How to Run the App

### 🛠 Requirements

- Java 17+
- Maven (optional, for building)
- Terminal access

### ▶️ Run the JAR

```bash
java -jar skypayBankTest1-KACEMI-Ayman.jar
```

🧪 Seed Users
Username	     Password  	Notes
ayman_kacemi	12345678	  Has a Saving + Current Account with history
skypay	      12345678  	Has a Current Account (for transfers)

➡️ You can create more accounts from the app directly.

🧭 Features Walkthrough




🏠 Main Menu

![image](https://github.com/user-attachments/assets/30d42f32-285e-4262-b198-4a6b5925076e)

📝 Sign In + Logged In Menu

![image](https://github.com/user-attachments/assets/3ffdfd0b-2064-4bed-8a70-d239357e5657)

📄 Print Statement (Operations)

![image](https://github.com/user-attachments/assets/e2d756fe-b266-4469-8034-becf58910687)




🧔 Author
Ayman KACEMI
Full-stack & Mobile Developer
📍 Casablanca, Morocco

📫 Contact
For any questions or feedback, feel free to reach out via [My LinkedIn](https://www.linkedin.com/in/ayman-kacemi).
