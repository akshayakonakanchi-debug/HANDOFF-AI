package com.handoffai.repository;

import com.handoffai.model.NextAction;
import com.handoffai.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NextActionRepository extends JpaRepository<NextAction, Long> {
    List<NextAction> findByProjectOrderByPriorityAsc(Project project);
}
