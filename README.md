

## Capstone Project

The app is created to support a music festival, providing information about the lineup, schedule, artist information, etc.

[![ScreenShot1](https://github.com/calin-tesu/Capstone-Project/blob/master/Screenshot_1.png)
[![ScreenShot1](https://github.com/calin-tesu/Capstone-Project/blob/master/Screenshot_2.png)
[![ScreenShot1](https://github.com/calin-tesu/Capstone-Project/blob/master/Screenshot_3.png)
[![ScreenShot1](https://github.com/calin-tesu/Capstone-Project/blob/master/Screenshot_4.png)
      

## Features



*   List of performing artists displayed in  a RecyclerView with GridLayout
*   Details about every artist
*   Schedule of festival by day and stage using a BottomNavigationView integrated with a TabLayout
*   Navigation between fragments/activities is ensured using a navigation drawer
*   All data about artistits is kept in Firebase Realtime Database and Firebase Storage
*   Provide a widget for displaying the current performing artists
*   App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts


## Features still in development phase



*   Will use Firebase Authentication and Realtime Database for storing personalised lineups for every user
*   Provide a way for buying tickets online
*   A separate app for uploading all data and images of artists to Firebase. In this moment the data is generic using a nested for loop to generate a random list of artists


## Libraries



*   Gson - to serialize and deserialize Java objects to (and from) JSON
*   Glide (v4.8.0) - to handle the loading and caching of images. ( to meet the project requirements, in ArtistDetailsActivity,  will use AsyncTask to establish an URLConnection to Firebase Storage and BitmapFactory to decode the stored images)
