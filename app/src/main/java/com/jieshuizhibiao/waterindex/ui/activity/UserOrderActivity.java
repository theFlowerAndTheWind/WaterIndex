package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
import com.jieshuizhibiao.waterindex.beans.TradeIndex;
import com.jieshuizhibiao.waterindex.contract.model.UserOrderModel;
import com.jieshuizhibiao.waterindex.contract.presenter.SysConfigPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UserOrderPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.SecondRequstViewImpl;
import com.jieshuizhibiao.waterindex.event.TradeIndexRefreshEvent;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

public class UserOrderActivity extends BaseActivity implements
        CommonViewImpl,
        SecondRequstViewImpl,
        TextWatcher {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_pay_min)
    TextView tvPayMin;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.img_pay_type_bank_card)
    ImageView imgPayTypeBankCard;
    @BindView(R.id.img_pay_type_alipay)
    ImageView imgPayTypeAlipay;
    @BindView(R.id.img_pay_type_wechat)
    ImageView imgPayTypeWechat;
    @BindView(R.id.edt_total)
    EditText edtTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_pay_timeout)
    TextView tvPayTimeout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.trans_container)
    FrameLayout transContainer;

    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;

    Unbinder unbinder;
    private TradeIndex tradeIndex;
    private String type;
    private String typeStr;
    private String trade_id;
    private String total;
    private UserOrderPresenter userOrderPresenter;
    private SysConfigPresenter sysConfigPresenter;
    private float ds_price = 3.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        getIntentExtra();
        unbinder = ButterKnife.bind(this);
        StatusBarUtil.setImmersionStatus(this, container);
        initView();
        if (tradeIndex != null) {
            trade_id = String.valueOf(tradeIndex.getTrade_id());
        }
        userOrderPresenter = new UserOrderPresenter(new UserOrderModel());
        userOrderPresenter.attachView(this);

        sysConfigPresenter = new SysConfigPresenter(new UserOrderModel());
        sysConfigPresenter.attachView(this);
        sysConfigPresenter.getSysConfig(this);
    }

    private void setFullScreen() {
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        win.setAttributes(lp);
    }


    private void initView() {

        if (!TextUtils.isEmpty(type) && tradeIndex != null) {
            if (type.equals("2")) {
                tvTitle.setText("购买节水指标");
                tvPayMin.setText(new StringBuilder("最小").append(typeStr).append("量：").append(tradeIndex.getPay_min()).toString());
//                edtSafePw.setVisibility(View.GONE);
//                edtSafePw.setText("");
                tvPayTimeout.setText(new StringBuilder("该订单付款时限为  ").append(tradeIndex.getPay_timeout()).append("  分钟").toString());


            } else if (type.equals("1")) {
                tvTitle.setText("出售节水指标");
                tvPayMin.setText(new StringBuilder("最小").append(typeStr).append("量：").append(tradeIndex.getPay_min()).toString());
//                edtSafePw.setVisibility(View.VISIBLE);
                tvPayTimeout.setVisibility(View.GONE);
            }
            tvTotal.setText(new StringBuilder("数量：").append(tradeIndex.getTotal()).toString());
            edtTotal.setHint(new StringBuilder("最多").append(typeStr).append("量：").append(tradeIndex.getTotal()).toString());
            edtTotal.addTextChangedListener(this);
            GlidImageManager.getInstance().loadCircleImg(this, tradeIndex.getAvatar(), imgAvatar, R.mipmap.head, R.mipmap.head);
            tvUserNickname.setText(tradeIndex.getUser_nickname());
            if (tradeIndex.getPay_type_bank_card().equals("1")) {
                imgPayTypeBankCard.setVisibility(View.VISIBLE);
            } else if (tradeIndex.getPay_type_bank_card().equals("0")) {
                imgPayTypeBankCard.setVisibility(View.GONE);
            }
            if (tradeIndex.getPay_type_alipay().equals("1")) {
                imgPayTypeAlipay.setVisibility(View.VISIBLE);
            } else if (tradeIndex.getPay_type_bank_card().equals("0")) {
                imgPayTypeAlipay.setVisibility(View.GONE);
            }
            if (tradeIndex.getPay_type_wechat().equals("1")) {
                imgPayTypeWechat.setVisibility(View.VISIBLE);
            } else if (tradeIndex.getPay_type_bank_card().equals("0")) {
                imgPayTypeWechat.setVisibility(View.GONE);
            }
            tvTotalPrice.setText(new StringBuilder("总价：").append("0.00元").toString());

        }
    }


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_order);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.img_cancle, R.id.btn_cancel, R.id.btn_create_order, R.id.trans_container})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_cancle:
                finish();
                break;

            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_create_order:
                createOrder();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void createOrder() {
        if (userOrderPresenter == null) {
            userOrderPresenter = new UserOrderPresenter(new UserOrderModel());
            userOrderPresenter.attachView(this);
        }
        total = edtTotal.getText().toString();
//        if (checkTime()) {
        if (checkEdt()) {
            userOrderPresenter.createOrder(this, trade_id, total);
        } else {
            edtTotal.setText("");
            llTotal.setBackground(edt_border_illegal);
            tvTotalPrice.setText(new StringBuilder("总价：").append("0.00元").toString());
        }
//        } else {
//            EventBus.getDefault().post(new TradeIndexRefreshEvent("time_too_late",null));
//            finish();
//        }
    }

    private boolean checkEdt() {
        String total1 = "0";
        String min = "0";
        if (!TextUtils.isEmpty(tradeIndex.getTotal()) && tradeIndex.getTotal().contains("T")) {
            total1 = tradeIndex.getTotal().replace("T", "").trim();
        }
        if (!TextUtils.isEmpty(tradeIndex.getPay_min()) && tradeIndex.getPay_min().contains("T")) {
            min = tradeIndex.getPay_min().replace("T", "").trim();
        }
        if (!TextUtils.isEmpty(total) && !TextUtils.isEmpty(total1) && !TextUtils.isEmpty(min)) {
            float floatTotal = Float.valueOf(total);//用户输入
            float floatMax = Float.valueOf(total1);//后台返回最大
            float floatMin = Float.valueOf(min);//后台返回最小

            if (floatTotal <= 0) {
                edtTotal.setHint("输入值需大于0！");
                return false;
            } else if (floatTotal > 0 && floatTotal < floatMin) {
                edtTotal.setHint(new StringBuilder("输入值不能小于").append(tradeIndex.getPay_min().toString()).toString());
                return false;
            } else if (floatTotal > floatMax) {
                edtTotal.setHint(new StringBuilder("输入值不能大于").append(tradeIndex.getTotal().toString()).toString());
                return false;
            } else if (floatTotal > 0 && floatTotal <= floatMax) {
                return true;
            }
        }
        return true;
    }

    private boolean checkTime() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int hour = c.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        if (hour > 10 && hour < 22) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void finish() {
        super.finish();
        // 参数1：MainActivity进场动画，参数2：SecondActivity出场动画
        overridePendingTransition(0, R.anim.actionsheet_dialog_out);
    }


    private void getIntentExtra() {
        Intent intent = getIntent();
        tradeIndex = intent.getParcelableExtra("trade_index");
        type = intent.getStringExtra("type");
        if (type.equals("2")) {
            typeStr = "购买";
        } else if (type.equals("1")) {
            typeStr = "出售";
        }
    }


    //下单成功
    @Override
    public void onRequestSuccess(Object bean) {
        finish();
        EventBus.getDefault().post(new TradeIndexRefreshEvent("creat_order_success", null));
    }

    //下单失败
    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showCustomToast(msg, 0);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            total = edtTotal.getText().toString().trim();
            if (!TextUtils.isEmpty(total)) {
                if (total.endsWith(".")) {
                    total = total + "0";
                }
                final Float aFloat = Float.valueOf(total);
                String price = String.format("%.2f", aFloat * ds_price);
//            LogUtils.e("tag","aFloat:"+aFloat+"  ds_price:"+ds_price+"  price:"+price);
                tvTotalPrice.setText(new StringBuilder("总价：").append(price).append("元").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            edtTotal.setText("");
        }
    }

    //获取单价成功
    @Override
    public void onSecondRequstSuccess(Object o) {
        if (o != null) {
            SysConfigResponseBean sysConfigResponseBean = (SysConfigResponseBean) o;

            if (sysConfigResponseBean != null && !TextUtils.isEmpty(sysConfigResponseBean.getPrice())) {
                ds_price = Float.valueOf(sysConfigResponseBean.getPrice());
            }
        }
    }

    //获取单价失败
    @Override
    public void onSecondRequstFailed(String msg) {
        LogUtils.e("tag", "******获取单价失败------" + msg);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (sysConfigPresenter != null) sysConfigPresenter.detachView();
        if (userOrderPresenter != null) userOrderPresenter.detachView();
        super.onDestroy();
    }
}