---
title: Overview
description: Learn how to port RPG Maker MV and MZ games to Android and iOS with Ludens.
head:
  - tag: meta
    attrs:
      name: keywords
      content: rpg maker mv android port, rpg maker mz android, rpg maker mv ios, rpg maker mz ios, compose multiplatform rpg maker
---

**Ludens** is a **Compose Multiplatform** wrapper developed in **Kotlin**, designed to help you
bring your **RPG Maker MV & MZ** projects to mobile devices. It takes your HTML5 game export and
wraps it in a modern native application, giving you full control over configuration and deployment
for both Android and iOS.

## What Problem Does It Solve?

RPG Maker MV and MZ export games as HTML5 web applications. Traditionally, distributing these games
to mobile users meant forcing them to play online via a browser, where games lack the native mobile
experience that players expect — home screen icons, on-screen controls, performance settings, and
proper standalone app distribution.

Ludens bridges this gap by providing a lightweight native shell built specifically for gaming. It
loads your game in an optimized WebView and layers a complete set of mobile-specific, native UI
functionalities on top.

## Features

### Optimized WebView

Your RPG Maker game runs inside an Android `WebView` that is deeply integrated into the Compose
Multiplatform UI. This WebView is specifically configured to reduce touch latency and optimize
canvas rendering for games. It handles all core game logic and audio, while the native Compose layer
seamlessly provides overlays, settings, and navigation without interrupting the engine.

### On-Screen Controls (Overlay)

Ludens provides a configurable on-screen control overlay:

- **Virtual Joystick** — Directional input for character movement.
- **Buttons A, B, X, Y** — Configurable action buttons mapped to keyboard events that the RPG Maker
  engine recognizes.
- **Opacity Control** — Adjust the transparency of all on-screen controls.
- **Position Control** — Reposition individual buttons to suit your layout preferences.

### Complete Settings Screen

The built-in settings screen is organized into four sections:

| Section      | Options                                                              |
|--------------|----------------------------------------------------------------------|
| **System**   | Theme (Light / Dark / System), Language (System / English / Español / 中文 / 日本語 / Português (Brasil) / Русский) |
| **Tools**    | Mute Audio, Show FPS, Toggle WebGL, Button Positions                 |
| **Controls** | Enable/Disable, Adjust Opacity, Key Mapping                          |
| **Actions**  | Configurable Quick Actions Menu (Order, Enable/Disable)              |

### Quick Actions Menu

The quick actions menu provides fast access to common toggles without entering the full settings
screen:

- Toggle Controls visibility
- Toggle Audio Mute
- Toggle FPS display
- Toggle WebGL rendering
- Open Settings

The order and visibility of these actions are fully configurable.

### WebView Diagnostics & Error Interception

When enabled, Ludens catches runtime JavaScript errors and native resource load failures in the WebView. Instead of failing silently or showing a black screen, it displays a native traceback dialog overlay containing complete debug logs, allowing developers or players to:
- Copy the complete traceback to the clipboard.
- Reload the WebView and restart the game instantly.

### Automated App Icon Generator

Ludens includes an integrated App Icon Generator that compiles your launcher icon assets for all target platforms from a single source image (SVG or PNG) in `project/assets/icons/`:
- **Android**: Legacy icons, square icons, and adaptive layers (foreground/background) in res mipmap folders.
- **iOS**: All required asset sizes with the proper JSON manifest catalog.
- **Google Play**: High-resolution store listing icons.

### Easy Configuration

All application identity, resources, and build properties are configured through `ludens.properties`:

- Application ID, version, and name (for system settings and launcher)
- Automated App Icon Generator config (format, background color, scale)
- Immersive mode, hardware acceleration, and cleartext traffic manifest flags
- Target compile-time languages selection (`ludens.languages.available`)
- Custom body and display fonts, plus language-specific fallbacks
- WebView runtime error diagnostics traceback toggle

### Automatic Resource Sync

Game assets (`project/www/`), custom fonts (`project/assets/fonts/`), and localized translation files (`project/assets/languages/`) are automatically synchronized to Compose resources during compilation, keeping your workspace clean.


## Platform Support

| Platform | Status                |
|----------|-----------------------|
| Android  | Supported (21+)       |
| iOS      | Coming Soon (iOS 13+) |

## Tech Stack

| Component             | Version |
|-----------------------|---------|
| Kotlin                | 2.3.0   |
| Compose Multiplatform | 1.9.3   |
| Compose WebView       | 2.0.3   |
| Virtual Joystick      | 1.0.0   |

:::caution[Important Note]
Ludens does **not** include a built-in native save system. Save files are managed by the RPG Maker
engine itself within the WebView, using the browser's LocalStorage or IndexedDB. This means saves
persist as long as the WebView data is not cleared.
:::

## Links

- [GitHub Repository](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Download Sample APK](https://github.com/yoimerdr/ludens/releases/latest)
