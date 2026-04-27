---
title: Compilación Android
description: Cómo compilar APKs de debug y release para tu proyecto Ludens.
---

Esta guía cubre la compilación de APKs tanto de debug (pruebas) como de release (producción) para tu
juego de RPG Maker envuelto con Ludens.

## Configuración de la App

Antes de compilar tu APK, asegúrate de haber configurado la identidad de tu aplicación en
`ludens.properties`. Este archivo en la raíz del proyecto controla cómo Android identifica tu
aplicación.

```properties title="ludens.properties"
ludens.android.id=com.tudominio.juego
ludens.android.name=Mi Juego RPG
ludens.android.launcherName=Juego RPG
ludens.android.version=1.0.0
```

Para ver la lista completa de propiedades disponibles, incluyendo permisos y aceleración de
hardware, consulta la guía de [Configuración Android](/es/configuration/android/).

## Compilación Debug

Para pruebas rápidas en un emulador o dispositivo físico.

### Opción A: Configuración de Ejecución (Recomendado)

Si prefieres usar la interfaz de Android Studio:

1. Abre el menú de configuraciones y selecciona **Edit Configurations...**.

![Acceso al menú de configuraciones.](../../../../../assets/images/guide/ludens-build-configurations.png)

2. Agrega una nueva tarea de **Gradle**.

![Creación de una nueva tarea de Gradle.](../../../../../assets/images/guide/ludens-build-configurations-gradle-task.png)

3. Nombra la tarea (ej. `assembleDebug`) y en el campo **Arguments** escribe: `assembleDebug`.

![Configuración de los argumentos de la tarea.](../../../../../assets/images/guide/ludens-build-configurations-gradle-task-assembleDebug.png)

4. Haz clic en **Run** para iniciar la compilación.

### Opción B: Terminal

1. Abre la pestaña **Terminal** en Android Studio.
2. Ejecuta:
   ```bash
   ./gradlew assembleDebug
   ```

### Resultado

El APK se generará en:

```text
composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

![Resultado exitoso de la compilación.](../../../../../assets/images/guide/ludens-build-debug.png)

:::note[Application ID de Debug]
La compilación de debug automáticamente añade `.debug` a tu `applicationId`. Esto te permite
instalar tanto la versión de prueba como la versión de producción en el mismo dispositivo sin que
entren en conflicto.
:::

:::tip[Pruebas y Solución de Problemas]
Instala este APK en un emulador o dispositivo real para verificar que el juego carga y los plugins
funcionan correctamente antes de proceder con una compilación release.

Si encuentras errores extraños de compilación o tus assets no se actualizan, puedes limpiar la caché
de Gradle ejecutando:

```bash
./gradlew clean
```

:::

## Compilación Release

Para generar un APK firmado para distribución en producción.

### Opción A: Asistente de Android Studio

Esta opción te guía paso a paso para firmar tu aplicación.

1. Ve a **Build > Generate Signed Bundle / APK**.

![Iniciar el asistente de firma.](../../../../../assets/images/guide/ludens-build-configurations-sign.png)

2. Selecciona **APK** y haz clic en **Next**.

3. Configura tu Keystore:

   **Crear Nueva** — Si no tienes una, haz clic en **Create new...**.

   ![Creación de un nuevo Keystore. Guarda bien la contraseña y el archivo .jks.](../../../../../assets/images/guide/ludens-build-configurations-sign-creation.png)

   **Usar Existente** — Si ya tienes una, cárgala e introduce las credenciales.

   ![Credenciales del Keystore.](../../../../../assets/images/guide/ludens-build-configurations-sign-use.png)

4. Selecciona el build flavor **release** y haz clic en **Create**.

![Selección de la variante de salida.](../../../../../assets/images/guide/ludens-build-configurations-sign-release.png)

5. **Resultado**:

![Notificación de APK generado exitosamente.](../../../../../assets/images/guide/ludens-build-release.png)

### Opción B: Tarea Gradle

Ideal para automatizar la compilación, pero requiere configuración manual previa.

1. Asegúrate de tener tu archivo `.jks` (Keystore) generado. Puedes usar el Paso 3 de la Opción A
   para crearlo.

2. Crea o edita el archivo `keystore.properties` en la raíz del proyecto con la ruta y credenciales:

   ```properties
   storePassword=tu_store_password
   keyPassword=tu_key_password
   keyAlias=tu_alias
   storeFile=C:/Ruta/A/Tu/llave.jks
   ```

3. Ejecuta la tarea `assembleRelease`:

   ```bash
   ./gradlew assembleRelease
   ```

4. **Resultado**:

![Resultado de la compilación desde Gradle.](../../../../../assets/images/guide/ludens-build-release-gradle.png)

### Ubicación del Archivo

| Método                   | Ubicación del APK                                                           |
|--------------------------|-----------------------------------------------------------------------------|
| **Asistente (Opción A)** | `composeApp/release/` (o la carpeta que seleccionaste durante el asistente) |
| **Gradle (Opción B)**    | `composeApp/build/outputs/apk/release/composeApp-release.apk`               |

:::caution[Seguridad del Keystore]
Mantén tu archivo `.jks` y las contraseñas seguros. Si los pierdes, no podrás actualizar tu
aplicación en la Play Store.
:::
