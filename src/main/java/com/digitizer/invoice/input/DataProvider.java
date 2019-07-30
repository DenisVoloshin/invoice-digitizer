package com.digitizer.invoice.input;

import java.io.IOException;
import java.io.InputStream;

/**
 * Defines data resource provided as a stream
 */
public interface DataProvider {

    /**
     * Opens the stream to be available for reading from it
     * @throws IOException
     */
    void openStream() throws IOException;

    /**
     * Closes the stream once the read process is complete.
     * @throws IOException
     */
    void closeStream() throws IOException;

    /**
     * Expose the stream to a reader
     * @return the available stream
     */
    InputStream getDataAsStream();
}
