package com.example.ticketmanager.config;

import com.example.ticketmanager.domain.Ticket;
import com.example.ticketmanager.domain.TicketStatus;
import com.example.ticketmanager.repository.TicketRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataInitializer {

    @Bean
    CommandLineRunner initializeTickets(TicketRepository ticketRepository) {
        return args -> {
            if (ticketRepository.count() > 0) {
                return;
            }

            ticketRepository.saveAll(List.of(
                new Ticket(
                    "Add xml validation for the stored characters",
                    "Antafes/Vampire-Editor",
                    "https://github.com/Antafes/Vampire-Editor/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "[Test] Add tests for DependencyNode",
                    "yasmramos/Veld",
                    "https://github.com/yasmramos/Veld/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Add DSA Question For Rust",
                    "xthxr/OpenSauce",
                    "https://github.com/xthxr/OpenSauce/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Create DappNode package for hildr",
                    "optimism-java/hildr",
                    "https://github.com/optimism-java/hildr/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Move log (PhasedTestListener.java:317) - [Phased Testing] Reducing Report for to debug",
                    "adobe/phased-testing",
                    "https://github.com/adobe/phased-testing/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Refactor/optimize code related to calculating the DefaultHashAlgorithms",
                    "DataONEorg/hashstore-java",
                    "https://github.com/DataONEorg/hashstore-java/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Split calls should be replaced with Regular Expressions",
                    "symt/BazaarNotifier",
                    "https://github.com/symt/BazaarNotifier/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "요청에 대한 응답을 세분화 한다.",
                    "mymateM/AcountApp",
                    "https://github.com/mymateM/AcountApp/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Add sensitive data secrets types for products",
                    "akto-api-security/akto",
                    "https://github.com/akto-api-security/akto/issues/100",
                    TicketStatus.NEW
                ),
                new Ticket(
                    "Add support for various new fields in the Sleep data",
                    "RADAR-base/RADAR-REST-Connector",
                    "https://github.com/RADAR-base/RADAR-REST-Connector/issues/100",
                    TicketStatus.NEW
                )
            ));
        };
    }
}
