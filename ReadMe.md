---

# 🎓 DegreeFlow — Academic Tracking System

A full-stack protoype academic tracking platform that brings together **degree auditing**, **course management**, and **professor insights** into one seamless experience.

DegreeFlow combines the functionality of DegreeWorks, Brightspace, and RateMyProfessor into a single modern application—helping students track progress, plan semesters, and make smarter academic decisions.

---

## 🚀 Project Overview

Students often struggle with:

* Fragmented academic systems
* Inaccurate or delayed credit tracking
* Lack of effective course planning tools

**DegreeFlow solves this** by providing a centralized, intuitive platform for managing the entire academic journey.

---

## 📁 Project Structure

```
BC-Hackathon-2026/
├── degworks-and-bs-frontend/    # Next.js Frontend (alternative)
├── degworks-and-bs-backend/     # Spring Boot Backend
├── Middle/                      # Middleware Service
├── src/                         # Main Frontend (Vite + React)
├── components/                  # Shared UI Components
└── public/                      # Static Assets
```

---

## 🛠️ Tech Stack

### Frontend (Vite + React)

* React 19 + Vite 8
* Tailwind CSS 4
* Radix UI + shadcn/ui
* Recharts (data visualization)
* React Router DOM 7
* React Hook Form + Zod

### Backend (Spring Boot)

* Spring Boot 3.4.1
* Java 17
* Spring Data JPA + Hibernate
* Spring Security
* H2 In-Memory Database

---

## ⚙️ Prerequisites

Make sure you have installed:

* Node.js ≥ 18
* pnpm
* Java 17+
* Maven 3.8+

---

## 🧑‍💻 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/BC-Hackathon-2026.git
cd BC-Hackathon-2026
```

---

### 2. Run Frontend (React + Vite)

```bash
pnpm install
pnpm dev
```

Frontend runs at:
👉 [http://localhost:5173](http://localhost:5173)

---

### 3. Run Backend (Spring Boot)

```bash
cd degworks-and-bs-backend
./mvnw spring-boot:run
```

Backend runs at:
👉 [http://localhost:8080](http://localhost:8080)

---

### 4. H2 Database Console

Access:

* URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* JDBC URL: `jdbc:h2:mem:test`
* Username: `sa`
* Password: *(leave empty)*

---

## ✨ Features

### 📊 Student Dashboard

* GPA tracking (cumulative, major, semester)
* Credit progress visualization
* Academic standing indicators

### 📚 Course Management

* Real-time course tracking
* Degree requirement completion status
* Categorized courses (core, major, electives)

### ⭐ Professor Ratings

* Ratings and difficulty scores
* Student reviews and feedback
* Tags (e.g., "Tough grader", "Clear lectures")

### 🗓️ Semester Planning

* Multi-semester planning view
* Course status (confirmed, tentative, waitlist)
* Credit load tracking

---

## 🔌 API Endpoints

### Students

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| GET    | `/api/students`      | Get all students  |
| GET    | `/api/students/{id}` | Get student by ID |
| POST   | `/api/students`      | Create student    |

### Courses

| Method | Endpoint            | Description      |
| ------ | ------------------- | ---------------- |
| GET    | `/api/courses`      | Get all courses  |
| GET    | `/api/courses/{id}` | Get course by ID |

### Professors

| Method | Endpoint               | Description         |
| ------ | ---------------------- | ------------------- |
| GET    | `/api/professors`      | Get all professors  |
| GET    | `/api/professors/{id}` | Get professor by ID |

### Enrollments

| Method | Endpoint           | Description     |
| ------ | ------------------ | --------------- |
| GET    | `/api/enrollments` | Get enrollments |
| POST   | `/api/enrollments` | Add enrollment  |

### Schedules

| Method | Endpoint         | Description     |
| ------ | ---------------- | --------------- |
| GET    | `/api/schedules` | Get schedules   |
| POST   | `/api/schedules` | Create schedule |

### Requirements

| Method | Endpoint            | Description      |
| ------ | ------------------- | ---------------- |
| GET    | `/api/requirements` | Get requirements |

### Professor Reviews

| Method | Endpoint                 | Description |
| ------ | ------------------------ | ----------- |
| GET    | `/api/professor-reviews` | Get reviews |
| POST   | `/api/professor-reviews` | Add review  |

---

## 🔐 Environment Variables

### Frontend

No configuration needed for local development.

### Backend (`application.properties`)

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.username=sa
spring.datasource.password=
```

---

## 📜 Scripts

### Frontend

```bash
pnpm dev        # Start dev server
pnpm build      # Build for production
pnpm preview    # Preview build
pnpm lint       # Lint code
```

### Backend

```bash
./mvnw spring-boot:run
./mvnw clean install
./mvnw test
```

---

## 🧩 Key Components

### Frontend

| Component          | Description           |
| ------------------ | --------------------- |
| StudentHeader      | Displays student info |
| GPACard            | GPA visualization     |
| CreditsProgress    | Credit tracking       |
| RequirementsList   | Degree audit          |
| BrightspaceCourses | Current courses       |
| ProfessorRatings   | Ratings UI            |
| SemesterPlanner    | Future planning       |
| Navigation         | App navigation        |

### Backend Models

* Student
* Course
* Professor
* ProfessorReview
* StudentEnrollment
* Requirement
* Schedule

---

## 🚀 Deployment

### Frontend (Vercel)

```bash
npm i -g vercel
vercel
```

### Backend (Docker)

```dockerfile
FROM openjdk:17-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
docker build -t degflow-backend .
docker run -p 8080:8080 degflow-backend
```

---

## 🤝 Contributing

1. Fork the repo
2. Create a branch (`feature/your-feature`)
3. Commit changes
4. Push to branch
5. Open a Pull Request

---

## 📄 License

Created for **BC Hackathon 2026**.

---

## 👥 Team

Built with the goal of simplifying academic life and empowering students with better tools.

---
