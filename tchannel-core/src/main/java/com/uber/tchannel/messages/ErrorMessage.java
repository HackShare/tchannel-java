/*
 * Copyright (c) 2015 Uber Technologies, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.uber.tchannel.messages;

import com.uber.tchannel.errors.ErrorType;
import com.uber.tchannel.tracing.Trace;

public final class ErrorMessage implements Message {

    private final long id;
    private final ErrorType errorType;
    private final Trace tracing;
    private final String message;

    /**
     * Designated Constructor
     *
     * @param id        unique of the message
     * @param errorType the type of error this represents
     * @param tracing   tracing information
     * @param message   human readable string meant for logs
     */
    public ErrorMessage(long id, ErrorType errorType, Trace tracing, String message) {
        this.id = id;
        this.errorType = errorType;
        this.tracing = tracing;
        this.message = message;
    }

    public long getId() {
        return this.id;
    }

    public MessageType getMessageType() {
        return MessageType.Error;
    }

    public ErrorType getType() {
        return errorType;
    }

    public Trace getTracing() {
        return tracing;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s id=%d message=%s>",
                this.getClass().getSimpleName(),
                this.getId(),
                this.message
        );
    }

}
