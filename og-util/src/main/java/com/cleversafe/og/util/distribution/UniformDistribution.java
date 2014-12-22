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
// Date: Oct 23, 2013
// ---------------------

package com.cleversafe.og.util.distribution;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Random;

/**
 * A distribution implementation that returns values conforming to a uniform distribution.
 * 
 * @since 1.0
 */
public class UniformDistribution extends AbstractDistribution {
  private final double min;

  /**
   * Constructs a uniform distribution instance, using the provided random instance for random seed
   * data
   * 
   * @param average the average value of this distribution. Spread is defined as distance between
   *        min and max
   * @param spread the spread of this distribution
   * @throws IllegalArgumentException if average is negative
   * @throws IllegalArgumentException if spread is negative
   */
  public UniformDistribution(final double average, final double spread, final Random random) {
    super(average, spread, random);
    this.min = average - spread;
    checkArgument(this.min >= 0.0, "min must be >= 0.0 [%s]", this.min);
  }

  @Override
  public double nextSample() {
    return this.min + (2 * this.spread * this.random.nextDouble());
  }

  @Override
  public String toString() {
    return "UniformDistribution [average=" + this.average + ", spread=" + this.spread + "]";
  }
}
