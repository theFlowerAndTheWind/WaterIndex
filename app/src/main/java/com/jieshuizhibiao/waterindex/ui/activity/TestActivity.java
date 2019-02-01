/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: TestActivity
 * Author: ccdc_android
 * Date: 2018/12/14 11:06 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.jieshuizhibiao.waterindex.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.http.BaseObserver;
import com.jieshuizhibiao.waterindex.http.RetrofitFactory;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.utils.ObservableTransformerUtils;
import com.jieshuizhibiao.waterindex.http.utils.RequestUtil;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.ui.view.NewAlertDialog;
import com.jieshuizhibiao.waterindex.ui.widget.PicturePopupWindow;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;

/**
 * @ProjectName: NewWaterIndex
 * @Package: com.quanminjieshui.waterindex.test
 * @ClassName: TestActivity
 * @Description: 用于各接口测试
 * @Author: sxt
 * @CreateDate: 2018/12/14 11:06 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/12/14 11:06 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestActivity extends BaseActivity {
    //    @BindView(R.id.btn_request)
    Button btnRequest, btnGoLogin,btnAppeal;
    EditText edt;
    private String edtContent;
    NewAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnRequest = findViewById(R.id.btn_request);
        btnGoLogin = findViewById(R.id.btn_go_login);
        btnAppeal=findViewById(R.id.btn_appeal);
        edt = findViewById(R.id.edt);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.btn_request, R.id.btn_go_login,R.id.btn_appeal})
    public void onClick(View v) {
        edtContent = edt.getText().toString();
        int id = v.getId();
        switch (id) {

            case R.id.btn_request:

                HashMap<String, Object> params = new HashMap<>();
                params.put("order_id", "1");
                RetrofitFactory.getInstance().createService()
                        .sellerSucc(RequestUtil.getRequestHashBody(params, false))
                        .compose(TestActivity.this.<BaseEntity>bindToLifecycle())
                        .compose(ObservableTransformerUtils.<BaseEntity>io())
                        .subscribe(new BaseObserver(TestActivity.this) {

                            @Override
                            protected void onSuccess(Object bean) throws Exception {

                            }

                            @Override
                            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            }
                        });

                break;

            case R.id.btn_go_login:
                startActivity(new Intent(TestActivity.this, LoginActivity.class));

                break;
            case R.id.btn_appeal:
                Intent i = new Intent(this, PayFloatActivity.class);
                i.putExtra("order_id", 2);
                i.putExtra("pi_id", 2);
                startActivity(i);
                overridePendingTransition(R.anim.actionsheet_dialog_in, 0);
                break;
        }
    }






}