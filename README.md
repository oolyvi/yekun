# Yekun

**Yekun** is an unofficial Android client for [MyAnimeList](https://myanimelist.net/) built with Kotlin and Jetpack Compose. It brings anime and manga discovery, rankings, seasonal charts, personal lists, and AI-powered features together in a modern Android application.

## ✨ Features

- **Anime Discovery** — Explore popular anime, recommendations, seasonal releases, and detailed anime information.
- **Manga Discovery** — Search manga, browse rankings, and view detailed manga information.
- **MyAnimeList Integration** — Sign in with your MyAnimeList account using OAuth 2.0 and PKCE.
- **Personal Lists** — View and manage your anime and manga lists, including scores, progress, status, and other list details.
- **Search** — Find anime and manga using the MyAnimeList API.
- **Rankings** — Browse anime and manga rankings.
- **Seasonal Charts** — Discover anime by season and year.
- **AI Assistant** — Access AI-powered functionality through Google's Gemini API.
- **User Profiles** — View profile information and statistics.
- **NSFW Filter** — Configure whether NSFW content is displayed.
- **Multilingual UI** — Available in English, Azerbaijani, Spanish, Hindi, Japanese, Portuguese, Russian, Turkish, and Korean.
- **Customization** — Choose light or dark themes and configure application settings.
- **Modern UI** — Material Design 3, animations, image loading, and loading placeholders.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Jetpack Compose | Declarative UI development |
| Material 3 | UI components and theming |
| MVVM | Presentation architecture |
| Repository Pattern | Data access and separation of concerns |
| Kotlin Coroutines | Asynchronous operations |
| StateFlow / UI State | Reactive UI state management |
| Hilt | Dependency injection |
| Retrofit | REST API communication |
| OkHttp | HTTP client and networking |
| Kotlin Serialization | JSON serialization and deserialization |
| Coil | Asynchronous image loading |
| DataStore | Persistent preferences |
| Google Gemini API | AI-powered functionality |
| Lottie | Animations |
| AndroidX Navigation Compose | Screen navigation |
| MyAnimeList API | Anime, manga, rankings, and user lists |
| Jikan API | Additional anime and user-statistics data |
| Gradle Kotlin DSL | Build configuration |

## 🏗️ Architecture

Yekun uses an MVVM-oriented architecture with repositories and dependency injection to separate UI logic from data access.

```text
com.oolyvi.yekun
├── data
│   ├── model
│   │   ├── anime
│   │   ├── manga
│   │   └── user
│   ├── network
│   └── repository
├── di
├── ui
│   ├── about
│   ├── ai
│   ├── anime_detail
│   ├── anime_list
│   ├── anime_ranking
│   ├── home
│   ├── manga_detail
│   ├── manga_list
│   ├── manga_ranking
│   ├── on_boarding
│   ├── profile
│   ├── search
│   ├── season_chart
│   └── settings
└── utils
```

### Architectural responsibilities

- **UI:** Jetpack Compose screens and reusable components.
- **ViewModels:** Coordinate presentation logic and expose screen state.
- **Repositories:** Encapsulate data access and coordinate API requests.
- **Network:** Define Retrofit API interfaces and OAuth-related networking.
- **Models:** Represent anime, manga, user information, and API responses.
- **Dependency Injection:** Hilt modules provide networking and preference dependencies.
- **Utilities:** Shared helpers for dates, networking, formatting, and UI behavior.

## 🚀 Getting Started

### Prerequisites

- Android Studio
- JDK compatible with the project's Gradle and Android Gradle Plugin versions
- Android SDK 34
- A MyAnimeList API client ID
- A Google Gemini API key for AI features

### 1. Clone the repository

```bash
git clone https://github.com/oolyvi/yekun_anime.git
cd yekun_anime
```

### 2. Configure API keys

Create a `local.properties` file in the project root if one does not already exist.

Add your API credentials:

```properties
MAL_API_KEY="YOUR_MYANIMELIST_CLIENT_ID"
GEMINI_API_KEY="YOUR_GEMINI_API_KEY"
```

- Obtain a client ID from the [MyAnimeList API](https://myanimelist.net/apiconfig).
- Obtain a Gemini API key from [Google AI Studio](https://aistudio.google.com/apikey).

The project reads these properties during Gradle configuration and exposes them through `BuildConfig`.

**Security note:** Never commit real API keys or other secrets to version control. Keep `local.properties` private and ensure it is excluded from Git. For production distribution, consider a more secure backend-mediated approach for sensitive API credentials.

### 3. Build and run

Open the project in Android Studio, allow Gradle to sync, and run the `app` configuration on an emulator or Android device.

Alternatively, build the debug APK from the terminal:

```bash
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

The debug APK is generated under:

```text
app/build/outputs/apk/debug/
```

## 📱 Compatibility

- **Minimum Android version:** Android 6.0 (API 23)
- **Compile SDK:** Android 14 (API 34)
- **Target SDK:** Android 14 (API 34)
- **Application ID:** `com.oolyvi.yekun`
- **Version:** 2.0.8

An internet connection is required for API-powered features.

## 🌐 APIs and Services

- [MyAnimeList API](https://myanimelist.net/apiconfig) — Anime and manga information, rankings, seasonal releases, authentication, and personal lists.
- [Jikan API](https://docs.api.jikan.moe/) — Additional anime and user-statistics data.
- [Google Gemini API](https://ai.google.dev/) — AI-powered functionality.

Yekun is an unofficial third-party application and is not affiliated with or endorsed by MyAnimeList.

## 🤝 Acknowledgements

Special thanks to everyone who contributed to the project, including:

- MyAnimeList API community contributors
- Logo and design collaborators
- Translators
- Testers and early users
- Everyone who provided feedback and helped improve Yekun

## 📄 License

Add a `LICENSE` file to this repository if you intend to distribute the source code under an open-source license. Until a license is specified, the repository should be treated as having no explicit open-source license.

---

**Developed by [oolyvi](https://github.com/oolyvi)**

*Built with Kotlin, Jetpack Compose, and a passion for anime and manga.*