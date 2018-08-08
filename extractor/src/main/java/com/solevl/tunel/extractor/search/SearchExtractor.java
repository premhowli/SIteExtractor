package com.solevl.tunel.extractor.search;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.InfoItem;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandler;

public abstract class SearchExtractor extends ListExtractor<InfoItem> {

    public static class NothingFoundException extends ExtractionException {
        public NothingFoundException(String message) {
            super(message);
        }
    }

    private final InfoItemsSearchCollector collector;
    private final String contentCountry;

    public SearchExtractor(StreamingService service, SearchQueryHandler urlIdHandler, String contentCountry) {
        super(service, urlIdHandler);
        collector = new InfoItemsSearchCollector(service.getServiceId());
        this.contentCountry = contentCountry;
    }

    public String getSearchString() {
        return getUIHandler().getSearchString();
    }

    public abstract String getSearchSuggestion() throws ParsingException;

    protected InfoItemsSearchCollector getInfoItemSearchCollector() {
        return collector;
    }

    @Override
    public SearchQueryHandler getUIHandler() {
        return (SearchQueryHandler) super.getUIHandler();
    }

    @Override
    public String getName() {
        return getUIHandler().getSearchString();
    }

    protected String getContentCountry() {
        return contentCountry;
    }
}
