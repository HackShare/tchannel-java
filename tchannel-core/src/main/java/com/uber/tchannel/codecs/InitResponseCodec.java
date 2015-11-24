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
package com.uber.tchannel.codecs;

import com.uber.tchannel.frames.InitResponseFrame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Map;

public final class InitResponseCodec {

    public static TFrame encode(ByteBufAllocator allocator, InitResponseFrame msg) {
        // Allocate new ByteBuf
        ByteBuf buffer = allocator.buffer();

        // version:2
        buffer.writeShort(msg.getVersion());

        // headers -> nh:2 (key~2 value~2){nh}
        CodecUtils.encodeHeaders(msg.getHeaders(), buffer);

        TFrame frame = new TFrame(buffer.writerIndex(), msg.getType(), msg.getId(), buffer);
        return frame;
    }

    public static InitResponseFrame decode(TFrame frame) {
        // version:2
        int version = frame.payload.readUnsignedShort();

        // headers -> nh:2 (key~2 value~2){nh}
        Map<String, String> headers = CodecUtils.decodeHeaders(frame.payload);

        InitResponseFrame initResponseFrame = new InitResponseFrame(frame.id, version, headers);
        return initResponseFrame;
    }

}
