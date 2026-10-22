
# Smart Community Backend

Spring Boot 3, Java 17, Spring Data JPA/Hibernate, PostgreSQL, Spring Security, JWT, Brevo transactional email and Google OAuth2. This is a student-friendly monolith. WebSocket delivery is **not** implemented; channel and conversation messages are REST persistence only.

## Run locally
1. Install JDK 17+, Maven 3.8+, and PostgreSQL.
2. Create a database: `CREATE DATABASE smart_community;`
3. Copy `.env.example` to `.env` and set real values. Spring Boot does not automatically load `.env`; export variables in your shell/IDE run configuration.
4. Set `DB_URL=jdbc:postgresql://localhost:5432/smart_community`, `DB_USERNAME`, `DB_PASSWORD`.
5. Set `JWT_SECRET` to a random secret with at least 32 bytes. Set Brevo API key and a sender address verified in Brevo. Google OAuth credentials are needed only for Google login.
6. From `backend/`, run `mvn spring-boot:run`.

Hibernate uses `spring.jpa.hibernate.ddl-auto=update`; no migration framework is included. Back up important data before schema changes.

## Google OAuth2
Register an OAuth client in Google Cloud Console. Set the authorized redirect URI to `http://localhost:8080/login/oauth2/code/google`. Configure `GOOGLE_CLIENT_ID` and `GOOGLE_CLIENT_SECRET`. After successful Google authentication the server redirects to `${FRONTEND_URL}/oauth/callback`. This initial scaffold does not place an application JWT in the redirect, so the frontend must be paired with a secure server-side handoff before using Google login as an API bearer-token flow. Do not add tokens to URL query parameters.

## Brevo
Create a Brevo account, verify the sender address, create an API key, and set `BREVO_API_KEY` and `BREVO_SENDER_EMAIL`. If these are missing, OTP delivery fails intentionally; the app does not pretend an email was sent.

## Main API routes
- `POST /api/auth/signup` — `{ "name":"Tarun", "email":"you@example.com", "password":"at-least-8-chars" }`
- `POST /api/auth/verify-otp` — `{ "email":"you@example.com", "otp":"123456" }`
- `POST /api/auth/resend-otp` — `{ "email":"you@example.com" }`
- `POST /api/auth/login` — `{ "email":"you@example.com", "password":"..." }`
- `GET /api/auth/me`, `GET /api/users/me`, `GET /api/dashboard` — Bearer JWT
- `/api/servers` — create/list/get/update/delete
- `/api/servers/{serverId}/members` — join/list members; `DELETE .../me` to leave
- `/api/servers/{serverId}/channels` — channel CRUD
- `/api/channels/{channelId}/messages` — send/list paginated/edit/delete
- `/api/conversations` — create/list/get; `/{id}/messages` send/list
- `/api/notifications` — list, `PATCH /{id}/read`, `PATCH /read-all`

## Important implementation notes
- JWT access tokens expire after 15 minutes. Refresh-token rotation is not included.
- OTPs are stored as BCrypt hashes, expire after five minutes, have a resend cooldown and five-attempt limit.
- Do not use production credentials in source control. `.env` is intentionally not committed.
- Review the OAuth2 callback flow and add a secure one-time code handoff for production.
- Authorization is enforced for server membership, channel access, message ownership, conversation participation, and notification ownership.
