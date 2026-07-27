---
title: iOS
description: Configuración específica de iOS para Ludens.
---

:::caution[Experimental / No probado]
El soporte de iOS y el pre-procesado de compilación son características experimentales y no se han probado completamente en entornos de producción.
:::

Ludens gestiona la configuración de compilación de iOS a través de `ludens.properties` en la raíz del proyecto.

## Identidad de la Aplicación

iOS lee su identidad del espacio de nombres compartido `ludens.app.*` por defecto (ver
[Configuración Compartida](/es/configuration/shared/#identidad-de-la-app)). Las claves de abajo son
sobreescrituras opcionales exclusivas de iOS — están comentadas en `ludens.properties` y solo
necesitas establecerlas cuando iOS deba divergir de los valores compartidos (por ejemplo, discrepancias en el identificador del paquete).

```properties
# Sobreescrituras opcionales de identidad para iOS
# ludens.ios.bundleId=
# ludens.ios.version=
# ludens.ios.versionCode=
# ludens.ios.name=
```

Propiedades configurables usando el prefijo `ludens.ios.*`:

| Propiedad     | Tipo   | Por defecto (hereda de)  | Descripción                                                            |
|---------------|--------|--------------------------|------------------------------------------------------------------------|
| `bundleId`    | String | `ludens.app.id`          | Bundle Identifier de iOS. Sobreescribir si `ludens.app.id` no aplica.  |
| `version`     | String | `ludens.app.version`     | Versión visible de marketing (`MARKETING_VERSION`).                   |
| `versionCode` | String | `ludens.app.versionCode` | Número de compilación interno (`CURRENT_PROJECT_VERSION`).             |
| `name`        | String | `ludens.app.name`        | Nombre visible (`PRODUCT_NAME`).                                       |

---

## Pre-Procesado del Build de iOS

Ludens incluye un plugin experimental de sincronización de metadatos pre-compilación que propaga las propiedades de identidad directamente en `Config.xcconfig` antes de compilar para iOS.

```properties
# Sincronización experimental de metadatos pre-build para iOS
ludens.ios.build.enable=false
```

Configura esta propiedad usando el prefijo `ludens.ios.build.*`:

| Propiedad | Tipo     | Por defecto | Descripción                                                                                                                   |
|-----------|----------|-------------|-------------------------------------------------------------------------------------------------------------------------------|
| `enable`  | Booleano | `false`     | Activa la sincronización pre-build de `PRODUCT_NAME`, `PRODUCT_BUNDLE_IDENTIFIER`, `MARKETING_VERSION` y `CURRENT_PROJECT_VERSION`. |

:::warning[Característica no probada]
`ludens.ios.build.enable` está desactivado (`false`) por defecto. Activa esta opción con precaución ya que no ha sido validada en flujos de trabajo de Xcode.
:::
