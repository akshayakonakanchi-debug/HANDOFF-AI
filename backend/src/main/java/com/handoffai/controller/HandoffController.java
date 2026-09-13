package com.handoffai.controller;

import com.handoffai.dto.HandoffDTO;
import com.handoffai.service.HandoffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/handoff")
@RequiredArgsConstructor
public class HandoffController {
    private final HandoffService handoffService;

    @PostMapping
    public ResponseEntity<HandoffDTO> createHandoff(@PathVariable Long projectId) {
        try {
            HandoffDTO handoff = handoffService.createHandoff(projectId);
            return ResponseEntity.ok(handoff);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
