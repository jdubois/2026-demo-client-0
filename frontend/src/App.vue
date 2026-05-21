<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="brand-mark">
        <span class="brand-icon">
          <i class="bi bi-kanban"></i>
        </span>
        <div>
          <span class="brand-eyebrow">Enterprise</span>
          <strong>TicketOps</strong>
        </div>
      </div>

      <nav class="nav flex-column sidebar-nav">
        <a class="nav-link active" href="#">
          <i class="bi bi-grid-1x2"></i>
          Dashboard
        </a>
        <a class="nav-link" href="#">
          <i class="bi bi-diagram-3"></i>
          Repositories
        </a>
        <a class="nav-link" href="#">
          <i class="bi bi-bar-chart-line"></i>
          Reporting
        </a>
        <a class="nav-link" href="#">
          <i class="bi bi-shield-check"></i>
          Governance
        </a>
      </nav>

      <div class="sidebar-footer">
        <span class="status-dot"></span>
        <span>Production workspace</span>
      </div>
    </aside>

    <div class="workspace">
      <header class="topbar">
        <div>
          <p class="text-uppercase text-secondary fw-bold small mb-1">Ticket management suite</p>
          <h1 class="h3 fw-bold mb-0">Contribution portfolio</h1>
        </div>
        <div class="topbar-actions">
          <span class="badge rounded-pill text-bg-light border">
            <i class="bi bi-github me-1"></i>
            GitHub synchronized
          </span>
          <button class="btn btn-dark" type="button">
            <i class="bi bi-sliders me-2"></i>
            Controls
          </button>
        </div>
      </header>

      <main class="workspace-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
.app-shell {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  background: #eef2f7;
}

.sidebar {
  position: sticky;
  top: 0;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 1.5rem;
  color: #dbeafe;
  background: linear-gradient(180deg, #0f172a 0%, #111827 100%);
  box-shadow: 16px 0 40px rgba(15, 23, 42, 0.14);
}

.brand-mark {
  display: flex;
  align-items: center;
  gap: 0.875rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid rgba(148, 163, 184, 0.22);
}

.brand-icon {
  display: grid;
  width: 2.75rem;
  height: 2.75rem;
  place-items: center;
  border-radius: 0.875rem;
  color: #0f172a;
  background: #f8fafc;
  box-shadow: 0 16px 38px rgba(15, 23, 42, 0.28);
}

.brand-eyebrow {
  display: block;
  color: #93c5fd;
  font-size: 0.7rem;
  font-weight: 800;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.brand-mark strong {
  color: #f8fafc;
  font-size: 1.2rem;
}

.sidebar-nav {
  gap: 0.35rem;
  margin-top: 1.75rem;
}

.sidebar-nav .nav-link {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.85rem 1rem;
  border-radius: 0.85rem;
  color: #cbd5e1;
  font-weight: 700;
}

.sidebar-nav .nav-link:hover,
.sidebar-nav .nav-link.active {
  color: #ffffff;
  background: rgba(59, 130, 246, 0.22);
}

.sidebar-footer {
  display: flex;
  align-items: center;
  gap: 0.625rem;
  margin-top: auto;
  padding: 1rem;
  border: 1px solid rgba(148, 163, 184, 0.2);
  border-radius: 1rem;
  color: #cbd5e1;
  background: rgba(15, 23, 42, 0.45);
  font-weight: 700;
}

.status-dot {
  width: 0.65rem;
  height: 0.65rem;
  border-radius: 999px;
  background: #22c55e;
  box-shadow: 0 0 0 0.35rem rgba(34, 197, 94, 0.14);
}

.workspace {
  min-width: 0;
}

.topbar {
  position: sticky;
  top: 0;
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  padding: 1.25rem 2rem;
  border-bottom: 1px solid #dbe4ef;
  background: rgba(248, 250, 252, 0.92);
  backdrop-filter: blur(18px);
}

.topbar-actions {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.workspace-content {
  padding: 2rem;
}

@media (max-width: 991.98px) {
  .app-shell {
    grid-template-columns: 1fr;
  }

  .sidebar {
    position: static;
    height: auto;
  }

  .sidebar-nav {
    flex-direction: row !important;
    overflow-x: auto;
  }

  .sidebar-footer {
    display: none;
  }

  .topbar {
    align-items: flex-start;
    flex-direction: column;
  }
}

@media (max-width: 575.98px) {
  .topbar,
  .workspace-content {
    padding: 1rem;
  }

  .topbar-actions {
    align-items: stretch;
    flex-direction: column;
    width: 100%;
  }
}
</style>
