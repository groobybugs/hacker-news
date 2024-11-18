# Hacker News Mobile App

A test mobile application that displays recent articles about mobile development from Hacker News.

## Features

- View recent mobile-related articles from Hacker News
- Pull-to-refresh for latest content
- Offline support
- Swipe to delete articles
- In-app web browser for reading articles

## Requirements

- Android Studio 
- Minimum SDK: 27 (Android 8.1)
- Target SDK: 35 (Android 15)

## Installation

1. Clone the repository
2. Open the project in Android Studio
3. Let Gradle sync and download dependencies
4. Run the app using an emulator or physical device

## Architecture & Technologies

- MVVM Architecture
- Kotlin + Jetpack Compose
- Dependency Injection with Hilt
- Room Database for offline storage
- Retrofit for API calls
- Paging 3 for infinite scrolling
- FastJson2 for JSON parsing
- Material 3 design components

## Building

To build a debug APK:
```bash
./gradlew assembleDebug
```

The APK will be in: `app/build/outputs/apk/debug/`
