const baseUrl = '/api/tickets'

async function request(url, options = {}) {
  const response = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  if (!response.ok) {
    const message = await response.text()
    throw new Error(message || `Request failed with status ${response.status}`)
  }

  if (response.status === 204) {
    return null
  }

  return response.json()
}

export function findTickets() {
  return request(baseUrl)
}

export function createTicket(ticket) {
  return request(baseUrl, {
    method: 'POST',
    body: JSON.stringify(ticket),
  })
}

export function updateTicket(ticket) {
  return request(`${baseUrl}/${ticket.id}`, {
    method: 'PUT',
    body: JSON.stringify(ticket),
  })
}

export function deleteTicket(id) {
  return request(`${baseUrl}/${id}`, {
    method: 'DELETE',
  })
}
