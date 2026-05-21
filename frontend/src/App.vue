<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <div class="app-shell">
    <section class="hero text-white">
      <div class="container py-5">
        <div class="row align-items-center g-4">
          <div class="col-lg-8">
            <span class="badge rounded-pill text-bg-light text-primary mb-3">
              <i class="bi bi-github me-1"></i>
              Assistant open source Java
            </span>
            <h1 class="display-4 fw-bold mb-3">Ticket Scout</h1>
            <p class="lead mb-0">
              Suivez les issues pour débutants des dépôts Java populaires, sélectionnez les
              meilleures opportunités et suivez leur progression jusqu'à l'intégration de votre
              contribution.
            </p>
          </div>
          <div class="col-lg-4">
            <div class="hero-card">
              <i class="bi bi-stars fs-1"></i>
              <p class="mb-0 mt-3">
                Préchargé avec des tickets GitHub découverts par la recherche MCP dans les dépôts
                Java de plus de 100 étoiles.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <main class="container py-5">
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.app-shell {
  min-height: 100vh;
}

.hero {
  background:
    radial-gradient(circle at top left, rgba(255, 255, 255, 0.25), transparent 32rem),
    linear-gradient(135deg, #0f172a 0%, #312e81 45%, #7c3aed 100%);
}

.hero-card {
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: 1.5rem;
  padding: 1.5rem;
  background: rgba(255, 255, 255, 0.14);
  backdrop-filter: blur(18px);
  box-shadow: 0 24px 80px rgba(15, 23, 42, 0.3);
}
</style>
