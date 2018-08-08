package com.solevl.tunel.extractor;

import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.channel.ChannelExtractor;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.kiosk.KioskList;
import com.solevl.tunel.extractor.linkhandler.LinkHandler;
import com.solevl.tunel.extractor.linkhandler.LinkHandlerFactory;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandlerFactory;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandler;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandlerFactory;
import com.solevl.tunel.extractor.playlist.PlaylistExtractor;
import com.solevl.tunel.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.linkhandler.*;
import com.solevl.tunel.extractor.stream.StreamExtractor;
import com.solevl.tunel.extractor.subscription.SubscriptionExtractor;

import java.util.Collections;
import java.util.List;

public abstract class StreamingService {
    public static class ServiceInfo {
        private final String name;
        private final List<MediaCapability> mediaCapabilities;

        public ServiceInfo(String name, List<MediaCapability> mediaCapabilities) {
            this.name = name;
            this.mediaCapabilities = Collections.unmodifiableList(mediaCapabilities);
        }

        public String getName() {
            return name;
        }

        public List<MediaCapability> getMediaCapabilities() {
            return mediaCapabilities;
        }

        public enum MediaCapability {
            AUDIO, VIDEO, LIVE
        }
    }

    public enum LinkType {
        NONE,
        STREAM,
        CHANNEL,
        PLAYLIST
    }

    private final int serviceId;
    private final ServiceInfo serviceInfo;

    public StreamingService(int id, String name, List<ServiceInfo.MediaCapability> capabilities) {
        this.serviceId = id;
        this.serviceInfo = new ServiceInfo(name, capabilities);
    }

    public final int getServiceId() {
        return serviceId;
    }

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    @Override
    public String toString() {
        return serviceId + ":" + serviceInfo.getName();
    }

    ////////////////////////////////////////////
    // Url Id handler
    ////////////////////////////////////////////
    public abstract LinkHandlerFactory getStreamLHFactory();
    public abstract ListLinkHandlerFactory getChannelLHFactory();
    public abstract ListLinkHandlerFactory getPlaylistLHFactory();
    public abstract SearchQueryHandlerFactory getSearchQHFactory();


    ////////////////////////////////////////////
    // Extractor
    ////////////////////////////////////////////
    public abstract SearchExtractor getSearchExtractor(SearchQueryHandler queryHandler, String contentCountry);
    public abstract SuggestionExtractor getSuggestionExtractor();
    public abstract SubscriptionExtractor getSubscriptionExtractor();
    public abstract KioskList getKioskList() throws ExtractionException;

    public abstract ChannelExtractor getChannelExtractor(ListLinkHandler urlIdHandler) throws ExtractionException;
    public abstract PlaylistExtractor getPlaylistExtractor(ListLinkHandler urlIdHandler) throws ExtractionException;
    public abstract StreamExtractor getStreamExtractor(LinkHandler UIHFactory) throws ExtractionException;

    public SearchExtractor getSearchExtractor(String query, List<String> contentFilter, String sortFilter, String contentCountry) throws ExtractionException {
        return getSearchExtractor(getSearchQHFactory().fromQuery(query, contentFilter, sortFilter), contentCountry);
    }

    public ChannelExtractor getChannelExtractor(String id, List<String> contentFilter, String sortFilter) throws ExtractionException {
        return getChannelExtractor(getChannelLHFactory().fromQuery(id, contentFilter, sortFilter));
    }

    public PlaylistExtractor getPlaylistExtractor(String id, List<String> contentFilter, String sortFilter) throws ExtractionException {
        return getPlaylistExtractor(getPlaylistLHFactory().fromQuery(id, contentFilter, sortFilter));
    }

    public SearchExtractor getSearchExtractor(String query, String contentCountry) throws ExtractionException {
        return getSearchExtractor(getSearchQHFactory().fromQuery(query), contentCountry);
    }

    public ChannelExtractor getChannelExtractor(String url) throws ExtractionException {
        return getChannelExtractor(getChannelLHFactory().fromUrl(url));
    }

    public PlaylistExtractor getPlaylistExtractor(String url) throws ExtractionException {
        return getPlaylistExtractor(getPlaylistLHFactory().fromUrl(url));
    }

    public StreamExtractor getStreamExtractor(String url) throws ExtractionException {
        return getStreamExtractor(getStreamLHFactory().fromUrl(url));
    }



    /**
     * figure out where the link is pointing to (a channel, video, playlist, etc.)
     */
    public final LinkType getLinkTypeByUrl(String url) throws ParsingException {
        LinkHandlerFactory sH = getStreamLHFactory();
        LinkHandlerFactory cH = getChannelLHFactory();
        LinkHandlerFactory pH = getPlaylistLHFactory();

        if (sH.acceptUrl(url)) {
            return LinkType.STREAM;
        } else if (cH.acceptUrl(url)) {
            return LinkType.CHANNEL;
        } else if (pH.acceptUrl(url)) {
            return LinkType.PLAYLIST;
        } else {
            return LinkType.NONE;
        }
    }
}
