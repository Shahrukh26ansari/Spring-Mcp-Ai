# QueryAI — Angular Frontend

Sleek dark-terminal UI for the QueryAI natural language database interface.
Type a plain-English question, get structured results from your MySQL database instantly.

---

## Stack

| Layer     | Tech            |
|-----------|-----------------|
| Framework | Angular 17      |
| Style     | CSS Variables   |
| Fonts     | Syne + Space Mono |
| HTTP      | Angular HttpClient |

---

## Pages

| Route    | Description                              |
|----------|------------------------------------------|
| `/`      | Landing page with hero, features, examples |
| `/query` | Main query interface with sidebar + table results |

---

## Project Structure

```
src/
├── app/
│   ├── app.component.ts        # Root component
│   ├── app.config.ts           # Providers (router, http, animations)
│   ├── app.routes.ts           # Route definitions
│   ├── models/
│   │   └── query.model.ts      # QueryRequest / QueryResponse interfaces
│   ├── services/
│   │   └── query.service.ts    # HTTP calls to Spring Boot API
│   └── pages/
│       ├── home/               # Landing page
│       └── query/              # Query interface
├── styles.css                  # Global design tokens + resets
└── index.html                  # Fonts loaded here
```

---

## Setup

### Prerequisites
- Node.js 18+
- Angular CLI: `npm install -g @angular/cli`

### Install & Run
```bash
npm install
ng serve
```
App runs at **http://localhost:4200**

> Make sure the Spring Boot backend is running on port 8080 first.

---

## API Connection

The frontend calls:
```
POST http://localhost:8080/api/query
{ "question": "..." }
```

To change the backend URL, edit:
```
src/app/services/query.service.ts  →  apiUrl
```

---

## Example Questions

- "Who are the top 5 customers by total spend?"
- "Show total revenue by product category"
- "List all pending orders with customer names"
- "How many orders were placed each month in 2024?"
- "What are the best-selling products?"
