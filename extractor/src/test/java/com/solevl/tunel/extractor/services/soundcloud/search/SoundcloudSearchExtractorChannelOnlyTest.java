package com.solevl.tunel.extractor.services.soundcloud.search;

import com.solevl.tunel.extractor.ListExtractor;

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;
import com.solevl.tunel.extractor.InfoItem;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.channel.ChannelInfoItem;
import com.solevl.tunel.extractor.services.soundcloud.SoundcloudSearchExtractor;
import com.solevl.tunel.extractor.services.soundcloud.SoundcloudSearchQueryHandlerFactory;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static com.solevl.tunel.extractor.ServiceList.SoundCloud;

public class SoundcloudSearchExtractorChannelOnlyTest extends SoundcloudSearchExtractorBaseTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        NewPipe.init(Downloader.getInstance());
        extractor = (SoundcloudSearchExtractor) SoundCloud.getSearchExtractor("lill uzi vert",
                asList(SoundcloudSearchQueryHandlerFactory.USERS), null, "de");
        extractor.fetchPage();
        itemsPage = extractor.getInitialPage();
    }

    @Test
    public void testGetSecondPage() throws Exception {
        SoundcloudSearchExtractor secondExtractor = (SoundcloudSearchExtractor) SoundCloud.getSearchExtractor("lill uzi vert",
                asList(SoundcloudSearchQueryHandlerFactory.USERS), null, "de");
        ListExtractor.InfoItemsPage<InfoItem> secondPage = secondExtractor.getPage(itemsPage.getNextPageUrl());
        assertTrue(Integer.toString(secondPage.getItems().size()),
                secondPage.getItems().size() >= 3);

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

        assertEquals("https://api-v2.soundcloud.com/search/users?q=lill+uzi+vert&limit=10&offset=20",
                removeClientId(secondPage.getNextPageUrl()));
    }

    @Test
    public void testGetSecondPageUrl() throws Exception {
        assertEquals("https://api-v2.soundcloud.com/search/users?q=lill+uzi+vert&limit=10&offset=10",
                removeClientId(extractor.getNextPageUrl()));
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
