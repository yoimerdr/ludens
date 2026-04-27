---
title: Configuración Compartida
description: Propiedades de configuración agnósticas de la plataforma para Ludens.
---

Las propiedades de configuración compartida se gestionan a través de `ludens.properties` en la raíz del proyecto. Estos
ajustes afectan la lógica central de la aplicación y la interfaz de usuario en todas las plataformas.

## Presets de Ajustes

Ludens utiliza presets para simplificar la configuración inicial. Puedes elegir entre varios presets incorporados o usar
el nombre `custom` para definir tu propio comportamiento.

Edita tu archivo `ludens.properties`:

```properties
# Selecciona el preset activo
ludens.settings.presetName=recommended
```

### Referencia de Presets

La siguiente propiedad se configura bajo el prefijo `ludens.settings.*`:

| Propiedad    | Por defecto   | Valores                                                          | Descripción                             |
|--------------|---------------|------------------------------------------------------------------|-----------------------------------------|
| `presetName` | `recommended` | `recommended`, `noactions`, `nocontrols`, `minimalist`, `custom` | Selecciona la lógica del preset activo. |

#### Presets Incorporados

| Nombre        | Comportamiento                                                                          |
|---------------|-----------------------------------------------------------------------------------------|
| `recommended` | (Predeterminado) Configuración equilibrada con controles y acciones rápidas activados.  |
| `noactions`   | Desactiva el panel de acciones rápidas pero mantiene los controles en pantalla.         |
| `nocontrols`  | Desactiva los controles en pantalla pero mantiene las acciones rápidas para navegación. |
| `minimalist`  | Máximo espacio: desactiva tanto controles como acciones rápidas por defecto.            |
| `custom`      | Ignora la lógica incorporada y usa las propiedades personalizadas definidas abajo.      |

---

## Configuración de Preset Personalizado

Las siguientes propiedades solo se consumen cuando `ludens.settings.presetName` está configurado como `custom`. Te
permiten ajustar con precisión el estado inicial del juego:

```properties
# Sobrescrituras del preset personalizado
ludens.settings.preset.toolMuted=false
ludens.settings.preset.toolShowFps=false
ludens.settings.preset.toolUseWebgl=false
ludens.settings.preset.controlEnabled=true
ludens.settings.preset.controlAlpha=0.4
ludens.settings.preset.actionEnabled=false
ludens.settings.preset.actionItems=settings
ludens.settings.preset.systemTheme=system
ludens.settings.preset.systemLanguage=system
```

### Detalles de Propiedades

Estas propiedades se configuran bajo el prefijo `ludens.settings.preset.*`:

| Propiedad        | Tipo     | Por defecto | Valores / Descripción                                                     |
|------------------|----------|-------------|---------------------------------------------------------------------------|
| `toolMuted`      | Booleano | `false`     | Estado de silencio inicial (`true`, `false`).                             |
| `toolShowFps`    | Booleano | `false`     | Visibilidad inicial del contador de FPS.                                  |
| `toolUseWebgl`   | Booleano | `false`     | Estado inicial del interruptor WebGL (solo MV).                           |
| `controlEnabled` | Booleano | `true`      | Estado inicial de la capa de controles virtuales.                         |
| `controlAlpha`   | Float    | `0.4`       | Opacidad del control virtual (`0.0` a `1.0`).                             |
| `actionEnabled`  | Booleano | `false`     | Visibilidad inicial del panel de acciones rápidas.                        |
| `actionItems`    | Lista    | `settings`  | Ítems separados por coma: `settings`, `controls`, `mute`, `fps`, `webgl`. |
| `systemTheme`    | String   | `system`    | Tema: `system`, `light`, `dark`.                                          |
| `systemLanguage` | String   | `system`    | Idioma: `system`, `en`, `es`.                                             |
