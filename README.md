# Ludens

<p align="center">
  <a href="README.es.md">Leer en Español</a> | <a href="BUILD.md">Build Guide</a> | <a href="https://tryludens.vercel.app/">Web Docs</a>
</p>

<p align="center">
  <a href="https://github.com/yoimerdr/ludens/releases/latest">
    <img alt="Version" src="https://img.shields.io/github/v/release/yoimerdr/ludens?display_name=tag&sort=semver&style=for-the-badge&label=Version">
  </a>
  <img alt="Android 21+" src="https://img.shields.io/badge/Android-21%2B-6EDB8D?style=for-the-badge&logo=android&logoColor=0B1F17">
  <img alt="iOS 13+" src="https://img.shields.io/badge/iOS-13%2B-CDCDCD?style=for-the-badge&logo=apple&logoColor=111827">
</p>

<p align="center">
  <a href="http://kotlinlang.org">
    <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-v2.3.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
  </a>
  <a href="https://github.com/JetBrains/compose-multiplatform">
    <img alt="Compose Multiplatform" src="https://img.shields.io/badge/Compose_Multiplatform-v1.9.3-0EA5E9?style=for-the-badge&logo=jetpackcompose&logoColor=white">
  </a>
  <a href="https://github.com/KevinnZou/compose-webview-multiplatform">
    <img alt="Compose WebView" src="https://img.shields.io/badge/Compose_WebView-v2.0.3-334155?style=for-the-badge">
  </a>
  <a href="https://github.com/yoimerdr/compose-virtualjoystick-multiplatform">
    <img alt="Virtual Joystick" src="https://img.shields.io/badge/Virtual_Joystick-v1.0.0-0F172A?style=for-the-badge">
  </a>
</p>

Ludens is a Compose Multiplatform wrapper built in Kotlin to port RPG Maker MV/MZ games to mobile devices. It embeds your HTML5 export inside a native app and provides configurable mobile controls and settings.

<p align="center">
  <img src="docs/src/assets/samples/ludens-0.2.0-sample.gif" alt="Ludens Demo" width="450">
</p>

<p align="center">
  <a href="https://github.com/yoimerdr/ludens/releases/latest"><strong>Download Sample APK</strong></a>
</p>

## Features

- Optimized WebView integration for RPG Maker MV/MZ.
- On-screen controls (overlay):
  - Virtual joystick.
  - Configurable buttons (A, B, X, Y).
- Complete settings screen:
  - System: Theme (Light/Dark/System), Language (System/English/Spanish).
  - Tools: Mute Audio, Show FPS, Toggle WebGL.
  - Controls: Enable/Disable, Opacity, Button Positions, Key Mapping.
  - Actions: Configurable quick actions menu (Order, Enable/Disable).
- Easy app/build configuration from [`ludens.properties`](ludens.properties).
- Efficient asset management in `composeResources/files`.

> [!NOTE]
> This project does not include a built-in native save system. Save files are managed by RPG Maker inside WebView (LocalStorage/IndexedDB).

## Quick Start

> [!WARNING]
> iOS build is not fully configured yet (currently template defaults). This guide focuses on Android.

> [!TIP]
> Always validate on emulator or physical device. Some RPG Maker plugins may not behave correctly on mobile WebView.

### Prerequisites

- Android Studio Otter 2 Feature Drop (2025.2.2) or higher.
  - [Setup Guide](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)
- JDK 17 or higher.
- RPG Maker MV/MZ project exported for web.
  - Optional: enable [`YDP_Ludens.js`](resources/plugins/js/YDP_Ludens.js) for extra compatibility/features in the client.
  - Get latest plugin and related plugins in [rpgm-plugins](https://github.com/yoimerdr/rpgm-plugins).
  - Recommendation: place it as the first plugin in your plugin manager.
  - Compatibility: useful for older WebView font-loading issues.
  - Important: verify mobile compatibility for all plugins used by your game.

### Export the Game

Export from RPG Maker using `Android / iOS` (recommended). If unavailable, use `Web Browsers`.

Depending on MV/MZ version, you may get either a game folder containing `www` or only the `www` folder. In both cases, `index.html` is required.

### Import Assets

Move the full `www` folder to:

`composeApp/src/commonMain/composeResources/files`

> [!WARNING]
> **Case Sensitivity**: Windows file system is **case-insensitive** (`/Path/Example` = `/path/example`), but Android and iOS use **case-sensitive** file systems. Ensure that all file references in your game's code (images, audio, data files) use **exact matching names**. If a file is named `MyImage.png`, you must reference it as `MyImage.png`, not `myimage.png`. This is a common source of silent failures on mobile.
> 
> **Tip**: You can use the plugin **[YDP_CrossAssets](https://github.com/yoimerdr/rpgm-plugins)** from the rpgm-plugins repository to automatically normalize file paths and avoid this issue.

Required structure:

```text
files/
  └── www/
       ├── audio/
       ├── img/
       ├── js/
       ├── ...
       └── index.html
```

### Android Configuration

Edit [`ludens.properties`](ludens.properties):

```properties
# Unique application identifier
ludens.android.id=com.yourorganization.sample

# Version visible to users
ludens.android.version=1.0.0

# Internal integer version code
ludens.android.versionCode=1

# App name in settings
ludens.android.name=Game Name

# Launcher label
ludens.android.launcherName=Game
```

[`gradle.properties`](gradle.properties) is still used for Gradle/Kotlin build options. Ludens app identity/settings are configured in [`ludens.properties`](ludens.properties).

### Build

For the full build flow with screenshots and release-signing details, see [BUILD.md](BUILD.md).

- Debug: `./gradlew assembleDebug`
  - Output: `composeApp/build/outputs/apk/debug/`
- Release: `./gradlew assembleRelease`
  - Create [`keystore.properties`](keystore.properties) first (see [`keystore.properties.template`](keystore.properties.template)).
  - Output: `composeApp/build/outputs/apk/release/`

### iOS

Coming soon. Current setup follows default Compose Multiplatform template configuration.

## Contributing

Contributions are welcome. For major changes, open an issue first.

1. Fork the repository.
2. Create your branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## License

Apache 2.0. See [`LICENSE`](LICENSE).

## Links

- [Web Docs](https://tryludens.vercel.app/)
- [GitHub Repository](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Acknowledgments

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Compose WebView Multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)
- [Compose Virtual Joystick](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

---

If this project helps you, consider giving it a star.
