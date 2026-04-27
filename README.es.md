# Ludens

<p align="center">
  <a href="README.md">Read in English</a> | <a href="BUILD.es.md">Guia de Construccion</a> | <a href="https://tryludens.vercel.app/">Docs Web</a>
</p>

<p align="center">
  <a href="https://github.com/yoimerdr/ludens/releases/latest">
    <img alt="Version" src="https://img.shields.io/github/v/release/yoimerdr/ludens?display_name=tag&sort=semver&style=for-the-badge&label=Version">
  </a>
  <img alt="Android 21+" src="https://img.shields.io/badge/Android-21%2B-6EDB8D?style=for-the-badge&logo=android&logoColor=0B1F17">
  <img alt="iOS 13+" src="https://img.shields.io/badge/iOS-13%2B-CDCDCD?style=for-the-badge&logo=apple&logoColor=111827">
</p>

<p align="center">
  <a href="http://kotlinlang.org">
    <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-v2.3.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
  </a>
  <a href="https://github.com/JetBrains/compose-multiplatform">
    <img alt="Compose Multiplatform" src="https://img.shields.io/badge/Compose_Multiplatform-v1.9.3-0EA5E9?style=for-the-badge&logo=jetpackcompose&logoColor=white">
  </a>
  <a href="https://github.com/KevinnZou/compose-webview-multiplatform">
    <img alt="Compose WebView" src="https://img.shields.io/badge/Compose_WebView-v2.0.3-334155?style=for-the-badge">
  </a>
  <a href="https://github.com/yoimerdr/compose-virtualjoystick-multiplatform">
    <img alt="Virtual Joystick" src="https://img.shields.io/badge/Virtual_Joystick-v1.0.0-0F172A?style=for-the-badge">
  </a>
</p>

Ludens es un wrapper de Compose Multiplatform desarrollado en Kotlin para portar juegos RPG Maker MV/MZ a dispositivos moviles. Integra tu exportacion HTML5 en una app nativa y agrega controles y ajustes moviles configurables.

<p align="center">
  <img src="docs/src/assets/samples/ludens-0.3.0-sample.gif" alt="Ludens Demo" width="480">
</p>

<p align="center">
  <a href="https://github.com/yoimerdr/ludens/releases/latest"><strong>Descargar APK de Ejemplo</strong></a>
</p>

## Caracteristicas

- Integracion WebView optimizada para RPG Maker MV/MZ.
- Controles en pantalla (overlay):
  - Joystick virtual.
  - Botones configurables (A, B, X, Y).
- Pantalla de ajustes completa:
  - Sistema: Tema (Claro/Oscuro/Sistema), Idioma (Sistema/Ingles/Espanol).
  - Herramientas: Silenciar audio, Mostrar FPS, Alternar WebGL.
  - Controles: Activar/Desactivar, Opacidad, Posiciones, Mapeo de teclas.
  - Acciones: Menu de acciones rapidas configurable (Orden, Habilitar/Deshabilitar).
- Configuracion de app/build desde [`ludens.properties`](ludens.properties).
- Gestion eficiente de assets en `composeResources/files`.

> [!NOTE]
> Este proyecto no incluye un sistema de guardado nativo integrado. Los guardados se gestionan dentro del WebView por RPG Maker (LocalStorage/IndexedDB).

## Guia Rapida

> [!WARNING]
> La construccion para iOS aun no esta completamente configurada (usa defaults de plantilla). Esta guia se centra en Android.

> [!TIP]
> Valida siempre en emulador o dispositivo real. Algunos plugins de RPG Maker pueden comportarse distinto en WebView movil.

### Requisitos Previos

- Android Studio Otter 2 Feature Drop (2025.2.2) o superior.
  - [Guia de configuracion](https://developer.android.com/courses/pathways/android-basics-compose-unit-1-pathway-2)
- JDK 17 o superior.
- Proyecto RPG Maker MV/MZ exportado para web.
  - Opcional: activar [`YDP_Ludens.js`](resources/plugins/js/YDP_Ludens.js) para compatibilidad/funciones extra.
  - Ultima version y plugins recomendados en [rpgm-plugins](https://github.com/yoimerdr/rpgm-plugins).
  - Recomendacion: colocalo como primer plugin en el gestor.
  - Compatibilidad: util para errores de carga de fuentes en WebView antiguo.
  - Importante: verifica compatibilidad movil de todos tus plugins.

### Exportar el Juego

Exporta desde RPG Maker usando `Android / iOS` (recomendado). Si no esta disponible, usa `Web Browsers`.

Segun la version MV/MZ, puedes obtener una carpeta de juego que contiene `www` o directamente la carpeta `www`. En ambos casos, `index.html` es obligatorio.

### Importar Assets

Mueve la carpeta completa `www` a:

`composeApp/src/commonMain/composeResources/files`

> [!WARNING]
> **Sensibilidad de Mayúsculas/Minúsculas**: El sistema de archivos de Windows es **insensible** (`/Path/Example` = `/path/example`), pero Android e iOS usan sistemas de archivos **sensibles**. Asegúrate de que todas las referencias a archivos en el código de tu juego (imágenes, audio, archivos de datos) usen nombres **exactamente iguales**. Si un archivo se llama `MiImagen.png`, debes referenciarlo como `MiImagen.png`, no como `miimagen.png`. Esta es una causa común de fallos silenciosos en móviles.
> 
> **Tip**: Puedes usar el plugin **[YDP_CrossAssets](https://github.com/yoimerdr/rpgm-plugins)** del repositorio rpgm-plugins para normalizar automáticamente las rutas de archivos y evitar este problema.

Estructura requerida:

```text
files/
  └── www/
       ├── audio/
       ├── img/
       ├── js/
       ├── ...
       └── index.html
```

### Configuracion Android

Edita [`ludens.properties`](ludens.properties):

```properties
# Identificador unico de la aplicacion
ludens.android.id=com.tuorganizacion.ejemplo

# Version visible para el usuario
ludens.android.version=1.0.0

# Version interna entera
ludens.android.versionCode=1

# Nombre de la aplicacion en el sistema
ludens.android.name=Nombre del Juego

# Nombre corto debajo del icono
ludens.android.launcherName=Juego
```

[`gradle.properties`](gradle.properties) se sigue usando para opciones de build de Gradle/Kotlin. La identidad y ajustes de Ludens se configuran en [`ludens.properties`](ludens.properties).

### Compilar

Para flujo completo con capturas y firma release, revisa [BUILD.es.md](BUILD.es.md).

- Debug: `./gradlew assembleDebug`
  - Salida: `composeApp/build/outputs/apk/debug/`
- Release: `./gradlew assembleRelease`
  - Crea antes [`keystore.properties`](keystore.properties) (ver [`keystore.properties.template`](keystore.properties.template)).
  - Salida: `composeApp/build/outputs/apk/release/`

### iOS

Proximamente. La configuracion actual sigue la plantilla por defecto de Compose Multiplatform.

## Contribuciones

Las contribuciones son bienvenidas. Para cambios grandes, abre un issue primero.

1. Haz fork del repositorio.
2. Crea tu rama (`git checkout -b feature/AmazingFeature`).
3. Haz commit (`git commit -m 'Add some AmazingFeature'`).
4. Haz push (`git push origin feature/AmazingFeature`).
5. Abre un Pull Request.

## Licencia

Apache 2.0. Revisa [`LICENSE`](LICENSE).

## Enlaces

- [Docs Web](https://tryludens.vercel.app/)
- [Repositorio en GitHub](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Agradecimientos

- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Compose WebView Multiplatform](https://github.com/KevinnZou/compose-webview-multiplatform)
- [Compose Virtual Joystick](https://github.com/yoimerdr/compose-virtualjoystick-multiplatform)

---

Si este proyecto te ayuda, considera darle una estrella.
