package com.linccy.channeladapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.linccy.channel.ChannelSelectBean;
import com.linccy.channel.ChannelUtil;
import com.linccy.channel.OnSaveTagListener;
import com.linccy.channeladapter.dialog.ChannelSelectListDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lin.cx 957109587@qq.com
 * @version 3.0
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                showDialog();
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        OnSaveTagListener<ChannelEntity> onSaveTagListener = new OnSaveTagListener<ChannelEntity>() {
            @Override
            public void onSave(List<ChannelEntity> selectedTags, List<ChannelEntity> unSelectedTags) {
//                List<ChannelEntity> selectedTagList = new ArrayList<>();
//                for(ChannelSelectBean castBean: selectedTags) {
//                    selectedTagList.add((ChannelEntity) castBean);
//                }
//                selectedTagList = ChannelUtil.flitUnSelectTag(NewsCommon.getDefaultHotFlashNewsTags(),selectedTagList);
                StringBuilder result = new StringBuilder("选择了");
                for (ChannelSelectBean castBean : selectedTags) {
                    result.append(castBean.getChannelSelectName()).append(", ");
                }
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
            }
        };
        ChannelSelectListDialog<ChannelEntity> dialog = new ChannelSelectListDialog<>(this, MockData.SELECT_LIST, MockData.AIL_LIST);
        dialog.setOnSaveTagListener(onSaveTagListener);
        dialog.show();
    }
}
