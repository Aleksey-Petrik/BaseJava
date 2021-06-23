package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface StreamSerialize {

    Resume doRead(InputStream is) throws IOException;

    void doWrite(Resume r, OutputStream os) throws IOException;
}
