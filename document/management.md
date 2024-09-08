
## API Documentation

### 1. **Get Rewards**

**Endpoint:** `GET /api/rewards/balance/{customerId}`  
**Description:** Retrieves the current reward balance for the specified user.

**Request Parameters:**
- `customerId` (path): The ID of the customer whose reward balance is to be retrieved.

**Response:**
- **Status Code:** 200 OK
- **Content-Type:** application/json

**Response Body:**
```json
{
  "isSuccessful": true,
  "statusCode": 200,
  "data": {
    "totalCashback": 100.50,
    "currentBalance": 50.25
  }
}
```

**Response Fields:**
- `isSuccessful` (boolean): Indicates if the request was successful.
- `statusCode` (integer): HTTP status code.
- `data` (object): Contains reward balance details.
    - `totalCashback` (number): The total amount of cashback.
    - `currentBalance` (number): The current balance.

**Error Responses:**
- **404 Not Found**: If the reward balance for the user is not found.

---

### 2. **Get Cashback History**

**Endpoint:** `GET /api/rewards/history/{customerId}`  
**Description:** Retrieves the cashback history for the specified user.

**Request Parameters:**
- `customerId` (path): The ID of the customer whose cashback history is to be retrieved.
- `page` (query, optional): The page number for pagination (default is 0).
- `size` (query, optional): The number of records per page (default is 10).

**Response:**
- **Status Code:** 200 OK
- **Content-Type:** application/json

**Response Body:**
```json
{
  "isSuccessful": true,
  "statusCode": 200,
  "size": 10,
  "page": 0,
  "total": 5,
  "data": [
    {
      "transactionId": "uuid-1",
      "amount": 25.00,
      "description": "Cashback on purchase",
      "createdDate": "2024-09-08T12:00:00Z"
    },
    {
      "transactionId": "uuid-2",
      "amount": 15.00,
      "description": "Referral bonus",
      "createdDate": "2024-09-07T12:00:00Z"
    }
  ]
}
```

**Response Fields:**
- `isSuccessful` (boolean): Indicates if the request was successful.
- `statusCode` (integer): HTTP status code.
- `size` (integer): The number of records per page.
- `page` (integer): The current page number.
- `total` (integer): The total number of pages.
- `data` (array): List of cashback history entries.
    - `transactionId` (string): Unique identifier for the cashback transaction.
    - `amount` (number): The amount of cashback.
    - `description` (string): Description of the cashback transaction.
    - `createdDate` (string): Date and time when the cashback was created.

**Error Responses:**
- **404 Not Found**: If the cashback history for the user is not found.

---

### 3. **Add Cashback**

**Endpoint:** `POST /api/rewards/history`  
**Description:** Adds a new cashback entry for the currently authenticated user.

**Request Body:**
```json
{
  "amount": 20.00,
  "description": "New cashback reward"
}
```

**Request Fields:**
- `amount` (number): The amount of cashback to add.
- `description` (string): Description of the cashback.

**Response:**
- **Status Code:** 201 Created
- **Content-Type:** application/json

**Response Body:**
```json
{
  "isSuccessful": true,
  "statusCode": 201,
  "data": {
    "transactionId": "uuid-3",
    "amount": 20.00,
    "description": "New cashback reward",
    "createdDate": "2024-09-08T12:00:00Z"
  }
}
```

**Response Fields:**
- `isSuccessful` (boolean): Indicates if the request was successful.
- `statusCode` (integer): HTTP status code.
- `data` (object): Contains details of the added cashback entry.
    - `transactionId` (string): Unique identifier for the cashback transaction.
    - `amount` (number): The amount of cashback.
    - `description` (string): Description of the cashback transaction.
    - `createdDate` (string): Date and time when the cashback was created.

**Error Responses:**
- **403 Forbidden**: If the user is not authorized to perform this action.

---