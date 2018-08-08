package com.solevl.tunel.extractor.services.youtube;

        /*
         * Created by Premangshu Howli on 12.08.17.
         *
         * Copyright (C) Premangshu Howli 2017 <hsolevl@gmail.com>
         * YoutubeTrendingKioskInfoTest.java is part of NewPipe.
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

import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.ServiceList;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.kiosk.KioskInfo;

import org.junit.BeforeClass;
import org.junit.Test;
import com.solevl.tunel.Downloader;

import com.solevl.tunel.extractor.linkhandler.LinkHandlerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link KioskInfo}
 */
public class YoutubeTrendingKioskInfoTest {
    static KioskInfo kioskInfo;

    @BeforeClass
    public static void setUp()
            throws Exception {
        NewPipe.init(Downloader.getInstance());
        StreamingService service = ServiceList.YouTube;
        LinkHandlerFactory LinkHandlerFactory = service.getKioskList().getListLinkHandlerFactoryByType("Trending");

        kioskInfo = KioskInfo.getInfo(ServiceList.YouTube, LinkHandlerFactory.fromId("Trending").getUrl(), null);
    }

    @Test
    public void getStreams() {
        assertFalse(kioskInfo.getRelatedItems().isEmpty());
    }

    @Test
    public void getId() {
        assertTrue(kioskInfo.getId().equals("Trending")
                || kioskInfo.getId().equals("Trends"));
    }

    @Test
    public void getName() {
        assertFalse(kioskInfo.getName().isEmpty());
    }
}
