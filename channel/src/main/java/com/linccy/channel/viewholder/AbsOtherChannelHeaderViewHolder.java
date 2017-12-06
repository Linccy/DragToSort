package com.linccy.channel.viewholder;

import android.view.View;

import com.linccy.channel.ChannelSelectBean;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public abstract class AbsOtherChannelHeaderViewHolder<T extends ChannelSelectBean> extends AbsChannelViewHolder<T> {
  public AbsOtherChannelHeaderViewHolder(View itemView) {
    super(itemView);
  }

  @Deprecated
  @Override
  final public void bindView(boolean state, T dataItem) {
//    throw new IllegalArgumentException("bindView 在HeaderViewHolder,  不能传data过来。 需要使用bindView(boolean state, int unSelectedCount, int selectedCount)");
  }

  protected abstract void bindView(boolean state, int unSelectedCount, int selectedCount);
}
