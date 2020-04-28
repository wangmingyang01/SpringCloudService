/*
 * Copyright 2017 Artem Labazin <xxlabaza@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shls.feign.processor;

import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.ContentProcessor;
import feign.form.ContentType;
import feign.form.multipart.*;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static feign.form.ContentType.MULTIPART;

/**
 *
 * @author Artem Labazin <xxlabaza@gmail.com>
 */
public class MultipartAndUrlencodedFormContentProcessor implements ContentProcessor {

  List<Writer> writers;

  Writer defaultPerocessor;

  /**
   * Constructor with specific delegate encoder.
   *
   * @param delegate specific delegate encoder for cases, when this processor couldn't handle request parameter.
   */
  public MultipartAndUrlencodedFormContentProcessor(Encoder delegate) {
    writers = new ArrayList<Writer>(6);
    addWriter(new ByteArrayWriter());
    addWriter(new SingleFileWriter());
    addWriter(new ManyFilesWriter());
    addWriter(new ParameterWriter());
    addWriter(new SpringSingleMultipartFileWriter());
    addWriter(new SpringManyMultipartFilesWriter());

    defaultPerocessor = new DelegateWriter(delegate);
  }

  public void process (RequestTemplate template, Charset charset, Map<String, Object> filesData, Map<String, Object> paramsData) throws Exception {
    String boundary = Long.toHexString(System.currentTimeMillis());
    Output output = new Output(charset);

    //附件类
    if (filesData != null) {
      for (Map.Entry<String, Object> entry : filesData.entrySet()) {
        Writer writer = findApplicableWriter(entry.getValue());
        writer.write(output, boundary, entry.getKey(), entry.getValue());
      }
    }

    //参数
    if (paramsData != null) {
      for (Map.Entry<String, Object> entry : paramsData.entrySet()) {
        Writer writer = new ParameterWriter();
        writer.write(output, boundary, entry.getKey(), entry.getValue());
      }
    }

    output.write("--").write(boundary).write("--").write(CRLF);

    String contentTypeHeaderValue = new StringBuilder()
        .append("multipart/form-data")
        .append("; charset=").append(charset.name())
        .append("; boundary=").append(boundary)
        .toString();

    template.header(CONTENT_TYPE_HEADER, contentTypeHeaderValue);
    // Feign's clients try to determine binary/string content by charset presence
    // so, I set it to null (in spite of availability charset) for backward compatibility.
    template.body(output.toByteArray(), null);

    output.close();
  }

    @Override
    public void process(RequestTemplate template, Charset charset, Map<String, Object> data) throws Exception {

    }

    @Override
  public ContentType getSupportedContentType () {
    return MULTIPART;
  }

  /**
   * Adds {@link Writer} instance in runtime.
   *
   * @param writer additional writer.
   */
  public final void addWriter (Writer writer) {
    writers.add(writer);
  }

  public final List<Writer> getWriters () {
    return Collections.unmodifiableList(writers);
  }

  public final void setWriter (int index, Writer writer) {
    writers.set(index, writer);
  }

  private Writer findApplicableWriter (Object value) {
    for (Writer writer : writers) {
      if (writer.isApplicable(value)) {
        return writer;
      }
    }
    return defaultPerocessor;
  }
}
