package com.handoffai.repository;

import com.handoffai.model.Evidence;
import com.handoffai.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    List<Evidence> findByProject(Project project);
}
