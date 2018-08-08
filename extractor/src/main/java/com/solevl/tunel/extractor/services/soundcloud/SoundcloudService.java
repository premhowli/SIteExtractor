package com.solevl.tunel.extractor.services.soundcloud;

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
import com.solevl.tunel.extractor.subscription.SubscriptionExtractor;

import org.schabi.newpipe.extractor.*;
import org.schabi.newpipe.extractor.linkhandler.*;

import com.solevl.tunel.extractor.playlist.PlaylistExtractor;
import com.solevl.tunel.extractor.stream.StreamExtractor;

import static java.util.Collections.singletonList;

public class SoundcloudService extends StreamingService {

    public SoundcloudService(int id) {
        super(id, "SoundCloud", singletonList(ServiceInfo.MediaCapability.AUDIO));
    }

    @Override
    public SearchExtractor getSearchExtractor(SearchQueryHandler queryHandler, String contentCountry) {
        return new SoundcloudSearchExtractor(this, queryHandler, contentCountry);
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new SoundcloudSearchQueryHandlerFactory();
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return SoundcloudStreamLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return SoundcloudChannelLinkHandlerFactory.getInstance();
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return SoundcloudPlaylistLinkHandlerFactory.getInstance();
    }


    @Override
    public StreamExtractor getStreamExtractor(LinkHandler LinkHandler) {
        return new SoundcloudStreamExtractor(this, LinkHandler);
    }

    @Override
    public ChannelExtractor getChannelExtractor(ListLinkHandler urlIdHandler) {
        return new SoundcloudChannelExtractor(this, urlIdHandler);
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler urlIdHandler) {
        return new SoundcloudPlaylistExtractor(this, urlIdHandler);
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return new SoundcloudSuggestionExtractor(getServiceId());
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        KioskList.KioskExtractorFactory chartsFactory = new KioskList.KioskExtractorFactory() {
            @Override
            public KioskExtractor createNewKiosk(StreamingService streamingService,
                                                 String url,
                                                 String id)
                    throws ExtractionException {
                return new SoundcloudChartsExtractor(SoundcloudService.this,
                        new SoundcloudChartsLinkHandlerFactory().fromUrl(url), id);
            }
        };

        KioskList list = new KioskList(getServiceId());

        // add kiosks here e.g.:
        final SoundcloudChartsLinkHandlerFactory h = new SoundcloudChartsLinkHandlerFactory();
        try {
            list.addKioskEntry(chartsFactory, h, "Top 50");
            list.addKioskEntry(chartsFactory, h, "New & hot");
        } catch (Exception e) {
            throw new ExtractionException(e);
        }

        return list;
    }


    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return new SoundcloudSubscriptionExtractor(this);
    }
}
