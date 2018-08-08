package com.solevl.tunel.extractor.exceptions;

/*
 * Created by Premangshu Howli on 12.09.16.
 *
 * Copyright (C) Premangshu Howli 2016 <hsolevl@gmail.com>
 * FoundAdException.java is part of NewPipe.
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

public class FoundAdException extends ParsingException {
    public FoundAdException(String message) {
        super(message);
    }

    public FoundAdException(String message, Throwable cause) {
        super(message, cause);
    }
}
