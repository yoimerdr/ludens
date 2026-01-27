# Ludens

[![Kotlin](https://img.shields.io/badge/kotlin-v2.3.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-v1.9.3-blue?logo=JetpackCompose)](https://github.com/JetBrains/compose-multiplatform)
[![Compose WebView](https://img.shields.io/badge/Compose%20WebView-v2.0.3-blue)](https://github.com/KevinnZou/compose-webview-multiplatform)
[![Virtual Joystick](https://img.shields.io/badge/Virtual%20Joystick-v1.0.0-blue?logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEiIHdpZHRoPSI2MDAiIGhlaWdodD0iNjAwIj48cGF0aCBkPSJNMTI5IDExMWMtNTUgNC05MyA2Ni05MyA3OEwwIDM5OGMtMiA3MCAzNiA5MiA2OSA5MWgxYzc5IDAgODctNTcgMTMwLTEyOGgyMDFjNDMgNzEgNTAgMTI4IDEyOSAxMjhoMWMzMyAxIDcxLTIxIDY5LTkxbC0zNi0yMDljMC0xMi00MC03OC05OC03OGgtMTBjLTYzIDAtOTIgMzUtOTIgNDJIMjM2YzAtNy0yOS00Mi05Mi00MmgtMTV6IiBmaWxsPSIjZmZmIi8+PC9zdmc+)](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-ios](http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat)

**Ludens** es un wrapper de **Compose Multiplatform** desarrollado en **Kotlin**, diseñado para portar juegos de RPG Maker MV/MZ a dispositivos móviles (Android e iOS). Este proyecto facilita la integración de tu juego HTML5 en una aplicación nativa moderna, ofreciendo control total sobre la configuración y el despliegue.

## Características

*   **WebView Optimizado**: Integra tu juego RPG Maker MV/MZ sin problemas.
*   **Controles en Pantalla (Overlay)**:
    *   Joystick Virtual.
    *   Botones Configurables (A, B, X, Y).
*   **Pantalla de Ajustes Completa**:
    *   **Sistema**: Tema (Claro/Oscuro/Sistema), Idioma (Sistema/Inglés/Español).
    *   **Herramientas**: Silenciar audio, Mostrar FPS, Alternar WebGL.
    *   **Controles**: Activar/Desactivar, Ajustar Opacidad, Posición de botones y Mapeo de teclas.
    *   **Acciones**: Menú de acciones rápidas configurable (Orden, Habilitar/Deshabilitar).
*   **Configuración Fácil**: Personaliza ID, versión y nombre desde `gradle.properties`.
*   **Compose Resources**: Gestión eficiente de assets en `composeResources/files`.

> **Nota**: Este proyecto no incluye un sistema de guardado nativo integrado; los archivos de guardado son gestionados por el propio motor del juego (RPG Maker) dentro del WebView (LocalStorage/IndexedDB).

## Guía Rápida

> **Advertencia**: La construcción para **iOS** aún no está completamente configurada (usa la configuración por defecto de la plantilla). Esta guía se centra en Android.

> **Testeo**: Se recomienda encarecidamente probar el juego en un emulador o dispositivo real. Algunos plugins de RPG Maker pueden no ser compatibles con el WebView móvil.

### Requisitos Previos

*   **Android Studio**: Se recomienda **Otter 2 Feature Drop | 2025.2.2** o superior.
    -   [Guía de configuración](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)
*   **JDK**: Versión 17 o superior.
*   **RPG Maker MV/MZ**: Tu proyecto exportado para web.
    -   *Opcional*: Incluir y activar el plugin [`YDP_Ludens.js`](resources/plugins/js/YDP_Ludens.js) (disponible en la carpeta `resources/` de este repositorio) para activar algunas funcionalidades adicionales en el cliente ludens.
        *   **Recomendación**: Debe ser el **primer plugin** en tu gestor de plugins.
        *   **Compatibilidad**: Necesario para soportar versiones antiguas de WebView (soluciona errores en la verificación de carga de `fonts`).
    -   *Importante*: Verificar que los plugins utilizados en tu juego sean compatibles con entornos móviles (WebView).

### Game Export
Exporta tu juego desde RPG Maker. La opción recomendada es **Android / iOS**. Si no está disponible, selecciona **Web Browsers**.

Dependiendo de la versión (MV/MZ), obtendrás una carpeta con el nombre de tu juego (que contiene `www`) o directamente la carpeta `www`. Lo importante es localizar dónde está el archivo `index.html` y los assets.

### Importar Assets
Mueve la carpeta **www** completa dentro de `files`. Es **necesario** que los archivos mantengan esta estructura, ya que la aplicación buscará el archivo `index.html` en la carpeta `www`.

Ubicación de destino: `composeApp/src/commonMain/composeResources/files`

La estructura final debe verse así:

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

#### Configuración
Edita el archivo `gradle.properties` en la raíz del proyecto para personalizar tu aplicación:

```properties
# Identificador único de la aplicación
ludens.applicationId=com.tuorganizacion.ejemplo

# Versión visible para el usuario
ludens.applicationVersion=1.0

# Nombre de la aplicación en el sistema
ludens.applicationName=Nombre del Juego

# Nombre corto debajo del icono
ludens.applicationLauncherName=Juego
```

#### Compilar

##### Debug Build
Para pruebas rápidas en emulador o dispositivo:
Ejecuta `./gradlew assembleDebug`.
El APK se generará en: `composeApp/build/outputs/apk/debug/`

##### Release Build
Para generar un APK firmado para producción:
1.  Crea tu firma y configura su uso creando un archivo `keystore.properties` en la raíz (usa `keystore.properties.template` como referencia).
2.  Ejecuta `./gradlew assembleRelease`.
3.  El APK firmado estará en: `composeApp/build/outputs/apk/release/`

### iOS
*Próximamente* - La configuración actual es la predeterminada de Compose Multiplatform.

## Contribuciones

¡Las contribuciones son bienvenidas! Por favor, siéntete libre de enviar un Pull Request. Para cambios mayores, por favor abre un issue primero para discutir qué te gustaría cambiar.

1. Haz un Fork del repositorio
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`)
3. Haz Commit de tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Haz Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0 - mira el archivo [LICENSE](LICENSE) para más detalles.

## Enlaces

- [Repositorio en GitHub](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Agradecimientos

- Construido con [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Compose WebView Multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)
- [Compose Virtual Joystick](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

---

**¡Si encuentras útil este proyecto, por favor considera darle una ⭐ en GitHub!**

