# 📘 Live Show Booking System – Take-Home Assignment

This is an in-memory, console-based implementation of a **Live Show Booking System**, designed as part of a Low-Level Design (LLD) assignment.  
It allows organizers to create shows and time slots, and users to book or cancel tickets, all within well-defined system constraints.

---

## ✅ Functional Requirements Covered

- **Register Live Shows** with a unique name and genre (`Comedy`, `Singing`, `Theatre`, etc.)
- **Declare time slots** (1-hour fixed slots from 9 AM to 9 PM) with capacities
- **Search shows by genre**, ranked by default using show start time
- **Book tickets** with constraints:
  - No overlapping bookings per user
  - Each booking can cover multiple persons
  - No partial bookings allowed
- **Cancel bookings**, making the slot available
- **Waitlist management**:
  - If a show is full, users can be added to a waitlist
  - Upon cancellation, the first waitlisted user is promoted
- **View bookings** and their statuses (`BOOKED`, `CANCELLED`, `WAITLISTED`)
- Bonus: Booking ID generator and waitlist promotion logic

---

## 🧱 Project Structure

```
com.aditya/
├── constant/       # Enums (BookingStatus, Genre)
├── model/          # Core models (Booking, Show, Slot)
├── pojo/           # Value types like TimeSlot
├── repository/     # In-memory data storage
├── service/        # Business logic
├── utils/          # Utilities like BookingIdGenerator
├── driver/         # Driver.java (main simulation)
└── BookMyShowApplication.java
```

---

## ⚙️ How to Run

> This is a console simulation (no REST or database). Everything runs from `Driver.java`.

### Steps:

1. Clone the repo / extract ZIP
2. Open in Spring Tool Suite (STS), IntelliJ, or Eclipse
3. Ensure Java 17+ is configured
4. Run:
   - `BookMyShowApplication.java` to boot
   - `Driver.java` to simulate the full flow

---

## 🎮 Sample Booking Flow

- `registerShow("TMKOC", Genre.COMEDY)`
- `onboardShowSlots("TMKOC", {9AM-10AM: 3, 12PM-1PM: 2})`
- `bookTicket("UserA", "TMKOC", 12PM, 2)` → BOOKED
- `cancelBooking(1000)` → CANCELLED
- `bookTicket("UserB", "TMKOC", 12PM, 1)` → BOOKED
- `bookTicket("UserC", "TMKOC", 12PM, 3)` → WAITLISTED
- `cancelBooking(1001)` → UserC promoted to BOOKED

---

## 📌 Design Considerations

- Code follows **SOLID principles** and is modular for extensibility
- **Strategy pattern** used for ranking shows by time (pluggable for future)
- All data is managed using **in-memory maps**, no external persistence
- No UI / REST — focus is on backend design and testability

---

## 📝 Notes

- No external libraries used beyond Java SDK
- No framework dependency enforced — Spring Boot structure used only for organization
- Input/output formatting kept clean for clarity during evaluation

---

