package com.solevl.tunel.extractor.services.soundcloud;

import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.SuggestionExtractor;
import com.solevl.tunel.extractor.exceptions.ExtractionException;

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

/**
 * Test for {@link SuggestionExtractor}
 */
public class SoundcloudSuggestionExtractorTest {
    private static SuggestionExtractor suggestionExtractor;

    @BeforeClass
    public static void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        suggestionExtractor = ServiceList.SoundCloud.getSuggestionExtractor();
    }

    @Test
    public void testIfSuggestions() throws IOException, ExtractionException {
        assertFalse(suggestionExtractor.suggestionList("lil uzi vert", "de").isEmpty());
    }
}
