package com.linccy.channeladapter.dialog;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.viewholder.AbsOtherViewHolder;
import com.linccy.channeladapter.R;


/**
 * 其他频道
 */
public class OtherViewHolder<T extends ChannelSelectBean> extends AbsOtherViewHolder<T> {
  private ImageView addView;
  private TextView textView;

  public OtherViewHolder(View itemView) {
    super(itemView);
    addView = (ImageView) itemView.findViewById(R.id.img_edit);
    textView = (TextView) itemView.findViewById(R.id.tag_name);
  }

  @Override
  public void bindView(boolean isEditState, T dataItem) {
    textView.setText(dataItem.getChannelSelectName());
    addView.setImageResource(R.drawable.news_item_add_selector);
  }
}