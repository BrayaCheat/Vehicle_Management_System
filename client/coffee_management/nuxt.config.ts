// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  extends: ["@nuxt/ui-pro"],
  modules: [
    "@nuxt/ui",
    "@nuxtjs/google-fonts",
    "@vueuse/nuxt",
    "@nuxtjs/supabase",
    "@pinia/nuxt",
    "nuxt-aos",
    "nuxt-icon",
    "@nuxt/image"
  ],
  googleFonts: {
    families: {
      Quicksand: [500],
    },
  },
  app: {
    head: {
      title: "DRIFT Cambodia",
      link: [
        { rel: 'icon', type: 'image/x-icon', href: '/images/drift.svg' }
      ],
      script: [
        {
          src: "https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"
        }
      ]
    }
  },
  supabase:{
    redirect: false
  }
});