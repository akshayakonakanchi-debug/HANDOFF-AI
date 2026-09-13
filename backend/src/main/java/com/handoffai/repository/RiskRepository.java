package com.handoffai.repository;

import com.handoffai.model.Risk;
import com.handoffai.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {
    List<Risk> findByProject(Project project);
}
