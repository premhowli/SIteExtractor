package com.solevl.tunel.extractor.services.soundcloud;

import com.grack.nanojson.JsonObject;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.stream.StreamType;
import com.solevl.tunel.extractor.stream.StreamInfoItemExtractor;

import static com.solevl.tunel.extractor.utils.Utils.replaceHttpWithHttps;

public class SoundcloudStreamInfoItemExtractor implements StreamInfoItemExtractor {

    protected final JsonObject itemObject;

    public SoundcloudStreamInfoItemExtractor(JsonObject itemObject) {
        this.itemObject = itemObject;
    }

    @Override
    public String getUrl() {
        return replaceHttpWithHttps(itemObject.getString("permalink_url"));
    }

    @Override
    public String getName() {
        return itemObject.getString("title");
    }

    @Override
    public long getDuration() {
        return itemObject.getNumber("duration", 0).longValue() / 1000L;
    }

    @Override
    public String getUploaderName() {
        return itemObject.getObject("user").getString("username");
    }

    @Override
    public String getUploaderUrl() {
        return replaceHttpWithHttps(itemObject.getObject("user").getString("permalink_url"));
    }

    @Override
    public String getUploadDate() throws ParsingException {
        return SoundcloudParsingHelper.toDateString(itemObject.getString("created_at"));
    }

    @Override
    public long getViewCount() {
        return itemObject.getNumber("playback_count", 0).longValue();
    }

    @Override
    public String getThumbnailUrl() {
        return itemObject.getString("artwork_url");
    }

    @Override
    public StreamType getStreamType() {
        return StreamType.AUDIO_STREAM;
    }

    @Override
    public boolean isAd() {
        return false;
    }
}
