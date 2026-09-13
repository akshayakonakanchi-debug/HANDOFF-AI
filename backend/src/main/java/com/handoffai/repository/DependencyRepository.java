package com.handoffai.repository;

import com.handoffai.model.Dependency;
import com.handoffai.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {
    List<Dependency> findByProject(Project project);
}
