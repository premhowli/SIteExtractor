package com.solevl.tunel.extractor.services.youtube.search;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeSearchExtractor;

import org.junit.Test;
import com.solevl.tunel.extractor.InfoItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/*
 * Created by Premangshu Howli on 27.05.18
 *
 * Copyright (C) Premangshu Howli 2018 <hsolevl@gmail.com>
 * YoutubeSearchExtractorBaseTest.java is part of NewPipe.
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
 * Test for {@link YoutubeSearchExtractor}
 */
public abstract class YoutubeSearchExtractorBaseTest {

    protected static YoutubeSearchExtractor extractor;
    protected static ListExtractor.InfoItemsPage<InfoItem> itemsPage;


    @Test
    public void testResultListElementsLength() {
        assertTrue(Integer.toString(itemsPage.getItems().size()),
                itemsPage.getItems().size() > 10);
    }

    @Test
    public void testUrl() throws Exception {
        assertTrue(extractor.getUrl(), extractor.getUrl().startsWith("https://www.youtube.com"));
    }
}
