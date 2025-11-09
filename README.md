# 💱 Exchange Crypto App

A modern Android application built with **Kotlin**, following the **MVVM architecture**.  
The app fetches real-time cryptocurrency market data from **CoinGecko API** and displays it in a clean, interactive UI with dynamic charts.

---

## 🚀 Features

### 🏠 **Cryptocurrency List (Home Screen)**
- Displays a list of coins with their:
  - 🪙 **Name**
  - 🔣 **Symbol**
  - 💵 **Current Price**
  - 📈 **24h Change** (with **dynamic colors**: 🟢 green for positive, 🔴 red for negative)
- Filter cryptocurrencies by:
  - 🧩 **Top Gainers**
  - 📉 **Top Losers**
  - 💰 **Market Cap**
  - 🌐 **All**
- Tap a crypto to view full details in the **Detail Screen**.

---

### 🔍 **Search Cryptocurrencies**
- Dynamic filtering by **name** or **symbol**.
- Smooth animation when displaying search results.
- Tap any crypto to navigate directly to the **Detail Screen**.

---

## ⚙️ Tech Stack

| 🧩 Category | ⚙️ Technology |
|--------------|------------------------------|
| 💡 **Language** | Kotlin |
| 🧱 **Architecture** | MVVM (Model–View–ViewModel) |
| 🔗 **Networking** | Retrofit |
| 🧭 **Navigation** | Jetpack Navigation Component |
| 🧩 **View Binding** | Enabled across all UI components |
| 🎨 **UI Framework** | Material Design 3 + Dynamic Colors |
| 📊 **API Source** | CoinGecko Public API |
| ⚡ **Async Handling** | Kotlin Coroutines |

---

## 🖼️ Screenshots

<p align="center">
 <img width="240" height="500" alt="image" src="https://github.com/user-attachments/assets/6553af37-c89a-42f0-8d58-2410d7c82884" />
  &nbsp;&nbsp;&nbsp;
  <img width="230" height="500" alt="Detail Screen" src="https://github.com/user-attachments/assets/0e725906-2981-4a86-9f87-f5603ca08381" />
  &nbsp;&nbsp;&nbsp;
 <img width="240" height="500" alt="image" src="https://github.com/user-attachments/assets/047a7260-bfb9-407e-9f58-fc8820bac675" />
</p>

---

## 🗂️ Project Structure

📁 com.practica.exchangecrypto
│
├── 🗂️ data/
│ ├── remote/ → Retrofit API interfaces & DTOs
│ ├── repository/ → Repository implementations
│ └── local/ → (Future) Caching / Database
│
├── 🧩 domain/
│ ├── model/ → Domain models
│ └── state/ → UI state management (Loading, Success, Error)
│
├── 🎨 ui/
│ ├── home/ → Home screen (crypto list + filters)
│ ├── search/ → Search functionality
│ ├── detail/ → Detail screen with chart and stats
│ └── model/ → UI-specific data classes
│
├── 💉 di/ → Hilt modules (NetworkModule, RepositoryModule, etc.)
│
└── ⚙️ utils/ → Helper classes, formatters, constants


---

## 🧭 Future Improvements

Here are some of the upcoming enhancements planned for **Exchange Crypto App**:

- 🎛️ **Better Filter Logic**  
  Improve the filter buttons so each metric (Volume, Market Cap, Gainers, etc.) works independently and with real data sorting.

- 📊 **Detail Screen Expansion**  
  Add more insights like supply info, trading volume chart, and related crypto comparisons.

- 🧪 **Testing Layer**  
  Implement unit tests for **ViewModel** and **Repository** layers to ensure code quality and reliability.

- 💾 **Favorites System**  
  Add the ability to mark cryptos as favorites and save them locally (Room database).

- ✨ **UI/UX Enhancements**  
  More animations, transitions, and light/dark theme adjustments for an even smoother experience.

---

### 🧠 **About**
Exchange Crypto App was built as a learning and showcase project to explore **clean architecture**, **MVVM pattern**, and **modern Android development** best practices.

---

### 📬 Contact
✉️ **ebrahimsantana35@gmail.com**  
💼 **LinkedIn:** [Ebrahim Santana](https://www.linkedin.com/in/ebrahim-santana-75a188301/)
