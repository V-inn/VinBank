# VinBank
Fictional Bank Application written in JAVA

**IMPORTANT: THE API DOES NOT MAKE USE OF CORS AS ALL REQUESTS ARE EXPECTED FROM A NON-BROWSER CLIENT.**

## Current API functionalities include:
- JWT Token authentication by cookie;
- [Account creation;](#creating-an-account)
- [Account authentication](#authenticating);
- [Money deposit](#deposit);
- [Money withdrawal](#withdraw);
- [Money transfer between existing bank accounts](#transaction);
- [Balance check](#check-balance);

To use the API, most endpoints will be protected by authentication and login is required.
Attempt to access protected endpoints while not authenticated will always result in a **403 Http Status**

**The open endpoints include:**
- /users for POST requests;
- [/users/login](#authenticating);
- [/users/hello](#testing-connection);

*See uses below.*

## Api endpoints:
### Testing connection
Connection to the API can be tested through a **GET** request to the **/users/hello** endpoint.

Successful connection will return a **200 Http Status** along with a response body containing "Hello".

### Creating an account:
An account can be created through a **POST** request to the **/users** endpoint.

The parsed parameters must be:
``` java
String name; //Required
String cpf; //Required
String password; //Required
String phone; //Required
double balance; //Optional
```

Successful account creation will return a **201 Created Http Status**

### Authenticating:
Authentication is done through a **POST** request to the **/users/login** endpoint.

The parsed parameters must be:
``` java
//NAME OR CPF OR BOTH: ATLEAST ONE IS NECESSARY.
String name;
String cpf;

String password; //Required
```

Sucessful authentication will return a **200 OK Http Status** along with an **HttpOnly Cookie** labeled as **token** which contains the user token.

### Test if user is authenticated:
Successful user authentication can be tested through a **GET** request on the **/users/authenticated** endpoint.

As the endpoint is protected, it will return a **200 OK Http Status** if and only if the user is sucessfully authenticated and has a token being sent through a cookie.

### Check balance:
Balance check is done through a **GET** request to the **/bank/balance** endpoint.

Successful request will return a **200 OK Http Status** along with the user balance.

### Deposit:
This endpoint is used to deposit an amount (increase account balance) to the *Authenticated User* balance.
Deposit is done through a **POST** request to the **/bank/deposit** endpoint.

The parsed parameter is a positive number.

Successful deposit will return a **200 Http Status** along with the message "Deposit succesful" on the response body.
Parsing a negative number or a non numerical content will result in a **400 Bad Request Http Status** along with the corresponding error message on the response body.


### Withdraw:
This endpoint is used to withdraw an amount (decrease account balance) to the *Authenticated User* balance.
Withdraw is done through a **POST** request to the **/bank/withdraw** endpoint.

The parsed parameter is a positive number.

Successful withdraw will return a **200 Http Status** along with the message "Withdraw succesful" on the response body.
Attempt to withdraw a number greater than *available balance* will result in a **400 Bad Request Http Status** along with the message "Insufficient balance" on the response body.
Parsing a negative number or a non numerical content will result in a **400 Bad Request Http Status** along with the corresponding error message on the response body.

### Transaction:
This endpoint is used to transfer (decrease requester's balance and increase destinatary's balance the same ammount) an ammount between two different existing accounts on the database.
Transaction is done through a **POST** request to the **bank/transaction** endpoint.

The parsed parameters must be:
``` java
//NAME OR CPF OR BOTH: ATLEAST ONE IS NECESSARY.
String name;
String cpf;

double ammount; //Required
```

Successful transaction will return a **200 OK Http Status** along with the message "Transaction succesful" on the response body.
Attempt to transfer greater than *available balance* will result in a **400 Bad Request Http Status** along with the message "Insufficient balance" on the response body.
Parsing a negative number or a non numerical content will result in a **400 Bad Request Http Status** along with the corresponding error message on the response body.
Attempt to transfer to a non-existing account will result ib a **400 Bad Request Http Statys** along with the message "Invalid destinatary".
