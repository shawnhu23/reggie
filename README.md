# Reggie

## Description
Reggie is an application for restaurant including a management system and an mobile app. The management system is mainly for the restaurant to manage their dish category, menu, set meal, orders and employees. The mobile app is for the end user to browse menu, add dish to the shopping cart and place orders.

### Management System

The management system is for the employees of the restaurant.

| Module      | Description                                                         |
| --------- | ------------------------------------------------------------ |
| login/logout | Employees have to login before they can access the management system.|
| Employee Management  | Administrator can manage the employee info through the management system including query, add, edit or ban an employee account|
| Category Management  | Manage the dish category and setmeal category including query, add, edit and delete a category|
| Dish Management  | Manage the dish info including query, add, edit, delete, start selling or stop selling a dish|
| Setmeal Management  | Manage the setmeal info including query, add, edit, delete, start selling or stop selling a setmeal|

### Mobile App

The mobile app is for the end users.

| Module| Description|
| ----------- | ------------------------------------------------------------ |
| login/logout   | Users need to login to place an order.                    |
| Menu   | Displace dish and category info for the users to select from|
| Shopping Cart | Users can select dish or setmeal to add to the shopping cart|
| Order    | After selecting the dish or setmeal, user can place the order|
| User Info| User can view personal info and manage their address book in profile page|

## Tech Stack

### User Layer
The management system is built with HTML5, Vue.js and ElementUI.

### Gateway Layer
Nginx is used as the HTTP server for the static resource. It is also used for load balancing.

### Application Layer

SpringBoot： Quickly build Spring projects, adopt the idea of "convention over configuration", and simplify the configuration development of Spring projects.

Spring: Unified management of various resources (beans) in the project. Being used in all layers of web development.

SpringMVC：Springmvc and spring do not need to be integrated through an intermediate integration layer and can be seamlessly integrated.

SpringSession: It mainly solves the problem of session sharing in a cluster environment.

lombok：It can simplify the java code in the form of simple annotations and improve the development efficiency of developers.

Swagger： It can automatically help developers generate interface documentation and test the interface.

### Data Layer

MySQL： The core business data of this project will be stored in MySQL.

MybatisPlus： The persistence layer of this project will use MybatisPlus to simplify development.

Redis： Used for caching and performance optimizations.

### Tools

git: used to manage the code.

maven: project management

## User Role

There are three kinds of users: administrator, employee, customer.

| Role             | Authorization                                                    |
| ---------------- | ------------------------------------------------------------ |
| Administrator   | Have access to all operations               |
| Employee | manage the dish, setmeal, category and order|
| customer          | browse the menu, modify the shopping cart, modify the address book and place orders  |
