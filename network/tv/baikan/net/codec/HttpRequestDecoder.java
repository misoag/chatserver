/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package tv.baikan.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import tv.baikan.net.http.HttpRequest;

/**
 * @Description:
 * @Author:HuangShuzhen
 * @Date 2013-4-19
 * @Version V1.0.0
 */
class HttpRequestDecoder implements ProtocolDecoder {

        @Override
        public void decode(IoSession session, IoBuffer inBuffer, ProtocolDecoderOutput out) throws Exception {
                byte[] data = new byte[inBuffer.limit()];
                inBuffer.get(data);
                // Decode To HttpRequest
                // long current = System.currentTimeMillis();
                HttpRequest request = new HttpRequest(data);
                // System.out.println("Cost:"+(System.currentTimeMillis()-current));
                out.write(request);
        }

        @Override
        public void dispose(IoSession arg0) throws Exception {

        }

        @Override
        public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {

        }

}
