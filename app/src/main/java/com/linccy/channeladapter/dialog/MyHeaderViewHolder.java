package com.linccy.channeladapter.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.viewholder.AbsMyChannelHeaderViewHolder;
import com.linccy.channeladapter.R;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class MyHeaderViewHolder<T extends ChannelSelectBean> extends AbsMyChannelHeaderViewHolder<T> implements View.OnClickListener {
  private TextView tvBtnEdit;

  public MyHeaderViewHolder(View itemView) {
    super(itemView);
    tvBtnEdit = (TextView) itemView.findViewById(R.id.tv_btn_edit);
    tvBtnEdit.setOnClickListener(this);
  }

  @Override
  public void bindView(boolean state, int unSelectedCount, int selectedCount) {
    if (state) {
      tvBtnEdit.setText(R.string.channel_save);
      tvBtnEdit.setActivated(true);
    } else {
      tvBtnEdit.setText(R.string.channel_save);
      tvBtnEdit.setActivated(false);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_btn_edit:
        if(getRootView().getParent() instanceof RecyclerView) {
          setModel(!tvBtnEdit.isActivated());
        }
        break;

      default:
        break;
    }
  }
}
