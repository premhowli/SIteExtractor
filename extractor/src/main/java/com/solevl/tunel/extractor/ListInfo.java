package com.solevl.tunel.extractor;

import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;

import java.util.List;

public abstract class ListInfo<T extends InfoItem> extends Info {
    private List<T> relatedItems;
    private String nextPageUrl = null;
    private final List<String> contentFilters;
    private final String sortFilter;

    public ListInfo(int serviceId,
                    String id,
                    String url,
                    String originalUrl,
                    String name,
                    List<String> contentFilter,
                    String sortFilter) {
        super(serviceId, id, url, originalUrl, name);
        this.contentFilters = contentFilter;
        this.sortFilter = sortFilter;
    }

    public ListInfo(int serviceId, ListLinkHandler listUrlIdHandler, String name) {
        super(serviceId, listUrlIdHandler, name);
        this.contentFilters = listUrlIdHandler.getContentFilters();
        this.sortFilter = listUrlIdHandler.getSortFilter();
    }

    public List<T> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<T> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public boolean hasNextPage() {
        return nextPageUrl != null && !nextPageUrl.isEmpty();
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String pageUrl) {
        this.nextPageUrl = pageUrl;
    }

    public List<String> getContentFilters() {
        return contentFilters;
    }

    public String getSortFilter() {
        return sortFilter;
    }
}
