package com.handoffai.controller;

import com.handoffai.dto.AnswerDTO;
import com.handoffai.dto.QuestionDTO;
import com.handoffai.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<AnswerDTO> answerQuestion(@PathVariable Long projectId, @RequestBody QuestionDTO questionDTO) {
        try {
            String answer = questionService.answerQuestion(projectId, questionDTO.getQuestion());
            AnswerDTO response = new AnswerDTO();
            response.setAnswer(answer);
            response.setContext("Analysis based on project evidence");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
