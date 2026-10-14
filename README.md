# Game Catalog

[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/WDAAPSDv1Uegi7vFhi7cvp/HPCZQG6EDwutDMocDwxBuL/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/circleci/WDAAPSDv1Uegi7vFhi7cvp/HPCZQG6EDwutDMocDwxBuL/tree/main)

Aplikasi katalog game yang mengambil data dari [RAWG API](https://rawg.io/apidocs). Project ini dibuat untuk submission Capstone Akhir kelas Menjadi Android Developer Expert di Dicoding.

## Fitur
- Daftar game populer (Home)
- Pencarian game
- Detail game
- Daftar game favorit (dynamic feature `:favorite`)

## Struktur Module
- `:app` : presentation layer untuk Home dan Detail
- `:core` : data layer, domain layer, dan komponen UI yang dipakai bersama
- `:favorite` : dynamic feature untuk halaman favorit

## Teknologi
- Clean Architecture + Modularization
- Koin untuk Dependency Injection
- Coroutine Flow untuk Reactive Programming
- Room + SQLCipher, Retrofit + OkHttp
- LeakCanary, ProGuard/R8, Certificate Pinning
- CircleCI untuk Continuous Integration

## Menjalankan Project
Tambahkan API key RAWG ke `local.properties`:

```
RAWG_API_KEY=api_key_kamu
```

Untuk CircleCI, tambahkan environment variable `RAWG_API_KEY` di **Project Settings → Environment Variables**.
