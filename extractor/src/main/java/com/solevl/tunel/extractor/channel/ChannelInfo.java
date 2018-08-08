package com.solevl.tunel.extractor.channel;

import com.solevl.tunel.extractor.ListExtractor;
import com.solevl.tunel.extractor.ListInfo;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.stream.StreamInfoItem;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.utils.ExtractorHelper;

import java.io.IOException;

/*
 * Created by Premangshu Howli on 31.07.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * ChannelInfo.java is part of NewPipe.
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

public class ChannelInfo extends ListInfo<StreamInfoItem> {

    public ChannelInfo(int serviceId, ListLinkHandler urlIdHandler, String name) throws ParsingException {
        super(serviceId, urlIdHandler, name);
    }

    public static ChannelInfo getInfo(String url) throws IOException, ExtractionException {
        return getInfo(NewPipe.getServiceByUrl(url), url);
    }

    public static ChannelInfo getInfo(StreamingService service, String url) throws IOException, ExtractionException {
        ChannelExtractor extractor = service.getChannelExtractor(url);
        extractor.fetchPage();
        return getInfo(extractor);
    }

    public static ListExtractor.InfoItemsPage<StreamInfoItem> getMoreItems(StreamingService service, String url, String pageUrl) throws IOException, ExtractionException {
        return service.getChannelExtractor(url).getPage(pageUrl);
    }

    public static ChannelInfo getInfo(ChannelExtractor extractor) throws IOException, ExtractionException {

        ChannelInfo info = new ChannelInfo(extractor.getServiceId(),
                extractor.getUIHandler(),
                extractor.getName());


        try {
            info.setAvatarUrl(extractor.getAvatarUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setBannerUrl(extractor.getBannerUrl());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setFeedUrl(extractor.getFeedUrl());
        } catch (Exception e) {
            info.addError(e);
        }

        final ListExtractor.InfoItemsPage<StreamInfoItem> itemsPage = ExtractorHelper.getItemsPageOrLogError(info, extractor);
        info.setRelatedItems(itemsPage.getItems());
        info.setNextPageUrl(itemsPage.getNextPageUrl());

        try {
            info.setSubscriberCount(extractor.getSubscriberCount());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setDescription(extractor.getDescription());
        } catch (Exception e) {
            info.addError(e);
        }
        try {
            info.setDonationLinks(extractor.getDonationLinks());
        } catch (Exception e) {
            info.addError(e);
        }

        return info;
    }

    private String avatarUrl;
    private String bannerUrl;
    private String feedUrl;
    private long subscriberCount = -1;
    private String description;
    private String[] donationLinks;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public long getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(long subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getDonationLinks() {
        return donationLinks;
    }

    public void setDonationLinks(String[] donationLinks) {
        this.donationLinks = donationLinks;
    }
}
