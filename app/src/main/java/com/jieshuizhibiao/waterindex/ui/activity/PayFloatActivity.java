package com.jieshuizhibiao.waterindex.ui.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.contract.model.BuyerDoPayModel;
import com.jieshuizhibiao.waterindex.contract.model.UploadFileModel;
import com.jieshuizhibiao.waterindex.contract.presenter.BuyerDoPayPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.PicturePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UploadFilePresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.PictureViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;
import com.jieshuizhibiao.waterindex.event.ChangeOrderStatusEvent;
import com.jieshuizhibiao.waterindex.event.UserDoPayEvent;
import com.jieshuizhibiao.waterindex.ui.widget.PicturePopupWindow;
import com.jieshuizhibiao.waterindex.utils.PictureFileUtil;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PayFloatActivity extends BaseActivity implements CommonViewImpl, UploadFileViewImpl, PictureViewImpl {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_msg)
    TextView txtMsg;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.btn_img)
    Button btnImg;


    private PicturePopupWindow popupWindow;
    private PicturePresenter picturePresenter;
    private BuyerDoPayPresenter buyerDoPayPresenter;
    private UploadFilePresenter uploadFilePresenter;
    private String snapshotUrl;
    private Uri snapshotUri;
    private long order_id;
    private long pi_id;

    public static final String ACTION_FORM_PAYFLOATACTIVITY = "action_from_payfloatactvity";
    public static final String BUYER_DO_PAY = "buyer_do_pay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentExtras();
        initView();
        uploadFilePresenter = new UploadFilePresenter(new UploadFileModel());
        uploadFilePresenter.attachView(this);
        picturePresenter = PicturePresenter.getInstance();
        picturePresenter.attachView(this);
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        order_id = intent.getLongExtra("order_id", 0);
        pi_id = intent.getLongExtra("pi_id", 0);
    }

    private void initView() {
        flContainer.setVisibility(View.VISIBLE);
        btnImg.setVisibility(View.VISIBLE);
        txtTitle.setText("付款确认");
        txtMsg.setText("请确认你已向卖方付款。恶意点击将直接冻结账号");
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_pay_float);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.btn_img, R.id.btn_pos, R.id.btn_neg})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_img:
                onViewClicked();
                showPopupView();
                break;
            case R.id.btn_neg:
                finish();
                break;

            case R.id.btn_pos:
                uploadFile();
                break;
        }
    }

    private void uploadFile() {
        if (uploadFilePresenter == null) {
            uploadFilePresenter = new UploadFilePresenter(new UploadFileModel());
            uploadFilePresenter.attachView(this);
        }
        try {
            File file = PictureFileUtil.Uri2File(snapshotUri, this);
            String token = (String) SPUtil.get(this, SPUtil.TOKEN, "token");
            if (file != null) {
                uploadFilePresenter.uploadFile(this, token, file);
            } else {
                ToastUtils.showCustomToast("支付凭证上传失败",0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCustomToast("支付凭证上传失败",0);
        }
    }

    private void buyerDoPay() {
        if (buyerDoPayPresenter == null) {
            buyerDoPayPresenter = new BuyerDoPayPresenter(new BuyerDoPayModel());
            buyerDoPayPresenter.attachView(this);
        }
        //校验图片上传是否成功
        if (!TextUtils.isEmpty(snapshotUrl)) {
            buyerDoPayPresenter.buyerDoPay(this, order_id, pi_id, snapshotUrl);
        } else {
            ToastUtils.showCustomToastMsg("请上传支付凭证",150);
        }
    }

    //买家身份-上传支付凭证 成功
    @Override
    public void onUploadFileSuccess(UploadFileResponseBean fileResponseBean) {
        if (fileResponseBean != null) {
            snapshotUrl = fileResponseBean.getUrl();
            buyerDoPay();
        }
    }

    //买家身份-上传支付凭证失败
    @Override
    public void onUploadFileFailed(String msg) {
        ToastUtils.showCustomToast("支付凭证上传失败，请重新上传",0);
    }


    //买家身份-支付  成功
    @Override
    public void onRequestSuccess(Object bean) {
        ToastUtils.showSuccessToast("支付成功！");

        EventBus.getDefault().post(new UserDoPayEvent(ACTION_FORM_PAYFLOATACTIVITY));
        EventBus.getDefault().post(new ChangeOrderStatusEvent("PayFloatActivity", BUYER_DO_PAY));
        startActivity(new Intent(this, MainActivity.class));
    }

    //买家身份-支付  失败
    @Override
    public void onRequestFailed(String msg) {
        ToastUtils.showSuccessToast("支付失败！");
    }

    @Override
    public void onViewClicked() {
        initPopupView();
    }

    @Override
    public void initPopupView() {
        if (popupWindow == null) {
            popupWindow = new PicturePopupWindow(this);
            popupWindow.setOnItemClickListener(new PicturePopupWindow.OnItemClickListener() {
                @Override
                public void onCaremaClicked() {
                    if (picturePresenter != null) {
                        picturePresenter.onCameraClicked();
                    }
                }

                @Override
                public void onPhotoClicked() {
                    if (picturePresenter != null) {
                        picturePresenter.onPhotoClicked();
                    }
                }

                @Override
                public void onCancelClicked() {
                    if (picturePresenter != null) {
                        picturePresenter.onCancleClicked();
                    }
                }
            });
        }
    }

    @Override
    public void showView(Bitmap bitmap) {
        final BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        btnImg.setBackground(bitmapDrawable);
    }

    @Override
    public void showPopupView() {
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_pay_float, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    @Override
    public void dismissPopupView() {
        popupWindow.dismiss();
    }

    @Override
    public boolean popupIsShowing() {
        return popupWindow.isShowing();
    }

    @Override
    public void go2PicSettingActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, PictureSettingActivity.class);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void go2SystemPhoto(int requestCode) {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestCode);
    }

    @Override
    public void go2SystemCamera(File tempFile, int requestCode) {
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml，下面2种方式都可以
            //            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //            Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig
            // .APPLICATION_ID + "" +
            //                    ".fileProvider", tempFile);
            //            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile
                    .getAbsolutePath());
            Uri uri = this.getContentResolver().insert(MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            snapshotUri = uri;
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            snapshotUri = Uri.fromFile(tempFile);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getCtx() {
        return PayFloatActivity.this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        picturePresenter.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            case PicturePresenter.REQUEST_PHOTO:  //调用系统相册返回
                if (resultCode == Activity.RESULT_OK) {
                    snapshotUri = intent.getData();
                }
                break;
        }

        if (snapshotUri == null) {
            return;
        }
        String cropImagePath = PictureFileUtil.getRealFilePathFromUri(this, snapshotUri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        if (bitMap != null) {
            showView(bitMap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        picturePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void finish() {
        super.finish();
        // 参数1：MainActivity进场动画，参数2：SecondActivity出场动画
        overridePendingTransition(0, R.anim.actionsheet_dialog_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (picturePresenter != null) picturePresenter.detachView();
        if (buyerDoPayPresenter != null) buyerDoPayPresenter.detachView();
        if (uploadFilePresenter != null) uploadFilePresenter.detachView();
        super.onDestroy();
    }
}
