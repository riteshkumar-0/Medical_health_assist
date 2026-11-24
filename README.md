# 🏥 Medical Information Assistant

> **AI-Powered Health Product Analysis & Medical Document Intelligence**  
> Built with **Spring Boot 3.4** · **Spring AI 1.0.0-M5** · **Groq AI (Llama 3.3 70B)** · **MySQL** · **Angular 17** · **Bootstrap 5**

## 🌐 Live Demo

| | Link |
|---|---|
| 🖥️ **Frontend** | [https://medical-health-assist.vercel.app](https://medical-health-assist.vercel.app) |
| ⚙️ **Backend API** | [https://medical-assistant-api-jqvw.onrender.com](https://medical-assistant-api-jqvw.onrender.com) |
| 📦 **GitHub Repo** | [https://github.com/riteshkumar-0/Medical_health_assist](https://github.com/riteshkumar-0/Medical_health_assist) |

> ⚠️ **Note:** The backend is hosted on Render's free tier and may take ~30-50 seconds to wake up on the first request.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Learning Objectives](#-learning-objectives)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Core Features](#-core-features-4-required)
- [Product Dataset](#-product-dataset)
- [Prompt Library](#-prompt-library-promptsyml)
- [REST API Endpoints](#-rest-api-endpoints)
- [Frontend Routes](#-frontend-routes)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Setup & Run Instructions](#-setup--run-instructions)
- [Environment Variables](#-environment-variables)
- [Screenshots](#-screenshots)

---

## 📖 Project Overview

The **Medical Information Assistant** is a full-stack application that leverages **Spring AI** to showcase core AI capabilities including **prompt engineering**, **structured output**, and **PDF document processing**. It provides an interactive dashboard for analyzing health monitoring products, comparing them side-by-side, summarizing medical documents, and generating AI-driven health insights — all powered by **Groq AI (Llama 3.3 70B)**.

---

## 🎓 Learning Objectives

By completing this project, proficiency is demonstrated in:

| # | Skill | Description |
|---|-------|-------------|
| 1 | **Prompt Engineering** | Crafting effective prompts with clear instructions for LLM interaction |
| 2 | **Prompt Templates** | Implementing placeholders (`{product_name}`, `{features}`, `{category}`) and dynamic parameter injection at runtime |
| 3 | **Prompt Library** | Organizing 9 reusable templates in a centralized YAML repository (`prompts.yml`) |
| 4 | **Structured Output** | Converting AI responses into Java DTOs for consistent, typed API responses |
| 5 | **PDF Summarization** | Extracting and summarizing medical documents using `PagePdfDocumentReader` |

---

## 🏗️ Architecture

```
┌─────────────────────┐         ┌──────────────────────────────────────┐
│   Angular 17 SPA    │  HTTP   │       Spring Boot 3.4 Backend        │
│   (Port 4200)       │◄──────►│       (Port 8080)                    │
│                     │  REST   │                                      │
│  ┌───────────────┐  │         │  ┌────────────┐   ┌──────────────┐  │
│  │  Components   │  │         │  │ Controllers│──►│   Services   │  │
│  │  - Dashboard  │  │         │  │ (5 REST)   │   │ (6 services) │  │
│  │  - Analysis   │  │         │  └────────────┘   └──────┬───────┘  │
│  │  - Comparison │  │         │                          │          │
│  │  - PDF Reader │  │         │  ┌────────────┐   ┌──────▼───────┐  │
│  │  - Insights   │  │         │  │PromptLib   │──►│ Azure OpenAI │  │
│  └───────────────┘  │         │  │(prompts.yml│   │ (GPT-5-mini) │  │
│                     │         │  │ 9 templates│   └──────────────┘  │
│  ┌───────────────┐  │         │  └────────────┘                     │
│  │  ApiService   │  │         │                   ┌──────────────┐  │
│  │  (HttpClient) │  │         │  ┌────────────┐   │    MySQL     │  │
│  └───────────────┘  │         │  │    JPA     │──►│ (Product DB) │  │
│                     │         │  └────────────┘   └──────────────┘  │
└─────────────────────┘         └──────────────────────────────────────┘
```

---

## 📂 Project Structure

```
Medical Information Assistant/
│
├── README.md
│
├── backend/                                    # Spring Boot + Spring AI Backend
│   ├── pom.xml                                 # Maven dependencies & build config
│   └── src/main/
│       ├── java/com/medical/
│       │   ├── MedicalInfoAssistantApplication.java    # @SpringBootApplication entry point
│       │   │
│       │   ├── config/
│       │   │   ├── CorsConfig.java             # CORS filter (allows localhost:4200)
│       │   │   ├── PromptConfig.java           # Loads prompts.yml into Spring bean
│       │   │   └── DataInitializer.java        # Seeds 6 sample products on startup
│       │   │
│       │   ├── entity/
│       │   │   └── Product.java                # JPA Entity (products table)
│       │   │
│       │   ├── repository/
│       │   │   └── ProductRepository.java      # Spring Data JPA repository
│       │   │
│       │   ├── dto/
│       │   │   ├── ProductDto.java             # Product data transfer object
│       │   │   ├── ProductAnalysisResponse.java        # Feature 1 response
│       │   │   ├── ProductComparisonRequest.java       # Feature 2 request
│       │   │   ├── ProductComparisonResponse.java      # Feature 2 response
│       │   │   ├── DocumentSummaryResponse.java        # Feature 3 response
│       │   │   ├── HealthInsightsResponse.java         # Feature 4 response
│       │   │   └── WellnessRecommendationResponse.java # Feature 4 wellness
│       │   │
│       │   ├── service/
│       │   │   ├── ProductService.java         # Product CRUD operations
│       │   │   ├── PromptLibraryService.java   # Template loading & parameter injection
│       │   │   ├── ProductAnalysisService.java # Feature 1: AI product analysis
│       │   │   ├── ProductComparisonService.java # Feature 2: AI product comparison
│       │   │   ├── PdfSummarizationService.java  # Feature 3: PDF reading & summarization
│       │   │   └── DashboardService.java       # Feature 4: Unified health insights
│       │   │
│       │   ├── controller/
│       │   │   ├── ProductController.java      # /api/products (CRUD)
│       │   │   ├── AnalysisController.java     # /api/analysis (Feature 1)
│       │   │   ├── ComparisonController.java   # /api/comparison (Feature 2)
│       │   │   ├── DocumentController.java     # /api/documents (Feature 3)
│       │   │   └── DashboardController.java    # /api/dashboard (Feature 4)
│       │   │
│       │   └── exception/
│       │       └── GlobalExceptionHandler.java # Centralized error handling
│       │
│       └── resources/
│           ├── application.properties          # App config (Azure OpenAI, MySQL, etc.)
│           └── prompts.yml                     # Centralized Prompt Library (9 templates)
│
└── frontend/                                   # Angular 17 + Bootstrap 5 Frontend
    ├── angular.json                            # Angular CLI configuration
    ├── package.json                            # npm dependencies
    ├── tsconfig.json                           # TypeScript configuration
    ├── tsconfig.app.json
    └── src/
        ├── index.html                          # HTML entry point
        ├── main.ts                             # Angular bootstrap
        ├── styles.css                          # Global styles
        └── app/
            ├── app.module.ts                   # Root Angular module
            ├── app-routing.module.ts           # Route definitions
            ├── app.component.ts                # Root component with navbar + router-outlet
            │
            ├── models/
            │   └── models.ts                   # 7 TypeScript interfaces
            │
            ├── services/
            │   └── api.service.ts              # HttpClient service (11 API methods)
            │
            ├── pipes/
            │   └── format-markdown.pipe.ts     # Markdown-to-HTML rendering pipe
            │
            └── components/
                ├── navbar/                     # Navigation bar
                │   ├── navbar.component.ts / .html / .css
                ├── dashboard/                  # Home dashboard with stats & catalog
                │   ├── dashboard.component.ts / .html / .css
                ├── product-analysis/           # Feature 1 UI
                │   ├── product-analysis.component.ts / .html / .css
                ├── product-comparison/         # Feature 2 UI
                │   ├── product-comparison.component.ts / .html / .css
                ├── pdf-summarizer/             # Feature 3 UI
                │   ├── pdf-summarizer.component.ts / .html / .css
                └── health-insights/            # Feature 4 UI
                    ├── health-insights.component.ts / .html / .css
```

---

## 🎯 Core Features (4 Required)

### Feature 1: Product Analysis Using Prompt Templates

**Goal:** Create reusable prompt templates with dynamic parameter injection.

| Aspect | Details |
|--------|---------|
| **Templates** | `product-analysis`, `product-analysis-brief` (defined in `prompts.yml`) |
| **Placeholders** | `{product_name}`, `{product_id}`, `{category}`, `{description}`, `{features}` |
| **Service** | `ProductAnalysisService.java` |
| **Controller** | `AnalysisController.java` → `GET /api/analysis/{identifier}` |
| **Analysis Types** | Detailed (7-section report) and Brief (3-sentence summary) |

**Workflow:**
1. User selects a product by ID or Name in the Angular UI
2. Backend fetches product details from MySQL
3. Loads the `product-analysis` template from `prompts.yml`
4. Injects product data into placeholders dynamically
5. Sends resolved prompt to Groq AI (Llama 3.3 70B)
6. Returns structured `ProductAnalysisResponse` to the frontend

---

### Feature 2: Product Comparison Using Prompt Library

**Goal:** Build a centralized prompt library with multiple templates for varied comparison use cases.

| Aspect | Details |
|--------|---------|
| **Templates** | `product-comparison`, `product-comparison-quick`, `category-comparison` |
| **Modes** | Product vs Product (detailed/quick) · Category-wide ranking |
| **Service** | `ProductComparisonService.java` |
| **Controller** | `ComparisonController.java` → `POST /api/comparison`, `GET /api/comparison/category/{cat}` |

**Key Demonstrations:**
- Three different prompt patterns for varied comparison use cases
- Dynamic template selection based on user's comparison type
- Supports both pairwise comparison and category-wide ranking

---

### Feature 3: Medical Document Summarization (PDF Processing)

**Goal:** Extract and summarize PDF content using `PagePdfDocumentReader` and structured output.

| Aspect | Details |
|--------|---------|
| **Templates** | `document-summary`, `document-summary-brief` |
| **PDF Reader** | Spring AI `PagePdfDocumentReader` (reads page by page) |
| **Service** | `PdfSummarizationService.java` |
| **Controller** | `DocumentController.java` → `POST /api/documents/summarize` (multipart) |
| **Max File Size** | 10MB |

**Structured Summary Format:**
1. Title/Topic
2. Key Findings (3–5 points)
3. Methodology
4. Clinical Implications
5. Target Audience
6. Summary (150 words)
7. Keywords (5–8 medical terms)

---

### Feature 4: Integrated Health Insights Dashboard

**Goal:** Combine all prompt templates into a unified service for end-to-end functionality.

| Aspect | Details |
|--------|---------|
| **Templates** | `health-insights`, `wellness-recommendation` |
| **Service** | `DashboardService.java` |
| **Controller** | `DashboardController.java` → `GET /api/dashboard/summary`, `/insights`, `/wellness/{id}` |

**Key Demonstrations:**
- Integrates multiple templates from the prompt library
- Modular service layer with clear separation of concerns
- Three endpoints: summary stats, AI insights, personalized wellness tips
- Full product catalog analysis in a single AI call

---

## 📦 Product Dataset

6 health monitoring products across 5 categories, auto-seeded on startup via `DataInitializer.java`:

| Product ID | Product Name | Category | Key Features (Highlights) |
|-----------|-------------|----------|--------------------------|
| **HM-001** | VitalSign Pro Monitor | Vital Signs Monitoring | Real-time ECG, NIBP, SpO2, 72hr battery, FDA-cleared, HL7 FHIR |
| **HM-002** | GlucoTrack Continuous Monitor | Glucose Monitoring | CGM every 5min, 14-day sensor, no fingerstick, insulin pump compatible |
| **HM-003** | CardioRhythm Smart ECG | Cardiac Monitoring | 12-lead ECG, AI arrhythmia detection, AFib screening, 30-sec recording |
| **HM-004** | SleepWell Analytics Band | Sleep Monitoring | Sleep stages (REM/Deep/Light), apnea screening, snoring detection, smart alarm |
| **HM-005** | PulseOx Fingertip Scanner | Vital Signs Monitoring | SpO2 ±2% accuracy, perfusion index, plethysmograph waveform, OLED display |
| **HM-006** | NeuroFocus EEG Headband | Neurological Monitoring | 4-channel EEG, focus scoring, stress monitoring, neurofeedback, meditation |

---

## 📝 Prompt Library (`prompts.yml`)

Centralized YAML repository containing **9 reusable prompt templates**:

| Template Key | Feature | Purpose |
|-------------|---------|---------|
| `product-analysis` | Feature 1 | Detailed 7-section product analysis |
| `product-analysis-brief` | Feature 1 | Concise 3-sentence analysis |
| `product-comparison` | Feature 2 | Detailed side-by-side comparison |
| `product-comparison-quick` | Feature 2 | Quick 5-bullet comparison |
| `category-comparison` | Feature 2 | Rank all products in a category |
| `document-summary` | Feature 3 | Structured 7-section PDF summary |
| `document-summary-brief` | Feature 3 | Concise 100-word summary |
| `health-insights` | Feature 4 | Full catalog market insights |
| `wellness-recommendation` | Feature 4 | Personalized wellness tips |

---

## 🔌 REST API Endpoints

### Product APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{identifier}` | Get product by ID or Name |
| `POST` | `/api/products` | Create a new product |

### Feature 1: Product Analysis
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/analysis/{identifier}` | Detailed AI product analysis |
| `GET` | `/api/analysis/{identifier}/brief` | Brief AI product analysis |

### Feature 2: Product Comparison
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/comparison` | Compare two products (body: `productIdA`, `productIdB`, `comparisonType`) |
| `GET` | `/api/comparison/category/{category}` | Compare all products in a category |

### Feature 3: PDF Summarization
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/documents/summarize` | Detailed PDF summary (multipart file upload) |
| `POST` | `/api/documents/summarize/brief` | Brief PDF summary (multipart file upload) |

### Feature 4: Dashboard & Insights
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/dashboard/summary` | Product stats, categories, distribution |
| `GET` | `/api/dashboard/insights` | AI-generated health market insights |
| `GET` | `/api/dashboard/wellness/{identifier}` | Personalized wellness recommendations |

---

## 🗺️ Frontend Routes

| Route | Component | Description |
|-------|-----------|-------------|
| `/dashboard` | `DashboardComponent` | Home page with stats, quick actions, product catalog |
| `/analysis` | `ProductAnalysisComponent` | Feature 1: Select product → AI analysis |
| `/comparison` | `ProductComparisonComponent` | Feature 2: Compare products or categories |
| `/pdf-summarizer` | `PdfSummarizerComponent` | Feature 3: Upload PDF → AI summary |
| `/health-insights` | `HealthInsightsComponent` | Feature 4: Market insights & wellness tips |

---

## 🛠️ Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend Framework** | Spring Boot | 3.4.1 |
| **AI Framework** | Spring AI (OpenAI Starter) | 1.0.0-M5 |
| **AI Model** | Groq AI — Llama 3.3 70B Versatile | Latest |
| **PDF Processing** | Spring AI `PagePdfDocumentReader` | 1.0.0-M5 |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA / Hibernate | 6.x |
| **Java** | JDK | 21 |
| **Build Tool** | Apache Maven | 3.8+ |
| **Frontend Framework** | Angular | 17.3 |
| **CSS Framework** | Bootstrap + Bootstrap Icons | 5.3.3 |
| **HTTP Client** | Angular HttpClient + RxJS | 7.8 |
| **Code Utilities** | Lombok | Latest |
| **Deployment** | Vercel (Frontend) · Render (Backend) · Aiven (MySQL) | Free Tier |

---

## ✅ Prerequisites

Before running the application, ensure you have:

- [x] **Java 21+** installed (`java -version`)
- [x] **Maven 3.8+** installed (`mvn -version`)
- [x] **Node.js 18+** and **npm** installed (`node -v && npm -v`)
- [x] **Angular CLI** installed globally (`npm install -g @angular/cli`)
- [x] **MySQL 8.0+** running on `localhost:3306`
- [x] **Groq API Key** (free at [console.groq.com](https://console.groq.com))

---

## 🚀 Setup & Run Instructions

### Step 1: Clone / Open the Project

Open the `Medical Information Assistant` folder in your IDE or terminal.

### Step 2: Set Environment Variables

```bash
# Linux / macOS
export GROQ_API_KEY=your-groq-api-key
export DB_PASSWORD=root@1234

# Windows (PowerShell)
$env:GROQ_API_KEY="your-groq-api-key"
$env:DB_PASSWORD="root@1234"
```

### Step 3: Setup MySQL Database

```sql
-- The database auto-creates via createDatabaseIfNotExist=true
-- Or create manually:
CREATE DATABASE medical_assistant_db;
```

> **Note:** Default credentials in `application.properties` are `root/root@1234`. Update as needed.

### Step 4: Run the Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

✅ Backend starts at: **http://localhost:8080**  
✅ 6 sample products are auto-seeded on first run.

### Step 5: Run the Frontend

```bash
cd frontend
npm install
ng serve
```

✅ Frontend starts at: **http://localhost:4200**

### Step 6: Open the Application

Navigate to **http://localhost:4200** in your browser.

---

## 🔐 Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `GROQ_API_KEY` | Groq API key (free at console.groq.com) | `gsk_abc123...` |
| `DB_PASSWORD` | MySQL database password | `root@1234` |
| `DATABASE_URL` | Production MySQL JDBC URL (Render only) | `jdbc:mysql://host:port/db` |
| `DATABASE_USERNAME` | Production MySQL username (Render only) | `avnadmin` |
| `DATABASE_PASSWORD` | Production MySQL password (Render only) | `...` |
| `CORS_ORIGIN` | Allowed frontend origin (Render only) | `https://your-app.vercel.app` |

---

## 📸 Screenshots

| Page | Description |
|------|-------------|
| **Dashboard** | Stats cards, quick actions, product catalog grid with category badges |
| **Product Analysis** | Select product → choose detailed/brief → AI-generated analysis |
| **Product Comparison** | Compare 2 products or all in a category → side-by-side AI output |
| **PDF Summarizer** | Drag-and-drop PDF upload → structured AI summary |
| **Health Insights** | Generate market insights + personalized wellness recommendations |

---

## 👨‍💻 Author

**Ritesh Kumar**

---

*Built with ❤️ using Spring AI and Groq AI*
