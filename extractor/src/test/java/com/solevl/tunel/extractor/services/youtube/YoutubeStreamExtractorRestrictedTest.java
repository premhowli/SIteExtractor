package com.solevl.tunel.extractor.services.youtube;

import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.stream.SubtitlesFormat;
import com.solevl.tunel.extractor.stream.VideoStream;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.schabi.newpipe.Downloader;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import com.solevl.tunel.extractor.stream.StreamExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static com.solevl.tunel.extractor.ExtractorAsserts.assertIsSecureUrl;
import static com.solevl.tunel.extractor.ServiceList.YouTube;

/**
 * Test for {@link YoutubeStreamLinkHandlerFactory}
 */
public class YoutubeStreamExtractorRestrictedTest {
    public static final String HTTPS = "https://";
    private static YoutubeStreamExtractor extractor;

    @BeforeClass
    public static void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = (YoutubeStreamExtractor) YouTube
                .getStreamExtractor("https://www.youtube.com/watch?v=i6JTvzrpBy0");
        extractor.fetchPage();
    }

    @Test
    public void testGetInvalidTimeStamp() throws ParsingException {
        assertTrue(extractor.getTimeStamp() + "", extractor.getTimeStamp() <= 0);
    }

    @Test
    public void testGetValidTimeStamp() throws IOException, ExtractionException {
        StreamExtractor extractor = YouTube.getStreamExtractor("https://youtu.be/FmG385_uUys?t=174");
        assertEquals(extractor.getTimeStamp() + "", "174");
    }

    @Test
    public void testGetAgeLimit() throws ParsingException {
        assertEquals(18, extractor.getAgeLimit());
    }

    @Test
    public void testGetName() throws ParsingException {
        assertNotNull("name is null", extractor.getName());
        assertFalse("name is empty", extractor.getName().isEmpty());
    }

    @Test
    public void testGetDescription() throws ParsingException {
        assertNotNull(extractor.getDescription());
        assertFalse(extractor.getDescription().isEmpty());
    }

    @Test
    public void testGetUploaderName() throws ParsingException {
        assertNotNull(extractor.getUploaderName());
        assertFalse(extractor.getUploaderName().isEmpty());
    }

    @Ignore // Currently there is no way get the length from restricted videos
    @Test
    public void testGetLength() throws ParsingException {
        assertTrue(extractor.getLength() > 0);
    }

    @Test
    public void testGetViews() throws ParsingException {
        assertTrue(extractor.getViewCount() > 0);
    }

    @Test
    public void testGetUploadDate() throws ParsingException {
        assertTrue(extractor.getUploadDate().length() > 0);
    }

    @Test
    public void testGetThumbnailUrl() throws ParsingException {
        assertIsSecureUrl(extractor.getThumbnailUrl());
    }

    @Test
    public void testGetUploaderAvatarUrl() throws ParsingException {
        assertIsSecureUrl(extractor.getUploaderAvatarUrl());
    }

    // FIXME: 25.11.17 Are there no streams or are they not listed?
    @Ignore
    @Test
    public void testGetAudioStreams() throws IOException, ExtractionException {
        // audio streams are not always necessary
        assertFalse(extractor.getAudioStreams().isEmpty());
    }

    @Test
    public void testGetVideoStreams() throws IOException, ExtractionException {
        List<VideoStream> streams = new ArrayList<>();
        streams.addAll(extractor.getVideoStreams());
        streams.addAll(extractor.getVideoOnlyStreams());

        assertTrue(Integer.toString(streams.size()),streams.size() > 0);
        for (VideoStream s : streams) {
            assertTrue(s.getUrl(),
                    s.getUrl().contains(HTTPS));
            assertTrue(s.resolution.length() > 0);
            assertTrue(Integer.toString(s.getFormatId()),
                    0 <= s.getFormatId() && s.getFormatId() <= 4);
        }
    }


    @Test
    public void testGetSubtitlesListDefault() throws IOException, ExtractionException {
        // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
        assertTrue(extractor.getSubtitlesDefault().isEmpty());
    }

    @Test
    public void testGetSubtitlesList() throws IOException, ExtractionException {
        // Video (/view?v=YQHsXMglC9A) set in the setUp() method has no captions => null
        assertTrue(extractor.getSubtitles(SubtitlesFormat.TTML).isEmpty());
    }
}
