// @ts-check
import { defineConfig } from 'astro/config';
import sitemap from '@astrojs/sitemap';
import starlight from '@astrojs/starlight';
import starlightVersions from 'starlight-versions';

// https://astro.build/config
export default defineConfig({
  site: process.env.SITE_URL || "https://tryludens.vercel.app",
  base: '/',
  integrations: [
    sitemap(),
    starlight({
      title: 'Ludens',
      description: 'Documentation for porting RPG Maker MV/MZ games to Android and iOS with Compose Multiplatform.',
      logo: {
        alt: 'Ludens',
        dark: 'public/icon.svg',
        light: 'public/icon.svg',
      },
      social: [
        { icon: 'github', label: 'GitHub', href: 'https://github.com/ludens/ludens' },
      ],
      customCss: [
        './src/styles/custom.css',
      ],
      components: {
        SiteTitle: './src/components/SiteTitle.astro',
      },
      defaultLocale: 'root',
      locales: {
        root: { label: 'English', lang: 'en' },
        es: { label: 'Español', lang: 'es' },
      },
      plugins: [
        starlightVersions({
          current: {

          },
          versions: [
            { slug: '0.1.0' },
          ],
        }),
      ],
      sidebar: [
        {
          label: 'Overview',
          translations: { es: 'Visión General' },
          slug: 'overview',
        },
        {
          label: 'Guide',
          translations: { es: 'Guía' },
          items: [
            {
              label: 'Getting Started',
              translations: { es: 'Primeros Pasos' },
              slug: 'guide/getting-started',
            },
            {
              label: 'Game Export',
              translations: { es: 'Exportar el Juego' },
              slug: 'guide/game-export',
            },
            {
              label: 'Build',
              translations: { es: 'Compilar' },
              items: [
                {
                  label: 'Android',
                  slug: 'guide/build/android',
                },
                {
                  label: 'iOS',
                  slug: 'guide/build/ios',
                },
              ],
            },
          ],
        },
        {
          label: 'Configuration',
          translations: { es: 'Configuración' },
          items: [
            {
              label: 'Shared',
              translations: { es: 'Compartida' },
              slug: 'configuration/shared',
            },
            {
              label: 'Android',
              slug: 'configuration/android',
            },
            {
              label: 'iOS',
              slug: 'configuration/ios',
            },
          ],
        },
        {
          label: 'Reference',
          translations: { es: 'Referencia' },
          items: [
            {
              label: 'Settings Reference',
              translations: { es: 'Referencia de Ajustes' },
              slug: 'reference/settings',
            },
            {
              label: 'Input Keys',
              translations: { es: 'Teclas de Entrada' },
              slug: 'reference/input-keys',
            },
            {
              label: 'Positionable Elements',
              translations: { es: 'Elementos Posicionables' },
              slug: 'reference/positions',
            },
          ],
        },
        {
          label: 'Resources',
          translations: { es: 'Recursos' },
          items: [
            {
              label: 'Examples',
              translations: { es: 'Ejemplos' },
              slug: 'resources/examples',
            },
            {
              label: 'Changelog',
              translations: { es: 'Historial de Versiones' },
              slug: 'resources/changelog',
            },
          ],
        },
        {
          label: 'FAQ',
          translations: { es: 'Preguntas Frecuentes' },
          slug: 'faq',
        },
      ],
    }),
  ],
});
