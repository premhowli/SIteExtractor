package com.solevl.tunel.extractor.playlist;

import com.solevl.tunel.extractor.InfoItemExtractor;
import com.solevl.tunel.extractor.exceptions.ParsingException;

public interface PlaylistInfoItemExtractor extends InfoItemExtractor {

    /**
     * Get the uploader name
     * @return the uploader name
     * @throws ParsingException
     */
    String getUploaderName() throws ParsingException;

    /**
     * Get the number of streams
     * @return the number of streams
     * @throws ParsingException
     */
    long getStreamCount() throws ParsingException;
}
