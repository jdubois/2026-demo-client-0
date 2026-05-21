package com.example.ticketmanager.config;

import com.example.ticketmanager.domain.Ticket;
import com.example.ticketmanager.domain.TicketStatus;
import com.example.ticketmanager.domain.User;
import com.example.ticketmanager.repository.TicketRepository;
import com.example.ticketmanager.repository.UserRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataInitializer {

    @Bean
    CommandLineRunner initializeTickets(TicketRepository ticketRepository, UserRepository userRepository) {
        return args -> {
            List<User> users = ensureUsers(userRepository);

            if (ticketRepository.count() > 0) {
                assignUnassignedTickets(ticketRepository, users.getFirst());
                return;
            }

            ticketRepository.saveAll(List.of(
                new Ticket(
                    "Add xml validation for the stored characters",
                    "Antafes/Vampire-Editor",
                    "https://github.com/Antafes/Vampire-Editor/issues/100",
                    TicketStatus.NEW,
                    users.get(0)
                ),
                new Ticket(
                    "[Test] Add tests for DependencyNode",
                    "yasmramos/Veld",
                    "https://github.com/yasmramos/Veld/issues/100",
                    TicketStatus.NEW,
                    users.get(1)
                ),
                new Ticket(
                    "Add DSA Question For Rust",
                    "xthxr/OpenSauce",
                    "https://github.com/xthxr/OpenSauce/issues/100",
                    TicketStatus.NEW,
                    users.get(2)
                ),
                new Ticket(
                    "Create DappNode package for hildr",
                    "optimism-java/hildr",
                    "https://github.com/optimism-java/hildr/issues/100",
                    TicketStatus.NEW,
                    users.get(0)
                ),
                new Ticket(
                    "Move log (PhasedTestListener.java:317) - [Phased Testing] Reducing Report for to debug",
                    "adobe/phased-testing",
                    "https://github.com/adobe/phased-testing/issues/100",
                    TicketStatus.NEW,
                    users.get(1)
                ),
                new Ticket(
                    "Refactor/optimize code related to calculating the DefaultHashAlgorithms",
                    "DataONEorg/hashstore-java",
                    "https://github.com/DataONEorg/hashstore-java/issues/100",
                    TicketStatus.NEW,
                    users.get(2)
                ),
                new Ticket(
                    "Split calls should be replaced with Regular Expressions",
                    "symt/BazaarNotifier",
                    "https://github.com/symt/BazaarNotifier/issues/100",
                    TicketStatus.NEW,
                    users.get(0)
                ),
                new Ticket(
                    "요청에 대한 응답을 세분화 한다.",
                    "mymateM/AcountApp",
                    "https://github.com/mymateM/AcountApp/issues/100",
                    TicketStatus.NEW,
                    users.get(1)
                ),
                new Ticket(
                    "Add sensitive data secrets types for products",
                    "akto-api-security/akto",
                    "https://github.com/akto-api-security/akto/issues/100",
                    TicketStatus.NEW,
                    users.get(2)
                ),
                new Ticket(
                    "Add support for various new fields in the Sleep data",
                    "RADAR-base/RADAR-REST-Connector",
                    "https://github.com/RADAR-base/RADAR-REST-Connector/issues/100",
                    TicketStatus.NEW,
                    users.get(0)
                )
            ));
        };
    }

    private static List<User> ensureUsers(UserRepository userRepository) {
        return List.of("julien", "alice", "bob")
            .stream()
            .map(username -> userRepository.findByUsername(username).orElseGet(() -> userRepository.save(new User(username))))
            .toList();
    }

    private static void assignUnassignedTickets(TicketRepository ticketRepository, User assignee) {
        List<Ticket> unassignedTickets = ticketRepository.findByAssigneeIsNull();
        unassignedTickets.forEach(ticket -> ticket.setAssignee(assignee));
        ticketRepository.saveAll(unassignedTickets);
    }
}
