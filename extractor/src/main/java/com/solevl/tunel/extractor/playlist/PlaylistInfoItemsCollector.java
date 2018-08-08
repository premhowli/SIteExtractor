package com.solevl.tunel.extractor.playlist;

import com.solevl.tunel.extractor.InfoItemsCollector;
import com.solevl.tunel.extractor.exceptions.ParsingException;

public class PlaylistInfoItemsCollector extends InfoItemsCollector<PlaylistInfoItem, PlaylistInfoItemExtractor> {

    public PlaylistInfoItemsCollector(int serviceId) {
        super(serviceId);
    }

    @Override
    public PlaylistInfoItem extract(PlaylistInfoItemExtractor extractor) throws ParsingException {

        String name = extractor.getName();
        int serviceId = getServiceId();
        String url = extractor.getUrl();

        PlaylistInfoItem resultItem = new PlaylistInfoItem(serviceId, url, name);

        try {
            resultItem.setUploaderName(extractor.getUploaderName());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setThumbnailUrl(extractor.getThumbnailUrl());
        } catch (Exception e) {
            addError(e);
        }
        try {
            resultItem.setStreamCount(extractor.getStreamCount());
        } catch (Exception e) {
            addError(e);
        }
        return resultItem;
    }
}
