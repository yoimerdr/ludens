---
title: Changelog
description: Version history and updates for the Ludens project.
---

This page documents the release history and major changes for the Ludens project.

For a complete list of commits and minor fixes, please refer to
the [GitHub Releases](https://github.com/yoimerdr/ludens/releases) page.

## v0.3.0 - 2026-04-25

### Added

- `build-logic` module with custom Gradle plugins for Ludens configuration, generated resources, and
  generated permissions.
- Root `ludens.properties` configuration as the main source for app identity, manifest flags,
  permissions, and settings presets.
- Typed compose resource accessors and settings preset generation.
- Platform-specific WebView helpers and memory-management utilities for Android and iOS.
- New keyboard control model and related UI state for game input mapping.
- Expanded range of keys supported by the control buttons to include standard and specific graphic
  keys.
- Responsive layout primitives and reusable design tokens for spacing, radius, strokes, and
  breakpoints.
- Searchable dropdown component for control selection flows.
- Boot resource handling, including `www/index.html` and memory-cleaner boot script support.

### Changed

- Android build now reads Ludens configuration from `ludens.properties` and applies app identity and
  manifest placeholders from that model.
- Settings preset generation now resolves default values from the new configuration pipeline instead
  of hardcoded app-side defaults.
- Home and settings screens were reworked for updated control behavior, action ordering, and more
  responsive layouts.
- Theme, typography, spacing, card, floating, and dock components were refreshed to match a new
  visual system.
- WebView startup and lifecycle handling were updated to improve stability and memory behavior.
- The plugin and settings experience was updated around `YDP_Ludens.js` and related control/key
  handling.

### Fixed

- Fixed configuration loading behavior that could be affected by cache or stale values.
- Fixed missing file/path handling for resource bootstrapping.
- Fixed settings layout padding mismatches.
- Fixed behavior around unexpected active actions.
- Fixed black screen for RPG Maker MZ games.

## v0.2.0 - 2026-02-05

### Added

- onRestart callback implementation for navigation and state reset

### Changed

- Refactored settings events to use `UpdateSettings` sealed interface pattern
- Improved settings event and request handling

### Fixed

- Fixed on-screen controls not showing when YDP_Ludens plugin is disabled or not present
- Fixed unnecessary WebView restart when updating settings without navigating away

## v0.1.0 - 2026-01-27

Initial project release.

### Added

#### Android Build Support

- Android 21+ support (Designed for modern devices)
- Optimized WebView for RPG Maker MV/MZ

#### Native Overlay Controls

- Virtual Joystick (Configurable opacity & position)
- Action Buttons (A, B, X, Y)

#### Comprehensive Settings System

- System Theme & Language
- Audio Mute & FPS Counter
- WebGL Toggle

#### Easy Android Build Configuration

- Customizable applicationId, Version, and Name via gradle.properties
- Simplified Asset Injection (composeResources/files/www)

:::note
iOS support is planned for future releases. The current codebase contains shared logic, but build
configuration is currently Android-focused.
:::
