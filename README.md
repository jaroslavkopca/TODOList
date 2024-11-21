
# TODO List Application

This is a simple TODO list application developed as part of an interview task for Orchitech. The application allows users to create, update, delete, and view TODO entries through a backend REST API and a frontend user interface.

## Project Overview

The TODO list application is split into two main components:
- **Backend**: A REST API built using **Spring Boot** and connected to a **PostgreSQL** database for persistent data storage. The backend provides endpoints to manage TODO entries (add, update, delete, and retrieve).
- **Frontend**: A **Vue.js** single-page application (SPA) that communicates with the backend API to display and manipulate TODO entries. The frontend includes filtering capabilities and supports managing TODO entries.

## Model

The core data model in this project is the `TODOEntry` entity. Each entry in the TODO list has the following properties:
- **title**: The description or name of the TODO task.
- **isCompleted**: A boolean value indicating whether the task is completed or not.
- **createdAt**: The timestamp of when the TODO entry was created.

## Backend

The backend is built using **Spring Boot** and exposes a REST API for managing TODO entries. The API supports:
- **GET** `/api/getAllTODOEntries`: Fetches all TODO entries.
- **GET** `/api/getTODOEntryById/{id}`: Fetches a TODO entry by ID.
- **POST** `/api/createTODOEntry`: Creates a new TODO entry.
- **PUT** `/api/updateTODOEntry`: Updates an existing TODO entry.
- **DELETE** `/api/deleteTODOEntry/{id}`: Deletes a TODO entry by ID.
- **GET** `/api/status/{isCompleted}`: Fetches TODO entries filtered by completion status.

The backend uses **PostgreSQL** as the database.

## Frontend

The frontend is a **Vue.js** application that interacts with the backend to manage TODO entries. Key features include:
- Adding, updating, and removing TODO entries.
- Filtering TODO entries based on their completion status (all, completed, not completed).
- Displaying a summary of remaining and completed tasks.

The frontend communicates with the backend using **fetch** API calls to retrieve and manage TODO entries.

## Tests

Unit tests are included for the backend API to ensure correct functionality. These tests use **MockMvc** to simulate HTTP requests and verify responses.

## Running the Application


### Steps

1. **Backend**:
   - The backend will be accessible at `http://localhost:8080`.

2. **Frontend**:
   - The frontend will be accessible at `http://localhost:5173`, this port is allowed for CORS.

