//package com.linccy.channeladapter
//
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import com.linccy.channel.ChannelUtil
//import com.linccy.channel.OnSaveTagListener
//import com.linccy.channeladapter.dialog.ChannelSelectListDialog
//import java.util.ArrayList
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        findViewById<View>(R.id.btn_1).setOnClickListener{
//            new ChannelSelectListDialog<>(v.getContext(), selectedChannelItems, allItems);
//            OnSaveTagListener<HotFlashNewsTagEntity> onSaveTagListener = new OnSaveTagListener<HotFlashNewsTagEntity>() {
//                @Override
//                public void onSave(List<HotFlashNewsTagEntity> selectedTags, List<HotFlashNewsTagEntity> unSelectedTags) {
//                    List<HotFlashNewsTagEntity> selectedTagList = new ArrayList<>();
//                    for(ChannelSelectBean castBean: selectedTags) {
//                    selectedTagList.add((HotFlashNewsTagEntity) castBean);
//                }
//                    selectedTagList = HotNewsTagTools.flitUnSelectNewsTag(NewsCommon.getDefaultHotFlashNewsTags(),selectedTagList);
//                    channelSelectListDialog.dismiss();
//                    List<HotFlashNewsTagEntity> newsTagEntityList = NewsCommon.getDefaultHotFlashNewsTags();
//                    newsTagEntityList.addAll(selectedTagList);
//                    EventBus.getDefault().post(new HotFlashNewsTagChangeEvent(newsTagEntityList));
//                    HotNewsTagTools.writeHotNewsTagPreference(context, selectedTagList);
//                }
//            };
//            channelSelectListDialog.setOnSaveTagListener(onSaveTagListener);
//        }
//        if(channelSelectListDialog.isShowing()) {
//            channelSelectListDialog.dismiss();
//        } else {
//            channelSelectListDialog.show();
//        }
//        }
////        findViewById<View>(R.id.btn_2).setOnClickListener(this)
////        findViewById<View>(R.id.btn_3).setOnClickListener(this)
//    }
//
//
//}
