// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightVersions from 'starlight-versions';

// https://astro.build/config
export default defineConfig({
  // site: "",
  base: '/docs',
  integrations: [
    starlight({
      title: 'Ludens',
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
      defaultLocale: 'root',
      locales: {
        root: { label: 'English', lang: 'en' },
        es: { label: 'Español', lang: 'es' },
      },
      plugins: [
        // starlightVersions({
        //   current: {
        //
        //   },
        //   versions: [
        //     { slug: '0.1.0' },
        //   ],
        // }),
      ],
      sidebar: [

      ],
    }),
  ],
});
