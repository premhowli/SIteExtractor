package com.solevl.tunel.extractor.services.soundcloud;

import com.solevl.tunel.extractor.NewPipe;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;

public class SoundcloudParsingHelperTest {
    @BeforeClass
    public static void setUp() {
        NewPipe.init(Downloader.getInstance());
    }

    @Test
    public void resolveUrlWithEmbedPlayerTest() throws Exception {
        Assert.assertEquals("https://soundcloud.com/trapcity", SoundcloudParsingHelper.resolveUrlWithEmbedPlayer("https://api.soundcloud.com/users/26057743"));
        Assert.assertEquals("https://soundcloud.com/nocopyrightsounds", SoundcloudParsingHelper.resolveUrlWithEmbedPlayer("https://api.soundcloud.com/users/16069159"));
    }

    @Test
    public void resolveIdWithEmbedPlayerTest() throws Exception {
        Assert.assertEquals("26057743", SoundcloudParsingHelper.resolveIdWithEmbedPlayer("https://soundcloud.com/trapcity"));
        Assert.assertEquals("16069159", SoundcloudParsingHelper.resolveIdWithEmbedPlayer("https://soundcloud.com/nocopyrightsounds"));

    }

}