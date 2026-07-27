# Contributing to Ludens

First off, thank you for considering contributing to Ludens! We welcome contributions of all kinds —
bug reports, feature suggestions, documentation improvements, and code changes.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How to Contribute](#how-to-contribute)
    - [Report a Bug](#report-a-bug)
    - [Suggest a Feature](#suggest-a-feature)
    - [Submit a Pull Request](#submit-a-pull-request)
- [Development Setup](#development-setup)
    - [Prerequisites](#prerequisites)
    - [Clone and Import](#clone-and-import)
    - [Configure the Game](#configure-the-game)
    - [Build](#build)
- [Project Overview](#project-overview)
    - [Architecture](#architecture)
    - [Key Dependencies](#key-dependencies)
- [Coding Guidelines](#coding-guidelines)
    - [Kotlin Style](#kotlin-style)
    - [Commit Messages](#commit-messages)
    - [Branching](#branching)
- [Pull Request Checklist](#pull-request-checklist)

---

## Code of Conduct

This project adheres to the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md). By
participating, you agree to uphold its terms.

## How to Contribute

### Report a Bug

Open a [bug report](https://github.com/yoimerdr/ludens/issues/new?template=bug_report.md). Include:

- A clear description of the bug.
- Steps to reproduce.
- Expected vs. actual behavior.
- Device/OS details and app version.
- Screenshots if applicable.

### Suggest a Feature

Open a [feature request](https://github.com/yoimerdr/ludens/issues/new?template=feature_request.md).
Include:

- The problem you're trying to solve.
- Your proposed solution.
- Any alternatives you've considered.

### Submit a Pull Request

1. Fork the repository.
2. Create a feature branch from `develop` (see [Branching](#branching)).
3. Make your changes following the [coding guidelines](#coding-guidelines).
4. Ensure the project builds successfully (see [Build](#build)).
5. Update [`CHANGELOG.md`](CHANGELOG.md) under the `[Unreleased]` section.
6. Update relevant documentation in the [`docs/`](docs/) folder if needed.
7. Open a PR against the `develop` branch using
   the [pull request template](.github/pull_request_template.md).

## Development Setup

### Prerequisites

- **Android Studio** Otter 2 Feature Drop (2025.2.2) or higher.
- **JDK 17** or higher (Android Studio bundles a compatible version).
- **RPG Maker MV/MZ project** exported for web (optional for development — you can run the app
  without game assets, it will show instructions instead).

### Clone and Import

```bash
git clone https://github.com/yoimerdr/ludens.git
```

Open Android Studio, select **Open**, and navigate to the cloned `ludens` folder. Gradle sync will
start automatically.

### Configure the Game

Optional but recommended for full testing:

1. Export your RPG Maker MV/MZ project for **Android / iOS** or **Web Browsers**.
2. Copy the `www` folder to either of these locations:
   - **Root (Recommended)**: `project/www/` (will be synced automatically during build if it contains more than just `index.html`)
   - **Internal**: `shared/src/commonMain/composeResources/files/www/`
3. Ensure `index.html` exists inside the `www` folder.

> [!WARNING]
> Android and iOS use **case-sensitive** file systems. Ensure all file references use exact casing.

### Build

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires keystore.properties)
./gradlew assembleRelease

# Clean build artifacts
./gradlew clean
```

Debug APK output: `androidApp/build/outputs/apk/debug/androidApp-debug.apk` (post-processed: `output/debug/Ludens-0.4.0-debug.apk`)

For full build instructions with screenshots, see [BUILD.md](BUILD.md).

## Project Overview

**Ludens** is a Compose Multiplatform wrapper for porting RPG Maker MV/MZ games to mobile. It wraps
an HTML5 game in a WebView with on-screen controls (virtual joystick, configurable buttons).

### Architecture

```
shared/src/              # KMP shared module
├── commonMain/          # Shared code (Kotlin Multiplatform)
│   ├── kotlin/com/yoimerdr/compose/ludens/
│   │   ├── app/          # Theme, navigation, DI setup
│   │   ├── core/         # Domain models, ports, infrastructure adapters
│   │   ├── features/     # Feature modules (home, settings)
│   │   └── ui/           # Reusable UI components
│   └── proto/            # Protobuf definitions for DataStore
├── androidMain/         # Android-specific implementations
└── iosMain/             # iOS-specific implementations

androidApp/              # Android application module (entry point, manifest, icons)
build-logic/             # Custom Gradle plugins
```

| Layer       | Purpose                                                                                |
|-------------|----------------------------------------------------------------------------------------|
| `app/`      | Application-level wiring: theme, navigation graph, Koin module aggregation             |
| `core/`     | Domain models, repository interfaces, platform abstractions (`expect`/`actual`)        |
| `features/` | Feature-scoped screens and logic (`home`: WebView + controls, `settings`: preferences) |
| `ui/`       | Reusable components (buttons, dropdowns, responsive layout primitives)                 |

### Key Dependencies

- **Koin 4.x** — Dependency injection with KSP annotation processing
- **Proto DataStore** — Settings persistence via protobuf (`settings.proto`)
- **compose-webview-multiplatform** — Game WebView
- **compose-virtualjoystick** — Virtual joystick controls
- **AndroidX Navigation Compose** — Screen navigation

## Coding Guidelines

### Kotlin Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) (
  `kotlin.code.style=official`).
- Use `expect`/`actual` for platform-specific code.
- Prefer immutable state and pure functions where possible.
- Use Koin modules for dependency wiring — see existing module files for patterns.
- Match the style and conventions of the surrounding code. When in doubt, look at existing
  implementations in the same package.

### Localization & Translations

- **DO NOT** edit or add `strings.xml` directly under `shared/src/commonMain/composeResources/values*`.
- **Source of Truth**: All localization strings reside under `project/assets/languages/<language_tag>/strings.xml` (e.g., `es/strings.xml`, `en/strings.xml`).
- **Sync Task**: The custom Gradle task `LanguageStringsSyncTask` runs during compilation to clean and regenerate the `values*` folders in `composeResources` based on the active languages and settings configured in `ludens.properties`. Any manual modifications under `composeResources/values*` will be **permanently lost** on the next build.

### Commit Messages

Use conventional, descriptive commit messages:

```
<type>: <brief description>

<optional body>
```

Types: `feat`, `fix`, `refactor`, `docs`, `chore`, `test`, `style`.

Examples:

```
feat: add configurable button key mappings
fix: prevent WebView restart on settings navigation
docs: update BUILD.md with new manifest properties
```

### Branching

- Base your work on the `develop` branch.
- Use descriptive branch names:
    - `feat/short-description`
    - `fix/short-description`
    - `docs/short-description`
- PRs targeting `main` are only for releases.

## Pull Request Checklist

Before submitting, ensure:

- [ ] Code follows the project's Kotlin style and conventions.
- [ ] `./gradlew assembleDebug` completes successfully.
- [ ] `ludens.properties` configurations are respected (test with different values).
- [ ] [`CHANGELOG.md`](CHANGELOG.md) is updated under `[Unreleased]`.
- [ ] (If applicable) Documentation in `docs/` is updated.
- [ ] PR targets the `develop` branch.
- [ ] PR template is filled out, including testing steps and platform verification.

---

Thank you for contributing to Ludens!
