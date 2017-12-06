package com.linccy.channel.viewholder;

import android.view.View;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.SetModeCallback;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public abstract class AbsMyChannelHeaderViewHolder <T extends ChannelSelectBean> extends AbsChannelViewHolder<T> {
  private SetModeCallback setModeCallback;

  public AbsMyChannelHeaderViewHolder(View itemView) {
    super(itemView);
  }

  @Deprecated
  @Override
  final public void bindView(boolean editState, T dataItem) {
//    throw new IllegalArgumentException("bindView 在HeaderViewHolder,  不能传data过来。 需要使用bindView(boolean state, int unSelectedCount, int selectedCount)");
  }

  public abstract void bindView(boolean state, int unSelectedCount, int selectedCount);

  public final void setModel(boolean state) {
    if(setModeCallback != null) {
      setModeCallback.setEditMode(state);
    }
  }

  public final void setSetModeCallback(SetModeCallback setModeCallback) {
    this.setModeCallback = setModeCallback;
  }
}
