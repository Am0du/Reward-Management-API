
## API Documentation

### Overview
The `reward_management.user.controller` package is responsible for handling user authentication requests, including user registration and login. It exposes RESTful API endpoints that allow users to sign up and log in to the reward management system. The package is part of a Spring Boot application and utilizes Swagger annotations for API documentation.

#### Endpoints

##### `POST /api/auth/signup`

**Description**: Registers a new user in the system.

- **Request Body**:
    - `SignUpDto`: DTO containing user registration details including email, first name, last name, and password.

- **Response**:
    - `ResponseEntity<AuthResponseDto<UserDto>>`:
        - **Status Code**: `201 Created`
        - **Body**: Contains the registered user's information along with an access token.

- **Possible Exceptions**:
    - `BadRequest`: Thrown if the user registration fails due to invalid input or other reasons.

**Example Request**:
```json
{
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "password": "securepassword123"
}
```

**Example Response**:
```json
{
  "statusCode": 201,
  "isSuccessful": true,
  "accessToken": "jwt-token",
  "data": {
    "userId": "1",
    "firstname": "John",
    "lastName": "Doe",
    "email": "user@example.com",
    "reward": {
      "currentBalance": 0.0,
      "totalCashback": 0.0
    }
  }
}
```

##### `POST /api/auth/login`

**Description**: Authenticates an existing user and returns a JWT token.

- **Request Body**:
    - `LoginDto`: DTO containing user login credentials including email and password.

- **Response**:
    - `ResponseEntity<AuthResponseDto<UserDto>>`:
        - **Status Code**: `200 OK`
        - **Body**: Contains the authenticated user's information along with an access token.

- **Possible Exceptions**:
    - `BadRequest`: Thrown if the login fails due to incorrect email or password.

**Example Request**:
```json
{
  "email": "user@example.com",
  "password": "securepassword123"
}
```

**Example Response**:
```json
{
  "statusCode": 200,
  "isSuccessful": true,
  "accessToken": "jwt-token",
  "data": {
    "userId": "1",
    "firstname": "John",
    "lastName": "Doe",
    "email": "user@example.com",
    "reward": {
      "currentBalance": 0.0,
      "totalCashback": 0.0
    }
  }
}
```