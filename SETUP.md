# HANDOFF AI - Complete Setup Guide

## System Requirements

### Minimum Requirements
- **Java 17** or higher
- **Node.js 16+** and npm 7+
- **Git**
- **MySQL 8.0+** (optional for demo mode)

### Recommended
- Java 17 LTS or newer
- Node.js 18 LTS or newer
- npm 9+
- MySQL 8.0+ (for persistent storage)

## Project Structure

```
HANDOFF-AI/
├── frontend/                 # React + Vite application
│   ├── src/
│   │   ├── components/       # Reusable React components
│   │   ├── pages/           # Page-level components
│   │   ├── services/        # API client
│   │   ├── data/            # Demo data
│   │   ├── App.jsx          # Main app component
│   │   └── main.jsx         # Entry point
│   ├── index.html           # HTML template
│   ├── package.json         # NPM dependencies
│   ├── vite.config.js       # Vite config
│   └── .env.example         # Environment template
│
├── backend/                  # Spring Boot application
│   ├── src/main/java/com/handoffai/
│   │   ├── controller/      # REST API controllers
│   │   ├── service/         # Business logic
│   │   ├── repository/      # Data access layer
│   │   ├── model/           # JPA entities
│   │   ├── dto/             # Data transfer objects
│   │   ├── config/          # Spring configuration
│   │   └── HandoffAiApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml              # Maven configuration
│
├── README.md                # Main documentation
├── SETUP.md                 # This file
└── .gitignore               # Git ignore rules
```

## Installation & Setup

### Step 1: Clone Repository

```bash
git clone https://github.com/akshayakonakanchi-debug/HANDOFF-AI.git
cd HANDOFF-AI
```

### Step 2: Start Backend (Spring Boot)

#### Option A: Demo Mode (No Database Required)

```bash
cd backend

# Clean build
mvn clean install

# Run with demo profile (uses in-memory H2 database)
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=demo"
```

Backend will start at: **http://localhost:8080**

API is available at: **http://localhost:8080/api**

#### Option B: With MySQL Database

1. **Create MySQL Database:**

```sql
CREATE DATABASE handoffai;
USE handoffai;
```

2. **Update `backend/src/main/resources/application.properties`:**

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/handoffai
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

3. **Run Backend:**

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Step 3: Start Frontend (React + Vite)

```bash
cd frontend

# Install dependencies (one time)
npm install

# Start development server
npm run dev
```

Frontend will start at: **http://localhost:5173**

### Step 4: Open in Browser

Navigate to: **http://localhost:5173**

## Usage

### First Time Using HANDOFF AI

1. **Open the application** at `http://localhost:5173`
2. **Click "LOAD DEMO PROJECT"** to load the sample project
3. **Click "CREATE HANDOFF"** to analyze the project
4. **Review the results:**
   - Status Summary (Completed, In Progress, Pending, Blocked)
   - Work State (organized by status)
   - Dependencies (task relationships)
   - Risks (identified problems)
   - Next Actions (prioritized steps)
5. **Ask Questions** using the Q&A panel

### Demo Project

The MVP includes a realistic demo project:

**Project:** College Cultural Fest 2026

**What's Included:**
- ✅ 4 completed tasks
- ⏳ 2 in-progress tasks
- ⭕ 2 not-started tasks
- 🚫 1 blocked task
- Dependencies between tasks
- Identified risks and blockers
- Recommended next actions
- Q&A capabilities

## API Endpoints

### Projects

```
GET    /api/projects              # List all projects
GET    /api/projects/{id}         # Get project details
POST   /api/projects              # Create new project
PUT    /api/projects/{id}         # Update project
```

### Handoff Analysis

```
POST   /api/projects/{id}/handoff # Create handoff (analyzes project)
```

### Questions

```
POST   /api/projects/{id}/questions # Ask question about project
```

## Creating a Custom Project

### Via API (cURL)

```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -d '{
    "name": "My Project",
    "description": "Project description",
    "teamSize": 3,
    "evidenceText": "COMPLETED:\n- Task 1\n- Task 2\n\nIN PROGRESS:\n- Task 3"
  }'
```

Response:
```json
{
  "id": 2,
  "name": "My Project",
  "description": "Project description",
  "teamSize": 3,
  "evidenceText": "..."
}
```

### Create Handoff

```bash
curl -X POST http://localhost:8080/api/projects/2/handoff
```

### Ask Question

```bash
curl -X POST http://localhost:8080/api/projects/2/questions \
  -H "Content-Type: application/json" \
  -d '{"question": "What should I do first?"}'
```

## Troubleshooting

### Issue: Backend won't start

**Solution:**
- Ensure Java 17+ is installed: `java -version`
- Check port 8080 is available
- Review logs for errors

```bash
java -version  # Should show 17 or higher
```

### Issue: Frontend won't load

**Solution:**
- Ensure Node.js 16+ is installed: `node -v`
- Delete `node_modules` and reinstall:
  ```bash
  rm -rf node_modules package-lock.json
  npm install
  npm run dev
  ```
- Check port 5173 is available

### Issue: Backend and Frontend can't communicate

**Solution:**
- Ensure backend is running at `http://localhost:8080`
- Check CORS is enabled (it is by default)
- Check browser console for errors
- Verify `.env` file in frontend has: `VITE_API_URL=http://localhost:8080/api`

### Issue: "Cannot find module" errors

**Solution:**
```bash
# Frontend
cd frontend
rm -rf node_modules
npm cache clean --force
npm install

# Backend
cd backend
mvn clean install
```

### Issue: Maven build fails

**Solution:**
- Ensure Java 17+ is installed
- Clear Maven cache:
  ```bash
  mvn clean
  rm -rf ~/.m2/repository
  mvn install
  ```

## Development

### Frontend Development

```bash
cd frontend
npm run dev
```

Front-end has hot reload enabled. Changes to `.jsx` or `.css` files automatically refresh.

### Backend Development

```bash
cd backend
mvn spring-boot:run
```

Backend recompilation requires restart.

### Building for Production

**Frontend:**
```bash
cd frontend
npm run build
# Output in: frontend/dist/
```

**Backend:**
```bash
cd backend
mvn clean package
# Output in: backend/target/handoff-ai-backend-1.0.0.jar
```

## Environment Variables

### Frontend (.env)

```
VITE_API_URL=http://localhost:8080/api
VITE_DEMO_MODE=true
```

### Backend (application.properties)

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/handoffai
spring.datasource.username=root
spring.datasource.password=your_password
ai.api.key=your_ai_api_key
```

## AI API Integration (Future)

To integrate with OpenAI, Anthropic, or similar:

1. **Add API key to backend environment:**
   ```properties
   ai.api.key=sk-your-api-key
   ai.provider=openai
   ```

2. **Implement AIService** to call external API

3. **Keep key secure:**
   - Never commit keys to Git
   - Use environment variables or `.env.local`
   - Never expose keys in frontend code

## Database Schema

### Project Table
```sql
CREATE TABLE project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  team_size INT,
  evidence_text LONGTEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Task Table
```sql
CREATE TABLE task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_id BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  status VARCHAR(50),
  description TEXT,
  due_date TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (project_id) REFERENCES project(id)
);
```

Additional tables: `dependency`, `risk`, `next_action`, `evidence`

(Schema is auto-created by JPA with `spring.jpa.hibernate.ddl-auto=create-drop` or `update`)

## Common Tasks

### View Backend Logs

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.com.handoffai=DEBUG"
```

### View Frontend Logs

Open browser DevTools (F12) → Console tab

### Check if Ports are In Use

```bash
# Linux/Mac
lsof -i :8080    # Backend
lsof -i :5173    # Frontend

# Windows
netstat -ano | findstr :8080
netstat -ano | findstr :5173
```

### Kill Process on Port

```bash
# Linux/Mac
lsof -ti:8080 | xargs kill -9
lsof -ti:5173 | xargs kill -9

# Windows
taskkill /PID <PID> /F
```

## Testing the Complete Workflow

1. **Backend Running:**
   ```bash
   curl http://localhost:8080/api/projects
   # Should return: []
   ```

2. **Frontend Running:**
   ```
   Navigate to http://localhost:5173
   Should display HANDOFF AI interface
   ```

3. **Load Demo:**
   ```
   Click "LOAD DEMO PROJECT"
   Should display: College Cultural Fest 2026
   ```

4. **Create Handoff:**
   ```
   Click "CREATE HANDOFF"
   Should analyze project and show results
   ```

5. **View Results:**
   - ✅ Status summary displays
   - ✅ Work state shows tasks by status
   - ✅ Dependencies visualized
   - ✅ Risks identified
   - ✅ Next actions listed

6. **Ask Questions:**
   ```
   Type: "What should I do first?"
   Should return actionable answer
   ```

## Performance Tips

- **Backend:** Use demo mode for local testing (faster than MySQL)
- **Frontend:** Clear browser cache if CSS/JS changes don't apply
- **Development:** Use browser DevTools for frontend debugging
- **Logs:** Enable DEBUG logging only when needed

## Next Steps

1. ✅ Set up backend and frontend
2. ✅ Load demo project
3. ✅ Test handoff creation
4. ✅ Verify Q&A functionality
5. 📋 Create custom projects
6. 🔌 Integrate AI API (optional)
7. 🗄️ Set up persistent MySQL (optional)
8. 🚀 Deploy to production (optional)

## Support & Resources

- **GitHub Issues:** Report bugs and feature requests
- **Documentation:** See README.md for product overview
- **Code Examples:** Demo data in `frontend/src/data/demoData.js`
- **API Reference:** See controllers in `backend/src/main/java/com/handoffai/controller/`

## License

MIT License - Free for personal and commercial use.

---

**HANDOFF AI: Never start from zero again.** 🚀
