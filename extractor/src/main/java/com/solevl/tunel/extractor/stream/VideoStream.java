package com.solevl.tunel.extractor.stream;

/*
 * Created by Premangshu Howli on 04.03.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * VideoStream.java is part of NewPipe.
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

import com.solevl.tunel.extractor.MediaFormat;

public class VideoStream extends Stream {
    public final String resolution;
    public final boolean isVideoOnly;


    public VideoStream(String url, MediaFormat format, String resolution) {
        this(url, format, resolution, false);
    }

    public VideoStream(String url, MediaFormat format, String resolution, boolean isVideoOnly) {
        super(url, format);
        this.resolution = resolution;
        this.isVideoOnly = isVideoOnly;
    }

    @Override
    public boolean equalStats(Stream cmp) {
        return super.equalStats(cmp) && cmp instanceof VideoStream &&
                resolution.equals(((VideoStream) cmp).resolution) &&
                isVideoOnly == ((VideoStream) cmp).isVideoOnly;
    }

    /**
     * Get the video resolution
     * @return the video resolution
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * Check if the video is video only.
     *
     * Video only streams have no audio
     * @return {@code true} if this stream is vid
     */
    public boolean isVideoOnly() {
        return isVideoOnly;
    }
}
