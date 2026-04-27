---
title: Shared Configuration
description: Platform-agnostic configuration properties for Ludens.
---

Shared configuration properties are managed through `ludens.properties` in the project root. These settings affect the core application logic and the user interface across all platforms.

## Settings Presets

Ludens uses presets to simplify the initial configuration. You can choose from several built-in presets or use the `custom` name to define your own behavior.

Edit your `ludens.properties`:

```properties
# Selects the active preset
ludens.settings.presetName=recommended
```

### Preset Reference

The following property is configured under the `ludens.settings.*` prefix:

| Property     | Default       | Values                                                           | Description                      |
| ------------ | ------------- | ---------------------------------------------------------------- | -------------------------------- |
| `presetName` | `recommended` | `recommended`, `noactions`, `nocontrols`, `minimalist`, `custom` | Selects the active preset logic. |

#### Built-in Presets

| Name          | Behavior                                                                      |
| ------------- | ----------------------------------------------------------------------------- |
| `recommended` | (Default) Balanced setup with on-screen controls and quick actions enabled.   |
| `noactions`   | Disables the quick actions panel but keeps on-screen controls.                |
| `nocontrols`  | Disables on-screen controls but keeps the quick actions panel for navigation. |
| `minimalist`  | Maximum screen space: disables both controls and quick actions by default.    |
| `custom`      | Ignores built-in logic and uses the custom preset properties defined below.   |

---

## Custom Preset Configuration

The following properties are only consumed when `ludens.settings.presetName` is set to `custom`. They allow you to fine-tune the game's default state:

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
| ---------------- | ------- | ---------- | ---------------------------------------------------------------------- |
| `toolMuted`      | Boolean | `false`    | Default audio mute state (`true`, `false`).                            |
| `toolShowFps`    | Boolean | `false`    | Default visibility for the FPS counter.                                |
| `toolUseWebgl`   | Boolean | `false`    | Default WebGL switch state (MV only).                                  |
| `controlEnabled` | Boolean | `true`     | Default state for the virtual controls overlay.                        |
| `controlAlpha`   | Float   | `0.4`      | Virtual control opacity (`0.0` to `1.0`).                              |
| `actionEnabled`  | Boolean | `false`    | Default visibility for the quick actions panel.                        |
| `actionItems`    | List    | `settings` | Comma-separated items: `settings`, `controls`, `mute`, `fps`, `webgl`. |
| `systemTheme`    | String  | `system`   | Default theme: `system`, `light`, `dark`.                              |
| `systemLanguage` | String  | `system`   | Default language: `system`, `en`, `es`.                                |
