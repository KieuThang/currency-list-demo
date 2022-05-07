#### currency-list-demo
1. Overview
   
This sample is used to demostrate the an application with below features:
- CurrencyListFragment should receive an array list of CurrencyInfo to create the ui.
- DemoActivity should provide 1 dataset, Currency List A of CurrencyInfo to
  CurrencyListFragment and the dataset should be queried from local db
- DemoActivity should provide 2 buttons to do the demo.
- First button to load the data and display
- Second button for sorting currency list
- CurrencyListFragment should provide a hook of item click listener to the parent
- All the IO operations MUST NOT be in UI Thread. They all are run on the worker threads.

And additional features:
- Search: The user can enter the text in the search bar to start searching the currencies.
- Android Instrusment test: the test code is implemented to check the application logic will run as expected.

2. Architecture & Workflow
   
This application is implemented with Clean Architecture, RxJava, DI and Kotlin Couroutines.
   ![alt text](https://github.com/KieuThang/currency-list-demo/blob/main/app/src/main/architecture.png?raw=true)

* Multi-threading:
This application used RxJava and Kotlin coroutines for multi-threading to ensure every IO operation
or events from the user input will be processed in the worker-thread, not in the main UI thread.

3. Video demo
   https://www.youtube.com/watch?v=Gv0jCvaJPMA

4. Testing
   
The Android Instrument test is divided into 2 parts
- AssetUtilTest: testing for read file from Asset before writing to local database.
- DemoViewModelTest: testing for read data from local database in the ViewModel class.
  ![alt text](https://github.com/KieuThang/currency-list-demo/blob/main/app/src/main/android_test_result.png?raw=true)
  
5. APK demo:
   ![alt text](https://github.com/KieuThang/currency-list-demo/blob/main/app/src/main/currency-demo-debug-1.0.apk?raw=true)

