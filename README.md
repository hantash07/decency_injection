## Dependency Injection

### Dependency
- It means a class is dependent to another class.
- Suppose there are two classes A and B. Class A uses the class B. This means that class A is dependent to class B. We can also say that class B is dependency of class A. In this case class A is a client and class B is a service.
- Class A is a client and class B is a service.
- Client and Service relation is contextual. It means class A can be a client and service at the same time base on context.

### How class obtain relation to another class
1. Instantiation
2. Static Method Call
3. Static Global Variable
4. Receive references from 'outside'

- The first 3 points are normal way to obtain relation to services. The last one comes from the concept of Dependency Injection.

### Dependency Injection = providing services to clients from outside.

### Three ways to "inject" or receive references from "outside":
1. Construction Injection
2. Method Injection
3. Property Injection

- Method injection is commonly used in Java and it will not be used in Kotlin instead we can use property injection.

### 1. Construction Injection
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
  - Easy to test. Since dependencies are explicitly passed through the constructor, it's easier to test the class by passing mock or stub implementations.

  #### Disadvantages
  - Classes with many dependencies become larger and difficult to manamge.
  - Don't work with predefine frameworks (e.g., Activity, Service or Fragment). If we can't control the constructor of a class, then constructor injection is not applicable.


### 2. Field Injection
- Dependencies are directly provided into the fields of a class.
- When object is created then depencies are provided to that class.
- Late Initialization.
- Usage: Useful in situations where you don't control object creation, or where you want to add dependencies without modifying the constructor.
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

#### 3. Method Injection
- Dependencies are provided to a class through its method.
- It allows to inject dependencies on a specific method
- Usage:
    - Dependencies is only required in a specific method.
    - It can be used with framework where the object is defined by a system same like Field Injection.
  ```
  class Engine @Inject constructor() {
    fun start() {
        println("Engine started")
    }
  }

  class Car {

    fun drive(engine: Engine) {
        engine.start()
        println("Car is driving")
    }
  }
  ```

  #### Advantages
  - Targeted Dependency Use: It provides a way to inject dependencies only where they are needed.
  - Flexibility: You can choose which methods should receive which dependencies, offering more control compared to field or constructor injection..

  #### Disadvantages
  - There is a risk of `NullPointerException`. If there is any issues in dependencies, these issues can be find out during run time, as dependencies are injected into fields after object creation.
  - Less Common: It's less common than constructor or field injection.
  - Complexity: Increases the complexity of dependency management, especially in larger projects where multiple methods require injection.

### Types of Binding in DI
- Binding tells dependency injection how to construct the object or where to get the dependencies.
- Following are the types:
  
  #### 1. @Provide Method Binding
  - This is the most common type of binding where you use a @Provides method inside a Dagger module to tell Dagger how to construct a particular type.
  - Use @Provides when the dependency cannot be created using a constructor or when you need to control the creation of the instance.

  #### 2. @Bind Method Binding
  - It is more efficient alternative to @Provides when binding an interface to an implementation, as it avoids the overhead of a method call and directly binds the type at compile-time.
  - This type is used if you want to create an instance from interface. The interface is converted into concrete implementation.
  - Use @Binds when you are simply binding an interface to a concrete implementation and the implementation can be provided by a constructor.
    ```
    @Module
    interface NetworkModule {

      @Binds
      fun bindApiService(apiServiceImpl: ApiServiceImpl): ApiService
    }
    ```
  - This tells Dagger that when ApiService is requested, it should provide an instance of ApiServiceImpl. The advantage of @Binds is that it’s more efficient since it does not require a method body.
  
  #### 3. @Inject Constructor Binding
  - This binding method uses @Inject annotations on the constructor of a class to automatically inform Dagger how to instantiate the class.
  - Use @Inject on constructors if you want dagger to automatically figure out how the dependency without using module.
  
  #### 4. Multibindings
  - Dagger provides support for multibindings, allowing you to inject collections (sets or maps) of objects rather than a single instance.
  - Use multibindings when you need to inject a collection of implementations, such as a set of plugins or a map of commands.
    ```
    @Module
    class PluginModule {

      @Provides
      @IntoSet
      fun provideLoggingPlugin(): Plugin {
          return LoggingPlugin()
      }

      @Provides
      @IntoSet
      fun provideAnalyticsPlugin(): Plugin {
          return AnalyticsPlugin()
      }
    }
    ```

### Main Benefits DI
1. Non repetitive definations or definations of the entire object.
2. Code reusability
3. Cleaner Codebase
4. Improve Testability

### Context Isolation
- By following dependency injection, it prevents memory leaks of accidentlly passing activity context into global object.

### Distinction in Dependency Injection:
- An object that encapsulate data only does not need to be injected outside. Only an object which encapsulate behaviour needed to be injected outside.

### Dependency Injection Architecture Patterns deals with objects not data structure.

### Dagger 2 - Conventions
#### Components
- Components are interfaces annotated with @Component
- Component contains list of modules
- Component connects the Service provider to a client.
- Responsible in providing the dependencies that other classes of the application required

#### Modules and Providers
- Modules are classes annotated with @Module
- Methods in modules that provide services are annotated with @Provides
- Module class contains the deifinations of an object.
- Provided services can be used as method arguments in other provider mwthods
- We can't provide more than one provider methods with samme return type
  
#### Scoped
- Scopes are annotations, annotated with @Scope
- All clients get the same instance of a scoped service from the same instance of a component
- It manage the lifecycle of dependencies to ensure that objects are reused appropriately within the context of an application, activity, or fragment.
- Scopes are typically tied to components in Dagger. A component annotated with a specific scope will provide dependencies that live as long as the component itself.
- @Singleton is a built-in scope used for application-wide singletons, while custom scopes can be defined for more specific lifecycles, such as activities or fragments.
- If a component has a scope, all the dependencies it provides should be annotated with the same scope. This ensures consistency in how objects are shared.
- Subcomponents in Dagger can have different scopes from their parent components. For example, an @ActivityScope subcomponent can depend on a @Singleton parent component. This allows different lifecycles to be managed hierarchically.

#### Component as injector
- Basically, Components are injectors, they know how to provide (inject) the constructed objects from module into their clients
- Void methods with single argument defined on component class generates injectors for the type of that argumment
- It targets client's non private and non final properties annotated with @Inject designate injection

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
- Retrieve multiple instances


## Hilt
- Hilt is built on top of Dagger and provides a set of standard components and annotations to make dependency injection easy in Android applications.
- With help of pre-defined components, it make easy to setup and use Dagger.

### Hilt Component
- Hilt provides pre-defined components that correspond to common Android lifecycle components.
- Component lifetimes are linked with the creation and destruction of a corresponding instance of an Android class.
  - **ApplicationComponent (@Singleton):** Lives for the entire lifecycle of the application. Dependencies provided in this component are singletons.
  - **ActivityComponent (@ActivityScoped):** Lives as long as the activity. Dependencies scoped to this component will be recreated for each new activity instance.
  - **FragmentComponent (@FragmentScoped):** Lives as long as the fragment. Dependencies are unique to the fragment.
  - **ViewModelComponent (@ViewModelScoped):** Tied to the lifecycle of a ViewModel.
  - **ServiceComponent (@ServiceScoped):** Lives as long as the service.
  - **ViewComponent (@ViewScoped):** Tied to the lifecycle of a view.
  - **ViewWithFragmentComponent (@ViewScoped):** Tied to the lifecycle of a view within a fragment.
- Each Hilt components has its own defualt data or binding. This data can be used in creating custom binding in modules.
  - **SingletonComponent** -> Application
  - **ActivityRetainedComponent** -> ActivityRetainedLifecycle
  - **ViewModelComponent** -> SavedStateHandle, ViewModelLifecycle
  - **ActivityComponent** -> Activity, FragmentActivity
  - **FragmentComponent** -> Fragment
  - **ViewComponent** -> View
  - **ViewWithFragmentComponent** -> View
  - **ServiceComponent** -> Service

### Scoped vs Unscoped bindings
- By default, all bindings in Dagger are “unscoped”. This means that each time the binding is requested, Dagger will create a new instance of the binding.
-  Dagger also allows a binding to be “scoped” to a particular component. A scoped binding will only be created once per instance of the component it’s scoped to, and all requests for that binding will share the same instance. In short an scoped binding will share same instance as long as the associated component exist.
- A common misconception is that all bindings declared in a module will be scoped to the component the module is installed in. However, this isn’t true. Only bindings declarations annotated with a scope annotation will be scoped.







