package com.solevl.tunel.extractor.services.soundcloud;

import com.solevl.tunel.extractor.ExtractorAsserts;
import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.services.BasePlaylistExtractorTest;
import com.solevl.tunel.extractor.services.DefaultTests;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.schabi.newpipe.Downloader;

import com.solevl.tunel.extractor.playlist.PlaylistExtractor;
import com.solevl.tunel.extractor.stream.StreamInfoItem;

import static org.junit.Assert.*;

/**
 * Test for {@link PlaylistExtractor}
 */
public class SoundcloudPlaylistExtractorTest {
    public static class LuvTape implements BasePlaylistExtractorTest {
        private static SoundcloudPlaylistExtractor extractor;

        @BeforeClass
        public static void setUp() throws Exception {
            NewPipe.init(Downloader.getInstance());
            extractor = (SoundcloudPlaylistExtractor) ServiceList.SoundCloud
                    .getPlaylistExtractor("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r?test=123");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(ServiceList.SoundCloud.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("THE PERFECT LUV TAPE®️", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("246349810", extractor.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/liluzivert/sets/the-perfect-luv-tape-r?test=123", extractor.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            DefaultTests.defaultTestRelatedItems(extractor, ServiceList.SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() {
            try {
                DefaultTests.defaultTestMoreItems(extractor, ServiceList.SoundCloud.getServiceId());
            } catch (Throwable ignored) {
                return;
            }

            fail("This playlist doesn't have more items, it should throw an error");
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = extractor.getUploaderUrl();
            ExtractorAsserts.assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("liluzivert"));
        }

        @Test
        public void testUploaderName() {
            assertTrue(extractor.getUploaderName().contains("LIL UZI VERT"));
        }

        @Test
        public void testUploaderAvatarUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", extractor.getStreamCount() >= 10);
        }
    }

    public static class RandomHouseDanceMusic implements BasePlaylistExtractorTest {
        private static SoundcloudPlaylistExtractor extractor;

        @BeforeClass
        public static void setUp() throws Exception {
            NewPipe.init(Downloader.getInstance());
            extractor = (SoundcloudPlaylistExtractor) ServiceList.SoundCloud
                    .getPlaylistExtractor("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(ServiceList.SoundCloud.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("House, Electro , Dance Music 2", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("310980722", extractor.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/hunter-leader/sets/house-electro-dance-music-2", extractor.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            DefaultTests.defaultTestRelatedItems(extractor, ServiceList.SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            DefaultTests.defaultTestMoreItems(extractor, ServiceList.SoundCloud.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = extractor.getUploaderUrl();
            ExtractorAsserts.assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("hunter-leader"));
        }

        @Test
        public void testUploaderName() {
            assertEquals("Gosu", extractor.getUploaderName());
        }

        @Test
        public void testUploaderAvatarUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", extractor.getStreamCount() >= 10);
        }
    }

    public static class EDMxxx implements BasePlaylistExtractorTest {
        private static SoundcloudPlaylistExtractor extractor;

        @BeforeClass
        public static void setUp() throws Exception {
            NewPipe.init(Downloader.getInstance());
            extractor = (SoundcloudPlaylistExtractor) ServiceList.SoundCloud
                    .getPlaylistExtractor("https://soundcloud.com/user350509423/sets/edm-xxx");
            extractor.fetchPage();
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Additional Testing
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testGetPageInNewExtractor() throws Exception {
            final PlaylistExtractor newExtractor = ServiceList.SoundCloud.getPlaylistExtractor(extractor.getUrl());
            DefaultTests.defaultTestGetPageInNewExtractor(extractor, newExtractor, ServiceList.SoundCloud.getServiceId());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // Extractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testServiceId() {
            assertEquals(ServiceList.SoundCloud.getServiceId(), extractor.getServiceId());
        }

        @Test
        public void testName() {
            assertEquals("EDM xXx", extractor.getName());
        }

        @Test
        public void testId() {
            assertEquals("136000376", extractor.getId());
        }

        @Test
        public void testUrl() throws Exception {
            assertEquals("https://soundcloud.com/user350509423/sets/edm-xxx", extractor.getUrl());
        }

        @Test
        public void testOriginalUrl() throws Exception {
            assertEquals("https://soundcloud.com/user350509423/sets/edm-xxx", extractor.getOriginalUrl());
        }

        /*//////////////////////////////////////////////////////////////////////////
        // ListExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testRelatedItems() throws Exception {
            DefaultTests.defaultTestRelatedItems(extractor, ServiceList.SoundCloud.getServiceId());
        }

        @Test
        public void testMoreRelatedItems() throws Exception {
            ListExtractor.InfoItemsPage<StreamInfoItem> currentPage = DefaultTests.defaultTestMoreItems(extractor, ServiceList.SoundCloud.getServiceId());
            // Test for 2 more levels
            for (int i = 0; i < 2; i++) {
                currentPage = extractor.getPage(currentPage.getNextPageUrl());
                DefaultTests.defaultTestListOfItems(ServiceList.SoundCloud.getServiceId(), currentPage.getItems(), currentPage.getErrors());
            }
        }

        /*//////////////////////////////////////////////////////////////////////////
        // PlaylistExtractor
        //////////////////////////////////////////////////////////////////////////*/

        @Test
        public void testThumbnailUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getThumbnailUrl());
        }

        @Ignore
        @Test
        public void testBannerUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getBannerUrl());
        }

        @Test
        public void testUploaderUrl() {
            final String uploaderUrl = extractor.getUploaderUrl();
            ExtractorAsserts.assertIsSecureUrl(uploaderUrl);
            assertTrue(uploaderUrl, uploaderUrl.contains("user350509423"));
        }

        @Test
        public void testUploaderName() {
            assertEquals("user350509423", extractor.getUploaderName());
        }

        @Test
        public void testUploaderAvatarUrl() {
            ExtractorAsserts.assertIsSecureUrl(extractor.getUploaderAvatarUrl());
        }

        @Test
        public void testStreamCount() {
            assertTrue("Error in the streams count", extractor.getStreamCount() >= 3900);
        }
    }
}
