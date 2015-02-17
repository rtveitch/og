/*
 * Copyright (C) 2005-2015 Cleversafe, Inc. All rights reserved.
 * 
 * Contact Information: Cleversafe, Inc. 222 South Riverside Plaza Suite 1700 Chicago, IL 60606, USA
 * 
 * licensing@cleversafe.com
 */

package com.cleversafe.og.json.type;

import java.io.IOException;
import java.util.List;

import com.cleversafe.og.json.HostConfig;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class HostConfigListTypeAdapterFactory implements TypeAdapterFactory {
  private final TypeToken<List<HostConfig>> matchingType;

  public HostConfigListTypeAdapterFactory() {
    this.matchingType = new TypeToken<List<HostConfig>>() {};
  }

  @Override
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
    if (!this.matchingType.equals(type))
      return null;

    final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

    return new TypeAdapter<T>() {
      @Override
      public void write(final JsonWriter out, final T value) throws IOException {
        delegate.write(out, value);
      }

      @Override
      @SuppressWarnings("unchecked")
      public T read(final JsonReader in) throws IOException {
        if (JsonToken.STRING == in.peek()) {
          final List<HostConfig> host = Lists.newArrayList();
          host.add(new HostConfig(in.nextString()));
          return (T) host;
        }

        return delegate.read(in);
      }
    }.nullSafe();
  }
}
