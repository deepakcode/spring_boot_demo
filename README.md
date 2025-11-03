## 🧩 CRUD example - Java 21 and spring boot 3.5.7 

This spring boot application cover below -
1. CRUD operation
2. Input validation
3. Exception handling
4. Database connection
5. Optimistic locking
6. Lombok

## ⚙️ Summary of HTTP Response Codes

| Operation    | Method | Success Code       | Error Codes (Examples)                     |
| ------------ | ------ | ------------------ | ------------------------------------------ |
| Create       | POST   | **201 Created**    | 400 Bad Request, 500 Internal Server Error |
| Read (All)   | GET    | **200 OK**         | 500 Internal Server Error                  |
| Read (By ID) | GET    | **200 OK**         | 404 Not Found                              |
| Update       | PUT    | **200 OK**         | 400 Bad Request, 404 Not Found             |
| Delete       | DELETE | **204 No Content** | 404 Not Found, 500 Internal Server Error   |
