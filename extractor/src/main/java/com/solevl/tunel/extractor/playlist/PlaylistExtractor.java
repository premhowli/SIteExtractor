package com.solevl.tunel.extractor.playlist;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.stream.StreamInfoItem;

public abstract class PlaylistExtractor extends ListExtractor<StreamInfoItem> {

    public PlaylistExtractor(StreamingService service, ListLinkHandler urlIdHandler) {
        super(service, urlIdHandler);
    }

    public abstract String getThumbnailUrl() throws ParsingException;
    public abstract String getBannerUrl() throws ParsingException;

    public abstract String getUploaderUrl() throws ParsingException;
    public abstract String getUploaderName() throws ParsingException;
    public abstract String getUploaderAvatarUrl() throws ParsingException;

    public abstract long getStreamCount() throws ParsingException;
}
