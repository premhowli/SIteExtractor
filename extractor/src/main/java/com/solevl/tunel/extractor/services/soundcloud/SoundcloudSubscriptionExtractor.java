package com.solevl.tunel.extractor.services.soundcloud;

import com.solevl.tunel.extractor.channel.ChannelInfoItem;
import com.solevl.tunel.extractor.channel.ChannelInfoItemsCollector;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.subscription.SubscriptionExtractor;
import com.solevl.tunel.extractor.subscription.SubscriptionItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extract the "followings" from a user in SoundCloud.
 */
public class SoundcloudSubscriptionExtractor extends SubscriptionExtractor {

    public SoundcloudSubscriptionExtractor(SoundcloudService service) {
        super(service, Collections.singletonList(ContentSource.CHANNEL_URL));
    }

    @Override
    public String getRelatedUrl() {
        return "https://soundcloud.com/you";
    }

    @Override
    public List<SubscriptionItem> fromChannelUrl(String channelUrl) throws IOException, ExtractionException {
        if (channelUrl == null) throw new InvalidSourceException("channel url is null");

        String id;
        try {
            id = service.getChannelLHFactory().fromUrl(getUrlFrom(channelUrl)).getId();
        } catch (ExtractionException e) {
            throw new InvalidSourceException(e);
        }

        String apiUrl = "https://api.soundcloud.com/users/" + id + "/followings"
                + "?client_id=" + SoundcloudParsingHelper.clientId()
                + "&limit=200";
        ChannelInfoItemsCollector collector = new ChannelInfoItemsCollector(service.getServiceId());
        // ± 2000 is the limit of followings on SoundCloud, so this minimum should be enough
        SoundcloudParsingHelper.getUsersFromApiMinItems(2500, collector, apiUrl);

        return toSubscriptionItems(collector.getItems());
    }

    private String getUrlFrom(String channelUrl) {
        channelUrl = channelUrl.replace("http://", "https://").trim();

        if (!channelUrl.startsWith("https://")) {
            if (!channelUrl.contains("soundcloud.com/")) {
                channelUrl = "https://soundcloud.com/" + channelUrl;
            } else {
                channelUrl = "https://" + channelUrl;
            }
        }

        return channelUrl;
    }

    /*//////////////////////////////////////////////////////////////////////////
    // Utils
    //////////////////////////////////////////////////////////////////////////*/

    private List<SubscriptionItem> toSubscriptionItems(List<ChannelInfoItem> items) {
        List<SubscriptionItem> result = new ArrayList<>(items.size());
        for (ChannelInfoItem item : items) {
            result.add(new SubscriptionItem(item.getServiceId(), item.getUrl(), item.getName()));
        }
        return result;
    }
}
