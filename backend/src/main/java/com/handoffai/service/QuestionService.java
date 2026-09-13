package com.handoffai.service;

import com.handoffai.model.*;
import com.handoffai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final DependencyRepository dependencyRepository;
    private final RiskRepository riskRepository;
    private final NextActionRepository nextActionRepository;

    public String answerQuestion(Long projectId, String question) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        String lowerQuestion = question.toLowerCase();
        List<Task> tasks = taskRepository.findByProject(project);

        // What should I do first?
        if (lowerQuestion.contains("do first") || lowerQuestion.contains("start with") || lowerQuestion.contains("next")) {
            List<NextAction> actions = nextActionRepository.findByProjectOrderByPriorityAsc(project);
            if (!actions.isEmpty()) {
                NextAction first = actions.get(0);
                return "First, " + first.getTitle() + ". " + first.getDescription() + " Reason: " + first.getReason();
            }
            return "No priority actions identified yet.";
        }

        // What is blocked?
        if (lowerQuestion.contains("blocked")) {
            List<Task> blockedTasks = tasks.stream()
                    .filter(t -> t.getStatus() == Task.TaskStatus.BLOCKED)
                    .collect(Collectors.toList());
            if (!blockedTasks.isEmpty()) {
                String blockedList = blockedTasks.stream()
                        .map(Task::getTitle)
                        .collect(Collectors.joining(", "));
                return "The following tasks are blocked: " + blockedList + ". These require immediate attention.";
            }
            return "No tasks are currently blocked.";
        }

        // What has been completed?
        if (lowerQuestion.contains("complete") || lowerQuestion.contains("done")) {
            List<Task> completedTasks = tasks.stream()
                    .filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED)
                    .collect(Collectors.toList());
            if (!completedTasks.isEmpty()) {
                String completedList = completedTasks.stream()
                        .map(Task::getTitle)
                        .collect(Collectors.joining(", "));
                return "Completed tasks: " + completedList;
            }
            return "No tasks have been completed yet.";
        }

        // What is in progress?
        if (lowerQuestion.contains("progress") || lowerQuestion.contains("working")) {
            List<Task> inProgressTasks = tasks.stream()
                    .filter(t -> t.getStatus() == Task.TaskStatus.IN_PROGRESS)
                    .collect(Collectors.toList());
            if (!inProgressTasks.isEmpty()) {
                String inProgressList = inProgressTasks.stream()
                        .map(Task::getTitle)
                        .collect(Collectors.joining(", "));
                return "Currently in progress: " + inProgressList;
            }
            return "No tasks are currently in progress.";
        }

        // What are the risks?
        if (lowerQuestion.contains("risk") || lowerQuestion.contains("problem")) {
            List<Risk> risks = riskRepository.findByProject(project);
            if (!risks.isEmpty()) {
                String riskList = risks.stream()
                        .map(r -> r.getTitle() + " (" + r.getSeverity() + ")")
                        .collect(Collectors.joining("; "));
                return "Identified risks: " + riskList;
            }
            return "No significant risks identified.";
        }

        // What depends on [task]?
        if (lowerQuestion.contains("depend")) {
            for (Task task : tasks) {
                if (lowerQuestion.contains(task.getTitle().toLowerCase())) {
                    List<Dependency> dependencies = dependencyRepository.findByProject(project).stream()
                            .filter(d -> d.getFromTask().getId().equals(task.getId()))
                            .collect(Collectors.toList());
                    if (!dependencies.isEmpty()) {
                        String dependentTasks = dependencies.stream()
                                .map(d -> d.getToTask().getTitle())
                                .collect(Collectors.joining(", "));
                        return "Tasks that depend on " + task.getTitle() + ": " + dependentTasks;
                    }
                    return "No tasks depend on " + task.getTitle();
                }
            }
        }

        // What should the new person know?
        if (lowerQuestion.contains("new person") || lowerQuestion.contains("handoff")) {
            List<Task> blockedTasks = tasks.stream()
                    .filter(t -> t.getStatus() == Task.TaskStatus.BLOCKED)
                    .collect(Collectors.toList());
            List<Risk> risks = riskRepository.findByProject(project);

            StringBuilder answer = new StringBuilder("Key handoff information:\n");
            answer.append("- Project: ").append(project.getName()).append("\n");
            answer.append("- Total tasks: ").append(tasks.size()).append("\n");
            answer.append("- Blocked items: ").append(blockedTasks.size()).append("\n");
            answer.append("- Active risks: ").append(risks.size()).append("\n");

            if (!blockedTasks.isEmpty()) {
                answer.append("\nCritical blockers:\n");
                blockedTasks.forEach(t -> answer.append("- ").append(t.getTitle()).append("\n"));
            }

            List<NextAction> actions = nextActionRepository.findByProjectOrderByPriorityAsc(project);
            if (!actions.isEmpty() && actions.size() <= 5) {
                answer.append("\nImmediate next steps:\n");
                actions.forEach(a -> answer.append("- ").append(a.getTitle()).append("\n"));
            }

            return answer.toString();
        }

        // Default: Return general status
        return "Project Status:\n" +
                "- Completed: " + tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.COMPLETED).count() + "\n" +
                "- In Progress: " + tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.IN_PROGRESS).count() + "\n" +
                "- Not Started: " + tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.NOT_STARTED).count() + "\n" +
                "- Blocked: " + tasks.stream().filter(t -> t.getStatus() == Task.TaskStatus.BLOCKED).count();
    }
}
