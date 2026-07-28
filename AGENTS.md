# Repository Guidelines

## Project Structure & Module Organization

Ludens is a Kotlin Multiplatform (Compose) wrapper that ports RPG Maker MV/MZ games to mobile via
WebView.

```
shared/src/
├── commonMain/
│   ├── kotlin/com/yoimerdr/compose/ludens/
│   │   ├── app/          # Theme, navigation, DI (Koin) wiring
│   │   ├── core/         # Domain models, ports, platform abstractions (expect/actual)
│   │   ├── features/     # Feature modules (home, settings)
│   │   └── ui/           # Reusable UI components
│   └── composeResources/ # Platform resources (static assets & dynamically synced resources)
├── androidMain/      # Android-specific implementations
└── iosMain/          # iOS-specific implementations

androidApp/           # Android application module (entry point, manifest, icons)
build-logic/          # Custom Gradle plugins (resource sync, manifest generation)
project/              # Game assets and static resources
├── assets/           # Application-wide static assets
│   ├── fonts/        # TTF/OTF custom typography fonts
│   └── languages/    # Source of truth for translations/localization (XML files)
└── www/              # RPG Maker MV/MZ exported game files (HTML, JS, CSS, audio, etc.)
docs/                 # Astro Starlight documentation site
resources/            # RPG Maker plugins (e.g., YDP_Ludens.js) and other resources
```

Key configuration files live at the repository root:

- `ludens.properties` — App identity, manifest flags, permissions, settings presets.
- `keystore.properties` — Release signing credentials (**do not commit**).
- `gradle.properties` — Kotlin/Gradle build-system options.

### Deep Dive: `composeResources/` vs `project/`

Understanding the source of truth for resources is critical to prevent build compilation issues.

#### `shared/src/commonMain/composeResources/`

This folder is the standard Compose Multiplatform resource location, but it is divided into two
distinct zones:

- **Static Resources (Manual Edit)**:
    - `drawable/`: Custom app icons, vectors, and static images.
    - `files/`: Raw static assets needed by the app. *Developers can add and modify files directly
      in this folder.* Note that `files/www/` is also where the exported RPG Maker game files
      reside (either placed here manually or auto-synced).
- **Synced Resources (Auto-Generated)**:
    - `values*` (e.g., `values/strings.xml`, `values-es/strings.xml`): Auto-populated from the
      translation source of truth (`project/assets/languages/`).
    - `font/`: Auto-populated and synced from custom typography sources (`project/assets/fonts/`).
    - *DO NOT edit these directories directly. They are completely overwritten or deleted on build.*

#### `project/` Directory

The single source of truth for game code and custom static configuration:

- **`project/assets/`**: Houses app-wide assets that are synced to target platform resources during
  builds.
    - **`project/assets/languages/`**: The **ONLY** place where localization files reside. Under
      each language folder (e.g., `es/`, `en/`, `ja/`, `pt-rBR/`), there is a `strings.xml` file.
    - **`project/assets/fonts/`**: Contains TTF/OTF font assets imported into the app.
    - **`project/www/`**: The deployment folder for the RPG Maker game
        * (Option A): If this directory contains **more than just the default `index.html` file**,
          the build system automatically synchronizes these files into
          `shared/src/commonMain/composeResources/files/www/` during compile time.
        * (Option B): If it only contains the default `index.html`, the auto-sync does not trigger
          and files must be placed directly in the internal `composeResources/files/www/` directory.

### Localization & Translations Rule

**DO NOT** add, edit, or delete `strings.xml` files inside the
`shared/src/commonMain/composeResources/values*` directories.
Ludens uses a custom Gradle task (`LanguageStringsSyncTask`) inside `build-logic`. During
compilation, this task completely cleans all `values*` folders in `composeResources` and
synchronizes the active languages from `project/assets/languages/`. Any manual changes inside
`composeResources` will be **permanently lost** on the next build.

**How to localize:**

1. Add/modify translations in `project/assets/languages/<language_tag>/strings.xml` (e.g.,
   `project/assets/languages/es/strings.xml`).
2. Enable/disable languages via `ludens.properties`.
3. Run a build to trigger `LanguageStringsSyncTask` which updates `composeResources` automatically.

## Approach

- Think before acting. Read existing files before writing code.
- Be concise in output but thorough in reasoning.
- Prefer editing over rewriting whole files.
- Do not re-read files you have already read unless the file may have changed.
- Skip files over 100KB unless explicitly required.
- Suggest running /cost when a session is running long to monitor cache ratio.
- Recommend starting a new session when switching to an unrelated task.
- Test your code before declaring done.
- No sycophantic openers or closing fluff.
- Keep solutions simple and direct.
- User instructions always override this file.

## Build & Development Commands

| Command                                                       | Description                                                 |
|---------------------------------------------------------------|-------------------------------------------------------------|
| `./gradlew assembleDebug`                                     | Build a debug APK                                           |
| `./gradlew assembleRelease`                                   | Build a signed release APK (requires `keystore.properties`) |
| `./gradlew :androidApp:compileDebugKotlinAndroid --no-daemon` | Focused Android Kotlin/Compose compile check.               |
| `./gradlew :shared:testDebugUnitTest`                         | Runs Android unit tests in the shared module.               |
| `./gradlew clean`                                             | Remove all build artifacts                                  |

**Prerequisites:** Android Studio Otter 2 Feature Drop (2025.2.2)+, JDK 17+.

Debug APK output: `androidApp/build/outputs/apk/debug/androidApp-debug.apk`
Post-processed output: `output/debug/Ludens-0.4.0-debug.apk` (configurable via `ludens.properties`)

## Coding Style & Naming Conventions

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) (
  `kotlin.code.style=official`).
- Use `expect`/`actual` declarations for platform-specific code.
- Prefer immutable state and pure functions.
- Wire dependencies through Koin modules — follow existing module patterns.
- Match the style of surrounding code; when in doubt, look at implementations in the same package.

## Testing Guidelines

Use `kotlin.test` for shared and platform tests. Put shared tests in
`shared/src/commonTest/kotlin/`; use platform test source sets only for platform-specific
behavior. There is no documented coverage gate, so focus on regression tests and run the narrowest
relevant Gradle test task plus a compile/build check.

## Core Design Rules

- **Binary Rhythm:** UI sections alternate between Pure Black (`#000000`) and Light Gray (`#F5F5F7`)
  using `colorScheme.background` and `colorScheme.surfaceContainerLowest`.
- **Strict Chromatic Accent:** Use Ludens Primary Blue (`#0071E3` mapped to `colorScheme.primary`) *
  *exclusively** for interactive elements (buttons, links, active states). No other accent colors
  should be introduced.
- **Typography:**
    - **Headings (20sp+):** `Plus Jakarta Sans`. Use tight line-heights and negative letter-spacing
      for billboard-like impact.
    - **Body (<20sp):** `Inter`.
- **Shapes & Radii:**
    - Use `LocalRadius.current.standard` (8.dp) for standard buttons.
    - Use `LocalRadius.current.comfortable` (11.dp) for cards/containers.
- **Spacing:** Avoid hardcoded `dp` values. Always use `LocalSpacing.current.*` (e.g., `medium` for
  14.dp standard padding).
- **File Sensitivity:** Always ensure exact case matching for asset references, as Android and iOS
  file systems are case-sensitive unlike Windows.

## Commit & Pull Request Guidelines

### Commit Messages

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>: <brief description>
```

Types: `feat`, `fix`, `refactor`, `docs`, `chore`, `test`, `style`.

### Pull Requests

- Branch from `develop` using descriptive names (`feat/…`, `fix/…`, `docs/…`).
- Target the `develop` branch (PRs to `main` are release-only).
- Ensure `./gradlew assembleDebug` passes before submitting.
- Update `CHANGELOG.md` under `[Unreleased]` and relevant docs in `docs/`.
- Fill out the PR template with testing steps and platform verification.

## Architecture Overview

| Layer       | Responsibility                                              |
|-------------|-------------------------------------------------------------|
| `app/`      | Theme, navigation graph, Koin module aggregation            |
| `core/`     | Domain models, repository interfaces, platform abstractions |
| `features/` | Feature-scoped screens and view models (home, settings)     |
| `ui/`       | Shared components (buttons, dropdowns, layout primitives)   |

Key dependencies: **Koin 4.x** (DI), **Proto DataStore** (settings), **compose-webview-multiplatform
** (game rendering), **compose-virtualjoystick** (controls), **AndroidX Navigation Compose**.

## Security & Configuration Tips

Do not commit local secrets or machine-specific files such as `keystore.properties` or
`local.properties`; use `keystore.properties.template` as the signing reference. App identity,
manifest flags, permissions, language/font sync, and settings presets are configured through
`ludens.properties`. Preserve exact RPG Maker asset casing because Android and iOS filesystems are
case-sensitive.