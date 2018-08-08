package com.solevl.tunel.extractor.services.soundcloud;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.solevl.tunel.extractor.Downloader;
import com.solevl.tunel.extractor.StreamingService;
import com.solevl.tunel.extractor.exceptions.ExtractionException;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.linkhandler.ListLinkHandler;
import com.solevl.tunel.extractor.stream.StreamInfoItem;
import com.solevl.tunel.extractor.playlist.PlaylistExtractor;
import com.solevl.tunel.extractor.stream.StreamInfoItemsCollector;

import javax.annotation.Nonnull;
import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class SoundcloudPlaylistExtractor extends PlaylistExtractor {
    private String playlistId;
    private JsonObject playlist;

    private StreamInfoItemsCollector streamInfoItemsCollector = null;
    private String nextPageUrl = null;

    public SoundcloudPlaylistExtractor(StreamingService service, ListLinkHandler urlIdHandler) {
        super(service, urlIdHandler);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {

        playlistId = getUIHandler().getId();
        String apiUrl = "https://api.soundcloud.com/playlists/" + playlistId +
                "?client_id=" + SoundcloudParsingHelper.clientId() +
                "&representation=compact";

        String response = downloader.download(apiUrl);
        try {
            playlist = JsonParser.object().from(response);
        } catch (JsonParserException e) {
            throw new ParsingException("Could not parse json response", e);
        }
    }

    @Nonnull
    @Override
    public String getId() {
        return playlistId;
    }

    @Nonnull
    @Override
    public String getName() {
        return playlist.getString("title");
    }

    @Override
    public String getThumbnailUrl() {
        String artworkUrl = playlist.getString("artwork_url");

        if (artworkUrl == null) {
            // If the thumbnail is null, traverse the items list and get a valid one,
            // if it also fails, return null
            try {
                final InfoItemsPage<StreamInfoItem> infoItems = getInitialPage();
                if (infoItems.getItems().isEmpty()) return null;

                for (StreamInfoItem item : infoItems.getItems()) {
                    final String thumbnailUrl = item.getThumbnailUrl();
                    if (thumbnailUrl == null || thumbnailUrl.isEmpty()) continue;

                    return thumbnailUrl;
                }
            } catch (Exception ignored) {
            }
        }

        return artworkUrl;
    }

    @Override
    public String getBannerUrl() {
        return null;
    }

    @Override
    public String getUploaderUrl() {
        return SoundcloudParsingHelper.getUploaderUrl(playlist);
    }

    @Override
    public String getUploaderName() {
        return SoundcloudParsingHelper.getUploaderName(playlist);
    }

    @Override
    public String getUploaderAvatarUrl() {
        return SoundcloudParsingHelper.getAvatarUrl(playlist);
    }

    @Override
    public long getStreamCount() {
        return playlist.getNumber("track_count", 0).longValue();
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        if (streamInfoItemsCollector == null) {
            computeStreamsAndNextPageUrl();
        }
        return new InfoItemsPage<>(streamInfoItemsCollector, getNextPageUrl());
    }

    private void computeStreamsAndNextPageUrl() throws ExtractionException, IOException {
        streamInfoItemsCollector = new StreamInfoItemsCollector(getServiceId());

        // Note the "api", NOT "api-v2"
        String apiUrl = "https://api.soundcloud.com/playlists/" + getId() + "/tracks"
                + "?client_id=" + SoundcloudParsingHelper.clientId()
                + "&limit=20"
                + "&linked_partitioning=1";

        nextPageUrl = SoundcloudParsingHelper.getStreamsFromApiMinItems(15, streamInfoItemsCollector, apiUrl);
    }

    @Override
    public String getNextPageUrl() throws IOException, ExtractionException {
        if (nextPageUrl == null) {
            computeStreamsAndNextPageUrl();
        }
        return nextPageUrl;
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(String pageUrl) throws IOException, ExtractionException {
        if (pageUrl == null || pageUrl.isEmpty()) {
            throw new ExtractionException(new IllegalArgumentException("Page url is empty or null"));
        }

        StreamInfoItemsCollector collector = new StreamInfoItemsCollector(getServiceId());
        String nextPageUrl = SoundcloudParsingHelper.getStreamsFromApiMinItems(15, collector, pageUrl);

        return new InfoItemsPage<>(collector, nextPageUrl);
    }
}
