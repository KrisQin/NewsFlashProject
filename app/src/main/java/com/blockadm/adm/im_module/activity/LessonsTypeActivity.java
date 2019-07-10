package com.blockadm.adm.im_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blockadm.adm.R;
import com.blockadm.adm.im_module.adapter.LessonTypeAdapter;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.base.BaseTitleActivity;
import com.blockadm.common.bean.StudyTypeInfo;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.ComObserver;
import com.blockadm.common.utils.L;

import java.util.ArrayList;

/**
 * Created by Kris on 2019/6/5
 *
 * @Describe TODO {  }
 */
public class LessonsTypeActivity extends BaseTitleActivity {

    private RecyclerView mRecyclerView;
    ArrayList<StudyTypeInfo> mList = new ArrayList<>();
    private LessonTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomView(R.layout.activity_lesson_type);
        setTitle("分类");

        mRecyclerView = findContentViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager (this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new LessonTypeAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);//设置数据

        mAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("StudyTypeInfo",mList.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        queryStudyTypeList();
    }

    public void queryStudyTypeList() {
        showDefaultLoadingDialog();
        CommonModel.queryStudyTypeList("3", new ComObserver<ArrayList<StudyTypeInfo>>() {

            @Override
            public void onSuccess(BaseResponse<ArrayList<StudyTypeInfo>> t, String errorMsg) {
                dismissLoadingDialog();
                L.d("-------------- queryStudyTypeList  --> onSuccess code: " + t.getCode());
                if (t.getData() != null && t.getData().size() > 0) {
                    mList.addAll(t.getData());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
