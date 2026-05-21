<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { statuses, useTicketsStore } from '@/stores/tickets'

const ticketsStore = useTicketsStore()

const emptyForm = {
  id: null,
  title: '',
  repository: '',
  link: '',
  status: 'NEW',
}

const form = reactive({ ...emptyForm })
const saving = ref(false)
const actionError = ref('')

const statusByValue = computed(() =>
  Object.fromEntries(statuses.map((status) => [status.value, status])),
)

const isEditing = computed(() => form.id !== null)
const inProgress = computed(
  () => ticketsStore.tickets.filter((ticket) => ticket.status === 'IN_PROGRESS').length,
)
const activeRepositories = computed(
  () => new Set(ticketsStore.tickets.map((ticket) => ticket.repository)).size,
)

function resetForm() {
  Object.assign(form, emptyForm)
  actionError.value = ''
}

function editTicket(ticket) {
  Object.assign(form, ticket)
  actionError.value = ''
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

async function submitTicket() {
  saving.value = true
  actionError.value = ''

  try {
    await ticketsStore.saveTicket({ ...form })
    resetForm()
  } catch (error) {
    actionError.value = error.message
  } finally {
    saving.value = false
  }
}

async function removeTicket(ticket) {
  actionError.value = ''

  try {
    await ticketsStore.removeTicket(ticket.id)
    if (form.id === ticket.id) {
      resetForm()
    }
  } catch (error) {
    actionError.value = error.message
  }
}

onMounted(() => {
  ticketsStore.loadTickets().catch((error) => {
    actionError.value = error.message
  })
})
</script>

<template>
  <div class="dashboard">
    <section class="dashboard-hero">
      <div>
        <span class="section-kicker">Vue d'ensemble stratégique</span>
        <h2 class="display-6 fw-bold mb-2">Pilotez le flux des contributions open source</h2>
        <p class="text-secondary mb-0">
          Priorisez les issues GitHub, suivez les signaux de responsabilité et gardez le pipeline
          de contribution visible de la découverte à la finalisation.
        </p>
      </div>
      <div class="hero-meta">
        <span class="meta-label">Mode de fonctionnement</span>
        <strong>Triage centralisé</strong>
      </div>
    </section>

    <section class="row g-3">
      <div class="col-sm-6 col-xl-3">
        <div class="metric-card">
          <span class="metric-icon bg-primary-subtle text-primary">
            <i class="bi bi-collection"></i>
          </span>
          <span class="metric-label">Tickets suivis</span>
          <strong>{{ ticketsStore.total }}</strong>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="metric-card">
          <span class="metric-icon bg-info-subtle text-info">
            <i class="bi bi-building-check"></i>
          </span>
          <span class="metric-label">Dépôts</span>
          <strong>{{ activeRepositories }}</strong>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="metric-card">
          <span class="metric-icon bg-warning-subtle text-warning">
            <i class="bi bi-activity"></i>
          </span>
          <span class="metric-label">En cours</span>
          <strong>{{ inProgress }}</strong>
        </div>
      </div>
      <div class="col-sm-6 col-xl-3">
        <div class="metric-card">
          <span class="metric-icon bg-success-subtle text-success">
            <i class="bi bi-check2-circle"></i>
          </span>
          <span class="metric-label">Terminés</span>
          <strong>{{ ticketsStore.completed }}</strong>
        </div>
      </div>
    </section>

    <div class="row g-4 align-items-start">
      <section class="col-xl-4">
        <div class="enterprise-card sticky-xl-top form-card">
          <div class="card-header-block">
            <div>
              <span class="section-kicker">Contrôle de l'arrivée</span>
              <h3 class="h5 fw-bold mb-0">
                {{ isEditing ? 'Mettre à jour le ticket' : 'Enregistrer un ticket' }}
              </h3>
            </div>
            <span class="card-action-icon">
              <i class="bi bi-ticket-perforated"></i>
            </span>
          </div>

          <form @submit.prevent="submitTicket">
            <div class="mb-3">
              <label for="title" class="form-label fw-semibold">Titre</label>
              <input
                id="title"
                v-model.trim="form.title"
                class="form-control"
                required
                maxlength="240"
              />
            </div>

            <div class="mb-3">
              <label for="repository" class="form-label fw-semibold">Dépôt GitHub</label>
              <input
                id="repository"
                v-model.trim="form.repository"
                class="form-control"
                required
                maxlength="120"
                placeholder="proprietaire/depot"
              />
            </div>

            <div class="mb-3">
              <label for="link" class="form-label fw-semibold">Lien de l'issue</label>
              <input
                id="link"
                v-model.trim="form.link"
                class="form-control"
                required
                maxlength="500"
                pattern="https://github.com/.+/.+/issues/[0-9]+"
                placeholder="https://github.com/owner/repository/issues/123"
              />
            </div>

            <div class="mb-4">
              <label for="status" class="form-label fw-semibold">Statut</label>
              <select id="status" v-model="form.status" class="form-select" required>
                <option v-for="status in statuses" :key="status.value" :value="status.value">
                  {{ status.label }}
                </option>
              </select>
            </div>

            <div v-if="actionError" class="alert alert-danger" role="alert">
              {{ actionError }}
            </div>

            <div class="d-grid gap-2">
              <button class="btn btn-dark btn-lg" type="submit" :disabled="saving">
                <span
                  v-if="saving"
                  class="spinner-border spinner-border-sm me-2"
                  aria-hidden="true"
                ></span>
                {{ isEditing ? 'Enregistrer les modifications' : 'Ajouter le ticket' }}
              </button>
              <button
                v-if="isEditing"
                class="btn btn-outline-secondary"
                type="button"
                @click="resetForm"
              >
                Annuler la modification
              </button>
            </div>
          </form>
        </div>
      </section>

      <section class="col-xl-8">
        <div class="enterprise-card">
          <div class="card-header-block border-bottom pb-3 mb-0">
            <div>
              <span class="section-kicker">Registre des tickets</span>
              <h3 class="h5 fw-bold mb-0">Backlog du portefeuille</h3>
            </div>
            <span class="readiness-pill">
              {{ ticketsStore.readyToStart }} prêts pour le triage
            </span>
          </div>

          <div v-if="ticketsStore.loading" class="text-center py-5">
            <div class="spinner-border text-primary" role="status">
              <span class="visually-hidden">Chargement...</span>
            </div>
          </div>

          <div v-else-if="ticketsStore.error" class="alert alert-danger mt-4" role="alert">
            {{ ticketsStore.error }}
          </div>

          <div v-else-if="ticketsStore.tickets.length === 0" class="empty-state">
            <i class="bi bi-inbox"></i>
            <h4 class="h5 fw-bold">Aucun ticket enregistré</h4>
            <p class="text-secondary mb-0">
              Ajoutez une issue GitHub pour commencer à suivre le portefeuille.
            </p>
          </div>

          <div v-else class="table-responsive">
            <table class="table enterprise-table align-middle mb-0">
              <thead>
                <tr>
                  <th scope="col">Ticket</th>
                  <th scope="col">Dépôt</th>
                  <th scope="col">Statut</th>
                  <th scope="col" class="text-end">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="ticket in ticketsStore.tickets" :key="ticket.id">
                  <td>
                    <div class="fw-bold">{{ ticket.title }}</div>
                    <a :href="ticket.link" target="_blank" rel="noreferrer" class="issue-link">
                      <i class="bi bi-box-arrow-up-right me-1"></i>
                      Ouvrir l'issue
                    </a>
                  </td>
                  <td>
                    <span class="repo-name">
                      <i class="bi bi-github me-2"></i>
                      {{ ticket.repository }}
                    </span>
                  </td>
                  <td>
                    <span
                      class="badge rounded-pill status-badge"
                      :class="statusByValue[ticket.status].className"
                    >
                      {{ statusByValue[ticket.status].label }}
                    </span>
                  </td>
                  <td>
                    <div class="d-flex justify-content-end gap-2">
                      <button
                        class="btn btn-outline-primary btn-sm"
                        type="button"
                        @click="editTicket(ticket)"
                      >
                        <i class="bi bi-pencil me-1"></i>
                        Modifier
                      </button>
                      <button
                        class="btn btn-outline-danger btn-sm"
                        type="button"
                        @click="removeTicket(ticket)"
                      >
                        <i class="bi bi-trash me-1"></i>
                        Supprimer
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  display: grid;
  gap: 1.5rem;
}

.dashboard-hero,
.enterprise-card,
.metric-card {
  border: 1px solid #d8e2ef;
  border-radius: 1rem;
  background: #ffffff;
  box-shadow: 0 20px 55px rgba(15, 23, 42, 0.07);
}

.dashboard-hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1.5rem;
  padding: 1.75rem;
  background: linear-gradient(135deg, rgba(37, 99, 235, 0.08), rgba(14, 165, 233, 0.02)), #ffffff;
}

.section-kicker {
  display: block;
  color: #2563eb;
  font-size: 0.75rem;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.hero-meta {
  min-width: 14rem;
  padding: 1rem;
  border: 1px solid #dbeafe;
  border-radius: 0.875rem;
  background: #f8fbff;
}

.meta-label {
  display: block;
  color: #64748b;
  font-size: 0.75rem;
  font-weight: 800;
  text-transform: uppercase;
}

.metric-card {
  display: grid;
  gap: 0.4rem;
  padding: 1.35rem;
}

.metric-icon {
  display: grid;
  width: 2.5rem;
  height: 2.5rem;
  margin-bottom: 0.35rem;
  place-items: center;
  border-radius: 0.8rem;
  font-size: 1.2rem;
}

.metric-label {
  display: block;
  color: #64748b;
  font-size: 0.78rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.metric-card strong {
  color: #0f172a;
  font-size: 2rem;
  line-height: 1;
}

.enterprise-card {
  padding: 1.5rem;
}

.form-card {
  top: 1.5rem;
}

.card-header-block {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.card-action-icon {
  display: grid;
  width: 2.75rem;
  height: 2.75rem;
  place-items: center;
  border-radius: 0.85rem;
  color: #2563eb;
  background: #eff6ff;
  font-size: 1.25rem;
}

.readiness-pill {
  padding: 0.45rem 0.8rem;
  border-radius: 999px;
  color: #075985;
  background: #e0f2fe;
  font-size: 0.78rem;
  font-weight: 800;
}

.enterprise-table {
  --bs-table-bg: transparent;
}

.enterprise-table thead th {
  color: #64748b;
  font-size: 0.72rem;
  font-weight: 800;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.enterprise-table tbody td {
  padding: 1rem 0.75rem;
  border-color: #e2e8f0;
}

.repo-name {
  color: #475569;
  font-weight: 600;
}

.issue-link {
  color: #2563eb;
  font-size: 0.88rem;
  font-weight: 700;
}

.status-badge {
  min-width: 6.5rem;
}

.empty-state {
  display: grid;
  place-items: center;
  padding: 3.5rem 1rem;
  text-align: center;
}

.empty-state i {
  color: #94a3b8;
  font-size: 2.5rem;
  margin-bottom: 0.75rem;
}

@media (max-width: 767.98px) {
  .dashboard-hero {
    align-items: stretch;
    flex-direction: column;
  }

  .hero-meta {
    min-width: 0;
  }
}
</style>
