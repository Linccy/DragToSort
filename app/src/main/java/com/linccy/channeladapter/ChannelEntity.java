package com.linccy.channeladapter;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.OrderSupport;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class ChannelEntity implements ChannelSelectBean, OrderSupport {


    private String tagName;
    private boolean canMove;
    private int order;

    public ChannelEntity(String tagName, boolean canMove, int order) {
        this.tagName = tagName;
        this.canMove = canMove;
        this.order = order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    @Override
    public boolean canMove() {
        return canMove;
    }

    @Override
    public String getChannelSelectName() {
        return tagName;
    }
}
