# Iowa Community Center (ICC)

A full-stack nonprofit web platform built with Spring Boot 4, Thymeleaf, and Bootstrap 5. Designed to serve immigrant, refugee, and underserved communities through education, workforce development, and family support programs.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4.0.6 |
| Security | Spring Security 7 (session-based auth) |
| Database | H2 (file-based, dev) / PostgreSQL (prod) |
| ORM | Spring Data JPA / Hibernate 7 |
| Templates | Thymeleaf 3 |
| Frontend | Bootstrap 5, Vanilla JS, Chart.js |
| Export | OpenPDF, Apache POI (Excel) |

---

## Getting Started

### Prerequisites
- Java 17+
- Gradle (wrapper included)

### Run locally

```bash
./gradlew bootRun
```

Visit [http://localhost:8080](http://localhost:8080)

### Default admin credentials

| Email | Password | Role |
|---|---|---|
| admin@icc-iowa.org | Admin@2024! | Super Admin |
| finance@icc-iowa.org | Finance@2024! | Finance |
| comms@icc-iowa.org | Comms@2024! | Communications |

> **Change all passwords before any production deployment.**

---

## Pages

### Public
| Route | Page |
|---|---|
| `/` | Home — hero, programs, events, impact stats, testimonials, news |
| `/about` | Mission, vision, values, staff, board |
| `/programs` | All programs; `/programs/{slug}` for detail |
| `/events` | Upcoming events; `/events/{id}` for detail |
| `/team` | Staff and board of directors |
| `/news` | News articles; `/news/{slug}` for detail |
| `/contact` | General, volunteer, and partner inquiry forms |
| `/donate` | One-time and monthly donations with receipt system |
| `/volunteer` | Volunteer application form |
| `/login` | Staff/admin login |
| `/register` | New account registration |

### Admin (`/admin/**` — authentication required)
| Route | Description |
|---|---|
| `/admin/dashboard` | Overview stats, charts, recent activity |
| `/admin/programs` | CRUD for programs |
| `/admin/events` | CRUD + publish/unpublish events |
| `/admin/news` | CRUD + publish news posts |
| `/admin/team` | Manage staff and board members |
| `/admin/donations` | View donations, PDF/Excel export |
| `/admin/volunteers` | Review applications, log hours, update status |
| `/admin/contacts` | Read and reply to contact messages |
| `/admin/users` | User management (Super Admin only) |
| `/admin/impact` | Manage homepage impact statistics |

---

## Programs

- After-School Excellence Program
- Early Childhood Learning Center
- Workforce & Economic Empowerment
- Community Dialogue & Meetings
- Driving Support Program
- Summer Enrichment Camp

---

## User Roles

| Role | Access |
|---|---|
| `SUPER_ADMIN` | Full access including user management |
| `ADMIN` | Content, programs, events, volunteers, news |
| `FINANCE` | Donations, receipts, and export reports |
| `PROGRAM_MANAGER` | Programs and participants |
| `VOLUNTEER_COORDINATOR` | Volunteer applications and hours |
| `COMMUNICATIONS` | News posts and newsletters |

---

## Features

- **Dark / Light mode** toggle with localStorage persistence
- **Scroll animations** via Intersection Observer API
- **Animated counters** on impact statistics
- **Donation system** — one-time & monthly, preset amounts, printable receipts
- **Volunteer management** — applications, status tracking, hours logging
- **Export reports** — PDF (OpenPDF) and Excel (Apache POI)
- **Role-based access control** enforced at route and method level
- **Auto-seeded demo data** on first startup
- **Responsive design** — mobile, tablet, and desktop

---

## Switching to PostgreSQL

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/iccdb
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## Project Structure

```
src/main/java/com/example/icoach/
├── config/          # Spring Security configuration
├── controller/      # Web controllers (public + admin)
│   └── admin/
├── model/           # JPA entities
├── repository/      # Spring Data JPA repositories
├── security/        # UserDetailsService implementation
└── service/         # Business logic + export services

src/main/resources/
├── templates/       # Thymeleaf HTML templates
│   ├── fragments/   # Reusable header, footer, admin layout
│   └── admin/       # Admin dashboard pages
└── static/
    ├── css/main.css # Full design system
    └── js/main.js   # Interactions and animations
```

---

## License

This project is intended for nonprofit use by the Iowa Community Center.
