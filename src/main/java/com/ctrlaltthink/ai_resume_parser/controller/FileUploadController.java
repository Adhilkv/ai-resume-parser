package com.ctrlaltthink.ai_resume_parser.controller;


import com.ctrlaltthink.ai_resume_parser.service.FileParserService;
import com.ctrlaltthink.ai_resume_parser.service.ResumeScoringEngine;
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

    @Autowired
    private FileParserService fileParserService;

    @Autowired
    private ResumeScoringEngine resumeScoringEngine;

    @PostMapping("/parse")
    public ResponseEntity<String> parseFile(@RequestParam("file") MultipartFile file) {
        try {
            String content = fileParserService.extractMetadata(file.getInputStream());
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error parsing file: " + e.getMessage());
        }
    }

    @PostMapping(
            value = "/parse2",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
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
