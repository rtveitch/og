//
// Copyright (C) 2005-2011 Cleversafe, Inc. All rights reserved.
//
// Contact Information:
// Cleversafe, Inc.
// 222 South Riverside Plaza
// Suite 1700
// Chicago, IL 60606, USA
//
// licensing@cleversafe.com
//
// END-OF-HEADER
//
// -----------------------
// @author: rveitch
//
// Date: Jul 13, 2014
// ---------------------

package com.cleversafe.og.util.io;

import java.io.InputStream;

/**
 * An input stream which knows its size
 * 
 * @since 1.0
 */
public abstract class SizedInputStream extends InputStream {
  /**
   * Gets the total size of this input stream
   * 
   * @return the total size of this input stream
   */
  public abstract long getSize();
}
