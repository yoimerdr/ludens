---
title: Android Build
description: How to build debug and release APKs for your Ludens project.
---

This guide covers building both debug (testing) and release (production) builds for your RPG Maker game wrapped with Ludens.

## Debug Build

For quick testing on an emulator or physical device.

### Option A: Run Configuration (Recommended)

If you prefer using the Android Studio interface:

1. Open the configurations menu and select **Edit Configurations...**.

![Accessing the configurations menu.](../../../../assets/images/guide/ludens-build-configurations.png)

2. Add a new **Gradle** task.

![Creating a new Gradle task.](../../../../assets/images/guide/ludens-build-configurations-gradle-task.png)

3. Name the task (e.g., `assembleDebug`) and in the **Arguments** field type: `assembleDebug`.

![Configuring the task arguments.](../../../../assets/images/guide/ludens-build-configurations-gradle-task-assembleDebug.png)

4. Click **Run** to start the build.

### Option B: Terminal

1. Open the **Terminal** tab in Android Studio.
2. Run:
   ```bash
   ./gradlew assembleDebug
   ```

### Result

The APK will be generated at:

```text
composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

![Successful build result.](../../../../assets/images/guide/ludens-build-debug.png)

:::note[Debug Application ID]
The debug build automatically appends `.debug` to your `applicationId`. This allows you to install both the debug version and the production version on the same device without them conflicting.
:::

:::tip[Testing & Troubleshooting]
Install this APK on an emulator or real device to verify that the game loads and plugins work correctly before proceeding to a release build.

If you encounter strange build errors or your assets aren't updating, you can clear the Gradle cache by running:

```bash
./gradlew clean
```

:::

## Release Build

To generate a signed APK for production distribution.

### Option A: Android Studio Wizard

This option guides you step-by-step through signing your application.

1. Go to **Build > Generate Signed Bundle / APK**.

![Starting the signing wizard.](../../../../assets/images/guide/ludens-build-configurations-sign.png)

2. Select **APK** and click **Next**.

3. Configure your Keystore:

   **Create New** — If you don't have one, click **Create new...**.

   ![Creating a new Keystore. Keep your password and the .jks file safe.](../../../../assets/images/guide/ludens-build-configurations-sign-creation.png)

   **Use Existing** — If you already have one, load it and enter credentials.

   ![Keystore credentials.](../../../../assets/images/guide/ludens-build-configurations-sign-use.png)

4. Select the **release** build flavor and click **Create**.

![Output variant selection.](../../../../assets/images/guide/ludens-build-configurations-sign-release.png)

5. **Result**:

![Notification of successfully generated APK.](../../../../assets/images/guide/ludens-build-release.png)

### Option B: Gradle Task

Ideal for automating the build, but requires prior manual configuration.

1. Ensure you have your `.jks` file (Keystore) generated. You can use Step 3 of Option A to create it.

2. Create or edit the `keystore.properties` file in the project root with the path and credentials:

   ```properties
   storePassword=your_store_password
   keyPassword=your_key_password
   keyAlias=your_alias
   storeFile=C:/Path/To/Your/key.jks
   ```

3. Run the `assembleRelease` task:

   ```bash
   ./gradlew assembleRelease
   ```

4. **Result**:

![Build result from Gradle.](../../../../assets/images/guide/ludens-build-release-gradle.png)

### Output Location

| Method                | APK Location                                                         |
| --------------------- | -------------------------------------------------------------------- |
| **Wizard (Option A)** | `composeApp/release/` (or the folder you selected during the wizard) |
| **Gradle (Option B)** | `composeApp/build/outputs/apk/release/composeApp-release.apk`        |

:::caution[Keystore Security]
Keep your `.jks` file and passwords secure. If you lose them, you will not be able to update your application on the Play Store.
:::
