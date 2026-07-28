# Step-by-Step Build Guide

<p align="center">
  <a href="BUILD.es.md">Leer en Español</a> | <a href="https://tryludens.vercel.app/">Web Docs</a>
</p>

This guide details the complete process to configure, customize, and generate an APK/AAB file for your RPG Maker MV/MZ game using **Ludens**.

## Environment Setup

### Android Studio

Download and install Android Studio. For this project, **Otter 2 Feature Drop | 2025.2.2** or higher is recommended.

- **Download Link**: [developer.android.com/studio](https://developer.android.com/studio)
- **Installation Guide**: [developer.android.com/studio/install](https://developer.android.com/studio/install)
- [Official Configuration Guide](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)

<p align="center">
  <img src="docs/src/assets/images/guide/android-studio-setup.png" alt="Android Studio Setup" height="320">
  <br>
  <em>Figure 1: Android Studio welcome screen where you can verify the installed version.</em>
</p>

Ensure the following are included during installation:

- Android SDK
- Android SDK Platform-Tools
- Android Virtual Device (Recommended for testing)

### Java Development Kit (JDK)

The project requires **Java 17**. Android Studio usually includes a compatible version (JetBrains Runtime), but you can install JDK 17 manually if you prefer.

- **Official Installation Guide**: [How to install the JDK](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html)

## Game Prerequisites (RPG Maker)

Before exporting your game, consider the following:

### Plugins and Compatibility

- **Warning**: Many RPG Maker plugins are designed for PC (desktop) only. Verify that your plugins are compatible with mobile environments (WebView).
- **Recommended Plugin**: [`YDP_Ludens.js`](resources/plugins/js/YDP_Ludens.js). You can find the latest version and other recommended plugins at the [rpgm-plugins repository](https://github.com/yoimerdr/rpgm-plugins).
  - This plugin helps fix font loading issues on older WebView versions and enables extra functionalities.
  - **Important**: Place it as the **first plugin** in your list.

<p align="center">
  <img src="docs/src/assets/images/guide/plugin-manager-ydp_ludens.png" alt="Plugin Manager with YDP_Ludens.js" height="320">
  <br>
  <em>Figure 2: The YDP_Ludens.js plugin must be placed at the top of the list to ensure correct loading.</em>
</p>

## Ludens Project Configuration

### Get the Project

1. Clone the repository.
   - To use the latest release version (replace `<latest_tag>` with the latest version tag, e.g. `0.4.0`):
     ```bash
     git clone --branch <latest_tag> https://github.com/yoimerdr/ludens.git
     ```
   - To use the latest development version, clone the `develop` branch:
     ```bash
     git clone --branch develop https://github.com/yoimerdr/ludens.git
     ```
   - Alternatively, download it as a ZIP from the [GitHub Releases page](https://github.com/yoimerdr/ludens/releases) and extract it.
2. Open Android Studio.
3.  Select **Open** and navigate to the `ludens` project folder.
4.  Wait for Gradle to finish syncing (Download/Sync).

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-android-studio-project.png" alt="Ludens Project in Android Studio" height="320">
  <br>
  <em>Figure 3: View of the project in Android Studio once Gradle sync is complete.</em>
</p>

### Directory Structure

Familiarize yourself with the location of key files:

- `shared/src/commonMain/composeResources/files`: Your game goes here.
- [`ludens.properties`](ludens.properties): Main Ludens configuration (app identity, manifest flags, permissions, settings preset).
- [`gradle.properties`](gradle.properties): Gradle/Kotlin performance and build-system options.
- [`keystore.properties`](keystore.properties): Local release signing credentials (do not commit).

## Exporting the Game

1.  Open your project in RPG Maker MV or MZ.
2.  Go to **File** > **Deployment**.
3.  Select the **Android / iOS** platform (Recommended). If unavailable, use **Web Browsers**.
4.  Export the game.

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-rpgmmv-deployment.png" alt="Deployment Option in RPG Maker" height="320">
  <br>
  <em>Figure 4: Select the Android / iOS option when deploying your game.</em>
</p>

## Asset Integration

This is the most critical step.

> [!WARNING]
> **Case Sensitivity**: Windows file system is **case-insensitive** (`/Path/Example` = `/path/example`), but Android and iOS use **case-sensitive** file systems. Ensure that all file references in your game's code (images, audio, data files) use **exact matching names**. If a file is named `MyImage.png`, you must reference it as `MyImage.png`, not `myimage.png`. This is a common source of silent failures on mobile.
>
> **Tip**: You can use the plugin **[YDP_CrossAssets](https://github.com/yoimerdr/rpgm-plugins)** from the rpgm-plugins repository to automatically normalize file paths and avoid this issue.

You have two valid options to integrate your assets:

**Option A (Recommended): Root Folder Synchronization**

1. Navigate to the root of the Ludens project repository.
2. Locate the `project/www/` folder.
3. Copy the contents of your exported **`www`** folder into `project/www/`.

> [!NOTE]
> The build system automatically synchronizes assets from `project/www/` to the required internal folder (`composeResources/files/www`). This synchronization **only** happens if there are files in `project/www/` other than the `index.html`.

**Option B: Internal Folder Direct Placement**

1. Navigate to the internal resources folder:
   `shared/src/commonMain/composeResources/files/`
2. Copy the entire **`www`** folder from your export and paste it inside `files`.

**Mandatory Internal Structure:**
After using either option, the application expects to find `index.html` inside the internal `www` directory.

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-android-studio-files.png" alt="File structure in Android Studio" height="320">
  <br>
  <em>Figure 5: Android Studio view showing the www folder and index.html file inside composeResources/files</em>
</p>

## Localization & Translations

Ludens supports multiple languages for its wrapper client UI.

> [!IMPORTANT]
> This localization system translates **only** the native client/wrapper UI (such as the Settings screen options, control overlays, and native dialogs). It **does not** translate the actual RPG Maker game content, text, or dialogues inside the WebView. Game dialogue and assets must be translated using RPG Maker's own translation plugins or files inside your exported `www/` directory.

The translation system works with two main components:

### 1. Restricting Available Languages
By default, the compilation includes all languages found in the project. If your game is only intended for a single language or a specific set of languages, you can restrict the options shown in the wrapper's settings menu by modifying [`ludens.properties`](ludens.properties):

```properties
# Limit the settings menu to only show English and Spanish
ludens.languages.available=en,es
```

### 2. Adding a New Language Translation
To add translation keys for a language not provided by default:

1. Create a directory named after the ISO language tag (e.g. `fr` for French, `ja` for Japanese) inside `project/assets/languages/`:
   `project/assets/languages/fr/`
2. Create a file named `strings.xml` inside that directory.
3. Copy the contents of the English [strings.xml](project/assets/languages/en/strings.xml) file as a base and translate the string values. For example:
   ```xml
   <resources>
       <string name="settings_title">Paramètres</string>
       <string name="tab_system">Système</string>
       ...
   </resources>
   ```
4. If you are using the `ludens.languages.available` property to restrict languages, make sure to add your new language tag to the list.
5. Rebuild the project. The build system will automatically process the assets and generate the translation directories during compilation.

> [!WARNING]
> **DO NOT** edit or add `strings.xml` files directly inside `shared/src/commonMain/composeResources/values*`. During build compilation, a custom Gradle task cleans these folders and regenerates them from the source of truth (`project/assets/languages/`). Any manual changes inside `composeResources` will be **permanently lost**.

## Android

### Customization

Edit the [`ludens.properties`](ludens.properties) file in the project root.

```properties
# Android application id
ludens.android.id=com.mystudio.rpg

# Visible version (e.g. 1.0.0)
ludens.android.version=1.0.0

# Integer version code
ludens.android.versionCode=1

# Name in Settings
ludens.android.name=My Epic RPG

# Name in Launcher (Icon)
ludens.android.launcherName=My RPG
```

> [!IMPORTANT]
> Ludens now reads [`ludens.properties`](ludens.properties) first. If missing, it can read the same `ludens.*` keys from [`gradle.properties`](gradle.properties); legacy keys like `ludens.applicationId` are not supported.

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-project-properties.png" alt="Configuration in ludens.properties" height="320">
  <br>
  <em>Figure 6: Modifying project properties to customize ID, version, and name.</em>
</p>

#### Optional Android Runtime/Manifest Configuration

You can also customize Android behavior using [`ludens.properties`](ludens.properties):

```properties
# Manifest placeholders
ludens.android.manifest.allowBackup=true
ludens.android.manifest.largeHeap=true
ludens.android.manifest.hardwareAccelerated=true
ludens.android.manifest.screenOrientation=sensorLandscape
ludens.android.manifest.usesCleartextTraffic=false
ludens.android.manifest.resizeableActivity=false

# Generated permissions manifest.
# All listed permissions are optional;
# The wrapper does not require or use features that depend on them.
# If your game needs permissions beyond basic network access,
# you may need to edit the wrapper source code.
ludens.android.permissions.internet=false
ludens.android.permissions.networkState=false
ludens.android.permissions.wakeLock=false
ludens.android.permissions.accessWifiState=false
ludens.android.permissions.changeWifiState=false
```

These values are consumed by the custom `build-logic` plugins and injected during build.

> [!TIP]
> Ludens supports additional configuration properties. Review [`ludens.properties`](ludens.properties) directly to see the full list and descriptions.

#### App Icon

Ludens includes an automated **App Icon Generator** plugin that creates launcher icons for all target platforms from a single source image (SVG or PNG).

##### Method A: Automated Generation (Recommended)

1. Place your source icon image inside the `project/assets/icons/` directory.
   - For best results, use a vector image named `icon.svg` or a high-resolution raster image named `icon.png` (at least 512x512 pixels).
   - If you want to use separate adaptive layers for Android, you can place `icon_foreground.svg`/`icon_foreground.png` and `icon_background.svg`/`icon_background.png` in that same directory.
2. Configure the icon generator in [`ludens.properties`](ludens.properties) under the `# ----- App Icon Generator -----` section. The values are automatically read by the build system:

```properties
# Name of the source master icon in project/assets/icons/
ludens.icons.name=icon

# Name of the foreground adaptive layer in project/assets/icons/
ludens.icons.foreground=icon_foreground

# Solid hex color background or resource reference
ludens.icons.background=#FDFDFD

# Android-specific configuration
ludens.icons.android.enable=true
ludens.icons.android.format=webp
ludens.icons.android.playstore=true

# iOS-specific configuration
ludens.icons.ios.enable=true

# Scale of the foreground asset inside the adaptive icon viewport
ludens.icons.scale=0.62
```

3. Build the project. The build system will automatically generate:
   - **Android**: Legacy round and square mipmap icons, XML adaptive icon sheets under `mipmap-anydpi-v26`, and vector/raster layers (`ic_launcher_foreground`, `ic_launcher_background`) placed in `androidApp/src/main/res/`.
   - **iOS**: All required AppIcon sizes (iPhone, iPad, App Store) along with the corresponding `Contents.json` asset catalog manifest under `iosApp/iosApp/Assets.xcassets/AppIcon.appiconset`.
   - **Google Play Store**: A high-resolution `ic_launcher-playstore.png` (512x512) listing icon.

##### Method B: Manual Configuration (Alternative)

If you prefer to generate your assets manually or use the standard Android developer tools:

> [!WARNING]
> **Disable Automatic Generation**: To prevent the automated generator task from overwriting your custom manual files on every build, you **MUST** disable the automatic generator inside [`ludens.properties`](ludens.properties):
> ```properties
> ludens.icons.android.enable=false
> ludens.icons.ios.enable=false
> ```

1. Right-click on the `androidApp/src/main/res` directory in Android Studio.
2. Select **New > Image Asset**.
3. Use the Asset Studio wizard to configure your layers and scale.

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-application-icon.png" alt="Configuring the Icon" height="320">
  <br>
  <em>Figure 7: Using Image Asset Studio to manually update the application icon.</em>
</p>

### Build and Test (Debug)

For testing during development, you can use the terminal or create a configuration in Android Studio.

#### Option A: Run Configuration (Recommended)

If you prefer using the Android Studio interface:

1.  Open the configurations menu and select **Edit Configurations...**.
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-configurations.png" alt="Edit Configurations" height="320">
  <br>
  <em>Figure 8: Accessing the configurations menu.</em>
</p>

2.  Add a new **Gradle** task.
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-configurations-gradle-task.png" alt="New Gradle Task" height="320">
  <br>
  <em>Figure 9: Creating a new Gradle task.</em>
</p>

3.  Name the task (e.g., `assembleDebug`) and in the **Arguments** field type: `assembleDebug`.
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-configurations-gradle-task-assembleDebug.png" alt="Configuring assembleDebug" height="320">
  <br>
  <em>Figure 10: Configuring the task arguments.</em>
</p>

#### Option B: Terminal

1.  Open the **Terminal** tab in Android Studio.
2.  Run:
    ```bash
    ./gradlew assembleDebug
    ```

#### Result

Regardless of the method, the APK will appear in: `androidApp/build/outputs/apk/debug/androidApp-debug.apk` (post-processed copy: `output/debug/Ludens-0.4.0-debug.apk`).

> [!TIP]
> Install this APK on an emulator or real device to verify that the game loads and plugins work correctly.

<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-debug.png" alt="Build Result" height="320">
  <br>
  <em>Figure 11: Successful build result.</em>
</p>

### Create Production Version (Release)

To generate the final signed APK:

#### Option A: Android Studio Wizard

This option guides you step-by-step to sign your application.

1.  Go to **Build > Generate Signed Bundle / APK**.
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-configurations-sign.png" alt="Build Menu" height="320">
  <br>
  <em>Figure 12: Starting the signing wizard.</em>
</p>

2.  Select **APK** and click Next.

3.  Configure your Keystore.
    - **Create New**: If you don't have one, click **Create new...**.
      <p align="center">
        <img src="docs/src/assets/images/guide/ludens-build-configurations-sign-creation.png" alt="Create Keystore" height="320">
        <br>
        <em>Figure 13: Creating a new Keystore. Keep your password and the .jks file safe.</em>
      </p>

    - **Use Existing**: If you already have one, load it and enter credentials.
      <p align="center">
        <img src="docs/src/assets/images/guide/ludens-build-configurations-sign-use.png" alt="Use Keystore" height="320">
        <br>
        <em>Figure 14: Keystore credentials.</em>
      </p>

4.  Select the **release** build flavor and click **Create**.
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-configurations-sign-release.png" alt="Release Selection" height="320">
  <br>
  <em>Figure 15: Output variant selection.</em>
</p>

5.  **Result**:
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-release.png" alt="Wizard Build Result" height="320">
  <br>
  <em>Figure 16: Notification of successfully generated APK.</em>
</p>

#### Option B: Gradle Task (Configurable)

Similar to the Debug process, this option is ideal for automating the build but requires prior manual configuration.

1.  Ensure you have your `.jks` file (Keystore) generated (you can use step 3 of Option A to create it).
2.  Create/Edit the [`keystore.properties`](keystore.properties) file in the project root with the path and credentials:

    ```properties
    storePassword=your_store_password
    keyPassword=your_key_password
    keyAlias=your_alias
    storeFile=C:/Path/To/Your/key.jks
    ```

    You can use [`keystore.properties.template`](keystore.properties.template) as reference.

3.  Run the `assembleRelease` task from the configurations window (as seen in the Debug section) or from the terminal:

    ```bash
    ./gradlew assembleRelease
    ```

4.  **Result**:
<p align="center">
  <img src="docs/src/assets/images/guide/ludens-build-release-gradle.png" alt="Gradle Build Result" height="320">
  <br>
  <em>Figure 17: Build result from Gradle.</em>
</p>

#### File Location

- **If you used Option A (Wizard)**: By default, Android Studio usually places it in `androidApp/release/` (or the folder you selected during the destination step).
- **If you used Option B (Gradle)**: The file will be in `androidApp/build/outputs/apk/release/androidApp-release.apk` (post-processed copy: `output/release/Ludens-0.4.0-release.apk`).

## iOS

_Coming soon_ - The current configuration is the default for Compose Multiplatform.
