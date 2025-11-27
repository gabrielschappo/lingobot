package com.example.lingobot.dto;

import java.util.ArrayList;
import java.util.List;

public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(String text) {
        this.contents = new ArrayList<>();
        Content content = new Content();
        Part part = new Part();
        part.setText(text);
        content.getParts().add(part);
        this.contents.add(content);
    }

    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }

    public static class Content {
        private List<Part> parts = new ArrayList<>();
        public List<Part> getParts() { return parts; }
        public void setParts(List<Part> parts) { this.parts = parts; }
    }

    public static class Part {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}