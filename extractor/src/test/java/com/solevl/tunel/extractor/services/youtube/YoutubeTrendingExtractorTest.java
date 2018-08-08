package com.solevl.tunel.extractor.services.youtube;

/*
 * Created by Premangshu Howli on 12.08.17.
 *
 * Copyright (C) Premangshu Howli 2017 <hsolevl@gmail.com>
 * YoutubeTrendingExtractorTest.java is part of NewPipe.
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

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;
import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeTrendingExtractor;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory;
import com.solevl.tunel.extractor.stream.StreamInfoItem;
import com.solevl.tunel.extractor.utils.Utils;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;
import static com.solevl.tunel.extractor.ExtractorAsserts.assertEmptyErrors;
import static com.solevl.tunel.extractor.ServiceList.YouTube;


/**
 * Test for {@link YoutubeTrendingLinkHandlerFactory}
 */
public class YoutubeTrendingExtractorTest {

    static YoutubeTrendingExtractor extractor;

    @BeforeClass
    public static void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = (YoutubeTrendingExtractor) YouTube
                .getKioskList()
                .getExtractorById("Trending", null);
        extractor.fetchPage();
        extractor.setContentCountry("de");
    }

    @Test
    public void testGetDownloader() throws Exception {
        assertNotNull(NewPipe.getDownloader());
    }

    @Test
    public void testGetName() throws Exception {
        assertFalse(extractor.getName().isEmpty());
    }

    @Test
    public void testId() throws Exception {
        assertEquals(extractor.getId(), "Trending");
    }

    @Test
    public void testGetStreamsQuantity() throws Exception {
        ListExtractor.InfoItemsPage<StreamInfoItem> page = extractor.getInitialPage();
        Utils.printErrors(page.getErrors());
        assertTrue("no streams are received", page.getItems().size() >= 20);
    }

    @Test
    public void testGetStreamsErrors() throws Exception {
        assertEmptyErrors("errors during stream list extraction", extractor.getInitialPage().getErrors());
    }

    @Test
    public void testHasMoreStreams() throws Exception {
        // Setup the streams
        extractor.getInitialPage();
        assertFalse("has more streams", extractor.hasNextPage());
    }

    @Test
    public void testGetNextPage() {
        assertTrue("extractor has next streams", extractor.getPage(extractor.getNextPageUrl()) == null
                || extractor.getPage(extractor.getNextPageUrl()).getItems().isEmpty());
    }

    @Test
    public void testGetUrl() throws Exception {
        assertEquals(extractor.getUrl(), extractor.getUrl(), "https://www.youtube.com/feed/trending");
    }
}
