# 💱 Exchange Crypto App

A modern Android application built with **Kotlin**, following the **MVVM architecture**.  
The app fetches real-time cryptocurrency market data from **CoinGecko API** and displays it in a clean, interactive UI with dynamic charts.

---

## 🚀 Features

- 📊 **Cryptocurrency List (Home Screen)**  
  - Displays a list of coins with their:
    - Name  
    - Symbol  
    - Current price  
    - 24h price change (**dynamic color**: 🟢 green for positive, 🔴 red for negative)  
  - Tap a crypto to see full details in **Detail Screen**.

- 🔍 **Search Cryptocurrencies**  
  - Dynamic filtering by name or symbol.  
  - Smooth animation for search results.  
  - Tap a crypto from search to navigate to **Detail Screen**.

- 📈 **Detail & Interactive Chart Screen**  
  - Shows price history over multiple time ranges (1d, 1w, 1m, 6m, 1y+).  
  - Chart line color changes automatically (🟢 uptrend / 🔴 downtrend).  
  - Displays additional metrics: Market Cap, 24h Volume, High/Low prices.  

- ❤️ **Favorites (Upcoming Feature)**  
  - Users can mark cryptos as favorites to easily track them.  
  - Will include local persistence for offline access.

- 🔄 **Real-Time Data Fetching**  
  - Using **Retrofit** + **Coroutines** for smooth asynchronous updates.  

- 🗄️ **Caching Support**  
  - Implemented with **OkHttp** to improve performance and reduce API calls.  

- 🧩 **Clean Architecture + Hilt**  
  - Separation of concerns with **MVVM** pattern and **dependency injection** using **Hilt**.  

- 🎨 **Modern UI Design**  
  - Built with **RecyclerView**, **ViewBinding**, and minimal, clean styling.  

---

## 🧠 Tech Stack

| Layer | Technologies |
|:------|:--------------|
| **Language** | Kotlin |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Dependency Injection** | Hilt (Dagger/Hilt) |
| **Networking** | Retrofit + OkHttp |
| **Asynchronous Tasks** | Coroutines + Flow |
| **UI Components** | RecyclerView, ViewBinding, MPAndroidChart |

---

## 🗂️ Project Structure

com.practica.exchangecrypto
│
├── data
│ ├── remote
│ │ ├── api # Retrofit API interfaces
│ │ ├── dto # Data Transfer Objects (API responses)
│ │ └── repository # Repository implementations
│
├── di # Hilt modules (NetworkModule, RepositoryModule, etc.)
│
├── domain
│ ├── model # Domain models
│ └── state # UI State management (Success, Error, Loading)
│
├── ui
│ ├── home # Crypto list (RecyclerView)
│ ├── search # Search screen
│ ├── detail # Detail & chart screen
│ ├── favorites # Favorites screen
│ └── model # UI-specific models
│
└── utils # Helper classes, constants, etc.

php-template
Copy code

---

## 🖼️ Screenshots

<p align="center">
  <img width="230" height="500" alt="image" src="https://github.com/user-attachments/assets/3c7cab30-9bff-43a8-8e32-b09954ba299e" />
  &nbsp;&nbsp;&nbsp;
  <img width="230" height="500" alt="Detail Screen" src="https://github.com/user-attachments/assets/0e725906-2981-4a86-9f87-f5603ca08381" />
  &nbsp;&nbsp;&nbsp;
 <img width="240" height="500" alt="image" src="https://github.com/user-attachments/assets/047a7260-bfb9-407e-9f58-fc8820bac675" />
</p>

---

## ⚙️ How to Run

1. **Clone this repository**  
   ```bash
   git clone https://github.com/ebrahim-android/exchange-crypto.git
Open the project in Android Studio

Sync Gradle to download dependencies

Run the app on an emulator or real device

🧭 Future Improvements
❤️ Complete Favorites screen with local persistence

🧪 Unit testing for ViewModel and Repository layers

🎨 Additional UI/UX enhancements (animations, theming)

🔍 Advanced search and filtering

📱 Author
Ebrahim Santana
🛠️ Built with ❤️ using Kotlin, MVVM, and Hilt.
