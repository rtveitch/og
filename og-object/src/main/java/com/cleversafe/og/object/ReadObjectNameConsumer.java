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
// Date: Jun 29, 2014
// ---------------------

package com.cleversafe.og.object;

import java.util.List;

import com.cleversafe.og.util.Operation;

/**
 * A {@code ObjectNameConsumer} implementation which consumes object names for read operations
 * 
 * @since 1.0
 */
public class ReadObjectNameConsumer extends ObjectNameConsumer
{
   /**
    * Constructs an instance
    * 
    * @param objectManager
    *           the object manager for this instance to work with
    * @param statusCodes
    *           the status codes this instance should work with
    * @throws IllegalArgumentException
    *            if any status code in status codes is invalid
    */
   public ReadObjectNameConsumer(final ObjectManager objectManager, final List<Integer> statusCodes)
   {
      super(objectManager, Operation.READ, statusCodes);
   }

   @Override
   protected void updateObjectManager(final ObjectName objectName)
   {
      this.objectManager.releaseNameFromRead(objectName);
   }
}