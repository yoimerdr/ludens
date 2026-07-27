---
title: iOS
description: iOS specific configuration for Ludens.
---

:::caution[Experimental / Untested]
iOS support and build pre-processing features are currently experimental and have not been fully tested in production environments.
:::

Ludens manages iOS build configuration through `ludens.properties` in the project root.

## Application Identity

iOS reads its identity from the shared `ludens.app.*` namespace by default (see
[Shared Configuration](/configuration/shared/#app-identity)). The keys below are optional
iOS-only overrides — they are commented out in `ludens.properties` and only need to be set
when iOS must diverge from the shared values (e.g., bundle identifier mismatches).

```properties
# Optional iOS identity overrides
# ludens.ios.bundleId=
# ludens.ios.version=
# ludens.ios.versionCode=
# ludens.ios.name=
```

Configurable properties using the `ludens.ios.*` prefix:

| Property      | Type   | Default (inherits from)  | Description                                                         |
|---------------|--------|--------------------------|---------------------------------------------------------------------|
| `bundleId`    | String | `ludens.app.id`          | iOS Bundle Identifier. Override when `ludens.app.id` cannot be used. |
| `version`     | String | `ludens.app.version`     | Visible marketing version (`MARKETING_VERSION`).                    |
| `versionCode` | String | `ludens.app.versionCode` | Internal build number (`CURRENT_PROJECT_VERSION`).                  |
| `name`        | String | `ludens.app.name`        | Display name (`PRODUCT_NAME`).                                      |

---

## iOS Build Pre-Processing

Ludens includes an experimental pre-build metadata synchronization plugin that propagates application identity properties directly into `Config.xcconfig` before building the iOS target.

```properties
# Experimental iOS pre-build metadata sync
ludens.ios.build.enable=false
```

Configure this property using the `ludens.ios.build.*` prefix:

| Property | Type    | Default | Description                                                                                                                   |
|----------|---------|---------|-------------------------------------------------------------------------------------------------------------------------------|
| `enable` | Boolean | `false` | Enables pre-build sync of `PRODUCT_NAME`, `PRODUCT_BUNDLE_IDENTIFIER`, `MARKETING_VERSION`, and `CURRENT_PROJECT_VERSION`. |

:::warning[Untested Feature]
`ludens.ios.build.enable` is set to `false` by default. Enable this option with caution as it has not been validated across Xcode build workflows.
:::
