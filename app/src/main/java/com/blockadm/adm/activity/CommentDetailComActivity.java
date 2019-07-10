package com.blockadm.adm.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blockadm.adm.R;
import com.blockadm.adm.adapter.NewsArticleCommentReplyAdapter;
import com.blockadm.adm.contact.CommentDetailContract;
import com.blockadm.adm.interf.OnRecyclerviewItemClickListener;
import com.blockadm.adm.model.CommentDetailModel;
import com.blockadm.adm.model.CommonModel;
import com.blockadm.adm.persenter.CommentDetailPresenter;
import com.blockadm.common.base.BaseActivity;
import com.blockadm.common.base.BaseComActivity;
import com.blockadm.common.bean.CommentBeanDTO;
import com.blockadm.common.bean.CommentReplyListBean;
import com.blockadm.common.bean.LessonsCommentReplyPageParams;
import com.blockadm.common.bean.params.AddReplyBean;
import com.blockadm.common.bean.CommentDetailDto;
import com.blockadm.common.bean.PageNewsArticleCommentDTO;
import com.blockadm.common.bean.PageNewsArticleReportBean;
import com.blockadm.common.call.OnRecycleViewItemClickListener;
import com.blockadm.common.comstomview.BaseTitleBar;
import com.blockadm.common.comstomview.CheckEmptyTextView;
import com.blockadm.common.comstomview.CircleImageView;
import com.blockadm.common.comstomview.EmptyRecyclerView;
import com.blockadm.common.comstomview.swipetoloadlayout.OnLoadMoreListener;
import com.blockadm.common.comstomview.swipetoloadlayout.OnRefreshListener;
import com.blockadm.common.comstomview.swipetoloadlayout.SwipeToLoadLayout;
import com.blockadm.common.http.BaseResponse;
import com.blockadm.common.http.MyObserver;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.GsonUtil;
import com.blockadm.common.utils.ImageUtils;
import com.blockadm.common.utils.KeyboardUtils;
import com.blockadm.common.utils.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2019/1/17.
 */
public class CommentDetailComActivity extends BaseActivity<CommentDetailPresenter,
        CommentDetailModel> implements CommentDetailContract.View {

    @BindView(R.id.tilte)
    BaseTitleBar tilte;
    @BindView(R.id.civ_headimage)
    CircleImageView civHeadimage;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_zan)
    TextView tvZan;
    @BindView(R.id.tv_cre_time)
    TextView tvCreTime;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    @BindView(R.id.swipe_target)
    EmptyRecyclerView swipeTarget;
    @BindView(R.id.ivLoadMore)
    ImageView ivLoadMore;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_follow_up)
    TextView tvFollowUp;
    @BindView(R.id.et_say_content)
    EditText etSayContent;
    @BindView(R.id.tv_send)
    CheckEmptyTextView tvSend;
    private int pageSize = 20;
    private int pageNum = 1;
    private AddReplyBean addReplyBean;
    private CommentBeanDTO.RecordsBean recordsBean1;
    private PageNewsArticleCommentDTO.RecordsBean recordsBean;
    private int type;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comment_detail);
        ButterKnife.bind(this);
        Serializable serializable = getIntent().getSerializableExtra(ConstantUtils.RECORDSBEAN);
        if (serializable instanceof PageNewsArticleCommentDTO.RecordsBean) {
            recordsBean =
                    (PageNewsArticleCommentDTO.RecordsBean) getIntent().getSerializableExtra(ConstantUtils.RECORDSBEAN);
            type = 1;
        } else if (serializable instanceof CommentBeanDTO.RecordsBean) {
            recordsBean1 =
                    (CommentBeanDTO.RecordsBean) getIntent().getSerializableExtra(ConstantUtils.RECORDSBEAN);
            type = 2;
        }

        initView();
    }


    private void initView() {
        if (recordsBean != null) {
            mPersenter.pageNewsArticleCommentReply(GsonUtil.GsonString(new PageNewsArticleReportBean(recordsBean.getId(), recordsBean.getNewsArticleId(), pageNum, pageSize)));
            tvNickname.setText(recordsBean.getNickName());
            tvContent.setText(recordsBean.getContent());
            tvCreTime.setText(recordsBean.getCreateTime());
            ImageUtils.loadImageView(recordsBean.getIcon(), civHeadimage);
            tvSend.setCheckEmptyEditTexts(etSayContent);

            if (recordsBean.getIsZan() != 0) {
                Drawable dra = getResources().getDrawable(R.mipmap.news_reply_agree_press);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                tvZan.setCompoundDrawables(dra, null, null, null);
            } else {
                Drawable dra = getResources().getDrawable(R.mipmap.reply_agree_default);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                tvZan.setCompoundDrawables(dra, null, null, null);
            }

            tilte.setOnLeftOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageNum = 1;
                    records.clear();
                    mPersenter.pageNewsArticleCommentReply(GsonUtil.GsonString(new PageNewsArticleReportBean(recordsBean.getId(), recordsBean.getNewsArticleId(), pageNum, pageSize)));
                }
            });
            swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageNum++;
                    mPersenter.pageNewsArticleCommentReply(GsonUtil.GsonString(new PageNewsArticleReportBean(recordsBean.getId(), recordsBean.getNewsArticleId(), pageNum, pageSize)));
                }
            });

            tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (addReplyBean != null) {
                        String content = etSayContent.getText().toString().trim();
                        addReplyBean.setContent(content);
                        mPersenter.addReply(GsonUtil.GsonString(addReplyBean));
                    } else {
                        int newsArticleCommentId = recordsBean.getId();
                        int newsArticleId = recordsBean.getNewsArticleId();
                        AddReplyBean addReplyBean = new AddReplyBean(0, "", 0, 0, 0,
                                newsArticleCommentId, 0, newsArticleId);
                        String content = etSayContent.getText().toString().trim();
                        addReplyBean.setContent(content);
                        mPersenter.addReply(GsonUtil.GsonString(addReplyBean));
                    }
                }
            });

            tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordsBean.getIsZan() == 1) {
                        CommonModel.operateNewsLessonsCommentCount(0, recordsBean.getId(), 1,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(CommentDetailComActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(0);
                                    Drawable dra =
                                            getResources().getDrawable(R.mipmap.reply_agree_default);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    tvZan.setCompoundDrawables(dra, null, null, null);
                                }

                            }


                        });
                    } else {
                        CommonModel.operateNewsLessonsCommentCount(0, recordsBean.getId(), 0,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(CommentDetailComActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean.setIsZan(1);
                                    Drawable dra =
                                            getResources().getDrawable(R.mipmap.news_reply_agree_press);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    tvZan.setCompoundDrawables(dra, null, null, null);
                                }

                            }


                        });
                    }

                }
            });

        } else if (recordsBean1 != null) {
            String json =
                    GsonUtil.GsonString(new LessonsCommentReplyPageParams(recordsBean1.getId(),
                            pageNum, pageSize));
            CommonModel.newsLessonsCommentReplyPage(json, commentDetailDtoMyObserver);
            tvNickname.setText(recordsBean1.getNickName());
            tvContent.setText(recordsBean1.getContent());
            tvCreTime.setText(recordsBean1.getCreateTime());
            ImageUtils.loadImageView(recordsBean1.getIcon(), civHeadimage);
            tvSend.setCheckEmptyEditTexts(etSayContent);

            if (recordsBean1.getIsZan() != 0) {
                Drawable dra = getResources().getDrawable(R.mipmap.news_reply_agree_press);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                tvZan.setCompoundDrawables(dra, null, null, null);
            } else {
                Drawable dra = getResources().getDrawable(R.mipmap.reply_agree_default);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                tvZan.setCompoundDrawables(dra, null, null, null);
            }

            tilte.setOnLeftOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pageNum = 1;
                    records.clear();
                    String json =
                            GsonUtil.GsonString(new LessonsCommentReplyPageParams(recordsBean1.getId(), pageNum, pageSize));
                    CommonModel.newsLessonsCommentReplyPage(json, commentDetailDtoMyObserver);
                }
            });
            swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageNum++;
                    String json =
                            GsonUtil.GsonString(new LessonsCommentReplyPageParams(recordsBean1.getId(), pageNum, pageSize));
                    CommonModel.newsLessonsCommentReplyPage(json, commentDetailDtoMyObserver);
                }
            });

            tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sendContent = etSayContent.getText().toString().trim();
                    if (!TextUtils.isEmpty(sendContent)) {
                        addReplyBean = new AddReplyBean();
                        addReplyBean.setContent(sendContent);
                        addReplyBean.setCommentType(0);
                        addReplyBean.setParentId(0);
                        addReplyBean.setMasterMemberId(0);
                        addReplyBean.setNewsLessonsCommentId(recordsBean1.getId());
                        String json = GsonUtil.GsonString(addReplyBean);
                        CommonModel.addContentReply(json, new MyObserver<CommentReplyListBean>() {
                            @Override
                            public void onSuccess(BaseResponse<CommentReplyListBean> t) {
                                if (t.getData() == null) {
                                    return;
                                }
                                CommentReplyListBean commentReplyListBean = t.getData();
                                CommentDetailDto.RecordsBean recordsBean =
                                        new CommentDetailDto.RecordsBean();
                                recordsBean.setId(commentReplyListBean.getId());
                                recordsBean.setCommentType(commentReplyListBean.getCommentType());
                                recordsBean.setContent(commentReplyListBean.getContent());
                                recordsBean.setCreateTime(commentReplyListBean.getCreateTime());
                                recordsBean.setIcon(commentReplyListBean.getIcon());
                                recordsBean.setMasterIcon(commentReplyListBean.getMasterIcon());
                                recordsBean.setMasterMemberId(commentReplyListBean.getMasterMemberId());
                                recordsBean.setMasterNickName(commentReplyListBean.getMasterNickName());
                                recordsBean.setZanCount(commentReplyListBean.getZanCount());
                                recordsBean.setNewsArticleCommentId(commentReplyListBean.getNewsArticleCommentId());
                                recordsBean.setParentId(commentReplyListBean.getParentId());
                                recordsBean.setVipLevel(commentReplyListBean.getVipLevel());
                                recordsBean.setVipLevelName(commentReplyListBean.getVipLevelName());
                                recordsBean.setNickName(commentReplyListBean.getNickName());
                                etSayContent.setText("");
                                records.add(0, recordsBean);
                                newsArticlePageAdapter.notifyDataSetChanged();
                                if (newsArticlePageAdapter.getItemCount() == 0) {
                                    tvFollowUp.setVisibility(View.GONE);
                                } else {
                                    tvFollowUp.setVisibility(View.VISIBLE);
                                    if (recordsBean != null) {
                                        tvFollowUp.setText("跟帖(" + newsArticlePageAdapter.getItemCount() + ")");
                                    }
                                }
                                addReplyBean = null;
                                KeyboardUtils.hideSoftInput(CommentDetailComActivity.this);
                            }


                        });
                    }

                }
            });
            tvZan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recordsBean1.getIsZan() == 1) {
                        CommonModel.operateNewsLessonsCommentCount(0, recordsBean1.getId(), 0,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(CommentDetailComActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean1.setIsZan(0);
                                    Drawable dra =
                                            getResources().getDrawable(R.mipmap.reply_agree_default);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }


                        });
                    } else {
                        CommonModel.operateNewsLessonsCommentCount(0, recordsBean1.getId(), 1,
                                new MyObserver<String>() {
                            @Override
                            public void onSuccess(BaseResponse<String> t) {
                                Toast.makeText(CommentDetailComActivity.this, t.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                                if (t.getCode() == 0) {
                                    recordsBean1.setIsZan(1);
                                    Drawable dra =
                                            getResources().getDrawable(R.mipmap.news_reply_agree_press);
                                    dra.setBounds(0, 0, dra.getMinimumWidth(),
                                            dra.getMinimumHeight());
                                    tvZan.setCompoundDrawables(dra, null, null, null);
                                }
                            }


                        });
                    }

                }
            });

        }

        etSayContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    tvSend.setText("发送");
                    tvSend.setBackground(null);
                } else {
                    tvSend.setText("");
                    tvSend.setBackgroundResource(R.mipmap.talk);
                }

            }
        });

        setPageAdapter();
    }

    private NewsArticleCommentReplyAdapter newsArticlePageAdapter;
    private void setPageAdapter() {
        newsArticlePageAdapter = new NewsArticleCommentReplyAdapter(records, type);
        newsArticlePageAdapter.setOnItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                L.d("XTT", "onItemClickListener type: " + type);
                if (records != null && records.size() > 0) {
                    CommentDetailDto.RecordsBean recordsBean = records.get(position);
                    int id1 = recordsBean.getId();
                    int memberId = recordsBean.getMemberId();
                    int newsArticleId = recordsBean.getNewsArticleId();
                    int newsArticleCommentId = recordsBean.getNewsArticleCommentId();
                    addReplyBean = new AddReplyBean(1, "", 0, memberId, 0,
                            newsArticleCommentId, id1, newsArticleId);
                    KeyboardUtils.showSoftInput(etSayContent);

                }
            }
        });

        swipeTarget.setAdapter(newsArticlePageAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        swipeTarget.setLayoutManager(mLinearLayoutManager);
    }


    MyObserver<CommentDetailDto> commentDetailDtoMyObserver = new MyObserver<CommentDetailDto>() {
        @Override
        public void onSuccess(BaseResponse<CommentDetailDto> t) {
            setAdapter(t.getData());
        }
    };

    @Override
    public void pageNewsArticleCommentReply(CommentDetailDto commentDetailDto) {
        setAdapter(commentDetailDto);
    }

    private void setAdapter(CommentDetailDto commentDetailDto) {
        if (commentDetailDto == null) {
            return;
        }
        if (commentDetailDto.getTotal() == 0) {
            tvFollowUp.setVisibility(View.GONE);
        } else {
            tvFollowUp.setVisibility(View.VISIBLE);
            if (recordsBean != null) {
                tvFollowUp.setText("跟帖(" + recordsBean.getReplyCount() + ")");
            } else if (recordsBean1 != null) {
                tvFollowUp.setText("跟帖(" + recordsBean1.getReplyCount() + ")");
            }
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }


        if (pageNum == 1) {
            records.clear();
        }

        records.addAll(commentDetailDto.getRecords());
        newsArticlePageAdapter.notifyDataSetChanged();



        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
            if (newsArticlePageAdapter.getItemCount() >= pageSize) {
                mLinearLayoutManager.scrollToPositionWithOffset(newsArticlePageAdapter.getItemCount() - pageSize, 0);
            } else {
                swipeTarget.smoothScrollToPosition(0);
            }
            mLinearLayoutManager.setStackFromEnd(true);

            if (newsArticlePageAdapter.getItemCount() == commentDetailDto.getTotal()) {
                Toast.makeText(this, getString(R.string.no_data_load_more), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<CommentDetailDto.RecordsBean> records = new ArrayList<>();

    @Override
    public void addReply(CommentReplyListBean commentReplyListBean) {
        CommentDetailDto.RecordsBean recordsBean = new CommentDetailDto.RecordsBean();
        recordsBean.setId(commentReplyListBean.getId());
        recordsBean.setCommentType(commentReplyListBean.getCommentType());
        recordsBean.setContent(commentReplyListBean.getContent());
        recordsBean.setCreateTime(commentReplyListBean.getCreateTime());
        recordsBean.setIcon(commentReplyListBean.getIcon());
        recordsBean.setMasterIcon(commentReplyListBean.getMasterIcon());
        recordsBean.setMasterMemberId(commentReplyListBean.getMasterMemberId());
        recordsBean.setMasterNickName(commentReplyListBean.getMasterNickName());
        recordsBean.setZanCount(commentReplyListBean.getZanCount());
        recordsBean.setNewsArticleCommentId(commentReplyListBean.getNewsArticleCommentId());
        recordsBean.setParentId(commentReplyListBean.getParentId());
        recordsBean.setVipLevel(commentReplyListBean.getVipLevel());
        recordsBean.setVipLevelName(commentReplyListBean.getVipLevelName());
        recordsBean.setNickName(commentReplyListBean.getNickName());
        etSayContent.setText("");
        records.add(0, recordsBean);
        newsArticlePageAdapter.notifyDataSetChanged();
        if (newsArticlePageAdapter.getItemCount() == 0) {
            tvFollowUp.setVisibility(View.GONE);
        } else {
            tvFollowUp.setVisibility(View.VISIBLE);
            if (recordsBean != null) {
                tvFollowUp.setText("跟帖(" + newsArticlePageAdapter.getItemCount() + ")");
            }
        }
        addReplyBean = null;
        KeyboardUtils.hideSoftInput(this);
    }
}
