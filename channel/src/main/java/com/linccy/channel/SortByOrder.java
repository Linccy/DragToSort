package com.linccy.channel;

import java.util.Comparator;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class SortByOrder implements Comparator<OrderSupport> {
  @Override
  public int compare(OrderSupport o1, OrderSupport o2) {
    return o1.getOrder() > o2.getOrder() ? 1 : -1;
  }
}
