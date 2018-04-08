package com.aitewei.manager.activity.statistics;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.ShipListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.Constant;
import com.aitewei.manager.common.Popup;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.ShipListEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.CustomDatePicker;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择查看的船舶
 */
public class ShipListChoiceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_tool_bar_title)
    TextView tvToolBarTitle;
    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list_view)
    RecyclerView listView;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.popup_container)
    LinearLayout popupContainer;

    private String currentDate;
    private String defaultEndDate;
    private String selectStartTime = "";
    private String selectEndTime = "";

    private SimpleDateFormat sdf;

    private ShipListAdapter adapter;

    private int type;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, ShipListChoiceActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_list_choice;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "选择查询的船舶");
        tvToolBarTitle.setText("最近一周");

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(this);

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        initDatePicker();

        long startTime = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000;
        selectStartTime = sdf.format(new Date(startTime)).split(" ")[0];
        selectEndTime = currentDate.split(" ")[0];
    }

    private CustomDatePicker startDatePicker;//开始时间选择器
    private CustomDatePicker endDatePicker;//结束时间选择器

    private void initDatePicker() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        final Date date = new Date();
        currentDate = sdf.format(date);

        startDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String selectDate = time.split(" ")[0];
                tvStartTime.setText(selectDate);
                tvEndTime.setText("");
                try {
                    long startTime = sdf.parse(time).getTime();
                    long endTime = startTime + 20 * 24 * 60 * 60 * 1000;
                    if (date.getTime() < endTime) {
                        defaultEndDate = currentDate;
                    } else {
                        defaultEndDate = sdf.format(endTime);
                    }
                    //初始化结束时间选择器
                    endDatePicker = new CustomDatePicker(activity, new CustomDatePicker.ResultHandler() {
                        @Override
                        public void handle(String time) { // 回调接口，获得选中的时间
                            String selectDate = time.split(" ")[0];
                            tvEndTime.setText(selectDate);
                        }
                    }, time, defaultEndDate); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
                    endDatePicker.showSpecificTime(false); // 不显示时和分
                    endDatePicker.setIsLoop(false); // 不允许循环滚动
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, "1970-01-01 00:00", currentDate); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        startDatePicker.showSpecificTime(false); // 不显示时和分
        startDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", -1);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);

        adapter = new ShipListAdapter(R.layout.layout_ship_list_item, null);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ShipListEntity.DataBean> dataList = adapter.getData();
                ShipListEntity.DataBean bean = dataList.get(position);
                String taskId = bean.getId();
                String shipName = bean.getShipName();
                switch (type) {
                    case Constant.TYPE_STATISTICS_CARGO_PROGRESS://货物统计
                        startActivity(CargoProgressStatisticsActivity.getIntent(activity,
                                Constant.TYPE_PROGRESS, taskId, shipName));
                        break;
                    case Constant.TYPE_STATISTICS_CARGO_EFFICAIENCY://货物效率统计
                        startActivity(CargoProgressStatisticsActivity.getIntent(activity,
                                Constant.TYPE_EFFICAIENCY, taskId, shipName));
                        break;
                    case Constant.TYPE_STATISTICS_UNLOADER_PROGRESS://卸船机作业量统计
                        startActivity(UnloaderStatisticsActivity.getIntent(activity,
                                taskId, shipName, UnloaderStatisticsActivity.TYPE_ALL));
                        break;
                    case Constant.TYPE_STATISTICS_UNLOADER_TEAM_PROGRESS://卸船机班次统计
                        Intent intent = new Intent();
                        intent.putExtra("taskId", taskId);
                        intent.putExtra("shipName", shipName);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case Constant.TYPE_STATISTICS_CABIN_PROGRESS://舱口统计
                        startActivity(CabinStatisticsActivity.getIntent(activity,
                                Constant.TYPE_PROGRESS, taskId, shipName));
                        break;
                    case Constant.TYPE_STATISTICS_CABIN_EFFICAIENCY://舱口效率统计
                        startActivity(CabinStatisticsActivity.getIntent(activity,
                                Constant.TYPE_EFFICAIENCY, taskId, shipName));
                        break;
                }
                finish();
            }
        });
        listView.setAdapter(adapter);

        requestListData();
    }

    private void requestListData() {
        int status = 1;//0|预靠船舶，1|作业船舶，2|离港船舶，3|所有船舶
        String params = "{\"shipStatus\":" + status + ",\"userId\":\"" + User.newInstance().getUserId() +
                "\",\"startTime\":\"" + selectStartTime + "\",\"endTime\":\"" + selectEndTime + "\"}";
        LogUtil.e("params=" + params);
        RetrofitFactory.getInstance()
                .doGetShipList(params)
                .compose(RxSchedulers.<ShipListEntity>compose())
                .subscribe(new BaseObserver<ShipListEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(ShipListEntity entity) {
                        errorCount = 0;
                        List<ShipListEntity.DataBean> beanList = entity.getData();
                        bindAdapter(beanList);
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        if (errorCount >= 3) {
                            if (refreshLayout != null) {
                                refreshLayout.setRefreshing(false);
                            }
                            listView.setVisibility(View.GONE);
                            loadView.setVisibility(View.VISIBLE);
                            loadView.setLoadError(msg + "");
                        } else {
                            errorCount++;
                            requestListData();
                        }
                    }
                });
    }

    private int errorCount;

    private void bindAdapter(List<ShipListEntity.DataBean> dataList) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
        adapter.loadMoreComplete();
        if (dataList != null && !dataList.isEmpty()) {
            loadView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter.setNewData(dataList);
        } else {
            adapter.loadMoreEnd();
            listView.setVisibility(View.GONE);
            loadView.setVisibility(View.VISIBLE);
            loadView.setLoadType(LoadGroupView.TYPE_EMPTY);
        }
    }

    @Override
    public void onRefresh() {
        requestListData();
    }

    @OnClick({R.id.btn_popup, R.id.tv_start_time, R.id.tv_end_time
            , R.id.btn_clear, R.id.btn_confirm, R.id.btn_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_popup://显示筛选弹窗
                showMenuPopup();
                break;
            case R.id.tv_start_time://选择开始日期
                String startTime = tvStartTime.getText().toString();
                if (TextUtils.isEmpty(startTime)) {
                    startTime = currentDate;
                }
                startDatePicker.show(startTime);
                break;
            case R.id.tv_end_time://选择结束日期
                startTime = tvStartTime.getText().toString();
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtils.show(activity, "请先选择开始日期");
                    return;
                }
                String endTime = tvEndTime.getText().toString();
                if (TextUtils.isEmpty(endTime)) {
                    endTime = defaultEndDate;
                }
                endDatePicker.show(endTime);
                break;
            case R.id.btn_clear://清空筛选
                tvStartTime.setText("");
                tvEndTime.setText("");
                if (!TextUtils.isEmpty(selectStartTime)
                        || !TextUtils.isEmpty(selectEndTime)) {
                    selectStartTime = "";
                    selectEndTime = "";
                    refreshLayout.setRefreshing(true);
                    requestListData();
                }
                popupContainer.setVisibility(View.GONE);
                break;
            case R.id.btn_confirm://确认筛选
                startTime = tvStartTime.getText().toString();
                if (TextUtils.isEmpty(startTime)) {
                    ToastUtils.show(activity, "请选择开始时间");
                    return;
                }
                endTime = tvEndTime.getText().toString();
                if (TextUtils.isEmpty(endTime)) {
                    ToastUtils.show(activity, "请选择结束时间");
                    return;
                }
                selectStartTime = startTime;
                selectEndTime = endTime;
                tvToolBarTitle.setText(selectStartTime + "/" + selectEndTime);
                popupContainer.setVisibility(View.GONE);
                refreshLayout.setRefreshing(true);
                requestListData();
                break;
            case R.id.btn_empty://隐藏弹窗
                popupContainer.setVisibility(View.GONE);
                break;
        }
    }

    private Popup menuPopup;

    /**
     * 菜单弹窗
     */
    private void showMenuPopup() {
        if (menuPopup == null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_ship_list_menu, null);
            view.findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //最近一周
                    menuPopup.dismiss();
                    tvToolBarTitle.setText("最近一周");
                    long startTime = System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000;
                    selectStartTime = sdf.format(new Date(startTime)).split(" ")[0];
                    selectEndTime = currentDate.split(" ")[0];
                    refreshLayout.setRefreshing(true);
                    requestListData();
                }
            });
            view.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //最近两周
                    menuPopup.dismiss();
                    tvToolBarTitle.setText("最近两周");
                    long startTime = System.currentTimeMillis() - 13 * 24 * 60 * 60 * 1000;
                    selectStartTime = sdf.format(new Date(startTime)).split(" ")[0];
                    selectEndTime = currentDate.split(" ")[0];
                    refreshLayout.setRefreshing(true);
                    requestListData();
                }
            });
            view.findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择时间
                    menuPopup.dismiss();
                    popupContainer.setVisibility(View.VISIBLE);
                }
            });
            view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuPopup.dismiss();
                }
            });
            menuPopup = new Popup.Builder()
                    .setLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setmContentView(view)
                    .setmBackgroundAlpha(activity, 0.5f)
                    .build();
        }
        menuPopup.showAtLocation(activity, findViewById(R.id.parent_layout), Gravity.BOTTOM, 0, 0);
    }
}
