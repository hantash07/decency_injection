## Dependency Injection

### Dependency
- It means a class is dependent to another class.
- Suppose there are two classes A and B. Class A uses the class B. This means that class A is dependent to class B. We can also say that class B is dependency of class A. In this case class A is a client and class B is a service.
- Client and Service relation is contextual. It means class A can be a client and service at the same time base on context.

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

### Construction Injection
- Dependencies are provided to a class through its constructor.
- During the creation of an object, the dependencies are provided.
- Dependencies can be declared as val, ensuring they are immutable and must be provided at the time of object creation.
- Usage: Better for classes with clear and explicit dependencies, promoting immutability and testability.
  ```
  class Engine @Inject constructor() {
    fun start() {
        println("Engine started")
    }
  }

  class Car @Inject constructor(private val engine: Engine) {
    fun drive() {
        engine.start()
        println("Car is driving")
    }
  }
  ```
  #### Advantages
  - Easy to find issue in the dependencies. If there is any issue in the dependencies it is easy to find out because dependencies are provide during object creation.
  - Immutability: 
  - Easy to test. Since dependencies are explicitly passed through the constructor, it's easier to test the class by passing mock or stub implementations.

  #### Disadvantages
  - Classes with many dependencies become larger and difficult to manamge.
  - Don't work with predefine frameworks (e.g., Activity, Service or Fragment). If we can't control the constructor of an object and this is created by system, then constructor injection is not applicable.


### Field Injection
- Dependencies are directly provided into the fields of a class.
- When object is created then depencies are provided to that class.
- Late Initialization.
- Usage: Useful in situations where you don't control object creation, or where you want to add dependencies without modifying the constructor. However, it should be used with caution due to the potential downsides regarding testability and clarity.
  ```
  class Engine @Inject constructor() {
    fun start() {
        println("Engine started")
    }
  }

  class Car {
    @Inject
    lateinit var engine: Engine

    fun drive() {
        engine.start()
        println("Car is driving")
    }
  }
  ```
  #### Advantages
  - Flexibility: Dependencies can be injected without modifying the constructor, making it easier to add or remove dependencies.
  - Works with Frameworks: Useful in frameworks where objects are created by the system and you don't control the constructor (e.g., Android Activities or Fragments).

  #### Disadvantages
  - There is a risk of `NullPointerException`. If there is any issues in dependencies, these issues can be find out during run time, as dependencies are injected into fields after object creation.
  - Difficult to test as dependencies are injected after object creation. You may need a DI framework running to perform tests.

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
- We can't provide more than one provider methods with samme return type
#### Scoped
- Scopes are annotations, annotated with @Scope
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
#### Type Binding
- @Binds allow to map specific provided type to another provided type (e.g. provide implementation of an interface)
- Custom bindings using @Binds must be defined as abstract functions in abstract modules.
- Abstract @Binds function can't coexist with non static provider methods in the same module. Therefore non static provider methods must be define inside companion object.
#### Qualifiers
- Qualifier are annotation class annotated with @Qualifier
- As in Dagger, we can't provide more than one provider methods of the same type. With the help of qualifiers we can do it by differentiating the same providers by using different qualifier.
#### Provider
- Provider is used when you need to perform late injection


### Hilt - Conventions
- Standalone library that wraps around Dagger2
- 







