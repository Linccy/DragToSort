package com.linccy.channel;

/**
 * 需要使用ChannelSelectListDialog 的实体必须继承该接口
 *
 * {@link ChannelSelectListDialog}
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public interface ChannelSelectBean extends OrderSupport{

  void setCanMove(boolean canMove);

  /**
   * 判断是否可以移动
   */
  boolean canMove();

  /**
   * 得到名字
   */
  String getChannelSelectName();
}
