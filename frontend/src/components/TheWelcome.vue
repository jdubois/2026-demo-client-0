<script setup>
import WelcomeItem from './WelcomeItem.vue'
import DocumentationIcon from './icons/IconDocumentation.vue'
import ToolingIcon from './icons/IconTooling.vue'
import EcosystemIcon from './icons/IconEcosystem.vue'
import CommunityIcon from './icons/IconCommunity.vue'
import SupportIcon from './icons/IconSupport.vue'

const openReadmeInEditor = () => fetch('/__open-in-editor?file=README.md')
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Documentation</template>

    La
    <a href="https://vuejs.org/" target="_blank" rel="noopener">documentation officielle</a>
    de Vue fournit toutes les informations nécessaires pour bien démarrer.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <ToolingIcon />
    </template>
    <template #heading>Outils</template>

    Ce projet est servi et empaqueté avec
    <a href="https://vite.dev/guide/features.html" target="_blank" rel="noopener">Vite</a>. La
    configuration d'IDE recommandée est
    <a href="https://code.visualstudio.com/" target="_blank" rel="noopener">VSCode</a>
    +
    <a href="https://github.com/vuejs/language-tools" target="_blank" rel="noopener"
      >Vue - Officiel</a
    >. Si vous devez tester vos composants et pages web, consultez
    <a href="https://vitest.dev/" target="_blank" rel="noopener">Vitest</a>
    et
    <a href="https://www.cypress.io/" target="_blank" rel="noopener">Cypress</a>
    /
    <a href="https://playwright.dev/" target="_blank" rel="noopener">Playwright</a>.

    <br />

    Des instructions supplémentaires sont disponibles dans
    <a href="javascript:void(0)" @click="openReadmeInEditor"><code>README.md</code></a
    >.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <EcosystemIcon />
    </template>
    <template #heading>Écosystème</template>

    Découvrez les outils et bibliothèques officiels pour votre projet :
    <a href="https://pinia.vuejs.org/" target="_blank" rel="noopener">Pinia</a>,
    <a href="https://router.vuejs.org/" target="_blank" rel="noopener">Vue Router</a>,
    <a href="https://test-utils.vuejs.org/" target="_blank" rel="noopener">Vue Test Utils</a> et
    <a href="https://github.com/vuejs/devtools" target="_blank" rel="noopener">Vue Dev Tools</a>. Si
    vous cherchez d'autres ressources, nous vous suggérons de consulter
    <a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">Awesome Vue</a>.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <CommunityIcon />
    </template>
    <template #heading>Communauté</template>

    Vous êtes bloqué ? Posez votre question sur
    <a href="https://chat.vuejs.org" target="_blank" rel="noopener">Vue Land</a>
    (notre serveur Discord officiel), ou
    <a href="https://stackoverflow.com/questions/tagged/vue.js" target="_blank" rel="noopener"
      >StackOverflow</a
    >. Vous pouvez aussi suivre le compte officiel
    <a href="https://bsky.app/profile/vuejs.org" target="_blank" rel="noopener">@vuejs.org</a>
    sur Bluesky ou le compte
    <a href="https://x.com/vuejs" target="_blank" rel="noopener">@vuejs</a>
    sur X pour les dernières nouvelles de l'univers Vue.
  </WelcomeItem>

  <WelcomeItem>
    <template #icon>
      <SupportIcon />
    </template>
    <template #heading>Soutenir Vue</template>

    En tant que projet indépendant, Vue s'appuie sur le soutien de la communauté pour durer. Vous
    pouvez nous aider en
    <a href="https://vuejs.org/sponsor/" target="_blank" rel="noopener">devenant sponsor</a>.
  </WelcomeItem>
</template>
