# Ludens

[Leer en Español](README.es.md) | [Build Guide](BUILD.md)

[![Kotlin](https://img.shields.io/badge/kotlin-v2.3.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.9.3-blue?logo=JetpackCompose)](https://github.com/JetBrains/compose-multiplatform)
[![Compose WebView](https://img.shields.io/badge/Compose%20WebView-v2.0.3-blue)](https://github.com/KevinnZou/compose-webview-multiplatform)
[![Virtual Joystick](https://img.shields.io/badge/Virtual%20Joystick-v1.0.0-blue?logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEiIHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIj48cGF0aCBkPSJNMTI5IDExMWMtNTUgNC05MyA2Ni05MyA3OEwwIDM5OGMtMiA3MCAzNiA5MiA2OSA5MWgxYzc5IDAgODctNTcgMTMwLTEyOGgyMDFjNDMgNzEgNTAgMTI4IDEyOSAxMjhoMWMzMyAxIDcxLTIxIDY5LTkxbC0zNi0yMDljMC0xMi00MC03OC05OC03OGgtMTBjLTYzIDAtOTIgMzUtOTIgNDJIMjM2YzAtNy0yOS00Mi05Mi00MmgtMTV6IiBmaWxsPSIjZmZmIi8+PC9zdmc+)](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)

**Ludens** is a **Compose Multiplatform** wrapper developed in **Kotlin**, designed to port RPG Maker MV/MZ games to mobile devices (Android and iOS). This project facilitates the integration of your HTML5 game into a modern native application, offering full control over configuration and deployment.

[Download Sample APK](https://github.com/yoimerdr/ludens/releases/latest)

## Features

*   **Optimized WebView**: Integrates your RPG Maker MV/MZ game seamlessly.
*   **On-Screen Controls (Overlay)**:
    *   Virtual Joystick.
    *   Configurable Buttons (A, B, X, Y).
*   **Complete Settings Screen**:
    *   **System**: Theme (Light/Dark/System), Language (System/English/Spanish).
    *   **Tools**: Mute Audio, Show FPS, Toggle WebGL.
    *   **Controls**: Enable/Disable, Adjust Opacity, Button Positions, and Key Mapping.
    *   **Actions**: Configurable Quick Actions Menu (Order, Enable/Disable).
*   **Easy Configuration**: Customize ID, version, and name directly from `gradle.properties`.
*   **Compose Resources**: Efficient asset management in `composeResources/files`.

> **Note**: This project does not include a built-in native save system; save files are managed by the game engine itself (RPG Maker) within the WebView (LocalStorage/IndexedDB).

## Quick Start

> **Warning**: The build for **iOS** is not fully configured yet (uses the template default configuration). This guide focuses on Android.

> **Testing**: It is strongly recommended to test the game on an emulator or real device. Some RPG Maker plugins may not be compatible with mobile WebView.

### Prerequisites

*   **Android Studio**: Recommended **Otter 2 Feature Drop | 2025.2.2** or higher.
    *   [Setup Guide](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)
*   **JDK**: Version 17 or higher.
*   **RPG Maker MV/MZ**: Your project exported for web.
    *   *Optional*: Include and activate the [YDP_Ludens.js](resources/plugins/js/YDP_Ludens.js) plugin for some additional features in the ludens client. You can find the latest version and other recommended plugins at the [rpgm-plugins repository](https://github.com/yoimerdr/rpgm-plugins).
        *   **Recommendation**: Should be the **first plugin** in your plugin manager.
        *   **Compatibility**: Required to support older WebView versions (fixes `fonts` loading verification errors).
    *   *Important*: Verify that the plugins used in your game are compatible with mobile environments (WebView).

### Game Export
Export your game from RPG Maker. The recommended option is **Android / iOS**. If unavailable, select **Web Browsers**.

Depending on the version (MV/MZ), you will get a folder with your game's name (containing `www`) or directly the `www` folder. The important thing is to locate where the `index.html` file and assets are.

### Import Assets
Move the entire **www** folder inside `files`. It is **necessary** for the files to maintain this structure, as the application will look for the `index.html` file in the `www` folder.

Target location: `composeApp/src/commonMain/composeResources/files`

The final structure should look like this:

```text
files/
  └── www/
       ├── audio/
       ├── img/
       ├── js/
       ├── ...
       └── index.html
```

### Android

#### Configuration
Edit the `gradle.properties` file in the project root to customize your application:

```properties
# Unique application identifier
ludens.applicationId=com.yourorganization.sample

# Version visible to the user
ludens.applicationVersion=1.0

# Application name in the system
ludens.applicationName=Game Name

# Short name under the icon
ludens.applicationLauncherName=Game
```

#### Build

> **Full Guide**: For a detailed walkthrough with images and specific configuration steps, please see the **[Build Guide](BUILD.md)**.

##### Debug Build
For quick testing on emulator or device:
Run `./gradlew assembleDebug`.
The APK will be generated at: `composeApp/build/outputs/apk/debug/`

##### Release Build
To generate a signed APK for production:
1.  Create your signature and configure its use by creating a `keystore.properties` file in the root (use `keystore.properties.template` as a reference).
2.  Run `./gradlew assembleRelease`.
3.  The signed APK will be at: `composeApp/build/outputs/apk/release/`

### iOS
*Coming Soon* - Current configuration is the default from Compose Multiplatform.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

## Links

- [GitHub Repository](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Acknowledgments

- Built with [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Compose WebView Multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)
- [Compose Virtual Joystick](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

---

**If you find this project useful, please consider giving it a ⭐ on GitHub!**