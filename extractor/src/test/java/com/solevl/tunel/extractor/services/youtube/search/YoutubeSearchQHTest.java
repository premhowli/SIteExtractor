package com.solevl.tunel.extractor.services.youtube.search;

import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;

import org.junit.Assert;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class YoutubeSearchQHTest {

    @Test
    public void testRegularValues() throws Exception {
        Assert.assertEquals("https://www.youtube.com/results?q=asdf", ServiceList.YouTube.getSearchQHFactory().fromQuery("asdf").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=hans", ServiceList.YouTube.getSearchQHFactory().fromQuery("hans").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=Poifj%26jaijf", ServiceList.YouTube.getSearchQHFactory().fromQuery("Poifj&jaijf").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=G%C3%BCl%C3%BCm", ServiceList.YouTube.getSearchQHFactory().fromQuery("Gülüm").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=%3Fj%24%29H%C2%A7B", ServiceList.YouTube.getSearchQHFactory().fromQuery("?j$)H§B").getUrl());
    }

    @Test
    public void testGetContentFilter() throws Exception {
        Assert.assertEquals(YoutubeSearchQueryHandlerFactory.VIDEOS, ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("", asList(new String[]{YoutubeSearchQueryHandlerFactory.VIDEOS}), "").getContentFilters().get(0));
        Assert.assertEquals(YoutubeSearchQueryHandlerFactory.CHANNELS, ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("asdf", asList(new String[]{YoutubeSearchQueryHandlerFactory.CHANNELS}), "").getContentFilters().get(0));
    }

    @Test
    public void testWithContentfilter() throws Exception {
        Assert.assertEquals("https://www.youtube.com/results?q=asdf&sp=EgIQAVAU", ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("asdf", asList(new String[]{YoutubeSearchQueryHandlerFactory.VIDEOS}), "").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=asdf&sp=EgIQAlAU", ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("asdf", asList(new String[]{YoutubeSearchQueryHandlerFactory.CHANNELS}), "").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=asdf&sp=EgIQA1AU", ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("asdf", asList(new String[]{YoutubeSearchQueryHandlerFactory.PLAYLISTS}), "").getUrl());
        Assert.assertEquals("https://www.youtube.com/results?q=asdf", ServiceList.YouTube.getSearchQHFactory()
                .fromQuery("asdf", asList(new String[]{"fjiijie"}), "").getUrl());
    }

    @Test
    public void testGetAvailableContentFilter() {
        final String[] contentFilter = ServiceList.YouTube.getSearchQHFactory().getAvailableContentFilter();
        assertEquals(4, contentFilter.length);
        assertEquals("all", contentFilter[0]);
        assertEquals("videos", contentFilter[1]);
        assertEquals("channels", contentFilter[2]);
        assertEquals("playlists", contentFilter[3]);
    }

    @Test
    public void testGetAvailableSortFilter() {
        final String[] contentFilter = ServiceList.YouTube.getSearchQHFactory().getAvailableSortFilter();
        assertEquals(0, contentFilter.length);
    }
}
