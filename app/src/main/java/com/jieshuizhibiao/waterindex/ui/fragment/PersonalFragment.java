package com.jieshuizhibiao.waterindex.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jieshuizhibiao.waterindex.R;
import com.jieshuizhibiao.waterindex.base.BaseActivity;
import com.jieshuizhibiao.waterindex.base.permission.config.PermissionConfig;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserIndexResponseBean;
import com.jieshuizhibiao.waterindex.beans.request.SetAvatarReqParams;
import com.jieshuizhibiao.waterindex.contract.presenter.PicturePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.SetAvatarPresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UploadFilePresenter;
import com.jieshuizhibiao.waterindex.contract.presenter.UserIndexPresenter;
import com.jieshuizhibiao.waterindex.contract.view.CommonViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.PictureViewImpl;
import com.jieshuizhibiao.waterindex.contract.view.UploadFileViewImpl;
import com.jieshuizhibiao.waterindex.event.LoginStatusChangedEvent;
import com.jieshuizhibiao.waterindex.ui.activity.AboutListActivity;
import com.jieshuizhibiao.waterindex.ui.activity.LoginActivity;
import com.jieshuizhibiao.waterindex.ui.activity.MainActivity;
import com.jieshuizhibiao.waterindex.ui.activity.PayFloatActivity;
import com.jieshuizhibiao.waterindex.ui.activity.PaymentTypeActivity;
import com.jieshuizhibiao.waterindex.ui.activity.PictureSettingActivity;
import com.jieshuizhibiao.waterindex.ui.activity.SafeCenterActivity;
import com.jieshuizhibiao.waterindex.ui.activity.SystemMessageActivity;
import com.jieshuizhibiao.waterindex.ui.activity.TransactionDemandActivity;
import com.jieshuizhibiao.waterindex.ui.activity.UserAssetActivity;
import com.jieshuizhibiao.waterindex.ui.activity.UserConfirmActivity;
import com.jieshuizhibiao.waterindex.ui.activity.UserInfoActivity;
import com.jieshuizhibiao.waterindex.ui.view.AlertChainDialog;
import com.jieshuizhibiao.waterindex.ui.widget.PicturePopupWindow;
import com.jieshuizhibiao.waterindex.utils.BitmapUtils;
import com.jieshuizhibiao.waterindex.utils.ImageFactory;
import com.jieshuizhibiao.waterindex.utils.LogUtils;
import com.jieshuizhibiao.waterindex.utils.PictureFileUtil;
import com.jieshuizhibiao.waterindex.utils.SPUtil;
import com.jieshuizhibiao.waterindex.utils.ToastUtils;
import com.jieshuizhibiao.waterindex.utils.Util;
import com.jieshuizhibiao.waterindex.utils.image.GlidImageManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by songxiaotao on 2018/12/5.
 * Class Note:我的个人中心
 */

public class PersonalFragment extends BaseFragment implements CommonViewImpl, UploadFileViewImpl, PictureViewImpl {
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private PicturePopupWindow popupWindow;
    private String mCurrentPhotoPath;
    private AlertChainDialog alertChainDialog;
    private Unbinder unbinder;
    private UserIndexPresenter userIndexPresenter;
    private UploadFilePresenter uploadFilePresenter;
    private PicturePresenter picturePresenter;
    private SetAvatarPresenter setAvatarPresenter;
    private Uri avatarUri;
    private String avatarUrl;

    private static final int REQUEST_TAKE_PHOTO = 0x110;//拍照结果回调
    /**
     * 是否登录
     * 记录当前是否登录，fargment切换后有登录、退出登录操作后，下次再显示时用该变量与本地SP所存结果比对，不一致时做刷新操作
     */
    private boolean isLogin = false;
    private String user_login, nike_name;//用户登录手机号，作用同isLogin

    @OnClick({R.id.relative_user_detail, R.id.relative_account_detail, R.id.relative_auth_detail,
            R.id.relative_payment_type, R.id.relative_transaction_demand, R.id.tv_version,
            R.id.relative_sys_msg, R.id.relative_safe_center, R.id.relative_about_us, R.id.img_avatar})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.relative_user_detail:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(UserInfoActivity.class);
                }
                break;
            case R.id.relative_account_detail:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(UserAssetActivity.class);
                }
                break;
            case R.id.relative_auth_detail:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(UserConfirmActivity.class);
                }
                break;
            case R.id.relative_payment_type:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(PaymentTypeActivity.class);
                }
                break;
            case R.id.relative_transaction_demand:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(TransactionDemandActivity.class);
                }
                break;
            case R.id.relative_sys_msg:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(SystemMessageActivity.class);
                }
                break;
            case R.id.relative_safe_center:
                if (checkLoginStatus() && !Util.isFastDoubleClick()) {
                    jump(SafeCenterActivity.class);
                }
                break;
            case R.id.relative_about_us:
                if (!Util.isFastDoubleClick()) {
                    jump(AboutListActivity.class);
                }
                break;
            case R.id.img_avatar:
                if (checkLoginStatus() &&!Util.isFastDoubleClick()) {
                    onViewClicked();
                    showPopupView();
                }
                break;
            default:
                break;
        }

    }

    private boolean checkLoginStatus() {
        boolean isLogin_ = (boolean) SPUtil.get(getActivity(), SPUtil.IS_LOGIN, false);
        if (!isLogin_) {
            if (alertChainDialog != null) {
                alertChainDialog.builder().setCancelable(false);
                alertChainDialog.setTitle("提示消息")
                        .setMsg("您当前未登录，请登录")
                        .setPositiveButton("登录", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toLogin();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
            }
        }
        return isLogin_;
    }

    private void toLogin() {
        Intent intent = new Intent();
        intent.putExtra("target", "main_personal");
        jump(LoginActivity.class, intent);
    }

    private void jump(Class<?> cls) {
        startActivity(new Intent(getBaseActivity(), cls));
    }

    private void jump(Class<?> cls, Intent intent) {
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        userIndexPresenter = new UserIndexPresenter();
        userIndexPresenter.attachView(this);
        uploadFilePresenter = new UploadFilePresenter();
        uploadFilePresenter.attachView(this);
        picturePresenter = PicturePresenter.getInstance();
        picturePresenter.attachView(this);
        setAvatarPresenter = new SetAvatarPresenter();
        setAvatarPresenter.attachView(this);
        doRequest();

        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showAvatar();
        showUserInfo();
    }

    private void initView() {
        alertChainDialog = new AlertChainDialog(getBaseActivity());
        tvVersion.setText("版本号 V" + Util.versionName(getBaseActivity()));
        initPopupView();
    }

    public void doRequest() {
        userIndexPresenter.userIndex(getBaseActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            doRequest();
            showAvatar();
            showUserInfo();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            doRequest();
            showAvatar();
            showUserInfo();
        }
    }

    @Override
    public void onReNetRefreshData(int viewId) {
        doRequest();
    }

    public void onEventMainThread(LoginStatusChangedEvent event) {
        if (event != null && event.getMsg().equals("login_status_changed_main_personal_refresh_nickname")) {
            isLogin = (boolean) SPUtil.get(getActivity(), SPUtil.IS_LOGIN, false);
            user_login = (String) SPUtil.get(getActivity(), SPUtil.USER_LOGIN, "user_login");
            tvNickname.setText((String) SPUtil.get(getActivity(), SPUtil.USER_NICKNAME, "********"));
        }
    }

    private void showUserInfo() {
        isLogin = (boolean) SPUtil.get(getActivity(), SPUtil.IS_LOGIN, false);
        user_login = (String) SPUtil.get(getActivity(), SPUtil.USER_LOGIN, "******");
        tvNickname.setText(TextUtils.isEmpty(user_login) ? "******" : Util.hide4Phone(user_login));
    }

    public String getIsLogin() {
        return new StringBuilder().append(isLogin).append(user_login).toString();
    }

//    public void initPopupView() {
//        if (popupWindow == null) {
//            popupWindow = new PicturePopupWindow(getBaseActivity());
//            popupWindow.setOnItemClickListener(new PicturePopupWindow.OnItemClickListener() {
//
//                @Override
//                public void onCaremaClicked() {
//                    cameraTask();
//                }
//
//                @Override
//                public void onPhotoClicked() {
//                    selectImgTask();
//                }
//
//                @Override
//                public void onCancelClicked() {
//                    popupWindow.dismiss();
//                }
//            });
//        }
//    }
//
//    public void showPopupView() {
//        View parent = LayoutInflater.from(getBaseActivity()).inflate(R.layout.fragment_personal, null);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
//    }
//
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
//        if (takePhotoIntent.resolveActivity(getBaseActivity().getPackageManager()) != null) {
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
//                    fileUrl = FileProvider.getUriForFile(getBaseActivity(), "com.quanminjieshui.waterindex.fileProvider", file);
//
//                    //授权4.4FileProvider content的grantUriPermission 方法
//                    List<ResolveInfo> mResolveList = getBaseActivity().getPackageManager().
//                            queryIntentActivities(takePhotoIntent, PackageManager.MATCH_DEFAULT_ONLY);
//                    for (ResolveInfo resolveInfo : mResolveList) {
//                        String packageName = resolveInfo.activityInfo.packageName;
//                        getBaseActivity().grantUriPermission(packageName, fileUrl, Intent.FLAG_GRANT_READ_URI_PERMISSION |
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
//        Flowable.just(mFile)
//                .subscribeOn(Schedulers.io())
//                .map(new Function<File, File>() {
//                    @Override
//                    public File apply(File file) throws Exception {
//                        return Luban.with(getBaseActivity()).load(file).get();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(File file) throws Exception {
//                        //获取到文件后调用上传接口
//                        doUploadFileRuest(file);
//                    }
//                });
//    }
//
//    public void cameraTask(){
//        popupWindow.dismiss();
//        String[] perms = {Manifest.permission.CAMERA};
//        performCodeWithPermission(getString(R.string.rationale_camera), PermissionConfig.REQUEST_CAMERA_PERM, perms, new BasePermissionFragment.PermissionCallback() {
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
//        performCodeWithPermission(getString(R.string.rationale_storage), PermissionConfig.REQUEST_STORAGE_PERM, perms, new BasePermissionFragment.PermissionCallback() {
//            @Override
//            public void hasPermission(List<String> allPerms) {
//                Intent intent = new Intent(getBaseActivity(), ImageGridActivity.class);
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
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
//
    /**
     * 文件上传
     */
    public void doUploadFileRuest(){
        File file=PictureFileUtil.Uri2File(avatarUri,getActivity());
        uploadFilePresenter.uploadFile(getBaseActivity(), (String) SPUtil.get(getBaseActivity(),SPUtil.TOKEN,"token"),file);
    }

    /**
     * 修改头像接口请求
     */
    public void doChangeAvatarRuest(String avatartUrl) {
        SetAvatarReqParams params = new SetAvatarReqParams();
        params.setAvatar(avatartUrl);
        setAvatarPresenter.setAvatar(getBaseActivity(), params);
        showLoadingDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (setAvatarPresenter != null) {
            setAvatarPresenter.detachView();
        }
        if (userIndexPresenter != null) {
            userIndexPresenter.detachView();
        }
        if (uploadFilePresenter != null) {
            uploadFilePresenter.detachView();
        }
        if (picturePresenter != null) picturePresenter.detachView();
    }

    @Override
    public void onRequestSuccess(Object bean) {
        dismissLoadingDialog();
        if (bean instanceof UserIndexResponseBean) {
//            avatar_url = ((UserIndexResponseBean) bean).getAvatar();
//            SPUtil.insert(getBaseActivity(),SPUtil.USER_AVATAR,avatar_url);
            nike_name = ((UserIndexResponseBean) bean).getNick_name();
            SPUtil.insert(getBaseActivity(), SPUtil.USER_NICKNAME, nike_name);
        } else {//上传头像
            ToastUtils.showCustomToast("头像更新成功", 1);
        }
    }

    @Override
    public void onRequestFailed(String msg) {
        dismissLoadingDialog();
        ToastUtils.showCustomToast(msg, 0);
    }

    private void showAvatar() {
        String avatarUrl = (String) SPUtil.get(getBaseActivity(), SPUtil.USER_AVATAR, "");
        //显示头像
        GlidImageManager.getInstance().loadCircleImg(getContext(), avatarUrl, imgAvatar, R.mipmap.head, R.mipmap.head);
    }


    @Override
    public void onUploadFileSuccess(UploadFileResponseBean fileResponseBean) {
        avatarUrl=fileResponseBean.getUrl();
        String fullUrl = fileResponseBean.getFull_url();
        SPUtil.insert(getBaseActivity(), SPUtil.USER_AVATAR, fullUrl);
        doChangeAvatarRuest(avatarUrl);//使用上传图片成功以后的绝对路径
    }

    @Override
    public void onUploadFileFailed(String msg) {
        ToastUtils.showCustomToast(msg, 0);
    }

    @Override
    public void onViewClicked() {
        initPopupView();
    }

    @Override
    public void initPopupView() {
        if (popupWindow == null) {
            popupWindow = new PicturePopupWindow(getActivity());
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
//        final BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
//        btnImg.setBackground(bitmapDrawable);
        GlidImageManager.getInstance().loadCircleImg(getActivity(),
                PictureFileUtil.Uri2File(avatarUri,getActivity()),
                imgAvatar,
                R.mipmap.head,
                R.mipmap.head);
        doUploadFileRuest();
    }

    @Override
    public void showPopupView() {
        View parent = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_personal, null);
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
        intent.setClass(getActivity(), PictureSettingActivity.class);
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
            Uri uri = getActivity().getContentResolver().insert(MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            avatarUri = uri;
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            avatarUri = Uri.fromFile(tempFile);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public Activity getCtx() {
        return getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        picturePresenter.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            case PicturePresenter.REQUEST_PHOTO:  //调用系统相册返回
                if (resultCode == Activity.RESULT_OK) {
                    avatarUri = intent.getData();
                }
                break;
        }

        if (avatarUri == null) {
            return;
        }
        String cropImagePath = PictureFileUtil.getRealFilePathFromUri(getActivity(), avatarUri);
        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
        if (bitMap != null) {
            showView(bitMap);
        }
    }


}
