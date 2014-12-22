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
// Date: Jul 2, 2014
// ---------------------

package com.cleversafe.og.json;

public class ObjectManagerConfig {
  String objectFileLocation;
  String objectFileName;

  public ObjectManagerConfig() {
    this.objectFileLocation = "./object";
    this.objectFileName = null;
  }

  public String getObjectFileLocation() {
    return this.objectFileLocation;
  }

  public String getObjectFileName() {
    return this.objectFileName;
  }
}
