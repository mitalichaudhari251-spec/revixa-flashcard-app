# revixa-flashcard-app
using android studio in java this app generated the revision card generator
# 📱 Revixa — Smart AI-Powered Study Companion

<div align="center">

![Revixa Banner](https://img.shields.io/badge/Revixa-Smart%20Study%20Companion-5C6BC0?style=for-the-badge&logo=android&logoColor=white)

[![Android](https://img.shields.io/badge/Platform-Android-green?style=flat-square&logo=android)](https://android.com)
[![API](https://img.shields.io/badge/Min%20SDK-24%20(Android%207.0)-blue?style=flat-square)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-orange?style=flat-square&logo=java)](https://java.com)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean-purple?style=flat-square)](https://developer.android.com/topic/architecture)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen?style=flat-square)](CONTRIBUTING.md)

<br/>

> **Revixa** is a modern Android flashcard app powered by the **SM-2 Spaced Repetition Algorithm** and **AI-assisted card generation** — designed to help students study smarter, retain more, and track their learning progress with beautiful analytics.

<br/>

[✨ Features](#-features) • [🏗 Architecture](#-architecture) • [🧠 Algorithm](#-sm-2-algorithm) • [🛠 Tech Stack](#-tech-stack) • [🚀 Setup](#-setup) • [🤝 Contributing](#-contributing)

</div>

---

## ✨ Features

### 🧠 Active Recall & Spaced Repetition
- **SM-2 Algorithm** — Cards scheduled based on how well you know them
- **3-Level Rating System** — 😖 Hard / 😐 Medium / 😊 Easy
- **Swipe Gestures** — Swipe left for Hard, right for Easy
- **Auto-Mastery Detection** — Cards with 21+ day interval marked as ✓ Mastered

### 📚 Smart Card Management
- Create cards with **Title, Question, Answer, Tags, Priority & Difficulty**
- **AI Card Generator** — Auto-generate flashcards from any topic keyword
- **Category System** — Organize cards by subject with color coding
- **Search & Filter** — Find any card instantly by keyword or tag
- **Priority Levels** — Low / Medium / High for exam-focused revision

### 📊 Progress & Analytics
- **Pie Chart** — Mastered vs Weak topics visualization
- **Accuracy Tracker** — Your correct answer percentage over time
- **Streak Counter** — Daily revision streak with XP bonus
- **XP Gamification** — Earn XP for every session completed
- **Achievement Badges** — 🔥 7-Day Streak / 🧠 100 Cards / 🏆 Champion

### 🌍 Community Library
- **Browse Public Decks** — Download decks shared by the community
- **Upload Your Decks** — Share your knowledge with other students
- **Rate & Review** — Help others find the best study material
- **Report System** — Flag inappropriate or low-quality content

### 🎮 Gamification System

| Action | XP Earned |
|--------|-----------|
| Per card reviewed | +10 XP |
| Full session complete | +50 XP |
| Daily streak bonus | +100 XP |

### 🔐 Security & Privacy
- Encrypted SharedPreferences (AES256-GCM)
- Firebase Firestore security rules
- Full input sanitization on all user fields
- Room parameterized queries (SQL-injection safe)
- ProGuard code obfuscation in release builds

---

## 🏗 Architecture

Revixa follows **MVVM + Clean Architecture** for complete separation of concerns, testability, and maintainability.

```
com.example.revixa
│
├── 📦 di/                         # Hilt Dependency Injection Modules
│   ├── AppModule.java
│   ├── DatabaseModule.java
│   └── RepositoryModule.java
│
├── 📦 data/                        # Data Layer
│   ├── local/
│   │   ├── dao/                    # Room DAO interfaces
│   │   │   ├── CardDao.java
│   │   │   └── CategoryDao.java
│   │   ├── entity/                 # Room Entity classes
│   │   │   ├── CardEntity.java
│   │   │   └── CategoryEntity.java
│   │   └── database/
│   │       └── AppDatabase.java    # Room Database (singleton + pre-populated)
│   ├── remote/
│   │   ├── FirebaseService.java    # Firestore CRUD operations
│   │   └── CommunityApi.java       # Community deck API wrapper
│   └── repository/
│       ├── CardRepositoryImpl.java
│       └── CommunityRepositoryImpl.java
│
├── 📦 domain/                      # Business Logic Layer (pure Java, no Android)
│   ├── model/
│   │   ├── Card.java
│   │   └── Category.java
│   ├── repository/
│   │   ├── CardRepository.java     # Interface
│   │   └── CommunityRepository.java
│   └── usecase/
│       ├── AddCardUseCase.java
│       ├── UpdateCardUseCase.java
│       ├── DeleteCardUseCase.java
│       ├── GetDueCardsUseCase.java
│       ├── ReviewCardUseCase.java
│       └── GenerateCardUseCase.java
│
├── 📦 presentation/                # UI Layer
│   ├── ui/
│   │   ├── splash/                 # SplashActivity
│   │   ├── home/                   # HomeActivity + HomeViewModel
│   │   ├── create/                 # CreateCardActivity + ViewModel
│   │   ├── revision/               # RevisionActivity + ViewModel
│   │   ├── progress/               # ProgressActivity + ViewModel
│   │   └── community/              # CommunityActivity + ViewModel
│   └── adapter/
│       ├── CardAdapter.java
│       ├── CategoryAdapter.java
│       └── DeckAdapter.java
│
└── 📦 utils/                       # Utilities & Helpers
    ├── Constants.java
    ├── DateUtils.java
    ├── SpacedRepetition.java       # SM-2 Algorithm implementation
    └── ValidationUtils.java
```

### Data Flow

```
┌─────────────────────┐
│   Activity / UI     │  ← observes LiveData, handles user events
└──────────┬──────────┘
           │
┌──────────▼──────────┐
│     ViewModel       │  ← holds & manages UI state
└──────────┬──────────┘
           │
┌──────────▼──────────┐
│      UseCase        │  ← encapsulates business rules
└──────────┬──────────┘
           │
┌──────────▼──────────┐
│  Repository (Interface) │  ← defined in domain layer
└──────────┬──────────┘
           │
     ┌─────┴──────┐
     ▼            ▼
┌─────────┐  ┌──────────┐
│  Room   │  │ Firebase │
│   DB    │  │Firestore │
└─────────┘  └──────────┘
```

---

## 🧠 SM-2 Algorithm

Revixa implements the **SuperMemo SM-2** spaced repetition algorithm to optimally schedule card reviews:

```
New Interval  =  Old Interval  ×  Ease Factor

Ease Factor formula:
  EF_new = EF_old + (0.1 − (5 − rating) × (0.08 + (5 − rating) × 0.02))

Constraints:
  Minimum EF       = 1.3
  Maximum Interval = 365 days
  Initial EF       = 2.5
  Initial Interval = 1 day
```

| User Rating | SM-2 Grade | Next Interval | EF Change |
|-------------|-----------|---------------|-----------|
| 😖 Hard | 1 | Reset → 1 day | − 0.20 |
| 😐 Medium | 3 | × EaseFactor | ± 0.00 |
| 😊 Easy | 5 | × EaseFactor × 1.3 | + 0.10 |

> ✅ Cards are automatically marked **Mastered** when interval ≥ 21 days with an Easy rating.

---

## 🛠 Tech Stack

| Library | Version | Purpose |
|---------|---------|---------|
| **Java** | 17 | Primary development language |
| **Android SDK** | API 34 | Target platform |
| **Hilt** | 2.50 | Dependency Injection |
| **Room** | 2.6.1 | Local SQLite database with migrations |
| **LiveData + ViewModel** | 2.7.0 | Reactive UI & lifecycle management |
| **Material Components** | 1.11.0 | UI design system |
| **MPAndroidChart** | 3.1.0 | Pie chart & progress analytics |
| **Firebase Firestore** | BOM 32.7 | Community deck cloud storage |
| **Firebase Auth** | BOM 32.7 | Google Sign-In authentication |
| **Glide** | 4.16.0 | Image loading & caching |
| **RxJava 3** | 3.1.8 | Reactive programming |
| **Lottie** | 6.3.0 | Smooth UI animations |
| **Security Crypto** | 1.1.0 | Encrypted SharedPreferences |
| **ProGuard** | — | Release build code obfuscation |

---

## 📋 Screens Overview

| Screen | Description |
|--------|-------------|
| 🟣 **Splash** | Animated logo with smooth fade-in (1.5s) |
| 🏠 **Home** | Dashboard — due cards, XP, streak counter, search, FAB |
| ➕ **Create Card** | AI generator, live validation, char counter, category picker |
| 🧠 **Revision** | Full-screen flip card with swipe gestures & SM-2 rating |
| 📊 **Progress** | Stats grid, pie chart, accuracy %, total reviews count |
| 🌍 **Community** | Browse, download & share public flashcard decks |

---

## 🚀 Setup

### Prerequisites
- **Android Studio** Hedgehog 2023.1.1 or newer
- **JDK 17**
- **Android SDK** API 24 minimum
- Internet connection for Gradle sync

### Step 1 — Clone
```bash
git clone https://github.com/mitalichaudhari251-spec/Revixa.git
cd Revixa
```

### Step 2 — Open in Android Studio
```
File → Open → Select the 'Revixa' folder → OK
```

### Step 3 — Wait for Gradle Sync
```
BUILD SUCCESSFUL ✅
```

### Step 4 — Run
```
Select emulator or physical device → Click ▶ Run (Shift + F10)
```

> ⚠️ **Windows users:** If you see _"Destination Path Too Long"_ error, extract to `C:\Revixa` instead of Downloads.

---

## 🔥 Firebase Setup _(Optional — for Community features)_

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create project → name: **Revixa**
3. Add Android app → package: `com.example.revixa`
4. Download `google-services.json` → place in `app/` folder
5. Enable **Cloud Firestore** + **Google Authentication**
6. Apply Firestore security rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /decks/{deck} {
      allow read: if true;
      allow write: if request.auth != null;
    }
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
    }
    match /reports/{report} {
      allow create: if request.auth != null;
    }
  }
}
```

7. Uncomment in `app/build.gradle`:
```groovy
id 'com.google.gms.google-services'
```

8. Uncomment in root `build.gradle`:
```groovy
classpath 'com.google.gms:google-services:4.4.0'
```

---

## 🗺 Roadmap

- [ ] 🤖 **Gemini AI Integration** — Real AI card generation via Google Gemini API
- [ ] 🔔 **Push Notifications** — Daily revision reminders via WorkManager
- [ ] 🌙 **Full Dark Mode** — Complete dark theme across all screens
- [ ] 📤 **Export Decks** — Export flashcard decks as PDF or CSV
- [ ] 🔊 **Text-to-Speech** — Audio playback of questions and answers
- [ ] 📱 **Home Screen Widget** — Quick revision widget
- [ ] 🧪 **Unit Tests** — Full coverage for SM-2 algorithm & use cases
- [ ] 🌐 **Offline Sync** — Background Firebase sync via WorkManager
- [ ] 🖼 **Image Cards** — Support images inside flashcard questions
- [ ] 📈 **Heatmap Calendar** — GitHub-style revision activity heatmap

---



### Commit Message Convention
```
feat:      New feature added
fix:       Bug fix
refactor:  Code refactoring (no feature change)
docs:      Documentation update
style:     Formatting, missing semicolons etc.
test:      Adding or updating tests
chore:     Build process or dependency updates
```

### Code Guidelines
- Follow standard **Java naming conventions**
- Add **Javadoc** for all public methods
- No hardcoded strings → use `strings.xml`
- No hardcoded colors → use `colors.xml`
- No hardcoded dimensions → use `dimens.xml`
- All Room DB operations must run on **background threads**
- Use **ViewModel** for all UI state — no logic in Activities

---

## 🐛 Known Issues

| Issue | Status | Workaround |
|-------|--------|-----------|
| Path Too Long on Windows | ⚠️ Windows OS limitation | Extract project to `C:\Revixa` |
| Community features need Firebase | ℹ️ Optional feature | Add `google-services.json` |
| AI generation is offline only | 🚧 Gemini integration planned | Local template generation works |

---



### 📱 Revixa — Study Smarter, Not Harder

[![GitHub stars](https://img.shields.io/github/stars/mitalichaudhari251-spec/Revixa?style=social)](https://github.com/mitalichaudhari251-spec/Revixa)
[![GitHub forks](https://img.shields.io/github/forks/mitalichaudhari251-spec/Revixa?style=social)](https://github.com/mitalichaudhari251-spec/Revixa/fork)
[![GitHub issues](https://img.shields.io/github/issues/mitalichaudhari251-spec/Revixa?style=social)](https://github.com/mitalichaudhari251-spec/Revixa/issues)

**⭐ If Revixa helped you study better, give it a star!**

_Built with ☕ Java · SM-2 Algorithm · Material Design_

</div>

