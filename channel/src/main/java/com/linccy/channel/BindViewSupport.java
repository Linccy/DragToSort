package com.linccy.channel;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public interface BindViewSupport<T extends ChannelSelectBean> {
  void bindView(boolean editState, T dataItem);
}
