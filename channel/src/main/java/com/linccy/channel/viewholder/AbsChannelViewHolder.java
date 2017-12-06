package com.linccy.channel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linccy.channel.BindViewSupport;
import com.linccy.channel.ChannelSelectBean;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public abstract class AbsChannelViewHolder<T extends ChannelSelectBean> extends RecyclerView.ViewHolder implements BindViewSupport<T> {
  private View rootView;
  public AbsChannelViewHolder(View itemView) {
    super(itemView);
    this.rootView = itemView;
  }

  public View getRootView() {
    return rootView;
  }
}
