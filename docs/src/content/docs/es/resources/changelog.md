---
title: Historial de Versiones
description: Historial de versiones y actualizaciones para el proyecto Ludens.
---

Esta página documenta el historial de lanzamientos y los cambios principales para el proyecto Ludens.

Para ver una lista completa de commits y correcciones menores, por favor consulta la página de [GitHub Releases](https://github.com/yoimerdr/ludens/releases).

## v0.2.0 - 2026-02-05

### Añadido
- Implementación del callback onRestart para navegación y reseteo de estado

### Modificado
- Refactorización de eventos de ajustes para usar el patrón de interfaz sellada `UpdateSettings`
- Mejora en el manejo de peticiones y eventos de ajustes

### Arreglado
- Arreglado que los controles en pantalla no se mostraran cuando el plugin YDP_Ludens está deshabilitado o no presente
- Arreglado el reinicio innecesario del WebView al actualizar ajustes sin navegar

## 0.1.0 - 2026-01-27

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
