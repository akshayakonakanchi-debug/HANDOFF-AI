package com.handoffai.service;

import com.handoffai.dto.HandoffDTO;
import com.handoffai.model.*;
import com.handoffai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HandoffService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final DependencyRepository dependencyRepository;
    private final RiskRepository riskRepository;
    private final NextActionRepository nextActionRepository;
    private final EvidenceRepository evidenceRepository;
    private final AnalysisService analysisService;

    public HandoffDTO createHandoff(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        // Analyze project evidence
        analysisService.analyzeProject(project);

        // Build handoff DTO
        HandoffDTO handoff = new HandoffDTO();
        handoff.setProjectId(project.getId());
        handoff.setProjectName(project.getName());

        List<Task> tasks = taskRepository.findByProject(project);

        handoff.setCompleted(tasks.stream()
                .filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED)
                .map(this::taskToMap)
                .collect(Collectors.toList()));

        handoff.setInProgress(tasks.stream()
                .filter(t -> t.getStatus() == Task.TaskStatus.IN_PROGRESS)
                .map(this::taskToMap)
                .collect(Collectors.toList()));

        handoff.setNotStarted(tasks.stream()
                .filter(t -> t.getStatus() == Task.TaskStatus.NOT_STARTED)
                .map(this::taskToMap)
                .collect(Collectors.toList()));

        handoff.setBlocked(tasks.stream()
                .filter(t -> t.getStatus() == Task.TaskStatus.BLOCKED)
                .map(this::taskToMap)
                .collect(Collectors.toList()));

        handoff.setRisks(riskRepository.findByProject(project).stream()
                .map(this::riskToMap)
                .collect(Collectors.toList()));

        handoff.setDependencies(dependencyRepository.findByProject(project).stream()
                .map(this::dependencyToMap)
                .collect(Collectors.toList()));

        handoff.setNextActions(nextActionRepository.findByProjectOrderByPriorityAsc(project).stream()
                .map(this::actionToMap)
                .collect(Collectors.toList()));

        handoff.setEvidence(evidenceRepository.findByProject(project).stream()
                .map(this::evidenceToMap)
                .collect(Collectors.toList()));

        return handoff;
    }

    private Map<String, Object> taskToMap(Task task) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", task.getId());
        map.put("title", task.getTitle());
        map.put("status", task.getStatus());
        map.put("description", task.getDescription());
        map.put("dueDate", task.getDueDate());
        return map;
    }

    private Map<String, Object> riskToMap(Risk risk) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", risk.getId());
        map.put("title", risk.getTitle());
        map.put("description", risk.getDescription());
        map.put("severity", risk.getSeverity());
        map.put("evidence", risk.getEvidence());
        return map;
    }

    private Map<String, Object> dependencyToMap(Dependency dep) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dep.getId());
        map.put("from", dep.getFromTask().getTitle());
        map.put("to", dep.getToTask().getTitle());
        map.put("description", dep.getDescription());
        return map;
    }

    private Map<String, Object> actionToMap(NextAction action) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", action.getId());
        map.put("priority", action.getPriority());
        map.put("title", action.getTitle());
        map.put("description", action.getDescription());
        map.put("reason", action.getReason());
        map.put("relatedTask", action.getRelatedTask());
        map.put("evidence", action.getEvidence());
        return map;
    }

    private Map<String, Object> evidenceToMap(Evidence evidence) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", evidence.getId());
        map.put("type", evidence.getType());
        map.put("source", evidence.getSource());
        map.put("content", evidence.getContent() != null ? evidence.getContent().substring(0, Math.min(100, evidence.getContent().length())) : "");
        return map;
    }
}
