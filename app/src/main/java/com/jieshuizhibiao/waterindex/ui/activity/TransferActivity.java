package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.BeforeMvMoneyResponse;
import com.jieshuizhibiao.waterindex.beans.request.MoveMoneryReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.BeforeMvMoneryPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.MoveMoneryPresenter;
import com.jieshuizhibiao.waterindex.contract.view.BeforeMvMoneryViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.ui.view.NewAlertDialog;
import com.jieshuizhibiao.waterindex.utils.AccountValidatorUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;


import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by songxiaotao on 2019/1/9.
 * Class Note:
 */

public class TransferActivity extends BaseActivity implements BeforeMvMoneryViewImpl, CommonViewImpl {

    @BindView(R.id.btn_jsl_transfer)
    Button transferBtn;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_ds1)
    TextView tvDs1;
    @BindView(R.id.tv_ds2)
    TextView tvDs2;
    @BindView(R.id.tv_able_ds)
    TextView tvAbleDs;
    @BindView(R.id.edt_transfer_amonut)
    EditText edTransferDs;
    @BindView(R.id.edt_transfer_amonut_pwd)
    EditText edTransferPass;
    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;

    private NewAlertDialog dialog;
    private BeforeMvMoneryPresenter beforeMvMoneryPresenter;
    private MoveMoneryPresenter moveMoneryPresenter;
    private MoveMoneryReqParams params;

    //by sxt
    private String c2cDs, bbDs;
    private String from, to;
    private String trans_type;//方向    1 划出(主站到c2c) 2划入
    private boolean isTransAll = false;//是否全额划转标志   true全额转划，dialog不显示扣减信息和结转水方信息
    private float max;//本次划转最大量
    private boolean isBfSucc = false;//beforeMvMoney是否成功执行标志
    private String edTransferDsContent;
    private String edTransferPassContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, title_bar);
        beforeMvMoneryPresenter = new BeforeMvMoneryPresenter();
        beforeMvMoneryPresenter.attachView(this);
        moveMoneryPresenter = new MoveMoneryPresenter();
        moveMoneryPresenter.attachView(this);
        initData();
        initView();
    }

    private void initData() {
        params = new MoveMoneryReqParams();
        Intent intent = getIntent();
        if (intent != null) {
            trans_type = getIntent().getStringExtra(UserAssetActivity.TRANS_TYPE);
            c2cDs = getIntent().getStringExtra(UserAssetActivity.C2C_DS);
            bbDs = getIntent().getStringExtra(UserAssetActivity.BB_DS);
            if (trans_type.equals("2")) {
                tvTitle1.setText("从节水指标账号");
                tvTitle2.setText("到数字节水账号");
                from = c2cDs;
                to = bbDs;

            } else if (trans_type.equals("1")) {
                tvTitle1.setText("从数字节水账号");
                tvTitle2.setText("到节水指标账号");
                from = bbDs;
                to = c2cDs;
            }
            max = Float.valueOf(from.replace("T", "").trim());
            tvDs1.setText(new StringBuilder("余额：\n").append(from).toString());
            tvDs2.setText(new StringBuilder("余额：\n").append(to).toString());
            tvAbleDs.setText(new StringBuilder("可划转数量").append(from).toString());
            params.setType(trans_type);//1  划出     2  划入
        }
    }

    private void initView() {
        tv_title_center.setText("资产划转");
        dialog = new NewAlertDialog(this);
        edTransferDs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    edTransferDsContent = edTransferDs.getText().toString().trim();
                    if (!TextUtils.isEmpty(edTransferDsContent)) {
                        if (edTransferDsContent.endsWith(".")) {
                            edTransferDsContent = edTransferDsContent + "0";
                        }
                    }
                    float edtFloatContent = Float.valueOf(edTransferDsContent);
                    //不能超最大值
                    if (edtFloatContent > max) {
                        isTransAll = false;
                        edTransferDs.setText("");
                        edTransferDs.setHint(new StringBuilder("可划转数量").append(max).toString());
                        edTransferDs.setBackground(edt_border_illegal);
                    } else if (edtFloatContent == max) {
                        isTransAll = true;
                    } else {
                        isTransAll = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    edTransferDs.setText("");
                }
            }
        });
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_transfer);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.left_ll, R.id.btn_jsl_transfer, R.id.tv_transfer_all_ds})
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.left_ll:
                goBack(view);
                break;
            case R.id.tv_transfer_all_ds:
                isTransAll = true;
                String str = String.format("%.5f", max);
                edTransferDs.setText(str);
                edTransferDs.setSelection(str.length());
                break;
            case R.id.btn_jsl_transfer:
                edTransferDsContent = edTransferDs.getText().toString();
                edTransferPassContent = edTransferPass.getText().toString();
                if (!TextUtils.isEmpty(edTransferDsContent) && Float.valueOf(edTransferDsContent) > 0 && Float.valueOf(edTransferDsContent) <= max) {
                    edTransferDs.setBackground(edt_border);
                } else {
                    edTransferDs.setBackground(edt_border_illegal);
                    edTransferDs.setText("");
                    return;
                }
                if (!TextUtils.isEmpty(edTransferPassContent) && AccountValidatorUtil.isPassword(edTransferPassContent)) {
                    edTransferPass.setBackground(edt_border);
                } else {
                    edTransferPass.setBackground(edt_border_illegal);
                    edTransferPass.setText("");
                    return;
                }
                //beforeMvMoney
                doRequest();
                break;
        }
    }


    //划转前网络请求成功（beforeMvMoney）
    @Override
    public void onBeforeMvMonerySuccess(BeforeMvMoneyResponse beforeMvMoneyResponse) {
        isBfSucc = true;
        dismissLoadingDialog();

        String msg = "";
        String info;
        if(beforeMvMoneyResponse!=null){
            String total_ds = beforeMvMoneyResponse.getTotal_ds();
            String res_ds = beforeMvMoneyResponse.getRes_ds();
            String gyj = beforeMvMoneyResponse.getGyj();
            float fltTotalDs=Float.valueOf(total_ds);
            float fltRes_Ds=Float.valueOf(res_ds);
            float fltGyj=Float.valueOf(gyj);
            //total_ds==res_ds或gyj==0   --->全额划转
            if (fltTotalDs==fltRes_Ds) {
                info = new StringBuilder()
                        .append("            划转总额：")
                        .append(String.format("%.5f",fltTotalDs)).append("  T")
                        .append("\n            实际到账：")
                        .append(String.format("%.5f",fltRes_Ds)).append("  T")
                        .toString();
            } else {
                msg = "系统将您划转的节水指标的30%结转到您的平台账号，并兑换为积分作为公益用途，您可以在兑换商城进行消费";
                info = new StringBuilder()
                        .append("划转总额：")
                        .append(String.format("%.5f",fltTotalDs)).append("  T")
                        .append("\n实际到账：")
                        .append(String.format("%.5f",fltRes_Ds)).append("  T")
                        .append("\n结转水方：")
                        .append(String.format("%.5f",fltGyj)).append("  水方")
                        .toString();
            }
            showDialog(NewAlertDialog.TYPES[2], "确认划转", msg, info, "确认划转", "取消");
        }
    }

    @Override
    public void onBeforeMvMoneryFailed(String msg) {
        dialog.dismiss();
        isBfSucc = false;
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    private void doRequest() {
        params.setSafe_pw(edTransferPassContent);
        params.setType(trans_type);
        params.setTotal(edTransferDsContent);
        if (isBfSucc) {//mvMoney
            moveMoneryPresenter.moveMonery(this, params);
        } else {//beforeMvMoney
            beforeMvMoneryPresenter.beforMoveMonery(this, params);
        }
    }

    //划转成功（mvMoney）
    @Override
    public void onRequestSuccess(Object bean) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("划转成功", 1);
//        DecimalFormat df = new DecimalFormat("###.00");
//        Double transferGyj = 0.00, transferArrive = 0.00, transferTotal = 0.00;
//        if (bean instanceof MoveMoneryBean) {
//            transferGyj = Double.valueOf(((MoveMoneryBean) bean).getGyj());
//            transferArrive = Double.valueOf(((MoveMoneryBean) bean).getArrive());
//            transferTotal = Double.valueOf(((MoveMoneryBean) bean).getTotal());
//        }
//        if (params.getType().equals("1")) {//c2c公益金
//            SPUtil.insert(this, SPUtil.C2C_TRAMSFER, (transferGyj <= 0.00 ? "0.00" : df.format(transferGyj)));
//        } else {
//            SPUtil.insert(this, SPUtil.BB_TRAMSFER, (transferGyj <= 0.00 ? "0.00" : df.format(transferGyj)));
//        }
        isBfSucc=false;
        isTransAll=false;
        finish();
    }

    @Override
    public void onRequestFailed(String msg) {
        isBfSucc=false;
        isTransAll=false;
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);

    }

    private void showDialog(final String type, String title, final String msg, final String info, final String positive, final String negative) {
        if (dialog == null) {
            dialog = new NewAlertDialog(this);
        }
        dialog.builder().setCancelable(true);
        dialog.setType(type)
                .setTitle(title)
                .setMsg(msg)
                .setTransInfo(info)
                .setPositiveButton(positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //mvMoney
                        doRequest();
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (beforeMvMoneryPresenter != null) {
            beforeMvMoneryPresenter.detachView();
        }
        if (moveMoneryPresenter != null) {
            moveMoneryPresenter.detachView();
        }
    }
}
