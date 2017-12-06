package com.linccy.channel.viewholder;

import android.view.View;

import com.linccy.channel.CanMoveSupport;
import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.OnDragVHListener;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public abstract class AbsMyViewHolder<T extends ChannelSelectBean> extends AbsChannelViewHolder<T> implements OnDragVHListener, CanMoveSupport {
  public AbsMyViewHolder(View itemView) {
    super(itemView);
  }
}
