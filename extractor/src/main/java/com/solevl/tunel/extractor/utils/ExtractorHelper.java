package com.solevl.tunel.extractor.utils;

import com.solevl.tunel.extractor.Info;
import com.solevl.tunel.extractor.InfoItem;
import com.solevl.tunel.extractor.InfoItemsCollector;
import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.stream.StreamInfo;
import com.solevl.tunel.extractor.stream.StreamExtractor;

import java.util.Collections;
import java.util.List;

public class ExtractorHelper {
    private ExtractorHelper() {}

    public static <T extends InfoItem> ListExtractor.InfoItemsPage<T> getItemsPageOrLogError(Info info, ListExtractor<T> extractor) {
        try {
            ListExtractor.InfoItemsPage<T> page = extractor.getInitialPage();
            info.addAllErrors(page.getErrors());

            return page;
        } catch (Exception e) {
            info.addError(e);
            return ListExtractor.InfoItemsPage.emptyPage();
        }
    }


    public static List<InfoItem> getRelatedVideosOrLogError(StreamInfo info, StreamExtractor extractor) {
        try {
            InfoItemsCollector<? extends InfoItem, ?> collector = extractor.getRelatedVideos();
            info.addAllErrors(collector.getErrors());

            //noinspection unchecked
            return (List<InfoItem>) collector.getItems();
        } catch (Exception e) {
            info.addError(e);
            return Collections.emptyList();
        }
    }
}
