# Exchange Crypto App

A simple and modern Android application built with **Kotlin** following the **MVVM architecture**.  
The app fetches real-time cryptocurrency market data from a public API and displays it in a clean and dynamic UI.  

## Features

- 📊 Display a list of cryptocurrencies with their:
  - Name  
  - Symbol  
  - Current price  
  - 24h price change (with dynamic color: green for positive, red for negative)  
- 🔄 Automatic data fetching from API (via Retrofit).  
- 🗄️ Caching support with OkHttp.  
- 🧩 Built with **clean architecture** and **dependency injection** using Hilt.  
- 🎨 Simple UI design with RecyclerView and ViewBinding.  

## Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM (Model-View-ViewModel)  
- **Dependency Injection**: Hilt (Dagger/Hilt)  
- **Networking**: Retrofit + OkHttp  
- **Asynchronous tasks**: Coroutines + Flow  
- **UI**: RecyclerView, ViewBinding  

## Project Structure

com.example.cryptotracker
│
├── data
│ ├── api # Retrofit API service
│ ├── model # Data classes for API response
│ └── repository # Repository implementation
│
├── di # Hilt modules (NetworkModule, RepositoryModule, etc.)
│
├── ui
│ ├── adapter # RecyclerView Adapter
│ ├── viewmodel # ViewModels
│ └── view # Activities/Fragments
│
└── utils # Helper classes

arduino
Copy code

## Screenshots

<img width="232" height="550" alt="image" src="https://github.com/user-attachments/assets/23609dfd-0626-4c82-ba9d-669d8a8acc30" />

## How to Run

1. Clone this repository:  
   ```bash
   git clone https://github.com/ebrahim-android/crypto-tracker.git
Open the project in Android Studio.

Sync Gradle to download dependencies.

Run the app on an emulator or device.

Future Improvements
Add search and filter functionality.

Support for multiple fiat currencies.

Dark mode support.

Unit testing for ViewModel and Repository layers.
