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

The application will read JSON content from sample.json. Thanks to GSON library this content will be converted into Kotlin data classes. 

As dependency injection, Dagger-Hilt library will be used. Dagger-Hilt takes care of injecting members into the Android class as well as handling and instantiating the proper Hilt components at the right point in the lifecycle. Dagger-Hilt libraryr will provide Singleton instances of Room database and DAO. Room is where storing data locally and data access object (DAO) contains functions for database queries.

Repository object will be responsible to retrieve data from data sources and provide this data to ViewModel. Coroutines and suspend functions will be used for running background operations.




