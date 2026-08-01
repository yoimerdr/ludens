---
title: Historial de Versiones
description: Historial de versiones y actualizaciones para el proyecto Ludens.
---

Esta página documenta el historial de lanzamientos y los cambios principales para el proyecto
Ludens.

Para ver una lista completa de commits y correcciones menores, por favor consulta la página
de [GitHub Releases](https://github.com/yoimerdr/ludens/releases).

## [No lanzado / Unreleased]

### Añadido

- Identidad de aplicación multiplataforma compartida (`ludens.app.*`) en `ludens.properties` con sobreescrituras opcionales por plataforma para Android e iOS. `LudensConfiguration` expone las propiedades resueltas `androidIdentity`/`iosIdentity` combinando valores por defecto compartidos con sobreescrituras específicas.
- Plugin de sincronización de metadatos pre-build para iOS que propaga `PRODUCT_NAME`, `PRODUCT_BUNDLE_IDENTIFIER`, `MARKETING_VERSION` y `CURRENT_PROJECT_VERSION` a `Config.xcconfig`.
- Plugin de renombrado y reubicación post-build de artefactos de Android con patrón de nombres configurable, directorio de salida y modo de operación (`copy`/`move`).

### Cambiado

- `ludens.properties` reestructurado: los valores de identidad se movieron del espacio de nombres `ludens.android.*` a `ludens.app.*`; las sobreescrituras por plataforma heredan de los valores compartidos cuando no están definidas.
- Módulo `composeApp` reestructurado en los módulos `shared` y `androidApp`.
- Actualización de AGP a 9.2.1, Kotlin a 2.4.10, Gradle wrapper a 9.4.1, Jackson a 2.22.1 y targetSDK a 37.

### Arreglado

- Corregida la reactivación individual de los controles en pantalla: los toggles por elemento ahora funcionan en ambos sentidos en lugar de exigir que el elemento ya esté habilitado.

## v0.4.0 - 2026-07-16

### Añadido

- Diálogo de traceback de diagnóstico e intercepción de errores de tiempo de ejecución del juego en WebView enriquecido que admite la captura de excepciones de JavaScript, intercepción de fallos de carga nativos de WebView, formateo de variables y traducciones localizadas en KMP.
- Plugin y tarea de Gradle de generación automática de iconos de la aplicación que compila iconos de launcher para Android (legacy y adaptativos SVG/PNG), iconos de la aplicación para iOS (todas las resoluciones y manifiesto JSON) e iconos de ficha de Google Play Store desde una única imagen origen en `project/assets/icons/`.
- Nuevo soporte de idiomas: Chino (`zh`), Japonés (`ja`), Portugués - Brasil (`pt-rBR`) y Ruso (`ru`) para la interfaz de usuario.
- Sincronización automática de assets web desde la carpeta raíz `project/www/` a los recursos de Compose.
- Plugins de sincronización de fuentes y cadenas de idiomas para la gestión de recursos dentro de `build-logic` para eliminar idiomas y fuentes no utilizados.
- Soporte de configuración de idioma y fuente para la generación de recursos, incluyendo soporte de metadatos de idioma base.
- Utilidades de extensión de proyecto para la gestión de assets y análisis de nombres de recursos.
- Soporte multilingüe con carga optimizada de i18n y análisis de alias de idiomas desde la configuración de presets.
- Renderizado condicional de `LanguageAction` basado en los idiomas disponibles.
- `CODE_OF_CONDUCT.md` y plantillas de issues actualizadas.

### Cambiado

- Diseño de `SideTabOptions` mejorado, optimizando el manejo del desbordamiento de texto.
- Inyección de view models optimizada y gestión de estado mejorada usando `rememberSaveable`.
- Se eliminaron fuentes específicas para usar tipos de letra variables.
- Reducción del manejo manual de recursos mediante el uso de nuevas utilidades auxiliares.
- Archivos de configuración movidos y documentación interna mejorada para mayor claridad.
- Se cambió la forma en que se aplican los plugins: ahora solo existe un único plugin `ludens.build`, y los demás deben ser activados/aplicados usando el DSL en gradle.
- Documentación del proyecto actualizada (`README`, `BUILD`, `CONTRIBUTING`) para detallar el nuevo método de sincronización de assets de la carpeta raíz.
- Se reemplazó la página de error 404 con un placeholder de configuración del juego para páginas index.html por defecto.
- Script de simulación de eventos de teclado de WebView optimizado agrupando eventos por tipo con un único condicional (guard) por grupo.

### Arreglado

- Corregido error inesperado en la generación de recursos de icono durante la compilación, que provocaba que los archivos se colocaran dentro de la carpeta "generated".
- Corregido el manejo de locales al incluir la orientación de configuración en `remember`.
- Corregido problema de precedencia de operadores en el script de eventos de teclado de movimiento envolviendo la asignación en paréntesis.
- Corregidas posibles excepciones de puntero nulo en los scripts de WebView agregando validaciones de nulos para los objetos globales `Input` y `Graphics`.
- Corregido el doble registro del error logger cuando se carga `YDP_Ludens`.
- Corregida la falta de descripciones de accesibilidad agregando descripciones de contenido a los botones del FloatingDock.

## v0.3.0 - 2026-04-25


### Añadido

- Módulo `build-logic` con plugins personalizados de Gradle para configuración de Ludens, recursos
  generados y permisos generados.
- Configuración raíz `ludens.properties` como fuente principal para identidad de app, flags del
  manifest, permisos y presets de ajustes.
- Accesorios tipados para recursos de compose y generación de presets de ajustes.
- Utilidades de gestión de memoria y WebView específicas por plataforma para Android e iOS.
- Nuevo modelo de control por teclado y estado de UI relacionado para el mapeo de entrada del juego.
- Ampliación de teclas soportadas en los controles para incluir el set estándar y teclas gráficas
  específicas.
- Primitivas de diseño responsivo y tokens reutilizables para espaciado, radios, bordes y
  breakpoints.
- Componente de menú desplegable con búsqueda para los flujos de selección de controles.
- Manejo de recursos de arranque, incluyendo `www/index.html` y script de limpieza de memoria en el
  arranque.

### Cambiado

- El build de Android ahora lee la configuración de `ludens.properties` y aplica la identidad de la
  app y placeholders del manifest desde allí.
- La generación de presets de ajustes ahora resuelve valores predeterminados desde el nuevo pipeline
  de configuración.
- Remodelación de pantallas de inicio y ajustes para comportamientos de control actualizados y
  diseños más responsivos.
- Refrescamiento del tema, tipografía, espaciado, tarjetas, botones flotantes y componentes del dock
  para igualar el nuevo sistema visual.
- Arranque del WebView y manejo del ciclo de vida actualizados para mejorar la estabilidad y
  memoria.
- Experiencia de configuración de plugins y ajustes actualizada en torno a `YDP_Ludens.js`.

### Arreglado

- Solucionado comportamiento de carga de configuración que podía ser afectado por caché o valores
  obsoletos.
- Solucionado manejo de rutas/archivos perdidos en el bootstrapping de recursos.
- Solucionado problema de espaciado en la interfaz de ajustes.
- Solucionado comportamiento en torno a acciones activas inesperadas.
- Solucionada pantalla negra para juegos creados en RPG Maker MZ.

## v0.2.0 - 2026-02-05

### Añadido

- Implementación del callback onRestart para navegación y reseteo de estado

### Modificado

- Refactorización de eventos de ajustes para usar el patrón de interfaz sellada `UpdateSettings`
- Mejora en el manejo de peticiones y eventos de ajustes

### Arreglado

- Arreglado que los controles en pantalla no se mostraran cuando el plugin YDP_Ludens está
  deshabilitado o no presente
- Arreglado el reinicio innecesario del WebView al actualizar ajustes sin navegar

## v0.1.0 - 2026-01-27

Lanzamiento público inicial del proyecto.

### Añadido

#### Soporte de Compilación Android

- Soporte para Android 21+ (Diseñado para dispositivos modernos)
- WebView optimizado para RPG Maker MV/MZ

#### Controles Nativos en Pantalla

- Joystick Virtual (Opacidad y posición configurable)
- Botones de Acción (A, B, X, Y)

#### Sistema de Ajustes Completo

- Tema del Sistema e Idioma
- Silenciador de Audio y Contador FPS
- Toggle de WebGL

#### Configuración Fácil de Compilación Android

- Personalización de applicationId, Versión y Nombre a través de gradle.properties
- Inyección de Assets Simplificada (composeResources/files/www)

:::note
El soporte para iOS está planeado para futuras versiones. El código base actual contiene lógica
compartida, pero la configuración de compilación está actualmente enfocada en Android.
:::
