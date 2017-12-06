package com.linccy.channeladapter.dialog;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.linccy.channel.AbsChannelAdapter;
import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.viewholder.AbsChannelViewHolder;
import com.linccy.channel.viewholder.AbsMyChannelHeaderViewHolder;
import com.linccy.channel.viewholder.AbsMyViewHolder;
import com.linccy.channeladapter.R;

import java.util.List;

/**
 * 拖拽排序 + 增删
 */
public class ChannelAdapter<T extends ChannelSelectBean> extends AbsChannelAdapter<T> {
  
  public ChannelAdapter(Context context, ItemTouchHelper helper, List<T> mMyChannelItems, List<T> mOtherChannelItems) {
    this(context, helper, mMyChannelItems, mOtherChannelItems, true);
  }

  public ChannelAdapter(Context context, ItemTouchHelper helper, List<T> mMyChannelItems, List<T> mOtherChannelItems, boolean editOpen) {
    super(context, helper, mMyChannelItems, mOtherChannelItems, editOpen);
  }

  @Override
  protected AbsChannelViewHolder<T> createOtherChannelHeaderViewHolder(View view) {
    return new OtherHeaderViewHolder<>(view);
  }

  @Override
  protected AbsMyChannelHeaderViewHolder<T> createMyChannelHeaderViewHolder(View view) {
    return new MyHeaderViewHolder<>(view);
  }

  @Override
  protected AbsMyViewHolder<T> createMyChannelViewHolder(View view) {
    return new MyViewHolder<>(view);
  }

  @Override
  protected OtherViewHolder<T> createOtherViewHolder(View view) {
    return new OtherViewHolder<>(view);
  }

  @Override
  protected int getMyChannelHeaderLayout() {
    return R.layout.item_news_tag_my_channel_header;
  }

  @Override
  protected int getMyChannelLayout() {
    return R.layout.item_news_tag_my;
  }

  @Override
  protected int getOtherChannelHeaderLayout() {
    return R.layout.item_news_tag_other_channel_header;
  }

  @Override
  protected int getOtherChannelLayout() {
    return R.layout.item_news_tag_other;
  }
}
