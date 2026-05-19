package com.lalmeida.cobitmaturityapi.organization.controller;

import com.lalmeida.cobitmaturityapi.cobit.controller.dto.OrganizationCreateDTO;
import com.lalmeida.cobitmaturityapi.cobit.domain.EvaluationProject;
import com.lalmeida.cobitmaturityapi.organization.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationService.OrganizationSessionResult> submitResponses(
            @RequestBody OrganizationCreateDTO dto) {

        var result = organizationService.createdAndStartEvaluation(dto);

        return ResponseEntity.ok(result);
    }
}
