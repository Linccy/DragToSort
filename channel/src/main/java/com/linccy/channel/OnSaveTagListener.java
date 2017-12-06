package com.linccy.channel;

import java.util.List;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public interface OnSaveTagListener<T extends ChannelSelectBean> {
  void onSave(List<T> selectedTags, List<T> unSelectTags);
}
