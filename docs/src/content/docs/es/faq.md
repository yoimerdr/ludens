---
title: Preguntas Frecuentes
description: FAQ de Ludens sobre compatibilidad, plugins, WebView y compilación para juegos de RPG Maker MV/MZ.
head:
  - tag: script
    attrs:
      type: application/ld+json
    content: '{"@context":"https://schema.org","@type":"FAQPage","mainEntity":[{"@type":"Question","name":"¿Qué versiones de RPG Maker son compatibles?","acceptedAnswer":{"@type":"Answer","text":"Ludens soporta RPG Maker MV y RPG Maker MZ exportados como HTML5."}},{"@type":"Question","name":"¿Puedo usar plugins de RPG Maker en Android?","acceptedAnswer":{"@type":"Answer","text":"Muchos plugins funcionan, pero plugins de escritorio o dependientes de Node.js pueden fallar en WebView móvil."}},{"@type":"Question","name":"¿Puedo generar Android App Bundle (AAB)?","acceptedAnswer":{"@type":"Answer","text":"Sí. Ejecuta ./gradlew bundleRelease para generar un build AAB."}}]}'
---

## General

### ¿Qué es Ludens?

Ludens es un wrapper de Compose Multiplatform que te permite portar juegos de RPG Maker MV/MZ a
dispositivos móviles (Android e iOS). Envuelve tu exportación de juego HTML5 en una aplicación
nativa con controles en pantalla, ajustes y soporte adecuado para distribución móvil.

### ¿Necesito saber Kotlin para usar Ludens?

No. Para el uso básico — exportar tu juego, personalizar la identidad de la app y compilar un APK —
solo necesitas editar `ludens.properties` y seguir la guía de compilación. No se requieren cambios
de código.

### ¿Ludens es gratuito?

Sí. Ludens está licenciado bajo la Licencia Apache 2.0.

---

## Compatibilidad

### ¿Qué versiones de RPG Maker son compatibles?

Ludens soporta juegos hechos con **RPG Maker MV** y **RPG Maker MZ**. Ambos exportan juegos HTML5
que pueden cargarse en el WebView.

### ¿Mis plugins funcionarán en móvil?

No todos. Muchos plugins de RPG Maker están diseñados específicamente para entornos de escritorio (
PC/Mac). Los plugins que dependen de **APIs de Node.js** (como `fs` para el acceso al sistema de
archivos, común en sistemas de guardado personalizados o integraciones de escritorio) bloquearán
inmediatamente el juego en dispositivos móviles. Además, los plugins que esperan entradas de teclado
físico sin proporcionar alternativas en pantalla podrían resultar injugables. Debes probar tu juego
exhaustivamente en un dispositivo Android físico o en un emulador para verificar la compatibilidad.

### ¿Qué versiones de Android son compatibles?

Ludens apunta a **Android API 21** (Android 5.0 Lollipop) como SDK mínimo, con un SDK objetivo de *
*36**. Esto cubre la gran mayoría de dispositivos Android en uso activo.

### ¿Se soporta iOS?

El soporte para iOS está planeado pero aún no está completamente configurado. El proyecto actual
incluye la plantilla iOS por defecto de Compose Multiplatform, pero el proceso de compilación y las
configuraciones no están finalizados.

---

## Compilación y Configuración

### ¿Puedo tener múltiples juegos instalados en el mismo dispositivo?

Sí. Para tener varios juegos instalados simultáneamente sin que se sobreescriban entre sí, cada
juego debe tener un `ludens.android.id` único configurado en su archivo `ludens.properties` (por
ejemplo, `com.miestudio.juego1` y `com.miestudio.juego2`).

### ¿Cómo cambio el nombre y el icono de la app?

- **Nombre de la app**: Edita `ludens.android.name` y `ludens.android.launcherName` en
  `ludens.properties`.
- **Icono de la app**: Reemplaza las imágenes en los directorios
  `composeApp/src/androidMain/res/mipmap-*` o usa el **Image Asset Studio** de Android Studio (clic
  derecho en `res` > **New > Image Asset**).

Consulta la página de [Configuración](/es/configuration/android/) para más detalles.

### ¿Qué es `keystore.properties`?

Es un archivo que almacena tus credenciales de firma para compilaciones release. Contiene la ruta a
tu archivo keystore `.jks` y las contraseñas asociadas. Una plantilla (
`keystore.properties.template`) está incluida en el repositorio.

### ¿Puedo generar un AAB (Android App Bundle) en vez de un APK?

Las tareas de compilación por defecto generan archivos APK. Para generar un AAB, puedes ejecutar:

```bash
./gradlew bundleRelease
```

El AAB se generará en `composeApp/build/outputs/bundle/release/`.

---

## Juego y Runtime

### ¿Dónde se almacenan los archivos de guardado?

Los archivos de guardado son gestionados íntegramamente por el motor de RPG Maker dentro del WebView
usando las APIs de Almacenamiento Web de HTML5 (**LocalStorage** o **IndexedDB**). Ludens no
proporciona un sistema de guardado nativo separado. Estas bases de datos web están fuertemente
aisladas (sandboxed) por el sistema operativo móvil por razones de seguridad.

:::caution[Advertencia de Pérdida de Datos]
Borrar los datos de la aplicación en los ajustes del dispositivo, o desinstalar la app por completo,
eliminará de forma inmediata y permanente los archivos de guardado del juego.
:::

### ¿Puedo usar WebGL?

Sí. WebGL está habilitado por defecto y puede alternarse en los ajustes de la aplicación. Cambiar a
WebGL requiere un reinicio de la aplicación, y solo está disponible para MV, dado que MZ requiere
WebGL por defecto.

### ¿Qué es el plugin YDP_Ludens.js?

Es un plugin opcional de RPG Maker que:

- Corrige errores de verificación de carga de fuentes en versiones antiguas de WebView.
- Habilita funcionalidades adicionales del lado del cliente soportadas por la capa nativa de Ludens.
- Debe colocarse como el **primer plugin** en tu gestor de plugins.
- Disponible en el [repositorio rpgm-plugins](https://github.com/yoimerdr/rpgm-plugins).

### ¿Puedo usar audio en el juego?

Sí. El WebView soporta reproducción de audio. Los ajustes de la aplicación incluyen un toggle de *
*Silenciar Audio** que silencia el audio usando el API del motor (MV/MZ) en lugar del WebView.

---

## Solución de Problemas

### Mi juego muestra un placeholder 404

Este es el placeholder que aparece si no se han colocado los assets correctamente.

Causas comunes:

1. La carpeta `www` no está en la ubicación correcta (`composeResources/files/www/`).
2. El archivo `index.html` falta en la raíz de la carpeta `www`.
3. Un plugin está causando un error. Verifica los logs de la consola del WebView del dispositivo
   usando la depuración remota de **Chrome DevTools**.

### La compilación falla con un error de Gradle

1. Asegúrate de estar usando **JDK 17** o superior.
2. Ejecuta **File > Sync Project with Gradle Files** en Android Studio.
3. Intenta **File > Invalidate Caches / Restart**.
4. Verifica que el Android SDK esté correctamente instalado via **File > Settings > Android SDK**.

### Los controles no responden

1. Verifica que los controles estén habilitados en los ajustes de la aplicación.
2. Verifica que los mapeos de teclas de los botones coincidan con las teclas que tu juego espera.
3. Algunos plugins pueden sobreescribir el manejo de input — prueba sin plugins de terceros para
   aislar el problema.
