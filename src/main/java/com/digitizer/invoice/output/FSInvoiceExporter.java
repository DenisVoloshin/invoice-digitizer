package com.digitizer.invoice.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Concrete implementation of {@link DataExporter} using FS as a data output
 */
public class FSInvoiceExporter implements DataExporter<String> {

    private static final String ILLEGAL = "ILLEGAL";

    private String exportedDataLocation;
    private StringBuilder stringBuilder;

    public FSInvoiceExporter(String exportedDataLocation) {
        this.exportedDataLocation = exportedDataLocation;
    }

    public FSInvoiceExporter() {
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void open() throws IOException {
        this.stringBuilder = new StringBuilder();
        if (exportedDataLocation != null) {
            if (!Files.exists(Paths.get(exportedDataLocation)) || !Files.isWritable(Paths.get(exportedDataLocation))) {
                throw new IOException("exported file is not writable or does not exist");
            }
        }
    }

    @Override
    public void export() throws IOException {
        if (exportedDataLocation != null) {
            new FileOutputStream(exportedDataLocation).write(this.stringBuilder == null ? new byte[0] :
                    this.stringBuilder.toString().getBytes());
        }
    }

    public String getExportedDataAsString() {
        return this.stringBuilder == null ? "" : this.stringBuilder.toString();
    }

    @Override
    public void addElement(String element) {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        }
        if (element.contains("?")) {
            this.stringBuilder.append(element).append(" ").append(ILLEGAL).append("\n");
        } else {
            this.stringBuilder.append(element).append("\n");
        }
    }
}
