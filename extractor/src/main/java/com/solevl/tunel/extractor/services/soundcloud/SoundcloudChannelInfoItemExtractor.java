package com.solevl.tunel.extractor.services.soundcloud;

import com.grack.nanojson.JsonObject;
import com.solevl.tunel.extractor.channel.ChannelInfoItemExtractor;

import static com.solevl.tunel.extractor.utils.Utils.replaceHttpWithHttps;

public class SoundcloudChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    private final JsonObject itemObject;

    public SoundcloudChannelInfoItemExtractor(JsonObject itemObject) {
        this.itemObject = itemObject;
    }

    @Override
    public String getName() {
        return itemObject.getString("username");
    }

    @Override
    public String getUrl() {
        return replaceHttpWithHttps(itemObject.getString("permalink_url"));
    }

    @Override
    public String getThumbnailUrl() {
        return itemObject.getString("avatar_url", "");
    }

    @Override
    public long getSubscriberCount() {
        return itemObject.getNumber("followers_count", 0).longValue();
    }

    @Override
    public long getStreamCount() {
        return itemObject.getNumber("track_count", 0).longValue();
    }

    @Override
    public String getDescription() {
        return itemObject.getString("description", "");
    }
}
