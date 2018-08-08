package com.solevl.tunel.extractor.services.youtube.extractors;

import com.solevl.tunel.extractor.exceptions.ParsingException;

import org.jsoup.nodes.Element;
import com.solevl.tunel.extractor.channel.ChannelInfoItemExtractor;
import com.solevl.tunel.extractor.utils.Utils;

/*
 * Created by Premangshu Howli on 12.02.17.
 *
 * Copyright (C) Premangshu Howli 2017 <hsolevl@gmail.com>
 * YoutubeChannelInfoItemExtractor.java is part of NewPipe.
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

public class YoutubeChannelInfoItemExtractor implements ChannelInfoItemExtractor {
    private final Element el;

    public YoutubeChannelInfoItemExtractor(Element el) {
        this.el = el;
    }

    @Override
    public String getThumbnailUrl() throws ParsingException {
        Element img = el.select("span[class*=\"yt-thumb-simple\"]").first()
                .select("img").first();

        String url = img.attr("abs:src");

        if (url.contains("gif")) {
            url = img.attr("abs:data-thumb");
        }
        return url;
    }

    @Override
    public String getName() throws ParsingException {
        return el.select("a[class*=\"yt-uix-tile-link\"]").first()
                .text();
    }

    @Override
    public String getUrl() throws ParsingException {
        return el.select("a[class*=\"yt-uix-tile-link\"]").first()
                .attr("abs:href");
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        Element subsEl = el.select("span[class*=\"yt-subscriber-count\"]").first();
        if (subsEl == null) {
            return 0;
        } else {
            return Long.parseLong(Utils.removeNonDigitCharacters(subsEl.text()));
        }
    }

    @Override
    public long getStreamCount() throws ParsingException {
        Element metaEl = el.select("ul[class*=\"yt-lockup-meta-info\"]").first();
        if (metaEl == null) {
            return 0;
        } else {
            return Long.parseLong(Utils.removeNonDigitCharacters(metaEl.text()));
        }
    }

    @Override
    public String getDescription() throws ParsingException {
        Element desEl = el.select("div[class*=\"yt-lockup-description\"]").first();
        if (desEl == null) {
            return "";
        } else {
            return desEl.text();
        }
    }
}
