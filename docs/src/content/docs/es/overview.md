---
title: Visión General
description: Aprende cómo portar juegos de RPG Maker MV y MZ a Android e iOS con Ludens.
head:
  - tag: meta
    attrs:
      name: keywords
      content: rpg maker mv android port, rpg maker mz android, rpg maker mv ios, rpg maker mz ios, compose multiplatform rpg maker, portar rpg maker a móvil
---

**Ludens** es un wrapper de **Compose Multiplatform** desarrollado en **Kotlin**, diseñado para ayudarte a llevar tus proyectos de **RPG Maker MV y MZ** a dispositivos móviles. Toma tu exportación de juego HTML5 y la envuelve en una aplicación nativa moderna, brindándote control total sobre la configuración y el despliegue tanto para Android como para iOS.

## ¿Qué Problema Resuelve?

RPG Maker MV y MZ exportan juegos como aplicaciones web HTML5. Tradicionalmente, distribuir estos juegos a usuarios móviles significaba obligarlos a jugar en línea a través de un navegador, donde los juegos carecen de la experiencia móvil nativa que los jugadores esperan — iconos en la pantalla de inicio, controles en pantalla, ajustes de rendimiento y distribución adecuada como aplicación independiente.

Ludens cierra esta brecha proporcionando un shell nativo ligero construido específicamente para gaming. Carga tu juego en un WebView optimizado y superpone un conjunto completo de funcionalidades de interfaz nativa específicas para móviles.

## Características

### WebView Optimizado

Tu juego de RPG Maker se ejecuta dentro de un `WebView` de Android que está profundamente integrado en la interfaz de Compose Multiplatform. Este WebView está configurado específicamente para reducir la latencia táctil y optimizar el renderizado del canvas para juegos. Maneja toda la lógica central y el audio del juego, mientras que la capa nativa de Compose proporciona overlays, ajustes y navegación sin interrumpir el motor.

### Controles en Pantalla (Overlay)

Ludens proporciona un overlay de controles en pantalla configurable:

- **Joystick Virtual** — Entrada direccional para el movimiento del personaje.
- **Botones A, B, X, Y** — Botones de acción configurables mapeados a eventos de teclado que el motor de RPG Maker reconoce.
- **Control de Opacidad** — Ajusta la transparencia de todos los controles en pantalla.
- **Control de Posición** — Reposiciona botones individuales según tus preferencias.

### Pantalla de Ajustes Completa

La pantalla de ajustes integrada se organiza en cuatro secciones:

| Sección       | Opciones                                                                |
|--------------|-------------------------------------------------------------------------|
| **Sistema**   | Tema (Claro / Oscuro / Sistema), Idioma (Sistema / Inglés / Español)  |
| **Herramientas** | Silenciar Audio, Mostrar FPS, Alternar WebGL, Posiciones de Botones|
| **Controles** | Activar/Desactivar, Ajustar Opacidad, Mapeo de Teclas               |
| **Acciones**  | Menú de Acciones Rápidas Configurable (Orden, Habilitar/Deshabilitar) |

### Menú de Acciones Rápidas

El menú de acciones rápidas proporciona acceso rápido a toggles comunes sin entrar a la pantalla completa de ajustes:

- Alternar visibilidad de Controles
- Alternar Silencio de Audio
- Alternar visualización de FPS
- Alternar renderizado WebGL
- Abrir Ajustes

El orden y la visibilidad de estas acciones son completamente configurables.

### Configuración Fácil

Todas las propiedades de identidad de la aplicación se configuran a través de `gradle.properties`:

- ID de aplicación (ej., `com.tuorganizacion.juego`)
- Cadena de versión
- Nombre de la aplicación (mostrado en ajustes del sistema)
- Nombre del launcher (mostrado bajo el icono de la pantalla de inicio)

### Compose Resources

Los assets del juego se gestionan a través del sistema de recursos de Compose Multiplatform. Colocas tu carpeta `www` exportada en `composeResources/files`, y el sistema de construcción se encarga del resto.

## Soporte de Plataformas

| Plataforma | Estado                  |
|------------|-------------------------|
| Android    | Soportado (21+)         |
| iOS        | Próximamente (iOS 13+)  |

## Stack Tecnológico

| Componente               | Versión |
|--------------------------|---------|
| Kotlin                   | 2.3.0   |
| Compose Multiplatform    | 1.9.3   |
| Compose WebView          | 2.0.3   |
| Virtual Joystick         | 1.0.0   |

:::caution[Nota Importante]
Ludens **no** incluye un sistema de guardado nativo integrado. Los archivos de guardado son gestionados por el propio motor de RPG Maker dentro del WebView, usando LocalStorage o IndexedDB del navegador. Esto significa que los guardados persisten mientras los datos del WebView no sean eliminados.
:::

## Enlaces

- [Repositorio en GitHub](https://github.com/yoimerdr/ludens)
- [Issue Tracker](https://github.com/yoimerdr/ludens/issues)
- [Descargar APK de Ejemplo](https://github.com/yoimerdr/ludens/releases/latest)
