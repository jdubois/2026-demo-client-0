package com.example.ticketmanager.controller;

import com.example.ticketmanager.domain.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TicketRequest(
    @NotBlank @Size(max = 240) String title,
    @NotBlank @Size(max = 120) String repository,
    @NotBlank @Size(max = 500) @Pattern(regexp = "https://github\\.com/.+/.+/issues/\\d+") String link,
    @NotNull TicketStatus status,
    @NotNull Long assigneeId
) {
}
