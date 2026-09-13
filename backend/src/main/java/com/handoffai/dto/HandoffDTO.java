package com.handoffai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandoffDTO {
    private Long projectId;
    private String projectName;
    private List<Map<String, Object>> completed;
    private List<Map<String, Object>> inProgress;
    private List<Map<String, Object>> notStarted;
    private List<Map<String, Object>> blocked;
    private List<Map<String, Object>> risks;
    private List<Map<String, Object>> dependencies;
    private List<Map<String, Object>> nextActions;
    private List<Map<String, Object>> evidence;
}
