package com.linccy.channeladapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class MockData {

    public static List<ChannelEntity> AIL_LIST = new ArrayList<>(Arrays.asList(new ChannelEntity[]{
            new ChannelEntity("推荐", false, 0),
            new ChannelEntity("热点" , true, 0),
            new ChannelEntity("科技" , true, 0),
            new ChannelEntity("汽车" , true, 0),
            new ChannelEntity("视频" , true, 0),
            new ChannelEntity("图片" , true, 0),
            new ChannelEntity("社会" , true, 0),
            new ChannelEntity("美食" , true, 0),
            new ChannelEntity("财经" , true, 0),
            new ChannelEntity("历史", false, 0),
            new ChannelEntity("情感" , true, 0),
            new ChannelEntity("房产" , true, 0),
            new ChannelEntity("文化" , true, 0),
            new ChannelEntity("科学" , true, 0),
            new ChannelEntity("故事" , true, 0),
            new ChannelEntity("游戏" , true, 0),
            new ChannelEntity("收藏" , true, 0),
            new ChannelEntity("精选" , true, 0)
    }));

    public static List<ChannelEntity> SELECT_LIST = new ArrayList<>(Arrays.asList(new ChannelEntity[]{
            new ChannelEntity("历史", false, 0),
            new ChannelEntity("情感" , true, 0),
            new ChannelEntity("房产" , true, 0),
            new ChannelEntity("文化" , true, 0),
            new ChannelEntity("科学" , true, 0),
            new ChannelEntity("故事" , true, 0),
            new ChannelEntity("游戏" , true, 0),
            new ChannelEntity("收藏" , true, 0),
            new ChannelEntity("精选" , true, 0)
    }));
}
