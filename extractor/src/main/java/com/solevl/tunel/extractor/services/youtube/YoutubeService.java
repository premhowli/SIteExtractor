package com.solevl.tunel.extractor.services.youtube;

import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.SuggestionExtractor;
import com.solevl.tunel.extractor.channel.ChannelExtractor;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.kiosk.KioskExtractor;
import com.solevl.tunel.extractor.kiosk.KioskList;
import com.solevl.tunel.extractor.linkhandler.LinkHandler;
import com.solevl.tunel.extractor.linkhandler.LinkHandlerFactory;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandlerFactory;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandler;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandlerFactory;
import com.solevl.tunel.extractor.search.SearchExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeChannelExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubePlaylistExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeSearchExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeStreamExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeSubscriptionExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeSuggestionExtractor;
import com.solevl.tunel.extractor.services.youtube.extractors.YoutubeTrendingExtractor;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeChannelLinkHandlerFactory;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubePlaylistLinkHandlerFactory;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeSearchQueryHandlerFactory;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory;
import com.solevl.tunel.extractor.services.youtube.linkHandler.YoutubeTrendingLinkHandlerFactory;
import com.solevl.tunel.extractor.subscription.SubscriptionExtractor;

import org.schabi.newpipe.extractor.*;
import org.schabi.newpipe.extractor.linkhandler.*;

import com.solevl.tunel.extractor.playlist.PlaylistExtractor;

import org.schabi.newpipe.extractor.services.youtube.extractors.*;
import org.schabi.newpipe.extractor.services.youtube.linkHandler.*;
import com.solevl.tunel.extractor.stream.StreamExtractor;

import static java.util.Arrays.asList;


/*
 * Created by Premangshu Howli on 23.08.15.
 *
 * Copyright (C) Premangshu Howli 2018 <hsolevl@gmail.com>
 * YoutubeService.java is part of NewPipe.
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

public class YoutubeService extends StreamingService {

    public YoutubeService(int id) {
        super(id, "YouTube", asList(ServiceInfo.MediaCapability.AUDIO, ServiceInfo.MediaCapability.VIDEO, ServiceInfo.MediaCapability.LIVE));
    }

    @Override
    public SearchExtractor getSearchExtractor(SearchQueryHandler query, String contentCountry) {
        return new YoutubeSearchExtractor(this, query, contentCountry);
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return YoutubeStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return YoutubeChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return YoutubePlaylistLinkHandlerFactory.getInstance();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return YoutubeSearchQueryHandlerFactory.getInstance();
    }

    @Override
    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) throws ExtractionException {
        return new YoutubeStreamExtractor(this, linkHandler);
    }

    @Override
    public ChannelExtractor getChannelExtractor(ListLinkHandler urlIdHandler) throws ExtractionException {
        return new YoutubeChannelExtractor(this, urlIdHandler);
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler urlIdHandler) throws ExtractionException {
        return new YoutubePlaylistExtractor(this, urlIdHandler);
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return new YoutubeSuggestionExtractor(getServiceId());
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        KioskList list = new KioskList(getServiceId());

        // add kiosks here e.g.:
        try {
            list.addKioskEntry(new KioskList.KioskExtractorFactory() {
                @Override
                public KioskExtractor createNewKiosk(StreamingService streamingService, String url, String id)
                throws ExtractionException {
                    return new YoutubeTrendingExtractor(YoutubeService.this,
                            new YoutubeTrendingLinkHandlerFactory().fromUrl(url), id);
                }
            }, new YoutubeTrendingLinkHandlerFactory(), "Trending");
            list.setDefaultKiosk("Trending");
        } catch (Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return new YoutubeSubscriptionExtractor(this);
    }

}
