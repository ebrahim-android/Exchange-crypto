# 💱 Exchange Crypto App

A simple and modern Android application built with **Kotlin**, following the **MVVM architecture**.  
The app fetches real-time cryptocurrency market data from a public API (CoinGecko) and displays it in a clean, interactive UI with dynamic charts.

---

## 🚀 Features

- 📊 **Cryptocurrency List**  
  Displays a list of coins with their:
  - Name  
  - Symbol  
  - Current price  
  - 24h price change (with **dynamic color**: 🟢 green for positive, 🔴 red for negative)

- 📈 **Interactive Chart Screen**  
  - Shows price history over multiple time ranges (1d, 1w, 1m, 6m, 1y+)  
  - Chart line color changes automatically (🟢 uptrend / 🔴 downtrend)

- 🔄 **Real-Time Data Fetching**  
  Using **Retrofit** and **Coroutines** for smooth asynchronous updates.

- 🗄️ **Caching Support**  
  Implemented with **OkHttp** to improve performance and reduce API calls.

- 🧩 **Clean Architecture + Hilt**  
  Separation of concerns with **MVVM** pattern and **dependency injection** using Hilt.

- 🎨 **Modern UI Design**  
  Built with **RecyclerView**, **ViewBinding**, and clean minimal styling.

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
│ │ ├── api # Retrofit API interface
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
│ ├── detail # Detail & chart screen
│ └── model # UI-specific models
│
└── utils # Helper classes, constants, etc.

yaml
Copy code

---

## 🖼️ Screenshots

<p align="center">
  <img width="230" height="500" alt="Home Screen" src="https://github.com/user-attachments/assets/23609dfd-0626-4c82-ba9d-669d8a8acc30" />
  &nbsp;&nbsp;&nbsp;
  <img width="230" height="500" alt="Detail Screen" src="https://github.com/user-attachments/assets/0e725906-2981-4a86-9f87-f5603ca08381" />
</p>

---

## ⚙️ How to Run

1. **Clone this repository**
   ```bash
   git clone https://github.com/ebrahim-android/exchange-crypto.git
Open the project in Android Studio.

Sync Gradle to download dependencies.

Run the app on an emulator or a real device.

🧭 Future Improvements

🔍 Add search and filter functionality.

🧪 Unit testing for ViewModel and Repository layers.

❤️ Add a fully functional Favorites screen with local persistence.

📱 Developed by: Ebrahim Santana
🛠️ Built with ❤️ using Kotlin, MVVM, and Hilt.
