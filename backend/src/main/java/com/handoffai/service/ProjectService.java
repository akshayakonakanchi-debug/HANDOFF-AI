package com.handoffai.service;

import com.handoffai.dto.ProjectDTO;
import com.handoffai.model.Project;
import com.handoffai.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDTO> getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setTeamSize(projectDTO.getTeamSize());
        project.setEvidenceText(projectDTO.getEvidenceText());
        
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setTeamSize(projectDTO.getTeamSize());
            project.setEvidenceText(projectDTO.getEvidenceText());
            
            Project updatedProject = projectRepository.save(project);
            return convertToDTO(updatedProject);
        }
        return null;
    }

    public Project getProjectEntityById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setTeamSize(project.getTeamSize());
        dto.setEvidenceText(project.getEvidenceText());
        return dto;
    }
}
