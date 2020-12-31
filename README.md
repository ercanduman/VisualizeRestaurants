# VisualizeRestaurants
Sample android application to visualize restaurant list provided via JSON file.

## Requirement
The goal of this test is to implement a sample project, where you visualize a
restaurant list. You are able to sort the restaurant list based on its current openings state,
you can favorite a restaurant and you can select a sort value to further sort the list. Finally
we would like to see you add the option to filter the restaurant list, based on a restaurant’s
name. In the attachments you can find a JSON file (sample.json), this file contains all the
necessary data to complete this assignment. Parse the JSON file and use it for the
visualization and sorting of the list. Use the following priority of the sorting (from the
highest to the lowest priority):
1.	Favorites: Favorite restaurants are at the top of the list; your current favorite restaurants are stored locally on the phone.
2.	Openings state: Restaurant is either open (top), you can order ahead (middle) or a restaurant is currently closed (bottom). (Values available in sample.json)
3.	Sort options: Always one sort option is chosen and this can be best match, newest, rating average, distance, popularity, average product price, delivery costs or the minimum cost. (Values available in sample.json)
4.	Filtering: It’s up to you how you how you want to search by restaurant name.

#### Additional
-	Please visualize the name of the restaurants, the current opening state, the selected sort, the sort value for a restaurant and if it’s a favorite or not.
-	Remember if you have multiple favorite restaurants, they are also sorted based on their current openings state and current selected sort.
-	We expect valid test cases
-	Readme file with all the needed information, how to get the sample project working and verify the test cases.

## Solution
A native android application will be created from scratch. Kotlin will be used as main development language.
The application will contain android architecture components such as ViewModel, Repository, LiveData, Room, etc.
Following diagram will be used as reference which shows basic form of MVVM architecture.
![mvvm-architecture](https://user-images.githubusercontent.com/11629459/49515908-3e1c3e80-f8a9-11e8-8360-2a3a4d2e6227.png)

The application will read JSON content from [sample.json](https://raw.githubusercontent.com/ercanduman/VisualizeRestaurants/master/app/src/main/assets/sample_android.json "sample.json"). Thanks to GSON library this content will be converted into Kotlin data classes. 

As dependency injection, Dagger-Hilt library will be used. Dagger-Hilt takes care of injecting members into the Android class as well as handling and instantiating the proper Hilt components at the right point in the lifecycle. Dagger-Hilt library will provide Singleton instances of Room database and DAO. Room is where storing data locally and data access object (DAO) contains functions for database queries.

Repository object will be responsible to retrieve data from data sources and provide this data to ViewModel. Coroutines and suspend functions will be used for running background operations.

### Restaurant List
Main Activity is the main UI which all user interactions will be handled and also restaurants list will be displayed in MainActivity.

<img src="https://raw.githubusercontent.com/ercanduman/VisualizeRestaurants/master/output/Screenshot_1606802704.png" width="25%" title="Restaurant List">

### Sorting Options

Spinner UI element is used for sorting options. Spinners provides a quick way to select one value from sorting options. In the default state, "bestMatches" used for restaurant list. 
Touching the spinner displays a dropdown menu with all other available values which can be;
 - best match
 - newest
 - average
 - distance
 - popularity
 - average product price
 - delivery costs
 - the minimum cost.
 
##### Sorting Options Image: 
 <img src="https://raw.githubusercontent.com/ercanduman/VisualizeRestaurants/master/output/Screenshot_1606802733.png" width="25%" title="Sorting Options">

### Filtering 

Similar to WhatsApp app search toolbar added for search(or filtering) functionality. Text changes in search field is applied to restaurant list and if any Restaurant has name contains queried text, then these items will be listed in RecyclerView.

<img src="https://raw.githubusercontent.com/ercanduman/VisualizeRestaurants/master/output/Screenshot_1606802759.png" width="25%" title="Filtering">

## Usage
A compressed apk file build and added to [/output/apk](https://github.com/ercanduman/VisualizeRestaurants/blob/master/output/apk/debug.zip "/output/apk") directory. This apk can be installed easily and used to run application.

####  Build
This application uses the Gradle build system. To build this project use "Import Project" in Android Studio and use the following command.
`> gradlew build`

###  Test
Test suite class (UITestSuite.class)  created to run all instrumentation test cases with single click. Following command can be used to run all test cases.

`>    adb shell am instrument -w -m -e class 'ercanduman.visualizerestaurant.UITestSuite' ercanduman.visualizerestaurant.test/ercanduman.visualizerestaurant.HiltTestRunner`

**Notice**: Before running command make sure that your device is up and running. To check running devices following command can be used.
`> adb devices`

####  Running a subset of tests
Running all tests: `adb shell am instrument -w ercanduman.visualizerestaurant.test/ercanduman.visualizerestaurant.HiltTestRunner`

Running all tests in a MainActivityTest: `adb shell am instrument -w -m    -e debug false -e class 'ercanduman.visualizerestaurant.ui.main.MainActivityTest' ercanduman.visualizerestaurant.test/ercanduman.visualizerestaurant.HiltTestRunner`

Running a single test in MainActivityTest: `adb shell am instrument -w -m    -e debug false -e class 'ercanduman.visualizerestaurant.ui.main.MainActivityTest#test_check_if_recyclerView_displayed' ercanduman.visualizerestaurant.test/ercanduman.visualizerestaurant.HiltTestRunner`


### Documentation in code
All classes and functions comment added.

Happy coding! :+1: :1st_place_medal:
## License

[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://github.com/ercanduman/VisualizeRestaurants/blob/master/LICENSE)

ENB Creative, Copyright (C) 2020
