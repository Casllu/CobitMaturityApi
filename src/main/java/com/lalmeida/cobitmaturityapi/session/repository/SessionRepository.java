package com.lalmeida.cobitmaturityapi.session.repository;

import com.lalmeida.cobitmaturityapi.session.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    List<Session> findByOrganizationIdAndStatus(UUID organizationId, String status);

}
