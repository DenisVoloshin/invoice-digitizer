package com.digitizer.invoice.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Concrete implementation of {@link DataProvider} using FS as a data source
 */
public class FSInvoiceArchiveProvider implements DataProvider {
    private final String filePath;
    private FileInputStream fileInputStream;

    public FSInvoiceArchiveProvider(String filePath) {
        this.filePath = filePath;
    }

    public void openStream() throws IOException {
        fileInputStream = new FileInputStream(filePath);
    }

    public void closeStream() throws IOException {
        fileInputStream.close();
    }

    @Override
    public InputStream getDataAsStream() {
        return fileInputStream;
    }
}
