package com.solevl.tunel.extractor.services.youtube.search;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.channel.ChannelInfoItem;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeSearchExtractor;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;
import com.solevl.tunel.extractor.InfoItem;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class YoutubeSearchExtractorChannelOnlyTest extends YoutubeSearchExtractorBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = (YoutubeSearchExtractor) ServiceList.YouTube.getSearchExtractor("pewdiepie",
                asList(YoutubeSearchQueryHandlerFactory.CHANNELS), null, "de");
        extractor.fetchPage();
        itemsPage = extractor.getInitialPage();
    }

    @Test
    public void testGetSecondPage() throws Exception {
        YoutubeSearchExtractor secondExtractor = (YoutubeSearchExtractor) ServiceList.YouTube.getSearchExtractor("pewdiepie",
                asList(YoutubeSearchQueryHandlerFactory.CHANNELS), null, "de");
        ListExtractor.InfoItemsPage<InfoItem> secondPage = secondExtractor.getPage(itemsPage.getNextPageUrl());
        assertTrue(Integer.toString(secondPage.getItems().size()),
                secondPage.getItems().size() > 10);

        // check if its the same result
        boolean equals = true;
        for (int i = 0; i < secondPage.getItems().size()
                && i < itemsPage.getItems().size(); i++) {
            if(!secondPage.getItems().get(i).getUrl().equals(
                    itemsPage.getItems().get(i).getUrl())) {
                equals = false;
            }
        }
        assertFalse("First and second page are equal", equals);

        assertEquals("https://www.youtube.com/results?q=pewdiepie&sp=EgIQAlAU&page=3", secondPage.getNextPageUrl());
    }

    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://www.youtube.com/results?q=pewdiepie&sp=EgIQAlAU&page=2", extractor.getNextPageUrl());
    }

    @Test
    public void testOnlyContainChannels() {
        for(InfoItem item : itemsPage.getItems()) {
            if(!(item instanceof ChannelInfoItem)) {
                fail("The following item is no channel item: " + item.toString());
            }
        }
    }
}
