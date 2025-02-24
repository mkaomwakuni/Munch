# Munchy - Recipe App

A modern Android recipe application built with Jetpack Compose and following clean architecture principles.

## 📸 Screenshots

<table>
  <tr>
    <td><img src="[https://github.com/user-attachments/assets/dd36dc83-246c-485b-9eaa-727a8972cb6b](https://private-user-images.githubusercontent.com/61048381/416345347-dd36dc83-246c-485b-9eaa-727a8972cb6b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUxMTYsIm5iZiI6MTc0MDQyNDgxNiwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNDctZGQzNmRjODMtMjQ2Yy00ODViLTllYWEtNzI3YTg5NzJjYjZiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjAxNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTM4NGZlMjVhMzQzMDNjZGU3MjBiZTJlN2U2ZmM4YTAwNjczZWNjMjk1NWU0MDNiMzZmNjYwYzZhMWMyNjI2YjQmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.fomFY45Qx1GGe31Pl42kCr3zpR0bJ-ng73fKd0HcOes)" width="400" /></td>
    <td><img src="[https://github.com/user-attachments/assets/19a3bca7-d931-4242-846a-c82a804f025f](https://private-user-images.githubusercontent.com/61048381/416345346-000605c8-6913-48af-898d-6b7f8c8410ac.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUxMTYsIm5iZiI6MTc0MDQyNDgxNiwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNDYtMDAwNjA1YzgtNjkxMy00OGFmLTg5OGQtNmI3ZjhjODQxMGFjLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjAxNlomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTdhZjJkNzRiZWYwYjA1MTA5MGRiZDc0YmVkNDY5OTc3Y2Q2OGI2ZTlkOWRhNmRhN2MyYzhhYWMxN2RhOGY5OTcmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.JICKbLqAOy7yAp6WFEo1tJrS58qPboUsnA2hVtVo-Ws)" width="400" /></td>
    <td><img src="[https://github.com/user-attachments/assets/f8907b93-35f8-40c9-8344-5a05d4c4b2a3](https://private-user-images.githubusercontent.com/61048381/416347804-f8907b93-35f8-40c9-8344-5a05d4c4b2a3.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUyNTQsIm5iZiI6MTc0MDQyNDk1NCwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDc4MDQtZjg5MDdiOTMtMzVmOC00MGM5LTgzNDQtNWEwNWQ0YzRiMmEzLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjIzNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTBiZDQ5N2ViYWM1NzgyNDgyZWU4MzBkNzJhZWJiNzk4NjVlYjk4MmMxOGE0ZGY2ZDE3OTQyMmQ0M2QzZDJjYjMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.F0TrywPXU6jpnCW5TYZ-VcXeEOsC6zgzadumgzmrKvU)" width="400" /></td>
  </tr>
  <tr>
    <td><img src="[https://github.com/user-attachments/assets/000605c8-6913-48af-898d-6b7f8c8410ac](https://private-user-images.githubusercontent.com/61048381/416345346-000605c8-6913-48af-898d-6b7f8c8410ac.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUyNTQsIm5iZiI6MTc0MDQyNDk1NCwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNDYtMDAwNjA1YzgtNjkxMy00OGFmLTg5OGQtNmI3ZjhjODQxMGFjLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjIzNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWNkM2RiZDZmZGMwNGQ1ZGFmNDI1YTE5ZjZmNzE2ZDBjNWU5OGNkOTA5MTcxMDMzZWUwMzRhZGQyNTQ1ZjE0MGYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.RI7_NDUbKpk48SBd8Bxxw_dTCI5O7hb_DxAI9HFWp_s)" width="400" /></td>
    <td><img src="[https://github.com/user-attachments/assets/39e74cac-9e57-49d5-9ffd-c13bec41833b](https://private-user-images.githubusercontent.com/61048381/416345350-39e74cac-9e57-49d5-9ffd-c13bec41833b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUyNTQsIm5iZiI6MTc0MDQyNDk1NCwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNTAtMzllNzRjYWMtOWU1Ny00OWQ1LTlmZmQtYzEzYmVjNDE4MzNiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjIzNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTgzYzI5ZDJmYWM4MjAzMDlmMjkzYjMzNWEyM2Y5NzA4ZDMyZmQwODE0OWI2MTBkYWNiYmFlNmM3ODc3ZGE1MDYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.XEKCOAF_2ShjNeeCkIH2vbNLGv4Ubf97NTuJhqLG65E)" width="400" /></td>
    <td><img src="[https://github.com/user-attachments/assets/94e219a0-0511-4c83-b178-586da325a08c](https://private-user-images.githubusercontent.com/61048381/416345351-94e219a0-0511-4c83-b178-586da325a08c.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUyNTQsIm5iZiI6MTc0MDQyNDk1NCwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNTEtOTRlMjE5YTAtMDUxMS00YzgzLWIxNzgtNTg2ZGEzMjVhMDhjLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjIzNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTQyMzA2OTA3OTY0Y2I1MWRhZGYwNWVlNDQ4MDVmNWJjOWMzZTFmNWU2NWQwZDRlMTY5Mzg5ODNlMDhiNmVlMTUmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.WeDsWqX-r3oLflFoQJg-LAFdxPS2OvRa4EsrIYsnpek)" width="400" /></td>
  </tr>
  <tr>
    <td><img src="[https://github.com/user-attachments/assets/0dd6a07b-9196-4790-af5f-b2d3320be150](https://private-user-images.githubusercontent.com/61048381/416345349-0dd6a07b-9196-4790-af5f-b2d3320be150.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDA0MjUyNTQsIm5iZiI6MTc0MDQyNDk1NCwicGF0aCI6Ii82MTA0ODM4MS80MTYzNDUzNDktMGRkNmEwN2ItOTE5Ni00NzkwLWFmNWYtYjJkMzMyMGJlMTUwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjI0VDE5MjIzNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWVjNDZmYzY1YTYxMDU5ZWE2NmViODFiYzIyZjc1YjQzNGE4MGYzOGM1MWQyYmJlZGMxMDBhNTYyNWE2MGQ5OTkmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.Hbca5GUlOv7r7CnNQaVnZN2HJVWnlCeqMqtD6ec3ZXA)" width="400" /></td>
    <td></td>
    <td></td>
  </tr>
</table>

## 🚀 Technologies & Libraries

### Core Technologies
- Kotlin
- Jetpack Compose
- Clean Architecture
- MVVM Pattern
- Material Design 3
- Repository Pattern

### Libraries & Dependencies

- **Dependency Injection**
  - [Hilt Android](https://dagger.dev/hilt/)
  - [Hilt Navigation Compose](https://developer.android.com/training/dependency-injection/hilt-jetpack-compose)

- **Networking**
  - [Retrofit](https://square.github.io/retrofit/)
  - [OkHttp](https://square.github.io/okhttp/)
  - [Gson](https://github.com/google/gson)

- **Local Storage**
  - [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
  - [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore)

- **Asynchronous Programming**
  - [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
  - [Flow](https://kotlinlang.org/docs/flow.html)

- **UI Components**
  - [Jetpack Compose UI](https://developer.android.com/jetpack/compose)
  - [Material 3 Components](https://m3.material.io/)
  - [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)

- **Development Tools**
  - [Timber](https://github.com/JakeWharton/timber)
  - [LeakCanary](https://square.github.io/leakcanary/)

## 🏗️ Architecture

The application follows Clean Architecture principles with three main layers:
- **Presentation (UI)**
- **Domain (Business Logic)**
- **Data (Repository & Data Sources)**

## 📚 Libraries

| Library | Documentation / Repository |
| --- | --- |
| [Hilt Android](https://dagger.dev/hilt/) | Official Hilt Android documentation |
| [Hilt Navigation Compose](https://developer.android.com/training/dependency-injection/hilt-jetpack-compose) | Hilt integration with Jetpack Compose Navigation |
| [Retrofit](https://square.github.io/retrofit/) | Retrofit documentation |
| [OkHttp](https://square.github.io/okhttp/) | OkHttp documentation |
| [Gson](https://github.com/google/gson) | Gson GitHub repository |
| [Room Database](https://developer.android.com/jetpack/androidx/releases/room) | Room Database documentation |
| [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) | DataStore Preferences documentation |
| [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) | Kotlin Coroutines overview |
| [Flow](https://kotlinlang.org/docs/flow.html) | Kotlin Flow documentation |
| [Jetpack Compose UI](https://developer.android.com/jetpack/compose) | Jetpack Compose official site |
| [Material 3 Components](https://m3.material.io/) | Material Design 3 guidelines |
| [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) | Jetpack Compose Navigation documentation |
| [Timber](https://github.com/JakeWharton/timber) | Timber GitHub repository |
| [LeakCanary](https://square.github.io/leakcanary/) | LeakCanary official site |

## 🤝 Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style Guidelines
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Keep functions small and focused
- Document complex logic
- Add KDoc comments for public APIs

## 📝 Code of Conduct

### Our Pledge
We pledge to make participation in our project a harassment-free experience for everyone, regardless of age, body size, disability, ethnicity, gender identity and expression, level of experience, nationality, personal appearance, race, religion, or sexual identity and orientation.
