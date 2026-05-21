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
  <div class="row g-4">
    <section class="col-lg-4">
      <div class="card border-0 shadow-lg sticky-lg-top form-card">
        <div class="card-body p-4">
          <div class="d-flex align-items-center justify-content-between mb-4">
            <div>
              <p class="text-uppercase text-primary fw-bold small mb-1">Ticket details</p>
              <h2 class="h4 fw-bold mb-0">{{ isEditing ? 'Edit ticket' : 'Add ticket' }}</h2>
            </div>
            <i class="bi bi-ticket-perforated fs-2 text-primary"></i>
          </div>

          <form @submit.prevent="submitTicket">
            <div class="mb-3">
              <label for="title" class="form-label fw-semibold">Title</label>
              <input
                id="title"
                v-model.trim="form.title"
                class="form-control"
                required
                maxlength="240"
              />
            </div>

            <div class="mb-3">
              <label for="repository" class="form-label fw-semibold">GitHub repository</label>
              <input
                id="repository"
                v-model.trim="form.repository"
                class="form-control"
                required
                maxlength="120"
                placeholder="owner/repository"
              />
            </div>

            <div class="mb-3">
              <label for="link" class="form-label fw-semibold">Issue link</label>
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
              <label for="status" class="form-label fw-semibold">Status</label>
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
              <button class="btn btn-primary btn-lg" type="submit" :disabled="saving">
                <span
                  v-if="saving"
                  class="spinner-border spinner-border-sm me-2"
                  aria-hidden="true"
                ></span>
                {{ isEditing ? 'Save changes' : 'Add ticket' }}
              </button>
              <button
                v-if="isEditing"
                class="btn btn-outline-secondary"
                type="button"
                @click="resetForm"
              >
                Cancel edit
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>

    <section class="col-lg-8">
      <div class="row g-3 mb-4">
        <div class="col-md-4">
          <div class="metric-card">
            <span class="metric-label">Tracked tickets</span>
            <strong>{{ ticketsStore.total }}</strong>
          </div>
        </div>
        <div class="col-md-4">
          <div class="metric-card">
            <span class="metric-label">Ready to help</span>
            <strong>{{ ticketsStore.readyToStart }}</strong>
          </div>
        </div>
        <div class="col-md-4">
          <div class="metric-card">
            <span class="metric-label">Completed</span>
            <strong>{{ ticketsStore.completed }}</strong>
          </div>
        </div>
      </div>

      <div v-if="ticketsStore.loading" class="text-center py-5">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <div v-else-if="ticketsStore.error" class="alert alert-danger" role="alert">
        {{ ticketsStore.error }}
      </div>

      <div v-else class="ticket-grid">
        <article v-for="ticket in ticketsStore.tickets" :key="ticket.id" class="ticket-card">
          <div class="d-flex align-items-start justify-content-between gap-3">
            <span class="badge rounded-pill" :class="statusByValue[ticket.status].className">
              {{ statusByValue[ticket.status].label }}
            </span>
            <a :href="ticket.link" target="_blank" rel="noreferrer" class="btn btn-sm btn-light">
              <i class="bi bi-box-arrow-up-right me-1"></i>
              GitHub
            </a>
          </div>

          <h3 class="h5 fw-bold mt-3 mb-2">{{ ticket.title }}</h3>
          <p class="repo-name mb-4">
            <i class="bi bi-github me-2"></i>
            {{ ticket.repository }}
          </p>

          <div class="d-flex gap-2">
            <button
              class="btn btn-outline-primary btn-sm"
              type="button"
              @click="editTicket(ticket)"
            >
              <i class="bi bi-pencil me-1"></i>
              Edit
            </button>
            <button
              class="btn btn-outline-danger btn-sm"
              type="button"
              @click="removeTicket(ticket)"
            >
              <i class="bi bi-trash me-1"></i>
              Remove
            </button>
          </div>
        </article>
      </div>
    </section>
  </div>
</template>

<style scoped>
.form-card {
  top: 1.5rem;
  border-radius: 1.25rem;
}

.metric-card,
.ticket-card {
  border: 1px solid rgba(99, 102, 241, 0.12);
  border-radius: 1.25rem;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 18px 48px rgba(15, 23, 42, 0.08);
}

.metric-card {
  padding: 1.25rem;
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
  color: #312e81;
  font-size: 2.25rem;
  line-height: 1;
}

.ticket-grid {
  display: grid;
  gap: 1rem;
}

.ticket-card {
  padding: 1.25rem;
  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
}

.ticket-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 24px 70px rgba(49, 46, 129, 0.16);
}

.repo-name {
  color: #64748b;
  font-weight: 600;
}
</style>
