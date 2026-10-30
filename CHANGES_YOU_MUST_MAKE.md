# RailGate — Setup Guide

## 1. Prerequisites
- Java 21
- Maven
- MySQL Server
- IntelliJ IDEA (recommended)
- Postman (for API testing)

## 2. Create the database

Open DBeaver or your MySQL client and run:

```sql
CREATE DATABASE railgate;
```

## 3. Configure environment variables

The application reads database credentials and the JWT secret from environment variables.

In PowerShell, set your own values:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/railgate"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "YOUR_MYSQL_PASSWORD"
$env:JWT_SECRET = "YOUR_BASE64_ENCODED_SECRET"
```

Use a securely generated, sufficiently long random JWT secret. Do not commit real credentials or secrets to GitHub.

## 4. Start the application

Run these commands from the directory containing `pom.xml`:

```powershell
mvn spring-boot:run
```

The application runs at:

`http://localhost:8080`

Hibernate is configured to create or update the database tables automatically.

## 5. Seed the database

If the sample data has not already been loaded, inspect `src/main/resources/data.sql` and run it against the `railgate` database using DBeaver.

The provided sample data includes crossings, trains, and an administrator account. Avoid running seed statements repeatedly if they would create duplicate records.

## 6. Test authentication

Use Postman:

**Login**
- Method: `POST`
- URL: `http://localhost:8080/api/auth/login`
- JSON body:

```json
{
  "email": "admin@railgate.in",
  "password": "Admin@123"
}
```

Use the returned access token as a Bearer token for protected endpoints. Keep tokens private. Change default credentials before using the application beyond local testing.

## 7. Useful API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/login` | Authenticate |
| GET | `/api/crossings` | List crossings |
| GET | `/api/trains` | List trains |
| GET | `/api/schedules/today` | View today's schedules |
| GET | `/api/users/me` | View the authenticated user's profile |
| GET | `/api/users/me/subscriptions` | View crossing subscriptions |
| POST | `/api/reports` | Submit a crossing status report |
| GET | `/api/reports/crossing/{crossingId}` | View unresolved reports for a crossing |

Protected endpoints require authentication. Some administrative and operational endpoints require specific roles.

## 8. WebSocket

The configured WebSocket endpoint is:

`ws://localhost:8080/ws`

Topic subscriptions and message behavior depend on the application's WebSocket configuration.

## 9. Security notes
- Never commit database passwords, JWT secrets, access tokens, or refresh tokens.
- Configure environment variables in your terminal or IDE run configuration.
- The sample administrator credentials are for local development only.
