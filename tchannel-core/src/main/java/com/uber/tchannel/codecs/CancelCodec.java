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

import com.uber.tchannel.frames.CancelFrame;
import com.uber.tchannel.frames.FrameType;
import com.uber.tchannel.tracing.Trace;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class CancelCodec {
    public static TFrame encode(ByteBufAllocator allocator, CancelFrame msg) {
        ByteBuf buffer = allocator.buffer();

        // ttl:4
        buffer.writeInt((int) msg.getTTL());

        // tracing:25
        CodecUtils.encodeTrace(msg.getTracing(), buffer);

        // why~2
        CodecUtils.encodeString(msg.getWhy(), buffer);

        TFrame frame = new TFrame(buffer.writerIndex(), FrameType.Cancel, msg.getId(), buffer);
        return frame;
    }

    public static CancelFrame decode(TFrame frame) {
        // ttl:4
        long ttl = frame.payload.readUnsignedInt();

        // tracing:25
        Trace tracing = CodecUtils.decodeTrace(frame.payload);

        // why~2
        String why = CodecUtils.decodeString(frame.payload);

        CancelFrame cancelFrame = new CancelFrame(frame.id, ttl, tracing, why);
        frame.release();
        return cancelFrame;
    }
}
