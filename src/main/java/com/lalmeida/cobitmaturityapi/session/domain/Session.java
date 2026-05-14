package com.lalmeida.cobitmaturityapi.session.domain;

import com.lalmeida.cobitmaturityapi.organization.domain.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sessions", schema = "agent_memory")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "user_id")
    private UUID userId;

    private String title;

    @Column(name = "session_type", nullable = false)
    private String sessionType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> context;

    @Column(nullable = false)
    private String status;

    @Column(name = "started_at", updatable = false)
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @PrePersist
    public void prePersist() {
        startedAt = OffsetDateTime.now();
        status = "active";
        sessionType = sessionType != null ? sessionType : "cobit_evaluation";
    }
}
