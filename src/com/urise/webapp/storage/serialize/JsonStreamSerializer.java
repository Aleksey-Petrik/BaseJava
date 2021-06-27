package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerialize{
    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return JsonParser.read(reader, Resume.class);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            JsonParser.write(r, writer);
        }
    }
}
