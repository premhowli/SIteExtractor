package com.solevl.tunel.extractor.services.youtube;

/*
 * Created by Premangshu Howli on 18.11.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * YoutubeSuggestionExtractorTest.java is part of NewPipe.
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
import org.schabi.newpipe.Downloader;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.SuggestionExtractor;
import com.solevl.tunel.extractor.exceptions.ExtractionException;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static com.solevl.tunel.extractor.ServiceList.YouTube;

/**
 * Test for {@link SuggestionExtractor}
 */
public class YoutubeSuggestionExtractorTest {
    private static SuggestionExtractor suggestionExtractor;

    @BeforeClass
    public static void setUp() throws Exception {
        NewPipe.init(Downloader.getInstance());
        suggestionExtractor = YouTube.getSuggestionExtractor();
    }

    @Test
    public void testIfSuggestions() throws IOException, ExtractionException {
        assertFalse(suggestionExtractor.suggestionList("hello", "de").isEmpty());
    }
}
