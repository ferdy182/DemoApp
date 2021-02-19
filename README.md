# DemoApp
Demo app using coroutines, sealed classes and MVVM

The code tries to fetch some json file with configuration for sections and checked/unchecked items from a remote repository, when the network request does not work (it always fails) it will return a local copy and parse the file to display the results in a list implemented with a RecyclerView and two types of view holders 
