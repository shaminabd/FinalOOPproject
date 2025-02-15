# Hospital Management System

## Description
This project is a **Hospital Management System** built using **Java**. It allows hospital administrators to manage doctors, patients, and appointments efficiently. The system provides essential functionalities like adding doctors, managing patient records, scheduling appointments, and tracking medical history.

## Features
- **Doctor Management**: Add, update, and view doctors with specialization details.
- **Patient Management**: Maintain patient records and personal details.
- **Appointments**: Schedule and manage doctor appointments.
- **Database Integration**: Uses **PostgreSQL** for storing hospital-related data.

## Technologies Used
- **Java** (Object-Oriented Programming)
- **Swing (if GUI is present)** / **Java Console Application**
- **PostgreSQL** (Database Management)
- **IntelliJ IDEA** (Recommended IDE)

## Installation & Setup
### Prerequisites
Ensure you have the following installed:
- **Java JDK 11+**
- **PostgreSQL Database**
- **IntelliJ IDEA or any Java IDE**

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/hospital-system.git
   ```
2. Open the project in IntelliJ IDEA.
3. Configure the **PostgreSQL database connection**.
4. Run `Main.java` to start the application.

## Database Setup
- Create a PostgreSQL database.
- Update the database credentials in the configuration file.
- Run the necessary SQL scripts to initialize tables.

## Usage Examples
### Adding a Doctor
```java
Doctor doctor = new Doctor("Dr. Smith", "Cardiology");
hospital.addDoctor(doctor);
```

### Registering a Patient
```java
Patient patient = new Patient("John Doe", 30, "123456789");
hospital.addPatient(patient);
```

### Scheduling an Appointment
```java
Appointment appointment = new Appointment(doctor, patient, "2025-03-10 10:00 AM");
hospital.scheduleAppointment(appointment);
```

## Project Structure
```
projectOOP1-master/
├── .idea/                  # IntelliJ project files
├── src/                    # Java source code
│   ├── Main.java           # Entry point of the application
│   ├── MyApplication.java  # Main application logic
│   ├── models/             # Data models (Doctors, Patients, etc.)
│   ├── services/           # Business logic services
│   ├── database/           # Database connection handling
├── .gitignore              # Git ignore file
├── pom.xml (if using Maven)
└── README.md               # Project documentation
```

## Future Improvements
- Implement user authentication (Admin, Doctor, Patient roles).
- Enhance GUI using JavaFX or a web-based frontend.
- Improve error handling and logging.

## Author
- **[Your Name]** - Developer of the Hospital Management System

## License
This project is open-source under the MIT License.

