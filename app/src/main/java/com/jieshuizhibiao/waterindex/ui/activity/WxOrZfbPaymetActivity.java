package com.jieshuizhibiao.waterindex.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.base.permission.config.PermissionConfig;
import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.AddPayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.beans.request.ChangePayMentTypeReqParams;
import com.jieshuizhibiao.waterindex.contract.model.UploadFileModel;
import com.jieshuizhibiao.waterindex.contract.presenter.AddPayMentTypePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.ChangePaymentTypePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.PicturePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UploadFilePresenter;
import com.jieshuizhibiao.waterindex.contract.view.AddPayMentTypeViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.ChangePaymentTypeViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.PictureViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;
import com.jieshuizhibiao.waterindex.http.config.HttpConfig;
import com.jieshuizhibiao.waterindex.ui.widget.PicturePopupWindow;
import com.jieshuizhibiao.waterindex.utils.BitmapUtils;
import com.jieshuizhibiao.waterindex.utils.ImageFactory;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.PictureFileUtil;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.StatusBarUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by songxiaotao on 2019/1/13.
 * Class Note:
 */

public class WxOrZfbPaymetActivity extends BaseActivity
        implements AddPayMentTypeViewImpl, ChangePaymentTypeViewImpl, PictureViewImpl, UploadFileViewImpl {

    @BindView(R.id.title_bar)
    View title_bar;
    @BindView(R.id.left_ll)
    LinearLayout left_ll;
    @BindView(R.id.tv_title_center)
    TextView tv_title_center;
    //    @BindView(R.id.btn_payment_qrcode_img)
//    Button btnQrcode;
    @BindView(R.id.ll_upload_qrcode)
    LinearLayout llUploadQrcode;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.tv_txt)
    TextView tvTxt;
    @BindView(R.id.edt_payment_comment_account)
    EditText edtAccount;
    @BindView(R.id.edt_capital_pass)
    EditText edtPayPass;
    @BindView(R.id.edt_payment_name)
    EditText edtPayName;
    @BindDrawable(R.drawable.gray_border_bg_shape)
    Drawable edt_border;
    @BindDrawable(R.drawable.red_border_illegal_bg_shape)
    Drawable edt_border_illegal;
    @BindString(R.string.key_edt_pay_type_qrcode)
    String keyPayQcode;
    @BindString(R.string.key_edt_pay_type_account)
    String keyPayAccount;
    @BindString(R.string.key_edt_pay_type_name)
    String keyPayName;
    @BindString(R.string.key_edt_pay_type_capital_pwd)
    String keyPayPass;

    private PicturePopupWindow popupWindow;
    private AddPayMentTypePresenter addPayMentTypePresenter;
    private static final int REQUEST_TAKE_PHOTO = 0x110;//拍照结果回调
    private String mCurrentPhotoPath, stringStream;
    private ChangePaymentTypePresenter changePaymentTypePresenter;
    private PayMentResponseBean.TypeList typeList;
    private PicturePresenter picturePresenter;
    private Uri qrcodeUri;
    private String qrcodeUrl;
    private UploadFilePresenter uploadFilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersionStatus(this, title_bar);
        addPayMentTypePresenter = new AddPayMentTypePresenter();
        addPayMentTypePresenter.attachView(this);
        changePaymentTypePresenter = new ChangePaymentTypePresenter();
        changePaymentTypePresenter.attachView(this);
        picturePresenter = PicturePresenter.getInstance();
        picturePresenter.attachView(this);
        uploadFilePresenter = new UploadFilePresenter(new UploadFileModel());
        uploadFilePresenter.attachView(this);
        initView();
    }

    private void initView() {
        if (getIntent().getStringExtra("payType").equals(HttpConfig.WX_TYPE)) {
            if (getIntent().getBooleanExtra("isHasAdd", false)) {
                tv_title_center.setText("编辑微信");
            } else {
                tv_title_center.setText("添加微信");
            }
            tvTxt.setText(R.string.hint_upload_payment_wx_img);
            edtAccount.setHint(R.string.hint_input_payment_wx_account);
        } else {
            if (getIntent().getBooleanExtra("isHasAdd", false)) {
                tv_title_center.setText("编辑支付宝");
            } else {
                tv_title_center.setText("添加支付宝");
            }
            tvTxt.setText(R.string.hint_upload_payment_zfb_img);
            edtAccount.setHint(R.string.hint_input_payment_zfb_account);
        }

        if (getIntent().getParcelableExtra("TypeList") != null) {
            typeList = getIntent().getParcelableExtra("TypeList");
            edtAccount.setText(typeList.getAccount_name());
            edtAccount.setSelection(typeList.getAccount_name().length());
        }
        initPopupView();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_add_payment_zfb_wx);
    }

    @Override
    public void onReNetRefreshData(int viewId) {

    }

    @OnClick({R.id.left_ll, R.id.ll_upload_qrcode, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_ll:
                goBack(v);
                finish();
                break;
            case R.id.ll_upload_qrcode:
                onViewClicked();
                showPopupView();
                break;
            case R.id.btn_confirm:
                if (getIntent().getBooleanExtra("isHasAdd", false)) {//编辑
                    ChangePayMentTypeReqParams params = new ChangePayMentTypeReqParams();
                    params.setUser_name(edtPayName.getText().toString());
                    params.setQrcode(stringStream);
                    params.setSafe_pw(edtPayPass.getText().toString());
                    params.setAccount_name(edtAccount.getText().toString());
                    params.setId(typeList.getId());
                    changePaymentTypePresenter.vertify(params);
                    changePaymentTypePresenter.changePaymentType(WxOrZfbPaymetActivity.this, params, getIntent().getStringExtra("payType"));
                } else {//添加
                    AddPayMentTypeReqParams params = new AddPayMentTypeReqParams();
                    if (getIntent().getStringExtra("payType").equals(HttpConfig.WX_TYPE)) {
                        params.setType(HttpConfig.WX_TYPE);
                    } else {
                        params.setType(HttpConfig.ZFB_TYPE);
                    }
                    params.setUser_name(edtPayName.getText().toString());
                    params.setQrcode(stringStream);
                    params.setSafe_pw(edtPayPass.getText().toString());
                    params.setAccount_name(edtAccount.getText().toString());
                    addPayMentTypePresenter.vertify(params);
                    addPayMentTypePresenter.addPayMentType(this, params);
                }
                break;

            default:
                break;
        }
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
        llUploadQrcode.setBackground(bitmapDrawable);
        imgAdd.setVisibility(View.GONE);
        tvTxt.setVisibility(View.GONE);
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
            qrcodeUri = uri;
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            qrcodeUri = Uri.fromFile(tempFile);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getCtx() {
        return WxOrZfbPaymetActivity.this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        picturePresenter.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            case PicturePresenter.REQUEST_PHOTO:  //调用系统相册返回
                if (resultCode == Activity.RESULT_OK) {
                    qrcodeUri = intent.getData();
                }
                break;
        }

        if (qrcodeUri == null) {
            return;
        }
        String cropImagePath = PictureFileUtil.getRealFilePathFromUri(this, qrcodeUri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        if (bitMap != null) {
            showView(bitMap);
            uploadFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        picturePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    /**
//     * 判断SDCard是否可用
//     */
//    public static boolean existSDCard() {
//        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//    }
//
//    private void takePhoto() {
//        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        takePhotoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        File file = null;
//        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
//            String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
//            if (existSDCard()) {
//                file = new File(Environment.getExternalStorageDirectory(), fileName);
//            } else {
//                file = new File(Environment.getDataDirectory(), fileName);
//            }
//            Uri fileUrl;
//            if (file != null) {
//                mCurrentPhotoPath = file.getAbsolutePath();
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//                    //不要完全使用Uri 避免一些不必要的麻烦
//                    fileUrl = Uri.fromFile(file);
//
//                } else {//包装7.0 Uri--清单文件中智能有一个FileProvider的声明
//                    fileUrl = FileProvider.getUriForFile(WxOrZfbPaymetActivity.this, "com.quanminjieshui.waterindex.fileProvider", file);
//
//                    //授权4.4FileProvider content的grantUriPermission 方法
//                    List<ResolveInfo> mResolveList = getPackageManager().
//                            queryIntentActivities(takePhotoIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                    for (ResolveInfo resolveInfo : mResolveList) {
//                        String packageName = resolveInfo.activityInfo.packageName;
//                        grantUriPermission(packageName, fileUrl, Intent.FLAG_GRANT_READ_URI_PERMISSION |
//                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    }
//                }
//                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUrl);
//            }
//            startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
//        }
//    }
//
//    public void dealTakePhoto() {
//        if (!TextUtils.isEmpty(BitmapUtils.getBrand()) && BitmapUtils.getBrand().equals("samsung") || BitmapUtils.getBrand().equals("Xiaomi")) {
//            //获取照片拍照角度
//            int degree = BitmapUtils.readPictureDegree(mCurrentPhotoPath);
//            LogUtils.i("拍照角度：" + degree);//正常角度应该返回0 三星的是90度
//            //将原图转为Bitmap
//            Bitmap mOriginalBitmap = ImageFactory.convertStremToBitmap(mCurrentPhotoPath);
//            //旋转图片  负值为顺时针旋转 正值为逆时针旋转
//            Bitmap mRotateBitmap = BitmapUtils.rotateBitmap(mOriginalBitmap, +(float) degree);
//            //旋转图片保存路径
//            String fileName = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
//            //转为File
//            File mRotateFile = ImageFactory.convertBitmapToFile(mRotateBitmap, fileName);
//            ZipImg(mRotateFile);
//
//            if (mOriginalBitmap != null) {
//                mOriginalBitmap.recycle();
//            }
//            if (mRotateBitmap != null) {
//                mRotateBitmap.recycle();
//            }
//        } else {//其他机型手机
//            File mFile = new File(mCurrentPhotoPath);
//            ZipImg(mFile);
//        }
//    }
//
//    private void ZipImg(File mFile) {
//
//        Flowable.just(mFile)
//                .subscribeOn(Schedulers.io())
//                .map(new Function<File, File>() {
//                    @Override
//                    public File apply(File file) throws Exception {
//                        return Luban.with(WxOrZfbPaymetActivity.this).load(file).get();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(File file) throws Exception {
//                        //转换字节流 需要上传的字节
//                        stringStream = ImageFactory.file2String(file);
//                        LogUtils.d("转换字节流"+stringStream);
//                        showImgData(file);
//                    }
//                });
//    }
//
//    /**
//     * 显示压缩后的图片 todo
//     * @param file
//     */
//    public void showImgData(File file){
//        Bitmap bitmap = BitmapUtils.getBitmap(file.getAbsolutePath());
//        final BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//        llUploadQrcode.setBackground(bitmapDrawable);
//        imgAdd.setVisibility(View.GONE);
//        tvTxt.setVisibility(View.GONE);
//    }
//
//    public void cameraTask(){
//        popupWindow.dismiss();
//        String[] perms = {Manifest.permission.CAMERA};
//        performCodeWithPermission(getString(R.string.rationale_camera), PermissionConfig.REQUEST_CAMERA_PERM, perms, new PermissionCallback() {
//            @Override
//            public void hasPermission(List<String> allPerms) {
//                takePhoto();
//                LogUtils.i("已获取camera权限");
//            }
//
//            @Override
//            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
//                if (hasPermanentlyDenied) {
//                    alertAppSetPermission(getString(R.string.rationale_ask_again), PermissionConfig.REQUEST_CAMERA_PERM);
//                    LogUtils.i("获取camera权限被拒");
//                }
//            }
//        });
//    }
//
//    public void selectImgTask(){
//        popupWindow.dismiss();
//        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        performCodeWithPermission(getString(R.string.rationale_storage), PermissionConfig.REQUEST_STORAGE_PERM, perms, new PermissionCallback() {
//            @Override
//            public void hasPermission(List<String> allPerms) {
//                Intent intent = new Intent(WxOrZfbPaymetActivity.this, ImageGridActivity.class);
//                startActivityForResult(intent, ImagePicker.RESULT_CODE_ITEMS);
//                LogUtils.i("已获取storage权限");
//            }
//
//            @Override
//            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
//                if (hasPermanentlyDenied) {
//                    alertAppSetPermission(getString(R.string.rationale_ask_again), PermissionConfig.REQUEST_STORAGE_PERM);
//                    LogUtils.i("获取storage权限被拒");
//                }
//            }
//        });
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_TAKE_PHOTO:
//                if (resultCode == Activity.RESULT_OK) {
//                    dealTakePhoto();
//                }
//                break;
//            case ImagePicker.RESULT_CODE_ITEMS:
//                if(data!=null){
//                    //打开相册没有选择图片返回
//                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                    for (ImageItem imageItem : images) {
//                        try {
//                            mCurrentPhotoPath = imageItem.path;
//                            //拿到用户选择的头像以后 进行字节流的转换 并上传服务器
//                            dealTakePhoto();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                break;
//            default:
//                super.onActivityResult(requestCode, resultCode, data);
//                break;
//        }
//
//    }

    @Override
    public void onAddEdtContentsLegal() {
        edtAccount.setBackground(edt_border);
        edtPayName.setBackground(edt_border);
        edtPayPass.setBackground(edt_border);
        llUploadQrcode.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onAddEdtContentsIllegal(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keyPayQcode)) && !verify.get(keyPayQcode)) {
            llUploadQrcode.setBackground(edt_border_illegal);
        } else {
            llUploadQrcode.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayName)) && !verify.get(keyPayName)) {
            edtPayName.setBackground(edt_border_illegal);
            edtPayName.setText("");
        } else {
            edtPayName.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayAccount)) && !verify.get(keyPayAccount)) {
            edtAccount.setBackground(edt_border_illegal);
            edtAccount.setText("");
        } else {
            edtAccount.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayPass)) && !verify.get(keyPayPass)) {
            edtPayPass.setBackground(edt_border_illegal);
            edtPayPass.setText("");
        } else {
            edtPayPass.setBackground(edt_border);
        }
    }

    @Override
    public void onAddEdtContentsIllegalBank(Map<String, Boolean> verify) {

    }

    @Override
    public void onAddPaymentTypeSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("添加成功！", 1);
        finish();
    }

    @Override
    public void onAddPaymentTypeFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    public void onChangeContentsLegal() {
        edtAccount.setBackground(edt_border);
        edtPayName.setBackground(edt_border);
        edtPayPass.setBackground(edt_border);
        llUploadQrcode.setBackground(edt_border);
        showLoadingDialog();
    }

    @Override
    public void onChangeEdtContentsIllegal(Map<String, Boolean> verify) {
        if (!Util.isEmpty(verify.get(keyPayQcode)) && !verify.get(keyPayQcode)) {
            llUploadQrcode.setBackground(edt_border_illegal);
        } else {
            llUploadQrcode.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayName)) && !verify.get(keyPayName)) {
            edtPayName.setBackground(edt_border_illegal);
            edtPayName.setText("");
        } else {
            edtPayName.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayAccount)) && !verify.get(keyPayAccount)) {
            edtAccount.setBackground(edt_border_illegal);
            edtAccount.setText("");
        } else {
            edtAccount.setBackground(edt_border);
        }
        if (!Util.isEmpty(verify.get(keyPayPass)) && !verify.get(keyPayPass)) {
            edtPayPass.setBackground(edt_border_illegal);
            edtPayPass.setText("");
        } else {
            edtPayPass.setBackground(edt_border);
        }
    }

    @Override
    public void onChangeEdtContentsIllegalBank(Map<String, Boolean> verify) {

    }

    @Override
    public void onChangePaymentTyoeSuccess() {
        dismissLoadingDialog();
        ToastUtils.showCustomToast("修改成功！", 1);
        finish();
    }

    @Override
    public void onChangePaymentTypeFalied(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    public void onUploadFileSuccess(UploadFileResponseBean fileResponseBean) {
        if (fileResponseBean != null) {
            qrcodeUrl = fileResponseBean.getUrl();
        }
    }

    @Override
    public void onUploadFileFailed(String msg) {
        ToastUtils.showCustomToast("二维码图片上传失败，请重新上传", 0);
    }

    private void uploadFile() {
        if (uploadFilePresenter == null) {
            uploadFilePresenter = new UploadFilePresenter(new UploadFileModel());
            uploadFilePresenter.attachView(this);
        }
        try {
            File file = PictureFileUtil.Uri2File(qrcodeUri, this);
            String token = (String) SPUtil.get(this, SPUtil.TOKEN, "token");
            if (file != null) {
                uploadFilePresenter.uploadFile(this, token, file);
            } else {
                ToastUtils.showCustomToast("二维码图片上传失败", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCustomToast("二维码图片上传失败", 0);
        }
    }

}
