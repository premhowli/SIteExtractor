package com.solevl.tunel.extractor;

import com.solevl.tunel.extractor.exceptions.ExtractionException;

import java.io.IOException;
import java.util.List;

/*
 * Created by Premangshu Howli on 28.09.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * SuggestionExtractor.java is part of NewPipe.
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

public abstract class SuggestionExtractor {

    private final int serviceId;

    public SuggestionExtractor(int serviceId) {
        this.serviceId = serviceId;
    }

    public abstract List<String> suggestionList(String query, String contentCountry) throws IOException, ExtractionException;

    public int getServiceId() {
        return serviceId;
    }
}
