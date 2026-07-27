---
title: Configuración Compartida
description: Propiedades de configuración agnósticas de la plataforma para Ludens.
---

Las propiedades de configuración compartida se gestionan a través de `ludens.properties` en la raíz
del proyecto. Estos ajustes afectan la lógica central de la aplicación y la interfaz de usuario en
todas las plataformas.

## Identidad de la App

Estas propiedades definen la identidad base de la aplicación usada en todas las plataformas. Tanto
Android como iOS leen de este espacio de nombres por defecto. Cada plataforma puede sobreescribir
valores individuales usando su propio prefijo (`ludens.android.*`) sin tocar los compartidos.

```properties
# Identidad compartida de la aplicación
ludens.app.id=com.ludens.compose.ludens
ludens.app.name=Ludens
ludens.app.version=0.4.0
ludens.app.versionCode=1
```

Configura estas propiedades usando el prefijo `ludens.app.*`:

| Propiedad     | Tipo    | Por defecto                 | Descripción                                                                                                      |
|---------------|---------|-----------------------------|------------------------------------------------------------------------------------------------------------------|
| `id`          | String  | `com.ludens.compose.ludens` | Identificador de la app en formato dominio invertido. Solo letras, dígitos y puntos (sin guiones ni guión bajo). |
| `name`        | String  | `Ludens`                    | Nombre visible en ajustes del sistema y otras interfaces de plataforma.                                          |
| `version`     | String  | `0.4.0`                     | Cadena de versión visible para el usuario (p. ej. `1.0.0`).                                                     |
| `versionCode` | Entero  | `1`                         | Número de build interno. Incrementar con cada release. No tiene que coincidir con la cadena de versión.          |

:::note[Sobreescrituras por plataforma]
Cada plataforma puede divergir de estos valores compartidos estableciendo su propia clave. Para
Android, son `ludens.android.id`, `ludens.android.version`, `ludens.android.versionCode` y
`ludens.android.name` — todas comentadas por defecto. Déjalas sin establecer para usar el valor
compartido. La propiedad `ludens.android.launcherName` no tiene equivalente compartido y sigue
siendo exclusiva de Android.
:::

---

## Presets de Ajustes

Ludens utiliza presets para simplificar la configuración inicial. Puedes elegir entre varios presets
incorporados o usar
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

Las siguientes propiedades solo se consumen cuando `ludens.settings.presetName` está configurado
como `custom`. Te
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
| `systemLanguage` | String   | `system`    | Idioma por defecto: `system`, `en`, `es`, `zh`, `ja`, `pt-rBR`, `ru`.      |

---

## Localización e Idiomas

Ludens admite varios idiomas para la interfaz de usuario de su cliente/wrapper.

:::caution[Importante]
Este sistema de localización traduce **únicamente** la interfaz nativa del cliente/wrapper (como la pantalla de Ajustes, las superposiciones de control y los diálogos nativos). **No** traduce el contenido ni los diálogos propios del juego de RPG Maker.
:::

Por defecto, la compilación incluye todos los idiomas encontrados en los assets del proyecto. Si tu juego solo está destinado a un único idioma o a un conjunto específico de idiomas, puedes restringir las opciones mostradas en el menú de ajustes del wrapper modificando `ludens.properties`:

```properties
# ----- Idiomas -----
# Qué idiomas incluir en la compilación.
# "*" significa todos los descubiertos en project/assets/languages/ (Por defecto).
# Lista solo los tags que necesitas para limitar la compilación (ej. en,es).
ludens.languages.available=en,es
```

| Propiedad | Tipo | Por defecto | Descripción |
|---|---|---|---|
| `ludens.languages.available` | Lista/String | `*` | Lista de códigos de idioma ISO separados por coma a incluir en la compilación (por ejemplo, `en,es,zh,ja`). |

Para añadir claves de traducción de un idioma no proporcionado por defecto, consulta la [guía de Localización en los documentos de COMPILACIÓN](/es/guide/build/android/#localización-y-traducciones).

---

## Fuentes Personalizadas y Tipografía

Ludens utiliza el sistema de recursos de Compose Multiplatform para gestionar la tipografía.

- **Carpeta de Origen**: Coloca tus archivos de fuentes personalizados `.ttf` o `.otf` en `project/assets/fonts/`.
- **Fuentes del Sistema**: La fuente de título predeterminada es `Plus Jakarta Sans` y la fuente de cuerpo es `Inter`.

Puedes configurar fuentes globales por defecto o especificar fuentes fallback para idiomas específicos en `ludens.properties`:

```properties
# ----- Fuentes -----
# Fuentes base (siempre incluidas, cámbialas solo si personalizas los archivos .ttf)
# ludens.fonts.display=plusjakartasans.ttf
# ludens.fonts.body=inter.ttf

# Fuentes fallback específicas de idioma
ludens.fonts.language.ja.body=notosans_sc.ttf
ludens.fonts.language.ja.display=notosans_sc.ttf
ludens.fonts.language.zh.body=notosans_sc.ttf
ludens.fonts.language.zh.display=notosans_sc.ttf
```

### Detalles de Propiedades

Estas propiedades utilizan el prefijo `ludens.fonts.*`:

| Propiedad | Tipo | Descripción |
|---|---|---|
| `display` | String | Fuente por defecto para elementos de título (títulos, botones). |
| `body` | String | Fuente por defecto para elementos de cuerpo/párrafo. |
| `language.<tag_idioma>.display` | String | Fuente de título fallback específicamente para el locale `<tag_idioma>`. |
| `language.<tag_idioma>.body` | String | Fuente de cuerpo fallback específicamente para el locale `<tag_idioma>`. |

