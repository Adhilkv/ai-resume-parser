package com.ctrlaltthink.ai_resume_parser.service;

import ai.djl.ModelException;
import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ResumeScoringEngine {

    private Predictor<String, float[]> predictor;
    private ZooModel<String, float[]> model;

    @PostConstruct
    public void init() {
        try {
            Criteria<String, float[]> criteria = Criteria.builder()
                    .setTypes(String.class, float[].class)
                    .optEngine("PyTorch")
                    //.optModelUrls("djl://ai.djl.huggingface/sentence-transformers/all-MiniLM-L6-v2")
                    .optModelUrls("djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
                    .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
                    .build();

            model = ModelZoo.loadModel(criteria);
            predictor = model.newPredictor();

            System.out.println("✅ ResumeScoringEngine initialized successfully.");
        } catch (IOException | ModelException e) {
            System.err.println("❌ Failed to initialize ResumeScoringEngine");
            e.printStackTrace(System.err);
            throw new RuntimeException("ResumeScoringEngine init failed", e); // rethrow to see root cause in Spring logs
        }
    }


    public double calculateScore(String resume, String jobDescription) throws TranslateException {
        if (predictor == null) {
            throw new IllegalStateException("Predictor not initialized. Check model loading logs.");
        }
        float[] resumeEmb = predictor.predict(resume);
        float[] jdEmb = predictor.predict(jobDescription);

        double value = cosineSimilarity(resumeEmb, jdEmb) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(value)); // Convert double to String to avoid precision issues
        bd = bd.setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places, half up
        return bd.doubleValue();
    }

    private double cosineSimilarity(float[] vec1, float[] vec2) {
        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dot += vec1[i] * vec2[i];
            norm1 += vec1[i] * vec1[i];
            norm2 += vec2[i] * vec2[i];
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
