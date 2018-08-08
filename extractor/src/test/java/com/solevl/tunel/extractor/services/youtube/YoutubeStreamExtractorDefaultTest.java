package com.solevl.tunel.extractor.services.youtube;

import com.solevl.tunel.extractor.ExtractorAsserts;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import com.solevl.tunel.extractor.stream.StreamExtractor;
import com.solevl.tunel.extractor.stream.StreamInfoItemsCollector;
import com.solevl.tunel.extractor.stream.StreamType;
import com.solevl.tunel.extractor.stream.SubtitlesFormat;
import com.solevl.tunel.extractor.stream.VideoStream;

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;
import com.solevl.tunel.extractor.stream.*;
import com.solevl.tunel.extractor.utils.Utils;

import java.io.IOException;

import static org.junit.Assert.*;

/*
 * Created by Premangshu Howli on 30.12.15.
 *
 * Copyright (C) Premangshu Howli 2015 <hsolevl@gmail.com>
 * YoutubeVideoExtractorDefault.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Test for {@link StreamExtractor}
 */
public class YoutubeStreamExtractorDefaultTest {
    private static YoutubeStreamExtractor extractor;

    @BeforeClass
    public static void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = (YoutubeStreamExtractor) ServiceList.YouTube
                .getStreamExtractor("https://www.youtube.com/watch?v=rYEDA3JcQqw");
        extractor.fetchPage();
    }

    @Test
    public void testGetInvalidTimeStamp() throws ParsingException {
        assertTrue(extractor.getTimeStamp() + "",
                extractor.getTimeStamp() <= 0);
    }

    @Test
    public void testGetValidTimeStamp() throws IOException, ExtractionException {
        StreamExtractor extractor = ServiceList.YouTube.getStreamExtractor("https://youtu.be/FmG385_uUys?t=174");
        assertEquals(extractor.getTimeStamp() + "", "174");
    }

    @Test
    public void testGetTitle() throws ParsingException {
        assertFalse(extractor.getName().isEmpty());
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


    @Test
    public void testGetLength() throws ParsingException {
        assertTrue(extractor.getLength() > 0);
    }

    @Test
    public void testGetViewCount() throws ParsingException {
        Long count = extractor.getViewCount();
        assertTrue(Long.toString(count), count >= /* specific to that video */ 1220025784);
    }

    @Test
    public void testGetUploadDate() throws ParsingException {
        assertTrue(extractor.getUploadDate().length() > 0);
    }

    @Test
    public void testGetUploaderUrl() throws ParsingException {
        assertTrue(extractor.getUploaderUrl().length() > 0);
    }

    @Test
    public void testGetThumbnailUrl() throws ParsingException {
        ExtractorAsserts.assertIsSecureUrl(extractor.getThumbnailUrl());
    }

    @Test
    public void testGetUploaderAvatarUrl() throws ParsingException {
        ExtractorAsserts.assertIsSecureUrl(extractor.getUploaderAvatarUrl());
    }

    @Test
    public void testGetAudioStreams() throws IOException, ExtractionException {
        assertFalse(extractor.getAudioStreams().isEmpty());
    }

    @Test
    public void testGetVideoStreams() throws IOException, ExtractionException {
        for (VideoStream s : extractor.getVideoStreams()) {
            ExtractorAsserts.assertIsSecureUrl(s.url);
            assertTrue(s.resolution.length() > 0);
            assertTrue(Integer.toString(s.getFormatId()),
                    0 <= s.getFormatId() && s.getFormatId() <= 4);
        }
    }

    @Test
    public void testStreamType() throws ParsingException {
        assertTrue(extractor.getStreamType() == StreamType.VIDEO_STREAM);
    }

    @Test
    public void testGetDashMpd() throws ParsingException {
        assertTrue(extractor.getDashMpdUrl(),
                extractor.getDashMpdUrl() != null || !extractor.getDashMpdUrl().isEmpty());
    }

    @Test
    public void testGetRelatedVideos() throws ExtractionException, IOException {
        StreamInfoItemsCollector relatedVideos = extractor.getRelatedVideos();
        Utils.printErrors(relatedVideos.getErrors());
        assertFalse(relatedVideos.getItems().isEmpty());
        assertTrue(relatedVideos.getErrors().isEmpty());
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
