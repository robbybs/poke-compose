# PokeCompose App

Aplikasi Android berbasis **Jetpack Compose** dengan arsitektur **Clean Architecture + DDD**.  
Proyek ini menampilkan integrasi **Room, Retrofit, Paging 3, dan DataStore** untuk menghadirkan pengalaman online/offline yang mulus.

---

## Fitur Utama
- **Login & Register**  
  Menggunakan **Room Database** untuk menyimpan data akun secara lokal.  
- **List Pokemon dengan Pagination**  
  Implementasi **Paging 3** untuk menampilkan list pokemon secara efisien.  
- **Detail Pokemon**  
  Data detail diambil melalui **Retrofit** dari [PokeAPI](https://pokeapi.co).  
- **Sync Online/Offline**  
  - **List Pokemon** tersimpan di Room + RemoteMediator untuk offline-first.  
  - **Detail Pokemon** juga disimpan ke Room agar bisa diakses tanpa internet.  
- **Halaman Profile**  
  Data user ditampilkan dari **Room Database**.  
- **Session Management**  
  Menggunakan **DataStore** untuk menyimpan status login/logout user.

---

## Tech Stack
- **Kotlin** + **Coroutines** + **Flow**
- **Jetpack Compose** (UI modern declarative)
- **Room Database** (local storage)
- **Paging 3** (infinite scroll & pagination)
- **Retrofit** + **OkHttp** (network layer)
- **DataStore Preferences** (session management)
- **Koin** (dependency injection)

---
