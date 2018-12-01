package com.example.brijj.pdfexample;

public class Model {
    private String filename,uri;

    public Model() {
    }

    public Model(String filename, String uri) {
        this.filename = filename;
        this.uri = uri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
