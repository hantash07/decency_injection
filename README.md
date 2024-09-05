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

### Hilt Application
- All apps using Hilt must contain an Application class annotated with @HiltAndroidApp
- @HiltAndroidApp kicks off the code generation of the Hilt components and also generates a base class for your application that uses those generated components.
- Just like other Hilt Android entry points, Application's members injected as well. This means you can use injected fields in the Application after super.onCreate() has been called.

### Android Entry Points
- Once you have enabled members injection in your Application, you can start enabling members injection in your other Android classes using the @AndroidEntryPoint annotation.
- You can use @AndroidEntryPoint on the following types:
  1. Activity
  2. Fragment
  3. View
  4. Service
  5. BroadcastReceiver

- ViewModels are supported via a separate API @HiltViewModel.
- ContentProviders are not directly supported due to their onCreate being called at startup.
- When adding to other types, note that as a general rule, Hilt types need to be attached to other Hilt types to work. So before adding [@AndroidEntryPoint] to a fragment, the activity must be annotated as well.

#### Retained Fragments
- Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration changes.
- If Hilt Fragment is retained, a runtime exeption will be thrown on configuration changes.
- A Hilt fragment should never be retained because it holds a reference to the component and that component holds references to the previous Activity instance. In addition, scoped bindings and providers that are injected into the fragment can also cause memory leaks if a Hilt fragment is retained.
- A non-Hilt fragment can be retained, even if attached to a Hilt activity. However, if that fragment contains a Hilt child fragment, a runtime exception will be thrown when a configuration change occurs.

#### Views with Fragment Binding
- By default, only SingletonComponent and ActivityComponent bindings can be injected into the view. To enable fragment bindings in your view, add the @WithFragmentBindings annotation to your class.

### Hilt View Model
- To enable injection of a ViewModel by Hilt use the @HiltViewModel annotation.
- Only dependencies from the ViewModelComponent and its parent components (e.g., SingletonComponent and ActivityRetainedComponent) can be provided into the ViewModel.
- All Hilt View Models are provided by the ViewModelComponent which follows the same lifecycle as a ViewModel, i.e. it survives configuration changes.
- To scope a dependency to a ViewModel use the @ViewModelScoped annotation.
- Dependencies annotated with @ViewModelScoped are created and shared within the same ViewModel instance but will be discarded once the ViewModel is cleared.
- @ViewModelScoped dependencies live as long as the ViewModel.
- This is useful for resources that should be shared and retained within a single ViewModel instance.
- Following are the Use Cases for @ViewModelScoped:
  1. **Repository Sharing:** When you want to share a repository or other data source across different methods in the ViewModel while maintaining the same instance for the lifecycle of the ViewModel.
  2. **Expensive Initialization:** If you have a dependency that is expensive to create, scoping it to the ViewModel can avoid reinitializing it unnecessarily.
- @ViewModelScoped ensures that dependencies live as long as a ViewModel and are shared within the same ViewModel instance.
- Use @InstallIn(ViewModelComponent::class) in your module to bind dependencies to the ViewModel scope.
- if a @ViewModelScoped dependencies needs to be shared across various View Models then it should be scoped using either @ActivityRetainedScoped or @Singleton.

#### What Happens After a Configuration Change?
- When the Activity or Fragment is destroyed and recreated, the ViewModel is retained.
- The Android framework retains the ViewModel using the ViewModelProvider, and Hilt doesn’t need to recreate the ViewModel or its dependencies.

### Hilt Modules
- Hilt modules are standard Dagger modules that have an additional @InstallIn annotation that determines which Hilt component(s) to install the module into.
- The modules annotated with @InstallIn will be installed into the corresponding component.
- Just like in Dagger, installing a module into a component allows that binding to be accessed as a dependency in any child component(s) bases on Hilt Component Hierarchy

#### Using @InstallIn
- A module is installed in a Hilt Component by annotating the module with the @InstallIn annotation.
- These annotations are required on all Dagger modules when using Hilt.
- If a module does not have an @InstallIn annotation, the module will not be part of the component and may result in compilation errors.
- In Hilt, it's possible to install a module into multiple components. This is useful when you want to provide dependencies across different scopes, such as application-wide (singleton) dependencies and more localized scopes like activities or fragments.
- You can’t directly specify multiple components in one @InstallIn annotation. Instead, create a module for each component.

### Entry Point
- Hilt uses entry points to allow dependency injection in classes that Hilt does not automatically inject, such as classes not managed by Android's framework
- Some Android components or classes cannot be directly injected using Hilt such as custom views, third-party libraries, or services like ContentProvider.
- Hilt Entry Points allow you to access Hilt's dependency graph outside of Hilt-injected classes.
- They provide a way to retrieve dependencies where automatic injection via @AndroidEntryPoint is not possible.
- Following are the scenarios when to use Entry Point
  1. **Custom Views:** If you have a custom view or class that isn't directly injected by Hilt.
  2. **Non-Hilt Components:** Classes like ContentProvider and BroadcastReceiver that don’t support @AndroidEntryPoint.

  ```
  @EntryPoint
  @InstallIn(ApplicationComponent::class)
  interface MyEntryPoint {
      fun getMyDependency(): MyDependency
  }

  // Usage
  val entryPoint = EntryPointAccessors.fromApplication(appContext, MyEntryPoint::class.java)
  val myDependency = entryPoint.getMyDependency()
  ```










