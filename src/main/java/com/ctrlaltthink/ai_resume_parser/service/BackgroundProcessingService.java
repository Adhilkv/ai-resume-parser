package com.ctrlaltthink.ai_resume_parser.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class BackgroundProcessingService {

    private final FileParserService fileParserService;
    private final ResumeScoringEngine resumeScoringEngine;
    private final Executor executor;
    private final ConcurrentMap<String, TaskResult> tasks = new ConcurrentHashMap<>();

    public BackgroundProcessingService(FileParserService fileParserService,
                                       ResumeScoringEngine resumeScoringEngine,
                                       @Qualifier("fileProcessingExecutor") Executor executor) {
        this.fileParserService = fileParserService;
        this.resumeScoringEngine = resumeScoringEngine;
        this.executor = executor;
    }

    public String submit(MultipartFile file, String jobDescription) {
        String id = UUID.randomUUID().toString();
        tasks.put(id, TaskResult.pending());
        CompletableFuture.runAsync(() -> process(id, file, jobDescription), executor);
        return id;
    }

    private void process(String id, MultipartFile file, String jobDescription) {
        tasks.put(id, TaskResult.running());
        try (InputStream is = file.getInputStream()) {
            String content = fileParserService.extractMetadata(is);
            double score = resumeScoringEngine.calculateScore(content, jobDescription == null ? "" : jobDescription.trim());
            tasks.put(id, TaskResult.completed(score));
        } catch (Exception e) {
            tasks.put(id, TaskResult.failed(e.getMessage()));
        }
    }

    public TaskResult getStatus(String id) {
        return tasks.get(id);
    }

    public static class TaskResult {
        public enum State { PENDING, RUNNING, COMPLETED, FAILED }

        private final State state;
        private final Double score;
        private final String error;

        private TaskResult(State state, Double score, String error) {
            this.state = state;
            this.score = score;
            this.error = error;
        }

        public static TaskResult pending() { return new TaskResult(State.PENDING, null, null); }
        public static TaskResult running() { return new TaskResult(State.RUNNING, null, null); }
        public static TaskResult completed(double score) { return new TaskResult(State.COMPLETED, score, null); }
        public static TaskResult failed(String error) { return new TaskResult(State.FAILED, null, error); }

        public State getState() { return state; }
        public Double getScore() { return score; }
        public String getError() { return error; }
    }
}