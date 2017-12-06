package com.linccy.channel;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.linccy.channel.viewholder.AbsChannelViewHolder;
import com.linccy.channel.viewholder.AbsMyChannelHeaderViewHolder;
import com.linccy.channel.viewholder.AbsMyViewHolder;
import com.linccy.channel.viewholder.AbsOtherViewHolder;

/**
 * 拖拽排序 + 增删
 * Created by YoKeyword on 15/12/28.
 */
public abstract class AbsChannelAdapter<T extends ChannelSelectBean>
        extends RecyclerView.Adapter<AbsChannelViewHolder<T>>
        implements OnItemMoveListener, SetModeCallback {

  // 我的频道 标题部分
  private static final int TYPE_MY_CHANNEL_HEADER = 0;
  // 我的频道
  public static final int TYPE_MY = 1;
  // 其他频道 标题部分
  private static final int TYPE_OTHER_CHANNEL_HEADER = 2;
  // 其他频道
  public static final int TYPE_OTHER = 3;

  // 我的频道之前的header数量  该demo中 即标题部分 为 1
  private static final int COUNT_PRE_MY_HEADER = 1;
  // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
  private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

  private static final long ANIM_TIME = 360L;

  private LayoutInflater mInflater;
  private ItemTouchHelper mItemTouchHelper;

  // 是否为 编辑 模式
  private boolean isEditMode;
  private Set<OnSaveTagListener<T>> saveTagListenerSet;

  private List<T> mMyChannelItems, mOtherChannelItems;

  // 我的频道点击事件
  private OnMyChannelItemClickListener mChannelItemClickListener;

  public AbsChannelAdapter(Context context, ItemTouchHelper helper, List<T> mMyChannelItems, List<T> mOtherChannelItems) {
    this(context, helper, mMyChannelItems, mOtherChannelItems, false);
  }

  public AbsChannelAdapter(Context context, ItemTouchHelper helper, List<T> mMyChannelItems, List<T> mOtherChannelItems, boolean editOpen) {
    this.mInflater = LayoutInflater.from(context);
    this.mItemTouchHelper = helper;
    this.mMyChannelItems = mMyChannelItems;
    this.mOtherChannelItems = mOtherChannelItems;
    this.saveTagListenerSet = new HashSet<>();
    this.isEditMode = editOpen;
  }

    @Override
  public int getItemViewType(int position) {
    if (position == 0) {    // 我的频道 标题部分
      return TYPE_MY_CHANNEL_HEADER;
    } else if (position == mMyChannelItems.size() + 1) {    // 其他频道 标题部分
      return TYPE_OTHER_CHANNEL_HEADER;
    } else if (position > 0 && position < mMyChannelItems.size() + 1) {
      return TYPE_MY;
    } else {
      return TYPE_OTHER;
    }
  }

  @Override
  public AbsChannelViewHolder<T> onCreateViewHolder(final ViewGroup parent, int viewType) {
    final View view;
    switch (viewType) {
      case TYPE_MY_CHANNEL_HEADER:
        return createMyChannelHeader(parent);

      case TYPE_MY:
        return createMyChannel(parent);

      case TYPE_OTHER_CHANNEL_HEADER:
        return createOtherChannelHeader(parent);

      case TYPE_OTHER:
        return createOtherChannel(parent);

      default:
        return null;
    }
  }

  private AbsChannelViewHolder<T> createOtherChannelHeader(ViewGroup parent) {
    View view = mInflater.inflate(getOtherChannelHeaderLayout(), parent, false);
    return createOtherChannelHeaderViewHolder(view);
  }

  protected abstract AbsChannelViewHolder<T> createOtherChannelHeaderViewHolder(View view);

  protected abstract AbsMyChannelHeaderViewHolder<T> createMyChannelHeaderViewHolder(View view);

  private AbsMyChannelHeaderViewHolder<T> createMyChannelHeader(final ViewGroup parent) {
    View view = mInflater.inflate(getMyChannelHeaderLayout(), parent, false);
    AbsMyChannelHeaderViewHolder<T> myChannelHeaderViewHolder = createMyChannelHeaderViewHolder(view);
    myChannelHeaderViewHolder.setSetModeCallback(this);
    return myChannelHeaderViewHolder;
  }

  private AbsMyViewHolder<T> createMyChannel(final ViewGroup parent) {
    View view = mInflater.inflate(getMyChannelLayout(), parent, false);
    AbsMyViewHolder<T> myHolder = createMyChannelViewHolder(view);
    bindMyChannel(myHolder);
    return myHolder;
  }

  protected abstract AbsMyViewHolder<T> createMyChannelViewHolder(View view);

  private void bindMyChannel(final AbsMyViewHolder<T> myHolder) {
    myHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {
        int position = myHolder.getAdapterPosition();
        if (isEditMode) {
          ViewParent parent = myHolder.getRootView().getParent();
          RecyclerView recyclerView = ((RecyclerView) parent);
          View targetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
          View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
          // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
          // 如果在屏幕内,则添加一个位移动画
          if (recyclerView.indexOfChild(targetView) >= 0) {
            int targetX, targetY;

            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            int spanCount = ((GridLayoutManager) manager).getSpanCount();

            // 移动后 高度将变化 (我的频道Grid 最后一个item在新的一行第一个)
            if ((mMyChannelItems.size() - COUNT_PRE_MY_HEADER) % spanCount == 0) {
              View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER - 1);
              targetX = preTargetView.getLeft();
              targetY = preTargetView.getTop();
            } else {
              targetX = targetView.getLeft();
              targetY = targetView.getTop();
            }

            moveMyToOther(myHolder);
//            startAnimation(recyclerView, currentView, targetX, targetY);

          } else {
            moveMyToOther(myHolder);
          }
        } else {
          if (mChannelItemClickListener != null) {
            mChannelItemClickListener.onItemClick(v, position - COUNT_PRE_MY_HEADER);
          }
        }
      }
    });

    myHolder.getRootView().setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(final View v) {
        if (myHolder.isCanMove()) {
          mItemTouchHelper.startDrag(myHolder);
        }
        return true;
      }
    });
  }

  private void onBindOtherChannel(final AbsOtherViewHolder otherHolder) {
    otherHolder.getRootView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ViewParent parent = otherHolder.getRootView().getParent();
        if (!(parent instanceof RecyclerView)) {
          throw (new IllegalArgumentException("OtherItem getRootView is not rootView"));
        }
        RecyclerView recyclerView = (RecyclerView) parent;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        int currentPosition = otherHolder.getAdapterPosition();
        // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
        View currentView = manager.findViewByPosition(currentPosition);
        // 目标位置的前一个item  即当前MyChannel的最后一个
        View preTargetView = manager.findViewByPosition(mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);

        // 如果targetView不在屏幕内,则为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
        // 如果在屏幕内,则添加一个位移动画
        if (recyclerView.indexOfChild(preTargetView) >= 0) {
          int targetX = preTargetView.getLeft();
          int targetY = preTargetView.getTop();

          int targetPosition = mMyChannelItems.size() - 1 + COUNT_PRE_OTHER_HEADER;

          GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
          int spanCount = gridLayoutManager.getSpanCount();
          // target 在最后一行第一个
          if ((targetPosition - COUNT_PRE_MY_HEADER) % spanCount == 0) {
            View targetView = manager.findViewByPosition(targetPosition);
            targetX = targetView.getLeft();
            targetY = targetView.getTop();
          } else {
            targetX += preTargetView.getWidth();

            // 最后一个item可见
            if (gridLayoutManager.findLastVisibleItemPosition() == getItemCount() - 1) {
              // 最后的item在最后一行第一个位置
              if ((getItemCount() - 1 - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 + item.height
                int firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
                if (firstVisiblePosition == 0) {
                  // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕 , targetY值不需要变化
                  // // FirstCompletelyVisibleItemPosition != 0 即 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                  if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                    int offset = (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                    targetY += offset;
                  }
                } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的 position != 0),
                  // 移动后, targetY值  + 一个item的高度
                  targetY += preTargetView.getHeight();
                }
              }
            } else {
              System.out.println("current--No");
            }
          }

          // 如果当前位置是otherChannel可见的最后一个
          // 并且 当前位置不在grid的第一个位置
          // 并且 目标位置不在grid的第一个位置

          // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
          // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
          if (currentPosition == gridLayoutManager.findLastVisibleItemPosition()
                  && (currentPosition - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount != 0
                  && (targetPosition - COUNT_PRE_MY_HEADER) % spanCount != 0) {
            moveOtherToMyWithDelay(otherHolder);
          } else {
            moveOtherToMy(otherHolder);
          }
//          startAnimation(recyclerView, currentView, targetX, targetY);

        } else {
          moveOtherToMy(otherHolder);
        }
      }
    });
  }

  protected abstract AbsOtherViewHolder<T> createOtherViewHolder(View view);

  private AbsOtherViewHolder<T> createOtherChannel(final ViewGroup parent) {
    View view = mInflater.inflate(getOtherChannelLayout(), parent, false);
    AbsOtherViewHolder<T> otherViewHolder = createOtherViewHolder(view);
    onBindOtherChannel(otherViewHolder);
    return otherViewHolder;
  }

  @Override
  public void onBindViewHolder(AbsChannelViewHolder<T> holder, int position) {
    if (holder instanceof AbsMyViewHolder) {
      T myTagItem = mMyChannelItems.get(position - COUNT_PRE_MY_HEADER);
      holder.bindView(isEditMode, myTagItem);
    } else if (holder instanceof AbsOtherViewHolder) {
      T otherTagItem = mOtherChannelItems.get(position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER);
      holder.bindView(isEditMode, otherTagItem);
    } else if (holder instanceof AbsMyChannelHeaderViewHolder) {
      ((AbsMyChannelHeaderViewHolder) holder).bindView(isEditMode, mOtherChannelItems.size(), mMyChannelItems.size());
    }
  }

  @Override
  public int getItemCount() {
    // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
    return mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER;
  }

  /**
   * 开始增删动画
   */
  private void startAnimation(RecyclerView recyclerView, final View currentView, float targetX, float targetY) {
    final ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();
    final ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView);

    Animation animation = getTranslateAnimator(
            targetX - currentView.getLeft(), targetY - currentView.getTop());
    currentView.setVisibility(View.INVISIBLE);
    mirrorView.startAnimation(animation);

    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        viewGroup.removeView(mirrorView);
        if (currentView.getVisibility() == View.INVISIBLE) {
          currentView.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
  }

  /**
   * 我的频道 移动到 其他频道
   *
   * @param myHolder
   */
  private void moveMyToOther(AbsMyViewHolder myHolder) {
    int position = myHolder.getAdapterPosition();

    int startPosition = position - COUNT_PRE_MY_HEADER;
    if (startPosition > mMyChannelItems.size() - 1) {
      return;
    }
    T item = mMyChannelItems.get(startPosition);
    mMyChannelItems.remove(startPosition);
    mOtherChannelItems.add(0, item);

    notifyItemMoved(position, mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
  }

  /**
   * 其他频道 移动到 我的频道
   *
   * @param otherHolder
   */
  private void moveOtherToMy(AbsOtherViewHolder otherHolder) {
    int position = processItemRemoveAdd(otherHolder);
    if (position == -1) {
      return;
    }
    notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
  }

  /**
   * 其他频道 移动到 我的频道 伴随延迟
   *
   * @param otherHolder
   */
  private void moveOtherToMyWithDelay(final AbsOtherViewHolder otherHolder) {
    final int position = processItemRemoveAdd(otherHolder);
    if (position == -1) {
      return;
    }
    delayHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
      }
    }, ANIM_TIME);
  }

  private Handler delayHandler = new Handler();

  private int processItemRemoveAdd(AbsOtherViewHolder otherHolder) {
    int position = otherHolder.getAdapterPosition();

    int startPosition = position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER;
    if (startPosition > mOtherChannelItems.size() - 1) {
      return -1;
    }
    T item = mOtherChannelItems.get(startPosition);
    mOtherChannelItems.remove(startPosition);
    mMyChannelItems.add(item);
    return position;
  }


  /**
   * 添加需要移动的 镜像View
   */
  private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view) {
    view.destroyDrawingCache();
    view.setDrawingCacheEnabled(true);
    final ImageView mirrorView = new ImageView(recyclerView.getContext());
    Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
    mirrorView.setImageBitmap(bitmap);
    view.setDrawingCacheEnabled(false);
    int[] locations = new int[2];
    view.getLocationOnScreen(locations);
    int[] parenLocations = new int[2];
    recyclerView.getLocationOnScreen(parenLocations);
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
    params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
    parent.addView(mirrorView, params);

    return mirrorView;
  }

  @Override
  public void onItemMove(int fromPosition, int toPosition) {
    T fromItem = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
    T toItem = mMyChannelItems.get(toPosition - COUNT_PRE_MY_HEADER);

    if (!toItem.canMove()) {
      //禁止对固定标签进行排序
      return;
    }
    mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
    mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, fromItem);
    notifyItemMoved(fromPosition, toPosition);
  }

  public List<T> getSelectedItems() {
    return mMyChannelItems;
  }

  public List<T> getUnSelectedItems() {
    return mOtherChannelItems;
  }

  @Override
  public void setEditMode(boolean state) {
    if (isEditMode != state) {
      //编辑状态改变
      isEditMode = state;
      for (OnSaveTagListener<T> saveTagListener : saveTagListenerSet) {
        saveTagListener.onSave(getSelectedItems(), getUnSelectedItems());
      }
      notifyDataSetChanged();
    }
  }

  public void addSaveTagListenerSet(OnSaveTagListener<T> saveTagListener) {
    if (saveTagListener != null) {
      this.saveTagListenerSet.add(saveTagListener);
    }
  }

  public void removeSaveTagListener(OnSaveTagListener<T> saveTagListener) {
    if (saveTagListener != null) {
      this.saveTagListenerSet.remove(saveTagListener);
    }
  }

  public void setSaveTagListenerSet(Set<OnSaveTagListener<T>> saveTagListenerSet) {
    this.saveTagListenerSet = saveTagListenerSet;
  }

  /**
   * 获取位移动画
   */
  private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
    TranslateAnimation translateAnimation = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.ABSOLUTE, targetX,
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.ABSOLUTE, targetY);
    // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
    translateAnimation.setDuration(ANIM_TIME);
    translateAnimation.setFillAfter(true);
    return translateAnimation;
  }

  interface OnMyChannelItemClickListener {
    void onItemClick(View v, int position);
  }


  @LayoutRes
  protected abstract int getMyChannelHeaderLayout();

  @LayoutRes
  protected abstract int getMyChannelLayout();

  @LayoutRes
  protected abstract int getOtherChannelHeaderLayout();

  @LayoutRes
  protected abstract int getOtherChannelLayout();

  public void setOnMyChannelItemClickListener(OnMyChannelItemClickListener listener) {
    this.mChannelItemClickListener = listener;
  }

}
