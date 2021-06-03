# HubProfile
This is a clean architecture app built with

- MVVM
- ViewBinding
- Extension Functions
- Dagger Hilt
- Coroutines
- Retrofit

# MVVM Android Application — Architectural

![Architectural](https://github.com/NinhvanLuyen/HubProfile/blob/master/images/architecture.png?raw=true)


## MVVM Why not ?

## MVVM Best practice

- Separation of concerns
> These UI-based classes should only contain logic that handles UI and operating system interactions. By keeping these classes as lean as possible, you can avoid many lifecycle-related problems.


- Notice that each component depends only on the component one level below it. 
> For example, activities and fragments depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.

## Dagger Hilt

>Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Hilt provides a standard way to use DI in your application by providing containers for every Android class in your project and managing their lifecycles automatically. 

- Hilt vs Dagger
 - Hilt is built on top of the popular DI library Dagger to benefit from the compile-time correctness, runtime performance, scalability, and Android Studio support that Dagger provides. For more information, see Hilt and Dagger.

## Corountine

Coroutines is our recommended solution for asynchronous programming on Android. Noteworthy features include the following:

- **Lightweight:** You can run many coroutines on a single thread due to support for suspension, which doesn't block the thread where the coroutine is running. Suspending saves memory over blocking while supporting many concurrent operations.
- **Fewer memory leaks:** Use structured concurrency to run operations within a scope.
- **Built-in cancellation support**: Cancellation is propagated automatically through the running coroutine hierarchy.
- **Jetpack integration**: Many Jetpack libraries include extensions that provide full coroutines support. Some libraries also provide their own coroutine scope that you can use for structured concurrency.

