package com.linccy.channeladapter.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;

import com.linccy.channel.AbsChannelAdapter;
import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.ChannelUtil;
import com.linccy.channel.ItemDragHelperCallback;
import com.linccy.channel.OnSaveTagListener;
import com.linccy.channeladapter.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择排序标签
 * 实体必须实现接口{@link ChannelSelectBean}
 *
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class ChannelSelectListDialog<T extends ChannelSelectBean> extends BottomSheetDialog {

  private List<T> selectedChannelItems;
  private List<T> unSelectChannelItems;
  private ChannelAdapter<T> adapter;
  private OnSaveTagListener listener;
  private View rootView;

  public ChannelSelectListDialog(@NonNull Context context) {
    this(context, new ArrayList<T>(), new ArrayList<T>());
  }

  public ChannelSelectListDialog(@NonNull Context context, @NonNull List<T> selectedChannelItems, @NonNull List<T> allChannelItems) {
    super(context);
    this.selectedChannelItems = selectedChannelItems;
    this.unSelectChannelItems = ChannelUtil.flitUnSelectTag(selectedChannelItems, allChannelItems);
    initView();
  }

  private void initView() {
    rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_bottom_sheet_channel_select, null, false);
    setContentView(rootView);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    final int spanSize = 4;
    GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanSize);
    assert recyclerView != null;
    recyclerView.setLayoutManager(layoutManager);

    ItemDragHelperCallback callback = new ItemDragHelperCallback();
    final ItemTouchHelper helper = new ItemTouchHelper(callback);
    helper.attachToRecyclerView(recyclerView);

    adapter = new ChannelAdapter<>(getContext(), helper, selectedChannelItems, unSelectChannelItems);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        int viewType = adapter.getItemViewType(position);
        return viewType == AbsChannelAdapter.TYPE_MY || viewType == AbsChannelAdapter.TYPE_OTHER ? 1 : spanSize;
      }
    });
    recyclerView.setAdapter(adapter);

    adapter.addSaveTagListenerSet(listener);
  }

  @Override
  public void show() {
    super.show();
  }

  public void setOnSaveTagListener(OnSaveTagListener<T> listener) {
    this.listener = listener;
    if (adapter != null) {
      adapter.addSaveTagListenerSet(listener);
    }
  }

  public List<T> getSelectedChannelItems() {
    return selectedChannelItems;
  }

  public void setSelectedChannelItems(List<T> selectedChannelItems) {
    this.selectedChannelItems = selectedChannelItems;
  }

  public List<T> getUnSelectChannelItems() {
    return unSelectChannelItems;
  }

  public void setUnSelectChannelItems(List<T> unSelectChannelItems) {
    this.unSelectChannelItems = unSelectChannelItems;
  }
}
