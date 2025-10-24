package com.ctrlaltthink.ai_resume_parser.controller;


import com.ctrlaltthink.ai_resume_parser.service.BackgroundProcessingService;
import com.ctrlaltthink.ai_resume_parser.service.FileParserService;
import com.ctrlaltthink.ai_resume_parser.service.ResumeScoringEngine;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private FileParserService fileParserService;
    private ResumeScoringEngine resumeScoringEngine;
    private final BackgroundProcessingService backgroundProcessingService;

    @Autowired
    public FileUploadController(FileParserService fileParserService,ResumeScoringEngine resumeScoringEngine, BackgroundProcessingService backgroundProcessingService) {
        this.fileParserService= fileParserService;
        this.resumeScoringEngine= resumeScoringEngine;
        this.backgroundProcessingService = backgroundProcessingService;
    }

    @PostMapping("/parallel-parse")
    public ResponseEntity<String> parseFileAsync(@RequestParam("file") MultipartFile file,
                                                 @RequestParam(value = "jobDescription", required = false) String jobDescription) {
        String taskId = backgroundProcessingService.submit(file, jobDescription);
        return ResponseEntity.accepted().body("taskId:" + taskId);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getStatus(@PathVariable("id") String id) {
        System.out.println("getStatus: Job Description Received: " + id);
        BackgroundProcessingService.TaskResult result = backgroundProcessingService.getStatus(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/parse")
    public ResponseEntity<String> parseFile(@RequestParam("file") MultipartFile file, @RequestParam("jobDescription") String jobDescription) {
        try {
            String content = fileParserService.extractMetadata(file.getInputStream());
            double score = resumeScoringEngine.calculateScore(content,jobDescription.trim());
            return ResponseEntity.ok("Total Score For Resume is " + score);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error parsing file: " + e.getMessage());
        }
    }

    //Testing: Simplified endpoint for raw byte stream upload
    @PostMapping(value = "/parse2", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> parseFile(@RequestBody byte[] fileBytes) {
        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            String content = fileParserService.extractMetadata(inputStream);

            //Must do some Rule Based Check here

            //Insert from UI
            StringBuilder jD = new StringBuilder();
            jD.append("Proficient in Java, Angular, and microservices architecture.,");
            jD.append("Skilled in SQL and NoSQL databases, with expertise in Spring, Spring Boot and Elasticsearch.,");
            jD.append("Utilized microservices technology, including Angular 7, Java 11, Spring 5, SOAP, and REST.,");
            jD.append("Worked with Azure, AWS, and Docker for deployment and management. ");

            double score = resumeScoringEngine.calculateScore(content,jD.toString());
            return ResponseEntity.ok("Total Score For Resume is " + score);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error parsing file: " + e.getMessage());
        }
    }

}
