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

### Dagger 2 - Conventions
#### Components
- Components are interfaces annotated with @Component
- Component contains list of modules
#### Modules and Providers
- Modules are classes annotated with @Module
- Methods in modules that provide services are annotated with @Provides
- Provided services can be used as method arguments in other provider mwthods
- Scopes are annotations, annotated with @Scope
#### Scoped
- Components that provide scoped service (scoped provider) must be scoped
- All clients get the same instance of a scoped service from the same instance of a component
#### Component as injector
- Basically, Components are injectors, they know how to provide (inject) the constructed objects from module into their clients
- Void methods with single argument defined on component class generates injectors for the type of that argumment
- Client's non private and non final properties annotated with @Inject designate injection targets
#### Dependent Component
- A component can be dependent into other components
- Component B that depends on Component A has implicit access to all services (@Provide methods) exposed by Component A
  - Services from Component A can be injected by Component B
  - Services from Component A can be consumed inside modules of Component B
- Services needs to be defined explicitly inside components
#### Sub component
- Subcomponent specified by @Subcomponent annotations
- Parent component exposes factory method which return subcomponent
- The argument of factory method are Subcomponent's module
- Subcomponent gets access to all services provided by parent
- Don't need to explicitly define the services in parent component
#### Multi Module
- Component can use more than one module
- Modules of a single component share the same object graph
- Dagger automatically instantiates modules with no argument constructors
- Modules having no argument constructors can only be used for multi module purpose
#### Automatic Discovery of Services (Instances)
- Dagger can automatically discover services having a public constructor annotated with @inject annotation
- Automatic discovered services can be scoped



