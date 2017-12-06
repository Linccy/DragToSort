package com.linccy.channeladapter.dialog;

import android.view.View;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.viewholder.AbsOtherChannelHeaderViewHolder;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class OtherHeaderViewHolder<T extends ChannelSelectBean> extends AbsOtherChannelHeaderViewHolder<T> {

  public OtherHeaderViewHolder(View itemView) {
    super(itemView);
  }

  @Override
  protected void bindView(boolean state, int unSelectedCount, int selectedCount) {

  }
}
