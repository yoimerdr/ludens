---
title: FAQ
description: FAQ for RPG Maker MV/MZ Android porting with Ludens, including compatibility, plugins, WebView, and APK build issues.
head:
  - tag: script
    attrs:
      type: application/ld+json
    content: '{"@context":"https://schema.org","@type":"FAQPage","mainEntity":[{"@type":"Question","name":"Which RPG Maker versions are supported?","acceptedAnswer":{"@type":"Answer","text":"Ludens supports RPG Maker MV and RPG Maker MZ games exported as HTML5."}},{"@type":"Question","name":"Can I use RPG Maker plugins on Android?","acceptedAnswer":{"@type":"Answer","text":"Many plugins work, but desktop-only plugins and Node.js API based plugins may fail on mobile WebView."}},{"@type":"Question","name":"Can I generate an Android App Bundle (AAB)?","acceptedAnswer":{"@type":"Answer","text":"Yes. Run ./gradlew bundleRelease to generate an AAB build."}}]}'
---

## General

### What is Ludens?

Ludens is a Compose Multiplatform wrapper that lets you port RPG Maker MV/MZ games to mobile devices (Android and iOS). It wraps your HTML5 game export in a native application with on-screen controls, settings, and proper mobile distribution support.

### Do I need to know Kotlin to use Ludens?

No. For basic usage — exporting your game, customizing the app identity, and building an APK — you only need to edit `gradle.properties` and follow the build guide. No code changes are required.

### Is Ludens free?

Yes. Ludens is licensed under the Apache 2.0 License.

---

## Compatibility

### Which RPG Maker versions are supported?

Ludens supports games made with **RPG Maker MV** and **RPG Maker MZ**. Both export HTML5 games that can be loaded in the WebView.

### Will my plugins work on mobile?

Not all of them. Many RPG Maker plugins are designed specifically for desktop environments (PC/Mac). Plugins that rely on **Node.js APIs** (like `fs` for file system access, common in custom save systems or desktop integrations) will immediately crash the game on mobile. Furthermore, plugins that expect physical keyboard inputs without providing on-screen alternatives might be unplayable. You must test your game thoroughly on a physical Android device or emulator to verify compatibility.

### What Android versions are supported?

Ludens targets **Android API 21** (Android 5.0 Lollipop) as the minimum SDK, with a target SDK of **36**. This covers the vast majority of Android devices in active use.

### Is iOS supported?

iOS support is planned but not fully configured yet. The current project includes the default Compose Multiplatform iOS template, but the build process and configurations are not finalized.

---

## Build & Configuration

### Can I have multiple games installed on the same device?

Yes. To have multiple games installed simultaneously without them overwriting each other, each game must have a unique `ludens.applicationId` configured in its `gradle.properties` file (e.g., `com.mystudio.game1` and `com.mystudio.game2`).

### How do I change the app name and icon?

- **App name**: Edit `ludens.applicationName` and `ludens.applicationLauncherName` in `gradle.properties`.
- **App icon**: Replace images in `composeApp/src/androidMain/res/mipmap-*` directories or use Android Studio's **Image Asset Studio** (right-click `res` > **New > Image Asset**).

See the [Configuration](/configuration/android/) page for full details.

### What is `keystore.properties`?

It's a file that stores your signing credentials for release builds. It contains the path to your `.jks` keystore file and the associated passwords. A template (`keystore.properties.template`) is included in the repository.

### Can I generate an AAB (Android App Bundle) instead of an APK?

The default build tasks generate APK files. To generate an AAB, you can run:

```bash
./gradlew bundleRelease
```

The AAB will be output to `composeApp/build/outputs/bundle/release/`.

---

## Game & Runtime

### Where are save files stored?

Save files are managed entirely by the RPG Maker engine within the WebView using HTML5 Web Storage APIs (**LocalStorage** or **IndexedDB**). Ludens does not provide a separate native save system. These web databases are heavily sandboxed by the mobile operating system for security.

:::caution[Data Loss Warning]
Clearing the application's data in the device settings, or uninstalling the app entirely, will immediately and permanently delete the game's save files.
:::

### Can I use WebGL?

Yes. WebGL is enabled by default and can be toggled in the in-app settings. Toggling WebGL requires an application restart and is only available for RPG Maker MV, as MZ requires WebGL by default.

### What is the YDP_Ludens.js plugin?

It's an optional RPG Maker plugin that:
- Fixes font loading verification errors on older WebView versions.
- Enables additional client-side features supported by the Ludens native layer.
- Should be placed as the **first plugin** in your plugin manager.
- Available at the [rpgm-plugins repository](https://github.com/yoimerdr/rpgm-plugins).

### Can I use audio in the game?

Yes. The WebView supports audio playback. The in-app settings include a **Mute Audio** toggle that uses the engine's API (MV/MZ) to silence the game.

---

## Troubleshooting

### My game shows a 404 placeholder

This is the placeholder that appears if the assets haven't been placed correctly.

Common causes:
1. The `www` folder is not in the correct location (`composeResources/files/www/`).
2. The `index.html` file is missing from the `www` folder root.
3. A plugin is causing an error. Check the device's WebView console logs using **Chrome DevTools** remote debugging.


### The build fails with a Gradle error

1. Ensure you are using **JDK 17** or higher.
2. Run **File > Sync Project with Gradle Files** in Android Studio.
3. Try **File > Invalidate Caches / Restart**.
4. Verify that the Android SDK is correctly installed via **File > Settings > Android SDK**.

### Controls are not responding

1. Verify that controls are enabled in the in-app settings.
2. Check that the button key mappings match the keys your game expects.
3. Some plugins may override input handling — test without third-party plugins to isolate the issue.
