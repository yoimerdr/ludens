---
title: Shared Configuration
description: Platform-agnostic configuration properties for Ludens.
slug: 0.4.0/configuration/shared
---

Shared configuration properties are managed through `ludens.properties` in the project root. These
settings affect the core application logic and the user interface across all platforms.

## Settings Presets

Ludens uses presets to simplify the initial configuration. You can choose from several built-in
presets or use the `custom` name to define your own behavior.

Edit your `ludens.properties`:

```properties
# Selects the active preset
ludens.settings.presetName=recommended
```

### Preset Reference

The following property is configured under the `ludens.settings.*` prefix:

| Property     | Default       | Values                                                           | Description                      |
|--------------|---------------|------------------------------------------------------------------|----------------------------------|
| `presetName` | `recommended` | `recommended`, `noactions`, `nocontrols`, `minimalist`, `custom` | Selects the active preset logic. |

#### Built-in Presets

| Name          | Behavior                                                                      |
|---------------|-------------------------------------------------------------------------------|
| `recommended` | (Default) Balanced setup with on-screen controls and quick actions enabled.   |
| `noactions`   | Disables the quick actions panel but keeps on-screen controls.                |
| `nocontrols`  | Disables on-screen controls but keeps the quick actions panel for navigation. |
| `minimalist`  | Maximum screen space: disables both controls and quick actions by default.    |
| `custom`      | Ignores built-in logic and uses the custom preset properties defined below.   |

***

## Custom Preset Configuration

The following properties are only consumed when `ludens.settings.presetName` is set to `custom`.
They allow you to fine-tune the game's default state:

```properties
# Custom preset overrides
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

### Property Details

These properties use the `ludens.settings.preset.*` prefix:

| Property         | Type    | Default    | Values / Description                                                   |
|------------------|---------|------------|------------------------------------------------------------------------|
| `toolMuted`      | Boolean | `false`    | Default audio mute state (`true`, `false`).                            |
| `toolShowFps`    | Boolean | `false`    | Default visibility for the FPS counter.                                |
| `toolUseWebgl`   | Boolean | `false`    | Default WebGL switch state (MV only).                                  |
| `controlEnabled` | Boolean | `true`     | Default state for the virtual controls overlay.                        |
| `controlAlpha`   | Float   | `0.4`      | Virtual control opacity (`0.0` to `1.0`).                              |
| `actionEnabled`  | Boolean | `false`    | Default visibility for the quick actions panel.                        |
| `actionItems`    | List    | `settings` | Comma-separated items: `settings`, `controls`, `mute`, `fps`, `webgl`. |
| `systemTheme`    | String  | `system`   | Default theme: `system`, `light`, `dark`.                              |
| `systemLanguage` | String  | `system`   | Default language fallback: `system`, `en`, `es`, `zh`, `ja`, `pt-rBR`, `ru`. |

***

## Localization & Languages

Ludens supports multiple languages for its wrapper client UI.

:::caution[Important]
This localization system translates **only** the native client/wrapper UI (such as the Settings screen, control overlays, and native dialogs). It **does not** translate the actual RPG Maker game content or dialogues.
:::

By default, the compilation includes all languages found in the project's assets. If your game is only intended for a single language or a specific set of languages, you can restrict the options shown in the wrapper's settings menu by modifying `ludens.properties`:

```properties
# ----- Languages -----
# Which languages to include in the build.
# "*" means all discovered from project/assets/languages/ (Default).
# List only the tags you need to limit the build (e.g. en,es).
ludens.languages.available=en,es
```

| Property | Type | Default | Description |
|---|---|---|---|
| `ludens.languages.available` | List/String | `*` | Comma-separated list of ISO language tags to include in the build (e.g., `en,es,zh,ja`). |

To add translation keys for a language not provided by default, see the [Localization guide in the BUILD docs](/0.4.0/guide/build/android/#localization--translations).

***

## Custom Fonts & Typography

Ludens uses Compose Multiplatform's resource system to manage typography.

* **Source Folder**: Place your custom `.ttf` or `.otf` font files in `project/assets/fonts/`.
* **System Fonts**: The default display font is `Plus Jakarta Sans` and the body font is `Inter`.

You can configure global default fonts or specify language-specific font fallbacks in `ludens.properties`:

```properties
# ----- Fonts -----
# Base fonts (always included, change only if you customize the .ttf files)
# ludens.fonts.display=plusjakartasans.ttf
# ludens.fonts.body=inter.ttf

# Language-specific font fallbacks
ludens.fonts.language.ja.body=notosans_sc.ttf
ludens.fonts.language.ja.display=notosans_sc.ttf
ludens.fonts.language.zh.body=notosans_sc.ttf
ludens.fonts.language.zh.display=notosans_sc.ttf
```

### Property Details

These properties use the `ludens.fonts.*` prefix:

| Property | Type | Description |
|---|---|---|
| `display` | String | Default font for heading elements (titles, buttons). |
| `body` | String | Default font for body/paragraph elements. |
| `language.<lang_tag>.display` | String | Heading font fallback specifically for `<lang_tag>` locale. |
| `language.<lang_tag>.body` | String | Body font fallback specifically for `<lang_tag>` locale. |
