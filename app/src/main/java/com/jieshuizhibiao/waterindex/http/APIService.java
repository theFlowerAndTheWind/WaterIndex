package com.jieshuizhibiao.waterindex.http;

import com.jieshuizhibiao.waterindex.beans.AccountDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.AuthDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.BeforeMvMoneyResponse;
import com.jieshuizhibiao.waterindex.beans.GetUrlResponseBean;
import com.jieshuizhibiao.waterindex.beans.ListOrder;
import com.jieshuizhibiao.waterindex.beans.ListTradeResponseBean;
import com.jieshuizhibiao.waterindex.beans.LoginResponseBean;
import com.jieshuizhibiao.waterindex.beans.MoveMoneryBean;
import com.jieshuizhibiao.waterindex.beans.PayMentResponseBean;
import com.jieshuizhibiao.waterindex.beans.RegisterResponseBean;
import com.jieshuizhibiao.waterindex.beans.SysConfigResponseBean;
import com.jieshuizhibiao.waterindex.beans.SysMsgBase;
import com.jieshuizhibiao.waterindex.beans.SystemMsgResponseBean;
import com.jieshuizhibiao.waterindex.beans.TradeIndexBase;
import com.jieshuizhibiao.waterindex.beans.UploadFileResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserAuthInfo;
import com.jieshuizhibiao.waterindex.beans.UserDetailResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserIndexResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserInfoResponseBean;
import com.jieshuizhibiao.waterindex.beans.UserMoney;
import com.jieshuizhibiao.waterindex.beans.appeal.AppealResponse;
import com.jieshuizhibiao.waterindex.beans.cancel.CancelResponse;
import com.jieshuizhibiao.waterindex.beans.paid.BuyerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.paid.SellerPaidResponse;
import com.jieshuizhibiao.waterindex.beans.succ.SuccResponse;
import com.jieshuizhibiao.waterindex.beans.unpay.BuyerUnpayResponse;
import com.jieshuizhibiao.waterindex.beans.unpay.SellerUnpayResponse;
import com.jieshuizhibiao.waterindex.http.bean.BaseEntity;
import com.jieshuizhibiao.waterindex.http.config.UrlConfig;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by WanghongHe on 2018/12/3 11:41.
 * post 对应@Body 和get 对应@Query
 */

public interface APIService {

    //登录接口
    @POST(UrlConfig.LOGIN)
    Observable<BaseEntity<LoginResponseBean>> login(@Body RequestBody requestBody);

//    @GET(UrlConfig.getMessageList)
//    Observable<BaseEntity<MessageBean>> getMessageList(@Query("currentPage") String currentPage, @Query("pageSize") String pageSize);  //消息列表

    //获取验证码
    @POST(UrlConfig.SEND_VER_CODE)
    Observable<BaseEntity> getSms(@Body RequestBody requestBody);

    //注册
    @POST(UrlConfig.REGISTER)
    Observable<BaseEntity<RegisterResponseBean>> register(@Body RequestBody requestBody);

    //忘记密码
    @POST(UrlConfig.FIND_PASS)
    Observable<BaseEntity> findPass(@Body RequestBody requestBody);

    //企业认证
    @POST(UrlConfig.COMPANY_AUTH)
    Observable<BaseEntity> companyAuth(@Body RequestBody body);

    //个人认证
    @POST(UrlConfig.PERSONAL_AUTH)
    Observable<BaseEntity> personalAuth(@Body RequestBody body);

    //修改密码
    @POST(UrlConfig.CHANGE_PASS)
    Observable<BaseEntity> changePass(@Body RequestBody requestBody);

    //成交明细
    @POST(UrlConfig.TRADE_DETAIL)
    Observable<BaseEntity> tradeDetail(@Body RequestBody requestBody);

    //用户身份认证信息
    @POST(UrlConfig.AUTH_DETAIL)
    Observable<BaseEntity<AuthDetailResponseBean>> authDetail(@Body RequestBody requestBody);

    //我的资产
    @POST(UrlConfig.USER_ACCOUNT)
    Observable<BaseEntity<AccountDetailResponseBean>> accountDetail(@Body RequestBody requestBody);

    //用户信息
    @POST(UrlConfig.USER_DETAIL)
    Observable<BaseEntity<UserDetailResponseBean>> userDetail(@Body RequestBody requestBody);

    //平台咨询详情
    @POST(UrlConfig.INFO_DETAIL)
    Observable<BaseEntity> infoDetail(@Body RequestBody requestBody);

    @POST(UrlConfig.SYSMSG)
    Observable<BaseEntity<SysMsgBase>> sysMsg(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     */
    @POST(UrlConfig.USER_MONEY)
    Observable<BaseEntity<UserMoney>> userMoney(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     * "type", "1";
     * "page", "1";
     * "page_size", "10";
     */
    @POST(UrlConfig.TRADE_INDEX)
    Observable<BaseEntity<TradeIndexBase>> tradeIndex(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     * "trade_id", "1";
     * "total", "1";
     */
    @POST(UrlConfig.USER_ORDER)
    Observable<BaseEntity> userOrder(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "type", "0";
     * "page", "1";
     * "page_size", "10";
     */
    @POST(UrlConfig.LISTORDER)
    Observable<BaseEntity<List<ListOrder>>> listOrder(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_UNPAY)
    Observable<BaseEntity<BuyerUnpayResponse>>buyerUnpay(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_PAID)
    Observable<BaseEntity<BuyerPaidResponse>>buyerPaid(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_DO_CANCEL)
    Observable<BaseEntity> buyerCancle(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     * "detail","未收到卖家放行"
     */
    @POST(UrlConfig.BUYER_DO_APPEAL)
    Observable<BaseEntity>buyerDoAppeal(@Body RequestBody body);

    /**
     * "token":token
     * "device_type":"android"
     * "order_id":"111"
     * "pi_id":
     * "pay_snapshot":"asdfadfasdf"
     */
    @POST(UrlConfig.BUYER_DO_PAY)
    Observable<BaseEntity>buyerDoPay(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_SUCC)
    Observable<BaseEntity<SuccResponse>>buyerSucc(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_APPEAL)
    Observable<BaseEntity<AppealResponse>>buyerAppeal(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.BUYER_CANCEL)
    Observable<BaseEntity<CancelResponse>>buyerCancel(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.SELLER_UNPAY)
    Observable<BaseEntity<SellerUnpayResponse>>sellerUnpay(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.SELLER_PAID)
    Observable<BaseEntity<SellerPaidResponse>>sellerPaid(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     * "detail","未收到买家付款"
     */
    @POST(UrlConfig.SELLER_DO_APPEAL)
    Observable<BaseEntity>sellerDoAppeal(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     * "safe_pw","******"
     */
    @POST(UrlConfig.SELLER_CHECKOUT)
    Observable<BaseEntity>sellerCheckout(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.SELLER_SUCC)
    Observable<BaseEntity<SuccResponse>>sellerSucc(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.SELLER_APPEAL)
    Observable<BaseEntity<AppealResponse>>sellerAppeal(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "order_id", "111";
     */
    @POST(UrlConfig.SELLER_CANCEL)
    Observable<BaseEntity<CancelResponse>>sellerCancel(@Body RequestBody body);

    /**
     * "token", token;
     * "device_type", "android";
     * "param", "ds_price";
     */
    @POST(UrlConfig.SYS_CONFIG)
    Observable<BaseEntity<SysConfigResponseBean>> sysConfig(@Body RequestBody body);


    /**
     * "token", token;
     * "device_type", "android";
     */
    @POST(UrlConfig.USER_AUTH_INFO)
    Observable<BaseEntity<UserAuthInfo>> userAuthInfo(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     * "file",File
     */
    @Multipart
    @POST(UrlConfig.UPLOAD)
    Observable<BaseEntity<UploadFileResponseBean>>uploadFile(@Part("token") RequestBody token,
                                                             @Part("device_type") RequestBody device_type,
                                                             @Part MultipartBody.Part file);


    /**
     * "token", token;
     * "device_type", "android";
     * "avatar","url"
     */
    @POST(UrlConfig.AVATAR)
    Observable<BaseEntity> setAvatar(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     */
    @POST(UrlConfig.USER_INFO)
    Observable<BaseEntity<UserInfoResponseBean>> userInfo(@Body RequestBody requestBody);

    /**
     * "token", token;
     * "device_type", "android";
     * "type", "0";
     * "page", "1";
     * "page_size", "10";
     * 买卖需求列表
     */
    @POST(UrlConfig.LIST_TRADE)
    Observable<BaseEntity<ListTradeResponseBean>> listTrade(@Body RequestBody requestBody);

    @POST(UrlConfig.GET_URL)
    Observable<BaseEntity<GetUrlResponseBean>>getUrl(@Body RequestBody body);

    /**
     * 修改资金密码
     * @param requestBody
     * @return
     */
    @POST(UrlConfig.CHANGE_CAPITAL_PASS)
    Observable<BaseEntity> changeCapitalPass(@Body RequestBody requestBody);

    /**
     * 设置资金密码
     * @param requestBody
     * @return
     */
    @POST(UrlConfig.SET_CAPITAL_PASS)
    Observable<BaseEntity> setCapitalPass(@Body RequestBody requestBody);

    /**
     * 用户首页
     */
    @POST(UrlConfig.USER_INDEX)
    Observable<BaseEntity<UserIndexResponseBean>> userIndex(@Body RequestBody requestBody);

    /**
     * 资产划转
     */
    @POST(UrlConfig.MOVE_MONERY)
    Observable<BaseEntity<MoveMoneryBean>> moveMonery(@Body RequestBody requestBody);

    /**
     * 收款方式--首页
     * token
     * device_type
     */
    @POST(UrlConfig.PAYMENT_TYPE)
    Observable<BaseEntity<PayMentResponseBean>> payMentType(@Body RequestBody requestBody);

    /**
     * 添加收款方式
     */
    @POST(UrlConfig.ADD_PAYMENT_TYPE)
    Observable<BaseEntity> addPayMentType(@Body RequestBody requestBody);

    /**
     * 收款方式 开启|禁用
     */
    @POST(UrlConfig.PAYMENT_TYPE_SWITCH)
    Observable<BaseEntity> payMentTypeSwitch(@Body RequestBody requestBody);

    /**
     * 收款方式 修改
     */
    @POST(UrlConfig.CHANGE_PAYMENT_TYPE)
    Observable<BaseEntity> changePayMentType(@Body RequestBody requestBody);

    /**
     * 买卖需求列表--发布
     */
    @POST(UrlConfig.ADD_TRADE)
    Observable<BaseEntity> addTrade(@Body RequestBody requestBody);

    /**
     * 买卖需求列表--下架
     */
    @POST(UrlConfig.DELETE_TRADE)
    Observable<BaseEntity> delTrade(@Body RequestBody requestBody);

    /**
     * 系统消息
     */
    @POST(UrlConfig.SYSTEM_MSSAGE)
    Observable<BaseEntity<SystemMsgResponseBean>> systemMsg(@Body RequestBody requestBody);

    /**
     * 资产划转钱确认:资产划转-划转前确认 参数和返回与资产划转一样!!!
     */
    @POST(UrlConfig.BEFORE_MOVE_MONERY)
    Observable<BaseEntity<BeforeMvMoneyResponse>> beforeMvMoney(@Body RequestBody requestBody);

    /**
     * 版本更新检查
     */
    @POST(UrlConfig.APP_VERSION)
    Observable<BaseEntity> appUpdate(@Body RequestBody requestBody);
}

