---
title: Historial de Versiones
description: Historial de versiones y actualizaciones para el proyecto Ludens.
---

Esta página documenta el historial de lanzamientos y los cambios principales para el proyecto Ludens.

Para ver una lista completa de commits y correcciones menores, por favor consulta la página de [GitHub Releases](https://github.com/yoimerdr/ludens/releases).

## v0.3.0 - 2026-04-25

### Añadido

- Módulo `build-logic` con plugins personalizados de Gradle para configuración de Ludens, recursos generados y permisos generados.
- Configuración raíz `ludens.properties` como fuente principal para identidad de app, flags del manifest, permisos y presets de ajustes.
- Accesorios tipados para recursos de compose y generación de presets de ajustes.
- Utilidades de gestión de memoria y WebView específicas por plataforma para Android e iOS.
- Nuevo modelo de control por teclado y estado de UI relacionado para el mapeo de entrada del juego.
- Ampliación de teclas soportadas en los controles para incluir el set estándar y teclas gráficas específicas.
- Primitivas de diseño responsivo y tokens reutilizables para espaciado, radios, bordes y breakpoints.
- Componente de menú desplegable con búsqueda para los flujos de selección de controles.
- Manejo de recursos de arranque, incluyendo `www/index.html` y script de limpieza de memoria en el arranque.

### Cambiado

- El build de Android ahora lee la configuración de `ludens.properties` y aplica la identidad de la app y placeholders del manifest desde allí.
- La generación de presets de ajustes ahora resuelve valores predeterminados desde el nuevo pipeline de configuración.
- Remodelación de pantallas de inicio y ajustes para comportamientos de control actualizados y diseños más responsivos.
- Refrescamiento del tema, tipografía, espaciado, tarjetas, botones flotantes y componentes del dock para igualar el nuevo sistema visual.
- Arranque del WebView y manejo del ciclo de vida actualizados para mejorar la estabilidad y memoria.
- Experiencia de configuración de plugins y ajustes actualizada en torno a `YDP_Ludens.js`.

### Arreglado

- Solucionado comportamiento de carga de configuración que podía ser afectado por caché o valores obsoletos.
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

- Arreglado que los controles en pantalla no se mostraran cuando el plugin YDP_Ludens está deshabilitado o no presente
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
El soporte para iOS está planeado para futuras versiones. El código base actual contiene lógica compartida, pero la configuración de compilación está actualmente enfocada en Android.
:::
