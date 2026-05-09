"# Banking Application API

A secure banking backend with JWT authentication, account management, and transactions.

## Features

- JWT Authentication & Authorization
- Account Management with auto-generated account numbers
- Transaction Management (Deposit, Withdraw, Transfer)
- User Registration & Login
- Input Validation & Error Handling
- Swagger API Documentation
- Transaction Limits & Audit Trail

## Tech Stack

- Java 21, Spring Boot 3+, Spring Security 6
- JWT, MySQL, JPA/Hibernate, Maven
- Swagger/OpenAPI, ModelMapper, Lombok

## API Endpoints

### Auth
- `POST /api/auth/register` - Register user
- `POST /api/auth/login` - Login user

### Accounts
- `POST /api/accounts` - Create account
- `GET /api/accounts` - Get all accounts
- `GET /api/accounts/{id}` - Get account by ID
- `PUT /api/accounts/{id}/deposit` - Deposit money
- `PUT /api/accounts/{id}/withdraw` - Withdraw money

### Transactions
- `POST /api/transactions/transfer` - Transfer between accounts
- `GET /api/transactions/account/{accountId}` - Get account transactions

## Setup

1. **Database**: Create MySQL database `banking_db`
2. **Config**: Update `application.properties` with DB credentials
3. **Run**: `mvn spring-boot:run`
4. **API Docs**: Visit `http://localhost:8080/swagger-ui.html`

## Sample Usage

### Register User
```bash
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Login
```bash
POST /api/auth/login
{
  "username": "john_doe",
  "password": "password123"
}
```

### Create Account
```bash
POST /api/accounts
Authorization: Bearer <jwt-token>
{
  "accountHolderName": "John Doe",
  "accountType": "SAVINGS",
  "userId": 1,
  "branchId": 1
}
```

### Transfer Money
```bash
POST /api/transactions/transfer
Authorization: Bearer <jwt-token>
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 500.00,
  "description": "Transfer"
}
```

## Features

- **JWT Authentication**: Secure user authentication and authorization
- **Account Management**: Create, read, update accounts with auto-generated account numbers
- **Transaction Management**: Deposit, withdraw, transfer funds with transaction history
- **User Management**: User registration and login with role-based access
- **Branch Management**: Multi-branch banking support
- **Validation**: Comprehensive input validation with custom error handling
- **Swagger Documentation**: Interactive API documentation
- **Transaction Limits**: Daily and monthly withdrawal limits
- **Audit Trail**: Complete transaction logging

## Technologies Used

- Java 21
- Spring Boot 3+
- Spring Security 6
- JWT (JSON Web Tokens)
- MySQL Database
- JPA/Hibernate
- Maven
- Swagger/OpenAPI
- ModelMapper
- Lombok

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/check-username/{username}` - Check username availability
- `GET /api/auth/check-email/{email}` - Check email availability

### Account Management
- `POST /api/accounts` - Create new account
- `GET /api/accounts` - Get all accounts
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/number/{accountNumber}` - Get account by account number
- `GET /api/accounts/user/{userId}` - Get accounts by user ID
- `PUT /api/accounts/{id}` - Update account
- `PUT /api/accounts/{id}/deposit` - Deposit money
- `PUT /api/accounts/{id}/withdraw` - Withdraw money
- `DELETE /api/accounts/{id}` - Delete account

### Transaction Management
- `POST /api/transactions/deposit/{accountId}` - Deposit to account
- `POST /api/transactions/withdraw/{accountId}` - Withdraw from account
- `POST /api/transactions/transfer` - Transfer between accounts
- `GET /api/transactions/{id}` - Get transaction by ID
- `GET /api/transactions/account/{accountId}` - Get transactions by account
- `GET /api/transactions/user/{userId}` - Get transactions by user
- `GET /api/transactions/history/{accountId}` - Get transaction history

## Sample API Usage

### 1. User Registration
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}
```

### 2. User Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

Response:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "username": "john_doe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "ROLE_USER",
    "expiresAt": "2026-05-10T12:05:32"
  }
}
```

### 3. Create Account
```bash
POST /api/accounts
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "accountHolderName": "John Doe",
  "accountType": "SAVINGS",
  "userId": 1,
  "branchId": 1
}
```

### 4. Deposit Money
```bash
POST /api/transactions/deposit/1
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "amount": 1000.00,
  "description": "Initial deposit"
}
```

### 5. Transfer Money
```bash
POST /api/transactions/transfer
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 500.00,
  "description": "Transfer to savings"
}
```

## Database Schema

### Users Table
- id (Primary Key)
- username (Unique)
- email (Unique)
- password (Encrypted)
- first_name
- last_name
- role
- is_active
- created_at
- updated_at

### Accounts Table
- id (Primary Key)
- account_holder_name
- account_number (Unique, Auto-generated)
- account_type
- balance
- account_status
- user_id (Foreign Key)
- branch_id (Foreign Key)
- created_at
- updated_at

### Branches Table
- id (Primary Key)
- branch_name
- branch_code (Unique)
- address, city, state, country, pin_code
- latitude, longitude
- phone_number, email
- is_active

### Transactions Table
- id (Primary Key)
- transaction_type
- amount
- transaction_status
- description
- transaction_date
- reference_number
- from_account_id (Foreign Key)
- to_account_id (Foreign Key)

## Security Features

- JWT-based authentication
- Password encryption with BCrypt
- Role-based authorization
- Transaction limits (daily/monthly withdrawal limits)
- Account status validation
- Input validation and sanitization

## Running the Application

1. **Database Setup**:
   - Create MySQL database: `banking_db`
   - Update `application.properties` with your database credentials

2. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Access Swagger UI**:
   - URL: `http://localhost:8080/swagger-ui.html`
   - No authentication required for documentation access

4. **Default Credentials**:
   - Create users through registration endpoint
   - Admin role can be assigned manually in database

## Project Structure

```
src/main/java/com/bank/BankingApplication/
├── BankingApplication.java
├── Config/
│   ├── SecurityUtilityConfig.java
│   └── SwaggerConfig.java
├── Controller/
│   ├── AccountController.java
│   ├── AuthController.java
│   └── TransactionController.java
├── DTO/
│   ├── AccountDto.java
│   ├── AuthResponseDto.java
│   ├── LoginRequestDto.java
│   ├── RegisterRequestDto.java
│   └── TransactionDto.java
├── Entity/
│   ├── Account.java
│   ├── Branch.java
│   ├── Transaction.java
│   └── User.java
├── Exception/
│   ├── AccountNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   ├── InsufficientBalanceException.java
│   └── UserNotFoundException.java
├── ModelMapper/
│   └── ProjectConfig.java
├── Repository/
│   ├── AccountRepository.java
│   ├── BranchRepository.java
│   ├── TransactionRepository.java
│   └── UserRepository.java
├── Service/
│   ├── AccountService.java
│   ├── AuthService.java
│   ├── EmailService.java
│   ├── PaymentService.java
│   └── TransactionService.java
├── Service/Impl/
│   ├── AccountServiceImpl.java
│   ├── AuthServiceImpl.java
│   └── TransactionServiceImpl.java
├── constants/
│   └── AppConstants.java
├── mapper/
│   ├── AccountMapper.java
│   └── TransactionMapper.java
├── response/
│   └── ApiResponse.java
├── security/
│   ├── CustomUserDetailsService.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── Role.java
├── util/
│   ├── AccountNumberGenerator.java
│   └── JwtUtil.java
└── resources/
    └── application.properties
```

## Error Handling

The application uses a centralized error handling mechanism with custom exceptions:

- `AccountNotFoundException`: When account is not found
- `UserNotFoundException`: When user is not found
- `InsufficientBalanceException`: When account has insufficient balance
- `UnauthorizedException`: When user is not authorized

All errors return standardized `ApiResponse` objects with appropriate HTTP status codes.

## Future Enhancements

- Email notifications for transactions
- Multi-currency support
- Account statements generation
- Admin dashboard
- Audit logging
- Rate limiting
- Two-factor authentication" 
