package com.github.annarybina.creditapplicationservice.controller;

import com.github.annarybina.creditapplicationservice.dto.CreditApplicationRequest;
import com.github.annarybina.creditapplicationservice.entity.CreditApplication;
import com.github.annarybina.creditapplicationservice.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping
    public Map<String, Long> submitApplication(@RequestBody CreditApplicationRequest request) {
        CreditApplication application = creditService.submitApplication(request);
        return Map.of("applicationId", application.getId());
    }

    @GetMapping("/{id}/status")
    public Map<String, String> getApplicationStatus(@PathVariable Long id) {
        String status = creditService.getApplicationStatus(id);
        return Map.of("status", status);
    }
}
