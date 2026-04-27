---
title: Android Configuration
description: Android specific configuration for Ludens.
---

Android-specific application identity and manifest properties are managed through
`ludens.properties` in the project root. This system allows you to customize your app without
touching Kotlin code or complex build scripts.

## Application Identity

These properties define the package name, version, and names shown by the Android system.

```properties
# ----- Android Identity -----
ludens.android.id=com.ludens.compose.ludens
ludens.android.version=0.3.0
ludens.android.versionCode=1
ludens.android.name=Ludens
ludens.android.launcherName=Ludens
ludens.android.minSDK=21
ludens.android.targetSDK=36
ludens.android.immersive=true
```

Configure these properties using the `ludens.android.*` prefix:

| Property       | Type    | Default                     | Description                                        |
|----------------|---------|-----------------------------|----------------------------------------------------|
| `id`           | String  | `com.ludens.compose.ludens` | Unique application identifier (package name).      |
| `version`      | String  | `0.3.0`                     | Visible version name shown to the user.            |
| `versionCode`  | Integer | `1`                         | Internal version code used for Play Store updates. |
| `name`         | String  | `Ludens`                    | Full application name in system settings.          |
| `launcherName` | String  | `Ludens`                    | Name displayed under the home screen icon.         |
| `minSDK`       | Integer | `21`                        | Minimum Android API level supported.               |
| `targetSDK`    | Integer | `36`                        | Target Android API level for the build.            |
| `immersive`    | Boolean | `true`                      | Enables immersive mode (hides system bars).        |

:::note
The `id` must follow the reverse domain format and must be unique if you plan to publish on the
Google Play Store. Changing it after publication creates a new listing.
:::

## Application Icon

Replace the default icon by updating the images in `composeApp/src/androidMain/res/mipmap-*`
directories, or use the **Image Asset Studio** tool in Android Studio:

1. Right-click on `composeApp/src/androidMain/res`.
2. Select **New > Image Asset**.
3. Configure the icon using your game's artwork.

![Using Image Asset Studio to update the application icon.](../../../assets/images/guide/ludens-application-icon.png)

The `mipmap-*` directories contain icons at different resolutions:

| Directory        | Resolution |
|------------------|------------|
| `mipmap-mdpi`    | 48×48 px   |
| `mipmap-hdpi`    | 72×72 px   |
| `mipmap-xhdpi`   | 96×96 px   |
| `mipmap-xxhdpi`  | 144×144 px |
| `mipmap-xxxhdpi` | 192×192 px |

## Manifest Configuration

Ludens automatically generates the `AndroidManifest.xml` based on these properties. This allows for
safe and predictable manifest management.

```properties
# ----- Android Manifest -----
ludens.android.manifest.allowBackup=true
ludens.android.manifest.largeHeap=true
ludens.android.manifest.hardwareAccelerated=true
ludens.android.manifest.screenOrientation=sensorLandscape
ludens.android.manifest.usesCleartextTraffic=false
ludens.android.manifest.resizeableActivity=false
```

### Game Orientation

By default, Ludens forces the application into landscape mode using `sensorLandscape`. This ensures
the game rotates according to the device sensor but stays in a horizontal orientation. To change
this, modify the `ludens.android.manifest.screenOrientation` property.

#### Common Orientations

| Value             | Behavior                                                                                 |
|-------------------|------------------------------------------------------------------------------------------|
| `sensorLandscape` | (Default) Landscape only, auto-rotates between left and right landscape based on sensor. |
| `sensorPortrait`  | Portrait only, auto-rotates between normal and upside-down portrait based on sensor.     |
| `landscape`       | Fixed landscape orientation (ignoring sensor).                                           |
| `portrait`        | Fixed portrait orientation (ignoring sensor).                                            |
| `fullSensor`      | Allows rotation to any of the 4 orientations.                                            |

### Property Reference

Configure these properties using the `ludens.android.manifest.*` prefix:

| Property               | Type    | Mapping                        | Description                                           |
|------------------------|---------|--------------------------------|-------------------------------------------------------|
| `allowBackup`          | Boolean | `android:allowBackup`          | Whether Android can back up app data to Google Drive. |
| `largeHeap`            | Boolean | `android:largeHeap`            | Requests a larger heap for memory intensive games.    |
| `hardwareAccelerated`  | Boolean | `android:hardwareAccelerated`  | Enables GPU acceleration for the UI.                  |
| `screenOrientation`    | String  | `android:screenOrientation`    | Defines the display orientation (see table above).    |
| `usesCleartextTraffic` | Boolean | `android:usesCleartextTraffic` | Allows cleartext HTTP traffic (Not recommended).      |
| `resizeableActivity`   | Boolean | `android:resizeableActivity`   | Allows the activity to be resized by the system.      |

## Permissions

If your RPG Maker plugins require access to device hardware or network services, you must declare
those permissions.
Ludens makes this easy with built-in toggles.

For example, if your game fetches highscores from an online leaderboard, you will need the
`internet` permission. If your game should prevent the screen from turning off during long
cutscenes, use the `wakeLock`permission.

```properties
# ----- Android Permissions -----
ludens.android.permissions.internet=false
ludens.android.permissions.networkState=false
ludens.android.permissions.wakeLock=false
ludens.android.permissions.accessWifiState=false
ludens.android.permissions.changeWifiState=false
```

Declare these permissions under the `ludens.android.permissions.*` prefix:

| Property          | Type    | Mapping                | Description                                     |
|-------------------|---------|------------------------|-------------------------------------------------|
| `internet`        | Boolean | `INTERNET`             | Grants network access for online features.      |
| `networkState`    | Boolean | `ACCESS_NETWORK_STATE` | Access to connectivity and network type info.   |
| `wakeLock`        | Boolean | `WAKE_LOCK`            | Keeps the CPU awake while rendering or playing. |
| `accessWifiState` | Boolean | `ACCESS_WIFI_STATE`    | Grants access to Wi-Fi connection state.        |
| `changeWifiState` | Boolean | `CHANGE_WIFI_STATE`    | Permission to change Wi-Fi connectivity.        |

## Advanced: Manual Manifest Customization

For configurations not covered by `ludens.properties`, edit the manifest directly at:
`composeApp/src/androidMain/AndroidManifest.xml`

:::caution
Modifying the manifest incorrectly can cause your application to crash on startup. Manual changes
may conflict with the automatic generator.
:::

### Adding Custom Permissions

If your plugins require hardware access like the Camera or Microphone, add the `<uses-permission>`
tag as a direct child
of the `<manifest>` element.

Example: Adding Microphone permission:

```xml

<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Add new permissions here -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <application>...</application>
</manifest>
```

:::caution[Runtime Permissions]
For "dangerous" permissions (Camera, Location, etc.) on Android 6.0+, you must also request
permission at runtime via a
custom bridge. Ludens currently does not provide a native permission bridge out of the box.
:::

## Signing Configuration

For release builds, you need a signing keystore. Create a `keystore.properties` file in the project
root based on the
`keystore.properties.template` file:

```properties
storePassword=your_store_password
keyPassword=your_key_password
keyAlias=your_alias
storeFile=C:/Path/To/Your/key.jks
```

:::caution[Security]
Never commit your `keystore.properties` file or `.jks` keystore to version control.
:::
