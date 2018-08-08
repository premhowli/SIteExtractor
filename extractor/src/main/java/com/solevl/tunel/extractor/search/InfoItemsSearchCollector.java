package com.solevl.tunel.extractor.search;

import com.solevl.tunel.extractor.InfoItemExtractor;
import com.solevl.tunel.extractor.InfoItemsCollector;
import com.solevl.tunel.extractor.channel.ChannelInfoItemsCollector;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.playlist.PlaylistInfoItemExtractor;
import com.solevl.tunel.extractor.InfoItem;
import com.solevl.tunel.extractor.channel.ChannelInfoItemExtractor;
import com.solevl.tunel.extractor.playlist.PlaylistInfoItemsCollector;
import com.solevl.tunel.extractor.stream.StreamInfoItemExtractor;
import com.solevl.tunel.extractor.stream.StreamInfoItemsCollector;

/*
 * Created by Premangshu Howli on 12.02.17.
 *
 * Copyright (C) Premangshu Howli 2017 <hsolevl@gmail.com>
 * InfoItemsSearchCollector.java is part of NewPipe.
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

/**
 * Collector for search results
 *
 * This collector can handle the following extractor types:
 * <ul>
 *     <li>{@link StreamInfoItemExtractor}</li>
 *     <li>{@link ChannelInfoItemExtractor}</li>
 *     <li>{@link PlaylistInfoItemExtractor}</li>
 * </ul>
 * Calling {@link #extract(InfoItemExtractor)} or {@link #commit(Object)} with any
 * other extractor type will raise an exception.
 */
public class InfoItemsSearchCollector extends InfoItemsCollector<InfoItem, InfoItemExtractor> {
    private final StreamInfoItemsCollector streamCollector;
    private final ChannelInfoItemsCollector userCollector;
    private final PlaylistInfoItemsCollector playlistCollector;

    InfoItemsSearchCollector(int serviceId) {
        super(serviceId);
        streamCollector = new StreamInfoItemsCollector(serviceId);
        userCollector = new ChannelInfoItemsCollector(serviceId);
        playlistCollector = new PlaylistInfoItemsCollector(serviceId);
    }

    @Override
    public InfoItem extract(InfoItemExtractor extractor) throws ParsingException {
        // Use the corresponding collector for each item extractor type
        if(extractor instanceof StreamInfoItemExtractor) {
            return streamCollector.extract((StreamInfoItemExtractor) extractor);
        } else if(extractor instanceof ChannelInfoItemExtractor) {
            return userCollector.extract((ChannelInfoItemExtractor) extractor);
        } else if(extractor instanceof PlaylistInfoItemExtractor) {
            return playlistCollector.extract((PlaylistInfoItemExtractor) extractor);
        } else {
            throw new IllegalArgumentException("Invalid extractor type: " + extractor);
        }
    }
}
