package com.aitewei.manager.activity.ship;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitewei.manager.R;
import com.aitewei.manager.adapter.ShipUnloaderProgressListAdapter;
import com.aitewei.manager.base.BaseActivity;
import com.aitewei.manager.common.User;
import com.aitewei.manager.entity.GetUnloaderUnshipInfoEntity;
import com.aitewei.manager.retrofit.RetrofitFactory;
import com.aitewei.manager.rxjava.BaseObserver;
import com.aitewei.manager.rxjava.RxSchedulers;
import com.aitewei.manager.utils.LogUtil;
import com.aitewei.manager.utils.ToastUtils;
import com.aitewei.manager.utils.ToolBarUtil;
import com.aitewei.manager.view.CustomDatePicker;
import com.aitewei.manager.view.LoadGroupView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 以卸船机为主的总进度
 */
public class ShipUnloaderProgressActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_team)
    TextView tvTeam;
    @BindView(R.id.popup_container)
    LinearLayout popupContainer;

    @BindView(R.id.load_view)
    LoadGroupView loadView;
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private String taskId;
    private String shipName;
    private String currentDate;
    private String selectDate;//选择的日期
    private String selectTeam;//选择的班次

    private ShipUnloaderProgressListAdapter adapter;

    private String startTime = "";
    private String endTime = "";
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public static Intent getIntent(Context context, String taskId, String shipName) {
        Intent intent = new Intent(context, ShipUnloaderProgressActivity.class);
        intent.putExtra("taskId", taskId);
        intent.putExtra("shipName", shipName);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ship_unloader_progress;
    }

    @Override
    protected void initView() {
        ToolBarUtil.init(activity, "卸船机总览");

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        taskId = getIntent().getStringExtra("taskId");
        shipName = getIntent().getStringExtra("shipName");

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        decimalFormat = new DecimalFormat("0.0");
        initDatePicker();

        adapter = new ShipUnloaderProgressListAdapter(R.layout.layout_ship_unloader_progress_list_item, null);
        listView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<GetUnloaderUnshipInfoEntity.DataBean> list = adapter.getData();
                GetUnloaderUnshipInfoEntity.DataBean bean = list.get(position);
                String unloaderName = bean.getUnloaderName();
                if (!"合计".equals(unloaderName)) {
                    startActivity(ShipUnloaderDetailProgressActivity.getIntent(activity, taskId, shipName, bean.getUnloaderName()
                            , bean.getUnloaderId(), startTime, endTime));
                }
            }
        });

        loadView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        String params = "{\"taskId\":" + taskId + ",\"startTime\":\"" + startTime
                + "\",\"endTime\":\"" + endTime
                + "\",\"userId\":\"" + User.newInstance().getUserId() + "\"}";
        LogUtil.e("doGetUnloaderUnshipInfo json=" + params);
        RetrofitFactory.getInstance()
                .doGetUnloaderUnshipInfo(params)
                .compose(RxSchedulers.<GetUnloaderUnshipInfoEntity>compose())
                .subscribe(new BaseObserver<GetUnloaderUnshipInfoEntity>(compositeDisposable) {
                    @Override
                    protected void onHandleSuccess(GetUnloaderUnshipInfoEntity entity) {
                        if (refreshLayout == null) {
                            return;
                        }
                        refreshLayout.setRefreshing(false);
                        List<GetUnloaderUnshipInfoEntity.DataBean> list = entity.getData();
                        if (list != null && !list.isEmpty()) {
                            loadView.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            GetUnloaderUnshipInfoEntity.DataBean dataBean = new GetUnloaderUnshipInfoEntity.DataBean();
                            dataBean.setUnloaderName("合计");
                            for (GetUnloaderUnshipInfoEntity.DataBean bean : list) {
                                dataBean.setUsedTime(dataBean.getUsedTime() + bean.getUsedTime());
                                dataBean.setUnloading(dataBean.getUnloading() + bean.getUnloading());
                            }
                            dataBean.setUsedTime(Double.valueOf(decimalFormat.format(dataBean.getUsedTime())));
                            dataBean.setUnloading(Double.valueOf(decimalFormat.format(dataBean.getUnloading())));
                            double usedTime = dataBean.getUsedTime();
                            if (usedTime == 0) {
                                usedTime = 1;
                            }
                            dataBean.setEfficiency(dataBean.getUnloading() / usedTime);
                            double efficiency = dataBean.getEfficiency();
                            if (efficiency != 0) {
                                dataBean.setEfficiency(Double.valueOf(decimalFormat.format(efficiency)));
                            }
                            list.add(dataBean);
                            adapter.setNewData(list);
                        } else {
                            loadView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            loadView.setLoadType(LoadGroupView.TYPE_EMPTY);
                        }
                    }

                    @Override
                    protected void onHandleRequestError(String code, String msg) {
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                            loadView.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                            loadView.setLoadError(msg + "");
                        }
                    }
                });
    }

    @OnClick({R.id.btn_popup, R.id.tv_time, R.id.tv_team
            , R.id.btn_clear, R.id.btn_confirm, R.id.btn_empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_popup://显示筛选弹窗
                popupContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_time://选择日期
                String time = tvTime.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    time = currentDate;
                }
                datePicker.show(time);
                break;
            case R.id.tv_team://选择班次
                showTeamListDialog();
                break;
            case R.id.btn_clear://清空筛选
                tvTime.setText("");
                tvTeam.setText("");
                startTime = "";
                endTime = "";
                tvInfo.setText("全部");
                popupContainer.setVisibility(View.GONE);
                refreshLayout.setRefreshing(true);
                requestData();
                break;
            case R.id.btn_confirm://确认筛选
                time = tvTime.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    ToastUtils.show(activity, "请选择时间");
                    return;
                }
                if (TextUtils.isEmpty(selectTeam)) {
                    ToastUtils.show(activity, "请选择班次");
                    return;
                }
                tvInfo.setText(selectDate + "----" + selectTeam);
                popupContainer.setVisibility(View.GONE);
                if ("白班".equals(selectTeam)) {
                    startTime = selectDate + " 08:00:00";
                    endTime = selectDate + " 20:00:00";
                } else if ("夜班".equals(selectTeam)) {
                    startTime = selectDate + " 20:00:00";
                    try {
                        Date date = dateFormat.parse(selectDate);
                        date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
                        String secondDate = dateFormat.format(date);
                        endTime = secondDate + " 08:00:00";
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                refreshLayout.setRefreshing(true);
                requestData();
                break;
            case R.id.btn_empty://隐藏弹窗
                popupContainer.setVisibility(View.GONE);
                break;
        }
    }

    private CustomDatePicker datePicker;

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        currentDate = sdf.format(new Date());
        datePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                selectDate = time.split(" ")[0];
                tvTime.setText(selectDate);
            }
        }, "1970-01-01 00:00", currentDate); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        datePicker.showSpecificTime(false); // 不显示时和分
        datePicker.setIsLoop(false); // 不允许循环滚动
    }

    private String[] teams = new String[]{"白班", "夜班"};
    private AlertDialog teamDialog;

    private void showTeamListDialog() {
        if (teamDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setSingleChoiceItems(teams, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectTeam = teams[which] + "";
                    tvTeam.setText(selectTeam);
                    dialog.dismiss();
                }
            });
            teamDialog = builder.setTitle("请选择需要查询的班次")
                    .setIcon(R.mipmap.ic_launcher)
                    .create();
        }
        teamDialog.show();
    }

}
