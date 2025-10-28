# AI Resume Parser

**AI Resume Parser** is a Java-based application designed to parse resumes and extract structured information such as personal details, education, work experience, and skills. This project leverages modern Java libraries and provides an easy-to-use solution for automating resume data extraction.

## Features

- Extract key information from resumes (PDF, DOCX).
- Support for parsing personal details, education, work experience, and skills.
- Modular and extendable design for future improvements.
- Easy integration with other Java applications.
- Swagger : http://localhost:8080/swagger-ui/index.html#
- Asymmetric Encryption

  
## Technologies Used

- **Java** - Core programming language.
- **Maven** - Dependency management and build tool.
- **Apache Tike** - For reading DOCX and PDF files.
- **DJL / Hugging Face** - AI/ML model support for smarter parsing.

## Getting Started

### Prerequisites

- Java JDK 21 or higher
- Maven 3.x
- Git

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Adhilkv/ai-resume-parser.git

---
### Asymmetric Encryption: Asymmetric encryption enhances security by using **two keys**: a **private key** to sign the token and a **public key** to verify it.
- ✅ The **private key** remains secure on the server and is never shared.
- ✅ The **public key** can be distributed to any service or client that needs to verify the token.
- ✅ Ideal for **microservices**, **3rd-party integrations**, and **stateless authentication**.


## 🔧 How to Generate RSA Key Pair

You can use `openssl` to generate the keys from the command line:

### 1. Generate a 2048-bit Private Key
```bash
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
```

### 2. Extract the Public Key from the Private Key
```bash
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

Now you have:
- `private_key.pem` — used to **sign** JWTs
- `public_key.pem` — used to **verify** JWTs

---
