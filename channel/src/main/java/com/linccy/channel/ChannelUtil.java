package com.linccy.channel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class ChannelUtil {

  /**
   * 过滤第二个flitList存在的target的item
   * @return 过滤后的flitList
   */
  public static <T extends ChannelSelectBean> List<T> flitUnSelectTag(List<T> target, List<T> flitList) {
    List<T> result = new ArrayList<>();

    for (T entity : flitList) {
      int times = 0;
      for (T selectItem : target) {
        if (selectItem.getChannelSelectName().equals(entity.getChannelSelectName())) {
          break;
        } else {
          times++;
        }
      }
      if (times == target.size()) {
        result.add(entity);
      }
    }
    return result;
  }
}
