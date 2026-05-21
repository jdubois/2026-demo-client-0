# Copilot instructions

## Build, test, and lint

- Full backend verification: `./mvnw verify`
- Backend tests only: `./mvnw test`
- Single backend test class: `./mvnw -Dtest=TicketManagerApplicationTests test`
- Single backend test method: `./mvnw -Dtest=TicketManagerApplicationTests#seededTicketsAreAvailableFromTheApi test`
- Run the app locally: `./mvnw spring-boot:run`
- Frontend setup: `cd frontend && npm install`
- Frontend dev server: `cd frontend && npm run dev`
- Frontend production build: `cd frontend && npm run build`
- Frontend unit tests: `cd frontend && npm run test:unit -- run`
- Single frontend test file: `cd frontend && npx vitest run src/components/__tests__/HelloWorld.spec.js`
- Frontend lint: `cd frontend && npm run lint`
- Frontend format: `cd frontend && npm run format`

`./mvnw verify` also installs Node/npm and builds the Vue app through `frontend-maven-plugin` during `generate-resources`. Docker must be available for Testcontainers and for Spring Boot's development-time PostgreSQL service from `compose.yaml`.

## Architecture

- This is a Spring Boot 4 full-stack app with a Vue 3 frontend. Backend code lives under `src/main/java/com/example/ticketmanager`; frontend code lives under `frontend`.
- The domain is intentionally small: `Ticket` is a JPA entity with `title`, `repository`, `link`, and `status`; `TicketStatus` is the backend status enum.
- The REST API is `/api/tickets`. `TicketController` directly uses `TicketRepository`; there is no service layer unless future business logic becomes complex enough to justify one.
- `DataInitializer` seeds the database with the initial GitHub issues only when the ticket table is empty.
- The Vue app uses Pinia for ticket state in `frontend/src/stores/tickets.js`, calls the backend through `frontend/src/services/ticketApi.js`, and renders the main CRUD UI in `frontend/src/views/HomeView.vue`.
- Vite proxies `/api` to Spring Boot in development and builds production assets into `src/main/resources/static`, so the Spring Boot JAR serves the compiled frontend.
- Runtime configuration is property-based. `src/main/resources/application.properties` imports optional `.env` values and defaults to PostgreSQL; test properties disable Docker Compose and use `ddl-auto=create-drop`.

## Key conventions

- Keep backend validation in sync between `Ticket`, `TicketRequest`, and the frontend form constraints. GitHub issue links are expected to match `https://github.com/{owner}/{repo}/issues/{number}`.
- Keep ticket statuses synchronized between `TicketStatus.java` and the `statuses` array in `frontend/src/stores/tickets.js`; the frontend also owns display labels and Bootstrap badge classes.
- Preserve the current API response shape by returning `Ticket` objects directly from the controller unless you intentionally introduce DTOs across both backend and frontend.
- Use constructor injection in Spring components. Tests that need MVC should follow `TicketManagerApplicationTests`: `@SpringBootTest`, `@AutoConfigureMockMvc`, and `@Import(TestcontainersConfiguration.class)`.
- Do not read or print `.env`; use `.env.sample` for placeholders. Local ports and datasource settings are intended to be overridden through environment variables.
- Prefer the configured Java LSP (`.github/lsp.json`, JDTLS) for Java symbol navigation and refactoring.
- For Vue/Vitest config, `vite.config.js` exports a callback; keep `vitest.config.js` resolving it with `viteConfig({ mode: 'test', command: 'serve' })` before merging.
