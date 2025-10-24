package com.ctrlaltthink.ai_resume_parser.service;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FileParserService {

    private final Tika tika = new Tika();

    // Extract plain text
    public String extractText(InputStream inputStream) throws Exception {
        return tika.parseToString(inputStream);
    }

    // Extract metadata + content
    public String extractMetadata(InputStream inputStream) throws Exception {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        parser.parse(inputStream, handler, metadata);

        StringBuilder result = new StringBuilder();
        result.append("Content: \n").append(handler.toString()).append("\n\n");
        result.append("Metadata: \n");
        for (String name : metadata.names()) {
            result.append(name).append(" : ").append(metadata.get(name)).append("\n");
        }
        return result.toString();
    }

}
