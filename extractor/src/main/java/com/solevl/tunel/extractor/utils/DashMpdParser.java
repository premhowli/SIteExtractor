package com.solevl.tunel.extractor.utils;

import com.solevl.tunel.extractor.NewPipe;
import com.solevl.tunel.extractor.exceptions.ParsingException;
import com.solevl.tunel.extractor.stream.VideoStream;
import com.solevl.tunel.extractor.Downloader;
import com.solevl.tunel.extractor.MediaFormat;
import com.solevl.tunel.extractor.exceptions.ReCaptchaException;
import com.solevl.tunel.extractor.services.youtube.ItagItem;
import com.solevl.tunel.extractor.stream.AudioStream;
import com.solevl.tunel.extractor.stream.Stream;
import com.solevl.tunel.extractor.stream.StreamInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Created by Premangshu Howli on 02.02.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * DashMpdParser.java is part of NewPipe.
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

public class DashMpdParser {

    private DashMpdParser() {
    }

    public static class DashMpdParsingException extends ParsingException {
        DashMpdParsingException(String message, Exception e) {
            super(message, e);
        }
    }

    /**
     * Will try to download (using {@link StreamInfo#dashMpdUrl}) and parse the dash manifest,
     * then it will search for any stream that the ItagItem has (by the id).
     * <p>
     * It has video, video only and audio streams and will only add to the list if it don't
     * find a similar stream in the respective lists (calling {@link Stream#equalStats}).
     *
     * @param streamInfo where the parsed streams will be added
     */
    public static void getStreams(StreamInfo streamInfo) throws DashMpdParsingException, ReCaptchaException {
        String dashDoc;
        Downloader downloader = NewPipe.getDownloader();
        try {
            dashDoc = downloader.download(streamInfo.getDashMpdUrl());
        } catch (IOException ioe) {
            throw new DashMpdParsingException("Could not get dash mpd: " + streamInfo.getDashMpdUrl(), ioe);
        } catch (ReCaptchaException e) {
            throw new ReCaptchaException("reCaptcha Challenge needed");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(dashDoc.getBytes());

            Document doc = builder.parse(stream);
            NodeList representationList = doc.getElementsByTagName("Representation");

            for (int i = 0; i < representationList.getLength(); i++) {
                Element representation = ((Element) representationList.item(i));
                try {
                    String mimeType = ((Element) representation.getParentNode()).getAttribute("mimeType");
                    String id = representation.getAttribute("id");
                    String url = representation.getElementsByTagName("BaseURL").item(0).getTextContent();
                    ItagItem itag = ItagItem.getItag(Integer.parseInt(id));
                    if (itag != null) {
                        MediaFormat mediaFormat = MediaFormat.getFromMimeType(mimeType);

                        if (itag.itagType.equals(ItagItem.ItagType.AUDIO)) {
                            AudioStream audioStream = new AudioStream(url, mediaFormat, itag.avgBitrate);

                            if (!Stream.containSimilarStream(audioStream, streamInfo.getAudioStreams())) {
                                streamInfo.getAudioStreams().add(audioStream);
                            }
                        } else {
                            boolean isVideoOnly = itag.itagType.equals(ItagItem.ItagType.VIDEO_ONLY);
                            VideoStream videoStream = new VideoStream(url, mediaFormat, itag.resolutionString, isVideoOnly);

                            if (isVideoOnly) {
                                if (!Stream.containSimilarStream(videoStream, streamInfo.getVideoOnlyStreams())) {
                                    streamInfo.getVideoOnlyStreams().add(videoStream);
                                }
                            } else if (!Stream.containSimilarStream(videoStream, streamInfo.getVideoStreams())) {
                                streamInfo.getVideoStreams().add(videoStream);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            throw new DashMpdParsingException("Could not parse Dash mpd", e);
        }
    }
}
