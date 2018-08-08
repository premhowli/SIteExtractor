package com.solevl.tunel.extractor.services.youtube.linkHandler;


import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandlerFactory;
import com.solevl.tunel.extractor.utils.Parser;

import java.util.List;

public class YoutubePlaylistLinkHandlerFactory extends ListLinkHandlerFactory {

    private static final YoutubePlaylistLinkHandlerFactory instance = new YoutubePlaylistLinkHandlerFactory();
    private static final String ID_PATTERN = "([\\-a-zA-Z0-9_]{10,})";

    public static YoutubePlaylistLinkHandlerFactory getInstance() {
        return instance;
    }

    @Override
    public String getUrl(String id, List<String> contentFilters, String sortFilter) {
        return "https://www.youtube.com/playlist?list=" + id;
    }

    @Override
    public String getId(String url) throws ParsingException {
        try {
            return Parser.matchGroup1("list=" + ID_PATTERN, url);
        } catch (final Exception exception) {
            throw new ParsingException("Error could not parse url :" + exception.getMessage(), exception);
        }
    }


    @Override
    public boolean onAcceptUrl(final String url) {
        final boolean hasNotEmptyUrl = url != null && !url.isEmpty();
        final boolean isYoutubeDomain = hasNotEmptyUrl && (url.contains("youtube") || url.contains("youtu.be"));
        return isYoutubeDomain && url.contains("list=");
    }
}
