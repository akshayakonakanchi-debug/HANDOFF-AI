# Backend deployment

## Render settings

Create a Render Web Service from this repository, or use `render.yaml`:

- Root directory: `backend`
- Build command: `mvn -B clean package -DskipTests`
- Start command: `java -jar target/handoff-ai-backend-1.0.0.jar`
- Runtime: Java

Render sets `PORT` automatically; the application now uses `${PORT:8080}`.

Add this environment variable after deploying the frontend:

```text
APP_CORS_ALLOWED_ORIGINS=https://YOUR-FRONTEND.vercel.app
```

Test the backend at:

```text
https://YOUR-BACKEND.onrender.com/api/projects
```

Expected response before creating a project:

```json
[]
```

The deployed MVP uses an in-memory H2 database, so data resets when the service restarts. No MySQL configuration is required for the demo.
