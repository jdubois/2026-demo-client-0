import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { createTicket, deleteTicket, findTickets, updateTicket } from '@/services/ticketApi'

export const statuses = [
  { value: 'NEW', label: 'Nouveau', className: 'text-bg-primary' },
  { value: 'SHORTLISTED', label: 'Présélectionné', className: 'text-bg-info' },
  { value: 'IN_PROGRESS', label: 'En cours', className: 'text-bg-warning' },
  { value: 'DONE', label: 'Terminé', className: 'text-bg-success' },
]

export const useTicketsStore = defineStore('tickets', () => {
  const tickets = ref([])
  const loading = ref(false)
  const error = ref('')

  const total = computed(() => tickets.value.length)
  const readyToStart = computed(
    () =>
      tickets.value.filter((ticket) => ticket.status === 'NEW' || ticket.status === 'SHORTLISTED')
        .length,
  )
  const completed = computed(
    () => tickets.value.filter((ticket) => ticket.status === 'DONE').length,
  )

  async function loadTickets() {
    loading.value = true
    error.value = ''

    try {
      tickets.value = await findTickets()
    } catch (requestError) {
      error.value = requestError.message
      throw requestError
    } finally {
      loading.value = false
    }
  }

  async function saveTicket(ticket) {
    error.value = ''
    const savedTicket = ticket.id ? await updateTicket(ticket) : await createTicket(ticket)
    const existingIndex = tickets.value.findIndex(
      (currentTicket) => currentTicket.id === savedTicket.id,
    )

    if (existingIndex === -1) {
      tickets.value.push(savedTicket)
    } else {
      tickets.value[existingIndex] = savedTicket
    }

    return savedTicket
  }

  async function removeTicket(id) {
    error.value = ''
    await deleteTicket(id)
    tickets.value = tickets.value.filter((ticket) => ticket.id !== id)
  }

  return {
    tickets,
    loading,
    error,
    total,
    readyToStart,
    completed,
    loadTickets,
    saveTicket,
    removeTicket,
  }
})
