# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.0] - 2026-02-05

### Added
- onRestart callback implementation for navigation and state reset

### Changed
- Refactored settings events to use `UpdateSettings` sealed interface pattern
- Improved settings event and request handling

### Fixed
- Fixed on-screen controls not showing when YDP_Ludens plugin is disabled or not present
- Fixed unnecessary WebView restart when updating settings without navigating away

## [0.1.0] - 2026-01-27

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

> **Note:** iOS support is planned for future releases. The current codebase contains shared logic, but build configuration is currently Android-focused.
