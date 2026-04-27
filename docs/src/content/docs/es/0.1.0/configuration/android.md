---
title: Configuración Android
description: Configuración específica de Android para Ludens.
slug: es/0.1.0/configuration/android
---

Todas las propiedades de identidad y compilación de la aplicación se gestionan a través de `gradle.properties` en la raíz del proyecto. No se requieren cambios de código Kotlin para la personalización básica.

## Propiedades de la Aplicación

Edita el archivo `gradle.properties` en la raíz del proyecto:

```properties
# Identificador único de la aplicación (formato de dominio inverso)
ludens.applicationId=com.tuorganizacion.ejemplo

# Versión visible para el usuario
ludens.applicationVersion=1.0

# Nombre de la aplicación mostrado en ajustes del sistema
ludens.applicationName=Nombre del Juego

# Nombre corto mostrado bajo el icono de la pantalla de inicio
ludens.applicationLauncherName=Juego
```

![Modificación de las propiedades del proyecto para personalizar ID, versión y nombre.](../../../../../assets/images/guide/ludens-project-properties.png)

### Referencia de Propiedades

| Propiedad                         | Formato                 | Descripción                                                |
|-----------------------------------|-------------------------|------------------------------------------------------------|
| `ludens.applicationId`            | `com.dominio.nombre`    | Identificador único de la app. Debe ser único en la Play Store. |
| `ludens.applicationVersion`       | `x.y` (ej., `1.0`)     | Cadena de versión visible para el usuario.                  |
| `ludens.applicationName`          | Texto libre             | Nombre completo de la aplicación mostrado en ajustes del sistema. |
| `ludens.applicationLauncherName`  | Texto corto             | Nombre mostrado bajo el icono de la pantalla de inicio.     |

:::note
El `applicationId` debe seguir el formato de dominio inverso y debe ser único si planeas publicar en Google Play Store. Cambiarlo después de la publicación crea un nuevo listado.
:::

## Icono de la Aplicación

Reemplaza el icono por defecto actualizando las imágenes en los directorios `composeApp/src/androidMain/res/mipmap-*`, o usa la herramienta **Image Asset Studio** de Android Studio:

1. Clic derecho en `composeApp/src/androidMain/res`.
2. Selecciona **New > Image Asset**.
3. Configura el icono usando la imagen de tu juego.

![Uso de Image Asset Studio para actualizar el icono de la aplicación.](../../../../../assets/images/guide/ludens-application-icon.png)

Los directorios `mipmap-*` contienen iconos en diferentes resoluciones:

| Directorio      | Resolución |
|-----------------|------------|
| `mipmap-mdpi`   | 48×48 px   |
| `mipmap-hdpi`   | 72×72 px   |
| `mipmap-xhdpi`  | 96×96 px   |
| `mipmap-xxhdpi` | 144×144 px |
| `mipmap-xxxhdpi`| 192×192 px |

## Configuración de Firma

Para compilaciones release, necesitas un keystore de firma. Crea un archivo `keystore.properties` en la raíz del proyecto usando la plantilla proporcionada:

```properties
storePassword=tu_store_password
keyPassword=tu_key_password
keyAlias=tu_alias
storeFile=C:/Ruta/A/Tu/llave.jks
```

Un archivo `keystore.properties.template` está incluido en el repositorio como referencia.

:::caution[Seguridad]
Nunca hagas commit de tu archivo `keystore.properties` o del keystore `.jks` al control de versiones.
:::

## Configuración del Manifest

Para una configuración más avanzada más allá de lo que ofrece `gradle.properties`, puedes editar directamente el archivo `AndroidManifest.xml`.

El archivo manifest se encuentra en:
`composeApp/src/androidMain/AndroidManifest.xml`

:::caution
Modificar el manifest incorrectamente puede causar que tu aplicación se cierre al inicio o no compile. Asegúrate de entender los cambios que estás haciendo.
:::

### Orientación del Juego

Por defecto, Ludens fuerza la aplicación al modo horizontal usando `sensorLandscape`. Esto asegura que el juego gire de acuerdo al sensor del dispositivo pero se mantenga en orientación horizontal.

Para cambiar esto, localiza la etiqueta `<activity>` en tu manifest y modifica el atributo `android:screenOrientation`.

### Orientaciones Comunes

| Valor | Comportamiento |
|-------|----------------|
| `sensorLandscape` | (Por defecto) Solo horizontal, gira automáticamente entre horizontal izquierdo y derecho según el sensor. |
| `sensorPortrait` | Solo vertical, gira automáticamente entre vertical normal e invertido según el sensor. |
| `landscape` | Orientación horizontal fija (ignora el sensor). |
| `portrait` | Orientación vertical fija (ignora el sensor). |
| `fullSensor` | Permite la rotación a cualquiera de las 4 orientaciones. |

**Ejemplo para un juego en vertical (portrait):**

```xml
<activity
    android:exported="true"
    android:screenOrientation="sensorPortrait"
    android:configChanges="orientation|screenSize"
    android:name=".MainActivity"
    android:label="@string/app_launcher_name">
    ...
</activity>
```

### Añadir Permisos

Si tus plugins de RPG Maker requieren acceso al hardware del dispositivo (como la cámara, el micrófono o acceso a internet), debes declarar esos permisos en el manifest. Ludens no impone el uso de ningún permiso por defecto para mantener la aplicación lo más respetuosa posible con la privacidad.

Por ejemplo, si tu juego cuenta con un plugin para un mini-juego de AR (Realidad Aumentada), necesitarás el permiso `CAMERA`. O si tu juego obtiene las puntuaciones más altas de una tabla de clasificación en línea, necesitarás `INTERNET`.

Añade la etiqueta `<uses-permission>` como hijo directo del elemento `<manifest>` (fuera de la etiqueta `<application>`).

:::caution[Permisos en Tiempo de Ejecución (Runtime)]
Declarar el permiso en el manifest es el primer paso. Sin embargo, para los permisos "peligrosos" (como Cámara, Ubicación o Almacenamiento) en las versiones modernas de Android (6.0+), también debes solicitar el permiso al usuario en tiempo de ejecución. Actualmente, Ludens no incluye un flujo nativo para solicitar permisos por defecto, por lo que podrías necesitar implementar un puente de JavaScript a Kotlin para activar el diálogo del sistema Android, o usar un plugin de terceros estilo Cordova/Capacitor si adaptas el núcleo.
:::

**Ejemplo: Añadir permiso de Micrófono:**

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <!-- Añade nuevos permisos aquí -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />

    <application>
        ...
    </application>
</manifest>
```

### Configuración de Copia de Seguridad (Backup)

Por defecto, el manifest incluye `android:allowBackup="true"`. Esto permite que el servicio de copia de seguridad integrado de Android pueda hacer un backup de los datos de tu app en el Google Drive del usuario.

Si tu juego contiene datos sensibles o quieres excluirte del sistema de auto-backup, puedes cambiar esto a `false`.

```xml
<application
    android:allowBackup="false"
    ... >
```
