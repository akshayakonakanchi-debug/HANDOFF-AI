package com.handoffai.service;

import com.handoffai.model.Task;
import com.handoffai.model.Dependency;
import com.handoffai.model.Risk;
import com.handoffai.model.NextAction;
import com.handoffai.model.Evidence;
import com.handoffai.model.Project;
import com.handoffai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final TaskRepository taskRepository;
    private final DependencyRepository dependencyRepository;
    private final RiskRepository riskRepository;
    private final NextActionRepository nextActionRepository;
    private final EvidenceRepository evidenceRepository;

    public void analyzeProject(Project project) {
        String evidence = project.getEvidenceText();
        if (evidence == null || evidence.trim().isEmpty()) {
            return;
        }

        // Clear existing data
        clearProjectAnalysis(project);

        // Extract tasks from evidence
        extractTasksFromEvidence(project, evidence);

        // Analyze dependencies
        analyzeDependencies(project, evidence);

        // Identify risks
        identifyRisks(project, evidence);

        // Generate next actions
        generateNextActions(project);
    }

    private void extractTasksFromEvidence(Project project, String evidence) {
        String lowerEvidence = evidence.toLowerCase();
        Map<String, Task.TaskStatus> statusMap = new LinkedHashMap<>();

        // Extract completed tasks
        if (lowerEvidence.contains("completed:")) {
            String section = extractSection(evidence, "completed:");
            parseTasksFromSection(section, Task.TaskStatus.COMPLETED, statusMap);
        }

        // Extract in progress tasks
        if (lowerEvidence.contains("in progress:") || lowerEvidence.contains("in_progress:")) {
            String section = extractSection(evidence, "in progress:");
            if (section.isEmpty()) section = extractSection(evidence, "in_progress:");
            parseTasksFromSection(section, Task.TaskStatus.IN_PROGRESS, statusMap);
        }

        // Extract not started tasks
        if (lowerEvidence.contains("not started:") || lowerEvidence.contains("not_started:")) {
            String section = extractSection(evidence, "not started:");
            if (section.isEmpty()) section = extractSection(evidence, "not_started:");
            parseTasksFromSection(section, Task.TaskStatus.NOT_STARTED, statusMap);
        }

        // Extract blocked tasks
        if (lowerEvidence.contains("blocked:")) {
            String section = extractSection(evidence, "blocked:");
            parseTasksFromSection(section, Task.TaskStatus.BLOCKED, statusMap);
        }

        // Save tasks
        for (Map.Entry<String, Task.TaskStatus> entry : statusMap.entrySet()) {
            Task task = new Task();
            task.setProject(project);
            task.setTitle(entry.getKey());
            task.setStatus(entry.getValue());
            taskRepository.save(task);
        }
    }

    private String extractSection(String text, String sectionName) {
        int startIndex = text.toLowerCase().indexOf(sectionName.toLowerCase());
        if (startIndex == -1) return "";

        startIndex += sectionName.length();
        int endIndex = text.toLowerCase().indexOf(":", startIndex);
        if (endIndex == -1) endIndex = text.length();

        return text.substring(startIndex, endIndex).trim();
    }

    private void parseTasksFromSection(String section, Task.TaskStatus status, Map<String, Task.TaskStatus> statusMap) {
        String[] lines = section.split("[\n-]");
        for (String line : lines) {
            String taskName = line.trim().replaceAll("^[-*]\\s*", "").trim();
            if (!taskName.isEmpty() && taskName.length() < 200) {
                statusMap.put(taskName, status);
            }
        }
    }

    private void analyzeDependencies(Project project, String evidence) {
        List<Task> tasks = taskRepository.findByProject(project);
        String lowerEvidence = evidence.toLowerCase();

        if (lowerEvidence.contains("dependencies:") || lowerEvidence.contains("→")) {
            // Parse simple dependency chains
            String[] lines = evidence.split("\n");
            for (int i = 0; i < lines.length - 1; i++) {
                String line = lines[i];
                String nextLine = lines[i + 1];

                if (line.contains("→")) {
                    String[] parts = line.split("→");
                    if (parts.length >= 2) {
                        Task fromTask = findTaskByName(tasks, parts[0].trim());
                        Task toTask = findTaskByName(tasks, parts[1].trim());

                        if (fromTask != null && toTask != null) {
                            Dependency dependency = new Dependency();
                            dependency.setProject(project);
                            dependency.setFromTask(fromTask);
                            dependency.setToTask(toTask);
                            dependencyRepository.save(dependency);
                        }
                    }
                }
            }
        }
    }

    private void identifyRisks(Project project, String evidence) {
        String[] riskKeywords = {"blocked", "at risk", "problem", "issue", "concern", "pending", "not confirmed"};
        List<Task> tasks = taskRepository.findByProject(project);

        // Check for blocked tasks
        for (Task task : tasks) {
            if (task.getStatus() == Task.TaskStatus.BLOCKED) {
                Risk risk = new Risk();
                risk.setProject(project);
                risk.setTitle("Blocked: " + task.getTitle());
                risk.setDescription("Task is currently blocked and cannot proceed.");
                risk.setSeverity(Risk.RiskSeverity.HIGH);
                risk.setEvidence("Task status: BLOCKED");
                riskRepository.save(risk);
            }
        }

        // Check evidence text for risk indicators
        String lowerEvidence = evidence.toLowerCase();
        for (String keyword : riskKeywords) {
            if (lowerEvidence.contains(keyword)) {
                int index = lowerEvidence.indexOf(keyword);
                String context = extractContext(evidence, index, 100);
                if (!context.isEmpty()) {
                    Risk risk = new Risk();
                    risk.setProject(project);
                    risk.setTitle("Risk: " + context.substring(0, Math.min(50, context.length())));
                    risk.setDescription(context);
                    risk.setSeverity(Risk.RiskSeverity.MEDIUM);
                    risk.setEvidence("Evidence text");
                    riskRepository.save(risk);
                }
            }
        }
    }

    private void generateNextActions(Project project) {
        List<Task> tasks = taskRepository.findByProject(project);
        List<Risk> risks = riskRepository.findByProject(project);
        int priority = 1;

        // Priority 1: Unblock blocked tasks
        for (Task task : tasks) {
            if (task.getStatus() == Task.TaskStatus.BLOCKED) {
                NextAction action = new NextAction();
                action.setProject(project);
                action.setPriority(priority++);
                action.setTitle("Resolve blocker for: " + task.getTitle());
                action.setDescription("This task is blocked and needs immediate attention.");
                action.setReason("Blocked tasks prevent project progress.");
                action.setRelatedTask(task.getTitle());
                nextActionRepository.save(action);
            }
        }

        // Priority 2: Start not started tasks that have no dependencies
        for (Task task : tasks) {
            if (task.getStatus() == Task.TaskStatus.NOT_STARTED) {
                NextAction action = new NextAction();
                action.setProject(project);
                action.setPriority(priority++);
                action.setTitle("Start: " + task.getTitle());
                action.setDescription("Begin work on this task.");
                action.setReason("This task is ready to start.");
                action.setRelatedTask(task.getTitle());
                nextActionRepository.save(action);
            }
        }

        // Priority 3: Complete in progress tasks
        for (Task task : tasks) {
            if (task.getStatus() == Task.TaskStatus.IN_PROGRESS) {
                NextAction action = new NextAction();
                action.setProject(project);
                action.setPriority(priority++);
                action.setTitle("Complete: " + task.getTitle());
                action.setDescription("Finish work on this task.");
                action.setReason("This task is already in progress.");
                action.setRelatedTask(task.getTitle());
                nextActionRepository.save(action);
            }
        }
    }

    private Task findTaskByName(List<Task> tasks, String name) {
        String normalizedName = name.toLowerCase().trim();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(normalizedName) || 
                normalizedName.contains(task.getTitle().toLowerCase())) {
                return task;
            }
        }
        return null;
    }

    private String extractContext(String text, int index, int length) {
        int start = Math.max(0, index - length / 2);
        int end = Math.min(text.length(), index + length / 2);
        return text.substring(start, end).trim();
    }

    private void clearProjectAnalysis(Project project) {
        taskRepository.findByProject(project).forEach(taskRepository::delete);
        dependencyRepository.findByProject(project).forEach(dependencyRepository::delete);
        riskRepository.findByProject(project).forEach(riskRepository::delete);
        nextActionRepository.findByProjectOrderByPriorityAsc(project).forEach(nextActionRepository::delete);
    }
}
