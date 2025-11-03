## 🧩 Example Project: `UserController`

### 1️⃣ Model: `User.java`

```java
package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
}
```

---

### 2️⃣ Repository: `UserRepository.java`

```java
package com.example.demo.repository;

import com.example.demo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
```

---

### 3️⃣ Custom Exceptions

```java
package com.example.demo.exception;

// Thrown when user not found
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id " + id);
    }
}

// For validation or bad input
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
```

---

### 4️⃣ Global Exception Handler

```java
package com.example.demo.handler;

import com.example.demo.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex, WebRequest req) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex, WebRequest req) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralError(Exception ex, WebRequest req) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", req);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, WebRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", req.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(body, status);
    }
}
```

---

### 5️⃣ Service Layer: `UserService.java`

```java
package com.example.demo.service;

import com.example.demo.User;
import com.example.demo.exception.*;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("Email cannot be blank");
        }
        return repo.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User existing = getUserById(id);
        existing.setName(userDetails.getName());
        existing.setEmail(userDetails.getEmail());
        return repo.save(existing);
    }

    public void deleteUser(Long id) {
        User existing = getUserById(id);
        repo.delete(existing);
    }
}
```

---

### 6️⃣ Controller: `UserController.java`

```java
package com.example.demo.controller;

import com.example.demo.User;
import com.example.demo.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // CREATE (201 Created)
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User saved = service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL (200 OK)
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // READ BY ID (200 OK / 404 NOT FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    // UPDATE (200 OK)
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(service.updateUser(id, user));
    }

    // DELETE (204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## ⚙️ Summary of HTTP Response Codes

| Operation    | Method | Success Code       | Error Codes (Examples)                     |
| ------------ | ------ | ------------------ | ------------------------------------------ |
| Create       | POST   | **201 Created**    | 400 Bad Request, 500 Internal Server Error |
| Read (All)   | GET    | **200 OK**         | 500 Internal Server Error                  |
| Read (By ID) | GET    | **200 OK**         | 404 Not Found                              |
| Update       | PUT    | **200 OK**         | 400 Bad Request, 404 Not Found             |
| Delete       | DELETE | **204 No Content** | 404 Not Found, 500 Internal Server Error   |

---

Would you like me to show a **sample JSON response** for success and error (like for 404 or 400)?
That will make it easier to visualize how clients see the responses.
