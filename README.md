# 🚀 HANDOFF AI

**Never start from zero again.**

HANDOFF AI solves the problem of work and context getting lost when one person leaves a project, changes teams, goes on leave, graduates, or another person takes over the work.

## Problem

When someone leaves a project—whether due to graduation, job change, leave, or team restructuring—the incoming person faces:
- ❌ Lost context
- ❌ Unclear project status
- ❌ Hidden dependencies
- ❌ Unknown risks
- ❌ No clear next steps
- ❌ Hours wasted re-discovering information

## Solution

HANDOFF AI reconstructs the **current state of ongoing work** from available evidence and teaches the next person what they need to know.

### Key Features

✅ **Current Work State** - Completed, In Progress, Pending, Blocked tasks  
✅ **Dependency Mapping** - Visual understanding of task relationships  
✅ **Risk Identification** - Automatic detection of potential problems  
✅ **Evidence-Based Analysis** - Never invent status; use real project data  
✅ **Next Actions** - Prioritized recommended next steps  
✅ **AI Q&A** - Ask HANDOFF questions about project state  
✅ **Demo Mode** - Works immediately without external configuration  

## Target Users

### 1. **Students**
- College clubs and group projects
- Final-year projects
- College events and organizations
- Team member changes during semester

### 2. **Corporate Teams**
- Employee leave or resignation
- Team restructuring
- Project ownership changes
- Onboarding new team members

### 3. **Any Collaborative Work**
- Physical projects (photos, checklists)
- Non-code work (spreadsheets, documents)
- Mixed evidence types (notes, schedules, files)

## Architecture

```
┌─────────────────┐
│  React Frontend │
│   (Vite, JS)    │
└────────┬────────┘
         │ REST API
         ▼
┌─────────────────┐
│  Spring Boot    │
│  Backend (Java) │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌────────┐ ┌────────┐
│ MySQL  │ │ AI API │
│ (opt)  │ │(secure)│
└────────┘ └────────┘
```

## Quick Start

### Prerequisites
- **Node.js** 16+ and npm 7+
- **Java** 17+
- **MySQL** 8.0+ (optional; demo mode works without it)

### 1. Start Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend at: `http://localhost:5173`

### 2. Start Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=demo"
```
Backend at: `http://localhost:8080`

### 3. Open Browser
Navigate to `http://localhost:5173` and click **"LOAD DEMO PROJECT"**

## Technology Stack

- **Frontend**: React, Vite, JavaScript, CSS
- **Backend**: Java, Spring Boot, Spring Data JPA
- **Database**: MySQL (optional)
- **Version Control**: Git & GitHub

## Features

1. **Dashboard** - Project overview with status summary
2. **Work State** - Completed, In Progress, Pending, Blocked tasks
3. **Dependencies** - Visual mapping of task relationships
4. **Risks** - Identified problems and blockers
5. **Next Actions** - Prioritized recommended steps
6. **Evidence** - Source attribution for all findings
7. **Q&A** - Ask questions about project state
8. **Demo Mode** - Works without setup

## Demo Project

**College Cultural Fest 2026**
- Team: Event Lead, Sponsorship Lead, Technical Lead, Volunteer Lead
- Status: Multiple tasks in different states
- Includes dependencies, risks, and next actions

See `SETUP.md` for detailed installation and configuration instructions.

---

**HANDOFF AI: Never start from zero again.** 🚀
