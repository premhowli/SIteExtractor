package com.solevl.tunel.extractor.kiosk;

/*
 * Created by Premangshu Howli on 12.08.17.
 *
 * Copyright (C) Premangshu Howli 2017 <hsolevl@gmail.com>
 * KioskInfo.java is part of NewPipe.
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

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.ListInfo;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ParsingException;

import org.schabi.newpipe.extractor.*;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.stream.StreamInfoItem;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.utils.ExtractorHelper;

import java.io.IOException;

public class KioskInfo extends ListInfo<StreamInfoItem> {

    private KioskInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static ListExtractor.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service,
                                                                           String url,
                                                                           String pageUrl,
                                                                           String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();
        KioskExtractor extractor = kl.getExtractorByUrl(url, pageUrl);
        extractor.setContentCountry(contentCountry);
        return extractor.getPage(pageUrl);
    }

    public static KioskInfo getInfo(String url,
                                    String contentCountry) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(url), url, contentCountry);
    }

    public static KioskInfo getInfo(StreamingService service,
                                    String url,
                                    String contentCountry) throws IOException, ExtractionException {
        KioskList kl = service.getKioskList();
        KioskExtractor extractor = kl.getExtractorByUrl(url, null);
        extractor.setContentCountry(contentCountry);
        extractor.fetchPage();
        return getInfo(extractor);
    }

    /**
     * Get KioskInfo from KioskExtractor
     *
     * @param extractor an extractor where fetchPage() was already got called on.
     */
    public static KioskInfo getInfo(KioskExtractor extractor) throws ExtractionException {

        final KioskInfo info = new KioskInfo(extractor.getServiceId(),
                extractor.getUIHandler(),
                extractor.getName());

        final ListExtractor.InfoItemsPage<StreamInfoItem> itemsPage = ExtractorHelper.getItemsPageOrLogError(info, extractor);
        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        return info;
    }
}
