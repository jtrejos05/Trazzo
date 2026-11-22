# Trazzo

**An Android app for discovering, sharing and saving art.** Artists publish their work (paintings, illustrations, crafts, clothing, tutorials) and the community explores, comments, reacts and follows them.

> **Kotlin · Jetpack Compose · MVVM · Hilt · Coroutines + StateFlow · Firebase (Auth, Firestore, Storage, Cloud Functions, FCM) · Retrofit**

<!-- Add 3–4 screenshots here: Home, artwork detail with comments, upload screen, profile -->
<p align="center">
  <img src="docs/screenshots/home.png" width="200" />
  <img src="docs/screenshots/detail.png" width="200" />
  <img src="docs/screenshots/upload.png" width="200" />
  <img src="docs/screenshots/profile.png" width="200" />
</p>

---

## Features

- **Discover:** a home feed, a trending view, and search by category.
- **Publish:** upload artwork with images, which are stored in Firebase Storage.
- **Interact:** like/dislike works, comment and reply to other users' comments, and save works to a personal collection.
- **Social:** user profiles, profile editing, and following other artists.
- **Accounts:** email/password registration and Google sign-in with Firebase Auth.
- **Notifications:** push notifications (FCM) when someone likes your work.

## Architecture

The app follows **MVVM** with unidirectional data flow:

```
Composable Screen  ──events──►  ViewModel  ──►  Repository  ──►  RemoteDataSource
       ▲                            │                               ├─ Firestore impl
       └──── immutable UI State ◄───┘ (StateFlow)                   └─ Retrofit impl (REST API)
```

- **Screens (Jetpack Compose)** render an immutable `State` and send user events to the ViewModel.
- **ViewModels** hold UI state in `StateFlow` and run work in coroutines.
- **Repositories** expose `Result<T>` and hide where data comes from.
- **Data sources** sit behind interfaces, with two interchangeable implementations: **Firestore** and **Retrofit** (REST backend). The implementation is swapped through **Hilt** dependency injection.
- **Navigation Component** handles screen routing.

### Serverless backend (Cloud Functions)

`CloudFunctions/functions/index.js` contains event-driven Firebase functions:

| Function | Trigger | What it does |
|---|---|---|
| `updateUserInfoInObras` | A user document is updated | When a user changes their name or photo, it batch-updates the denormalized author info on all of their artworks |
| `sendLikeNotification` | A like is created on an artwork | Sends a push notification to the artwork's author through FCM |

## Testing

ViewModels and repositories have unit tests with **JUnit**, **MockK** and **kotlinx-coroutines-test**:

- `ViewModel/`: `DetalleViewModel`, `PerfilViewModel`, `IniciarSesionViewModel`, `CrearEditarCommentViewModel`
- `Repository/`: `AuthRepository`, `UserRepository`

```bash
./gradlew test
```

## Getting started

1. Open the project in **Android Studio**. Minimum SDK is 24 and target SDK is 36.
2. Add your own `app/google-services.json` from a Firebase project with Auth, Firestore, Storage and Cloud Messaging enabled.
3. Sync Gradle and run the app on a device or an emulator.
4. Optional: deploy the Cloud Functions.
   ```bash
   cd CloudFunctions/functions
   npm install
   firebase deploy --only functions
   ```

## Team and my contribution

Built as a team project at Pontificia Universidad Javeriana by Nicolás Algarra, Sara García, Juan Camilo Alba and **Juan Martín Trejos**.


## HECHO POR: NICOLÁS ALGARRA, SARA GARCÍA, JUAN CAMILO ALBA, JUAN MARTIN TREJOS.

