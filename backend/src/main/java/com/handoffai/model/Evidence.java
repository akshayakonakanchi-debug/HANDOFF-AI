package com.handoffai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvidenceType type;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String source;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum EvidenceType {
        NOTE, FILE, SCHEDULE, CHECKLIST, MEETING_NOTES, TASK_LIST, BUDGET, OTHER
    }
}
