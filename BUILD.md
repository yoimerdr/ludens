# Step-by-Step Build Guide
[Leer en Español](BUILD.es.md)

This guide details the complete process to configure, customize, and generate an APK/AAB file for your RPG Maker MV/MZ game using **Ludens**.

## Environment Setup

### Android Studio
Download and install Android Studio. For this project, **Otter 2 Feature Drop | 2025.2.2** or higher is recommended.

*   **Download Link**: [developer.android.com/studio](https://developer.android.com/studio)
*   **Installation Guide**: [developer.android.com/studio/install](https://developer.android.com/studio/install)
*   [Official Configuration Guide](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)

<p align="center">
  <img src="resources/images/guide/android-studio-setup.png" alt="Android Studio Setup" height="320">
  <br>
  <em>Figure 1: Android Studio welcome screen where you can verify the installed version.</em>
</p>

Ensure the following are included during installation:
*   Android SDK
*   Android SDK Platform-Tools
*   Android Virtual Device (Recommended for testing)

### Java Development Kit (JDK)
The project requires **Java 17**. Android Studio usually includes a compatible version (JetBrains Runtime), but you can install JDK 17 manually if you prefer.

*   **Official Installation Guide**: [How to install the JDK](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html)

## Game Prerequisites (RPG Maker)

Before exporting your game, consider the following:

### Plugins and Compatibility
*   **Warning**: Many RPG Maker plugins are designed for PC (desktop) only. Verify that your plugins are compatible with mobile environments (WebView).
*   **Recommended Plugin**: [`YDP_Ludens.js`](resources/plugins/js/YDP_Ludens.js).
    *   This plugin helps fix font loading issues on older WebView versions and enables extra functionalities.
    *   **Important**: Place it as the **first plugin** in your list.

<p align="center">
  <img src="resources/images/guide/plugin-manager-ydp_ludens.png" alt="Plugin Manager with YDP_Ludens.js" height="320">
  <br>
  <em>Figure 2: The YDP_Ludens.js plugin must be placed at the top of the list to ensure correct loading.</em>
</p>

## Ludens Project Configuration

### Get the Project
1.  Clone this repository or download it as a ZIP and extract it.
2.  Open Android Studio.
3.  Select **Open** and navigate to the `ludens` project folder.
4.  Wait for Gradle to finish syncing (Download/Sync).

<p align="center">
  <img src="resources/images/guide/ludens-android-studio-project.png" alt="Ludens Project in Android Studio" height="320">
  <br>
  <em>Figure 3: View of the project in Android Studio once Gradle sync is complete.</em>
</p>

### Directory Structure
Familiarize yourself with the location of key files:
*   `composeApp/src/commonMain/composeResources/files`: Your game goes here.
*   `gradle.properties`: Here you will configure the name and version.

## Exporting the Game
1.  Open your project in RPG Maker MV or MZ.
2.  Go to **File** > **Deployment**.
3.  Select the **Android / iOS** platform (Recommended). If unavailable, use **Web Browsers**.
4.  Export the game.

<p align="center">
  <img src="resources/images/guide/ludens-rpgmmv-deployment.png" alt="Deployment Option in RPG Maker" height="320">
  <br>
  <em>Figure 4: Select the Android / iOS option when deploying your game.</em>
</p>

## Asset Integration

This is the most critical step.

1.  Navigate to the project folder in your file explorer:
    `composeApp/src/commonMain/composeResources/files`
2.  Copy the entire **`www`** folder from your export and paste it inside `files`, or if you only have the game assets, create the `www` folder and paste the files inside it.

**Mandatory Structure:**
The application expects to find `index.html` inside `www`.
<p align="center">
  <img src="resources/images/guide/ludens-android-studio-files.png" alt="File structure in Android Studio" height="320">
  <br>
  <em>Figure 5: Android Studio view showing the www folder and index.html file inside composeResources/files</em>
</p>

## Android
### Customization

Edit the `gradle.properties` file in the project root.

```properties
# Unique ID (format com.domain.name)
ludens.applicationId=com.mystudio.rpg

# Visible Version (e.g. 1.0)
ludens.applicationVersion=1.0

# Name in Settings
ludens.applicationName=My Epic RPG

# Name in Launcher (Icon)
ludens.applicationLauncherName=My RPG
```

<p align="center">
  <img src="resources/images/guide/ludens-project-properties.png" alt="Configuration in gradle.properties" height="320">
  <br>
  <em>Figure 6: Modifying project properties to customize ID, version, and name.</em>
</p>

#### App Icon
Replace the images in `composeApp/src/androidMain/res/mipmap-*` or use the **Image Asset Studio** tool:
1.  Right-click on `composeApp/src/androidMain/res`.
2.  New > Image Asset.

<p align="center">
  <img src="resources/images/guide/ludens-application-icon.png" alt="Configuring the Icon" height="320">
  <br>
  <em>Figure 7: Using Image Asset Studio to update the application icon.</em>
</p>

### Build and Test (Debug)

For testing during development, you can use the terminal or create a configuration in Android Studio.

#### Option A: Run Configuration (Recommended)
If you prefer using the Android Studio interface:

1.  Open the configurations menu and select **Edit Configurations...**.
<p align="center">
  <img src="resources/images/guide/ludens-build-configurations.png" alt="Edit Configurations" height="320">
  <br>
  <em>Figure 8: Accessing the configurations menu.</em>
</p>

2.  Add a new **Gradle** task.
<p align="center">
  <img src="resources/images/guide/ludens-build-configurations-gradle-task.png" alt="New Gradle Task" height="320">
  <br>
  <em>Figure 9: Creating a new Gradle task.</em>
</p>

3.  Name the task (e.g., `assembleDebug`) and in the **Arguments** field type: `assembleDebug`.
<p align="center">
  <img src="resources/images/guide/ludens-build-configurations-gradle-task-assembleDebug.png" alt="Configuring assembleDebug" height="320">
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
Regardless of the method, the APK will appear in: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`.

> **Recommendation**: Install this APK on an emulator or real device to verify that the game loads and plugins work correctly.

<p align="center">
  <img src="resources/images/guide/ludens-build-debug.png" alt="Build Result" height="320">
  <br>
  <em>Figure 11: Successful build result.</em>
</p>

### Create Production Version (Release)

To generate the final signed APK:

#### Option A: Android Studio Wizard
This option guides you step-by-step to sign your application.

1.  Go to **Build > Generate Signed Bundle / APK**.
<p align="center">
  <img src="resources/images/guide/ludens-build-configurations-sign.png" alt="Build Menu" height="320">
  <br>
  <em>Figure 12: Starting the signing wizard.</em>
</p>

2.  Select **APK** and click Next.

3.  Configure your Keystore.

    *   **Create New**: If you don't have one, click **Create new...**.
        <p align="center">
          <img src="resources/images/guide/ludens-build-configurations-sign-creation.png" alt="Create Keystore" height="320">
          <br>
          <em>Figure 13: Creating a new Keystore. Keep your password and the .jks file safe.</em>
        </p>

    *   **Use Existing**: If you already have one, load it and enter credentials.
        <p align="center">
          <img src="resources/images/guide/ludens-build-configurations-sign-use.png" alt="Use Keystore" height="320">
          <br>
          <em>Figure 14: Keystore credentials.</em>
        </p>

4.  Select the **release** build flavor and click **Create**.
<p align="center">
  <img src="resources/images/guide/ludens-build-configurations-sign-release.png" alt="Release Selection" height="320">
  <br>
  <em>Figure 15: Output variant selection.</em>
</p>

5.  **Result**:
<p align="center">
  <img src="resources/images/guide/ludens-build-release.png" alt="Wizard Build Result" height="320">
  <br>
  <em>Figure 16: Notification of successfully generated APK.</em>
</p>

#### Option B: Gradle Task (Configurable)
Similar to the Debug process, this option is ideal for automating the build but requires prior manual configuration.

1.  Ensure you have your `.jks` file (Keystore) generated (you can use step 3 of Option A to create it).
2.  Create/Edit the `keystore.properties` file in the project root with the path and credentials:

    ```properties
    storePassword=your_store_password
    keyPassword=your_key_password
    keyAlias=your_alias
    storeFile=C:/Path/To/Your/key.jks
    ```

3.  Run the `assembleRelease` task from the configurations window (as seen in the Debug section) or from the terminal:
    ```bash
    ./gradlew assembleRelease
    ```

4.  **Result**:
<p align="center">
  <img src="resources/images/guide/ludens-build-release-gradle.png" alt="Gradle Build Result" height="320">
  <br>
  <em>Figure 17: Build result from Gradle.</em>
</p>

#### File Location
*   **If you used Option A (Wizard)**: By default, Android Studio usually places it in `composeApp/release/` (or the folder you selected during the destination step).
*   **If you used Option B (Gradle)**: The file will be in `composeApp/build/outputs/apk/release/composeApp-release.apk`.


## iOS

*Coming soon* - The current configuration is the default for Compose Multiplatform.
