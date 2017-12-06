package com.linccy.channeladapter.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.viewholder.AbsMyViewHolder;
import com.linccy.channeladapter.R;


/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class MyViewHolder<T extends ChannelSelectBean> extends AbsMyViewHolder<T> {
  private TextView textView;
  private ImageView imgEdit;
  private View tagView;
  private boolean canMove = true;

  public MyViewHolder(View itemView) {
    super(itemView);
    tagView = itemView.findViewById(R.id.layout_tag);
    textView = (TextView) itemView.findViewById(R.id.tag_name);
    imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
  }

  /**
   * item 被选中时
   */
  @Override
  public void onItemSelected() {
//            textView.setBackgroundResource(R.drawable.bg_channel_p);
  }

  /**
   * item 取消选中时
   */
  @Override
  public void onItemFinish() {
//            textView.setBackgroundResource(R.drawable.bg_channel);
  }

  @Override
  public boolean isCanMove() {
    return canMove;
  }

  @Override
  public void setCanMove(boolean canMove) {
    this.canMove = canMove;
  }

  @Override
  public void bindView(boolean editState, ChannelSelectBean myTagItem) {
    float lastSize = textView.getTextSize();
    textView.setText(myTagItem.getChannelSelectName());
    if (!myTagItem.canMove()) {
      //不可移动的item
      imgEdit.setVisibility(View.GONE);
      canMove = false;
    } else {
      canMove = true;
      imgEdit.setVisibility(View.VISIBLE);
      imgEdit.setImageResource(R.drawable.news_item_remove_selector);
    }

    tagView.setLongClickable(canMove);

    float afterSize = textView.getTextSize();
//      float scale = afterSize / lastSize;
//      if (scale < 0.8f) {
//        imgEdit.setScaleX(scale);
//      }
//      imgEdit.setEnabled(isEditMode);

  }
}

