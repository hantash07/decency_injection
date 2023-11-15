## Dependency Injection

### Dependency
- It means a class is dependent to another class.
- Suppose there are two classes A and B. Class A uses the class B. This means that class A is dependent to class B. We can also say that class B is dependency of class A. In this case class A is a client and class B is a service.
- Client and Service relation is contextual. It means A class can be a client and service at the same time base on context.

### How Client obtain relation to services:
1. Instantiation
2. Static Method Call
3. Static Global Variable
4. Receive references from 'outside'

- The first 3 points are normal way to obtain relation to services. The last one comes from the concept of Dependency Injection.

### Dependency Injection = providing services to clients from outside.

### Three ways to "inject" services from "outside":
1. Construction Injection
2. Method Injection
3. Property Injection

- Method injection is commonly used in Java and it will not be used in Kotlin instead we can use property injection.

### Main Benefits:
1. Non repetitive definations of the entire object.
2. Code reusability

### Context Isolation:
- By following dependency injection, it prevents memory leaks of accidentlly passing activity context into global object.

### Distinction in Dependency Injection:
- An object that encapsulate data only does not need to be injected outside. Only an object which encapsulate behaviour needed to be injected outside.

### Dependency Injection Architecture Patterns deals with objects not data structure.



