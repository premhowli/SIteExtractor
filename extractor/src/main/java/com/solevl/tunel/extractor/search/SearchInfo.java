package com.solevl.tunel.extractor.search;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.ListInfo;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.linkhandler.SearchQueryHandler;
import com.solevl.tunel.extractor.InfoItem;
import com.solevl.tunel.extractor.utils.ExtractorHelper;

import java.io.IOException;


public class SearchInfo extends ListInfo<InfoItem> {

    private String searchString;
    private String searchSuggestion;

    public SearchInfo(int serviceId,
                      SearchQueryHandler qIHandler,
                      String searchString) {
        super(serviceId, qIHandler, "Search");
        this.searchString = searchString;
    }


    public static SearchInfo getInfo(StreamingService service, SearchQueryHandler searchQuery, String contentCountry) throws ExtractionException, IOException {
        SearchExtractor extractor = service.getSearchExtractor(searchQuery, contentCountry);
        extractor.fetchPage();
        return getInfo(extractor);
    }

    public static SearchInfo getInfo(SearchExtractor extractor) throws ExtractionException, IOException {
        final SearchInfo info = new SearchInfo(
                extractor.getServiceId(),
                extractor.getUIHandler(),
                extractor.getSearchString());

        try {
            info.searchSuggestion = extractor.getSearchSuggestion();
        } catch (Exception e) {
            info.addError(e);
        }

        ListExtractor.InfoItemsPage<InfoItem> page = ExtractorHelper.getItemsPageOrLogError(info, extractor);
        info.setRelatedItems(page.getItems());
        info.setNextPageUrl(page.getNextPageUrl());

        return info;
    }


    public static ListExtractor.InfoItemsPage<InfoItem> getMoreItems(StreamingService service,
                                                                     SearchQueryHandler query,
                                                                     String contentCountry,
                                                                     String pageUrl)
            throws IOException, ExtractionException {
        return service.getSearchExtractor(query, contentCountry).getPage(pageUrl);
    }

    // Getter
    public String getSearchString() {
        return searchString;
    }

    public String getSearchSuggestion() {
        return searchSuggestion;
    }
}
