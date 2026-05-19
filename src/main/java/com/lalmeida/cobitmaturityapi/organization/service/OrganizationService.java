package com.lalmeida.cobitmaturityapi.organization.service;

import com.lalmeida.cobitmaturityapi.cobit.controller.dto.OrganizationCreateDTO;
import com.lalmeida.cobitmaturityapi.organization.domain.Organization;
import com.lalmeida.cobitmaturityapi.organization.repository.OrganizationRepository;
import com.lalmeida.cobitmaturityapi.session.domain.Session;
import com.lalmeida.cobitmaturityapi.session.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SessionRepository sessionRepository;

    public OrganizationService(OrganizationRepository organizationRepository, SessionRepository sessionRepository) {
        this.organizationRepository = organizationRepository;
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    public OrganizationSessionResult createdAndStartEvaluation (OrganizationCreateDTO dto) {

        Organization org = new Organization();
        org.setName(dto.getName());
        org.setSlug(generateSlug(dto.getName()));
        org.setSector(dto.getSector());
        org.setSize(dto.getSize());
        org = organizationRepository.save(org);

        Session session = new Session();
        session.setOrganization(org);
        session.setTitle("Avaliação COBIT 2019 — " + dto.getName());
        session.setSessionType("cobit_evaluation");
        session = sessionRepository.save(session);

        return new OrganizationSessionResult(org, session);
    }

    private String generateSlug(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized)
                .replaceAll("")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }

    public record OrganizationSessionResult(Organization organization, Session session) {}
}

