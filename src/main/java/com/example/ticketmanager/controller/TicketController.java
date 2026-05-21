package com.example.ticketmanager.controller;

import com.example.ticketmanager.domain.Ticket;
import com.example.ticketmanager.domain.User;
import com.example.ticketmanager.repository.TicketRepository;
import com.example.ticketmanager.repository.UserRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/tickets")
class TicketController {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    TicketController(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    List<Ticket> findAll() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("/{id}")
    Ticket findOne(@PathVariable Long id) {
        return getTicket(id);
    }

    @PostMapping
    ResponseEntity<Ticket> create(@Valid @RequestBody TicketRequest ticketRequest) {
        User assignee = getUser(ticketRequest.assigneeId());
        Ticket ticket = new Ticket(
            ticketRequest.title(),
            ticketRequest.repository(),
            ticketRequest.link(),
            ticketRequest.status(),
            assignee
        );
        Ticket savedTicket = ticketRepository.save(ticket);
        return ResponseEntity.created(URI.create("/api/tickets/" + savedTicket.getId())).body(savedTicket);
    }

    @PutMapping("/{id}")
    Ticket update(@PathVariable Long id, @Valid @RequestBody TicketRequest ticketRequest) {
        Ticket ticket = getTicket(id);
        ticket.setTitle(ticketRequest.title());
        ticket.setRepository(ticketRequest.repository());
        ticket.setLink(ticketRequest.link());
        ticket.setStatus(ticketRequest.status());
        ticket.setAssignee(getUser(ticketRequest.assigneeId()));
        return ticketRepository.save(ticket);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Ticket not found");
        }
        ticketRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Ticket getTicket(Long id) {
        return ticketRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ticket not found"));
    }

    private User getUser(Long id) {
        return userRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Assignee not found"));
    }
}
