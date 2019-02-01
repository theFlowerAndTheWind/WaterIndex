package com.jieshuizhibiao.waterindex.http.config;

/**
 * Created by WanghongHe on 2018/12/3 12:23.
 */

public class UrlConfig {
    //登录
    /**
     * {
     * "code":1,
     * "msg":"登录成功",
     * "data":{
     * "id":14,
     * "is_blocked":0,
     * "user_login":"18329257177",
     * "avatar":"http:\/\/jsl.sshsky.com\/upload\/",
     * "user_nickname":"",
     * "token":"880486ea9371415e52b8ef0878b743fa7b4ea77fa1a8dd89ec9067c6e8a63129"
     * }
     */
    public final static String LOGIN = "/api/home/public/login";
    //登录
    /**
     * 获取短信验证码
     * {
     * "code":1,
     * "msg":"操作成功",
     * "data":"4444"
     * }
     */
    public final static String SEND_VER_CODE = "api/home/public/sendVercode";
    //登录
    /***
     * 注册
     *
     * {
     *     "code":1,
     *    "msg":"注册成功",
     *     "data":{
     *        "uid":"14",
     *        "user_login":"18329257177",
     *        "token":"d6bdbc551eb58413b80cd619cc9c4e65d19cedfb02ed04699cba16906f1c21a6"
     *    }
     * }
     */
    public final static String REGISTER = "api/home/public/register";
    //找回密码
    public static final String FIND_PASS = "api/home/public/findPass";
    //身份认证-企业
    public static final String COMPANY_AUTH = "api/home/public/companyAuth";

    //平台咨询详情
    public static final String INFO_DETAIL = "api/home/info/infoDetail";
    //用户信息
    /**
     * 用户信息
     * {
     * "code":1,
     * "msg":"操作成功",
     * "data":{
     * "user_login":"18329257177",
     * "create_time":"2018-12-13",
     * "avatar":"http:\/\/www.quanminjieshui.com\/upload\/",
     * "user_type":"企业",
     * "is_blocked":0,
     * "user_status":1
     * }
     * }
     */
    public static final String USER_DETAIL = "api/home/user/userDetail";
    //用户信息
    /**
     * 我的资产
     * <p>
     * {
     * "code":1,
     * "msg":"操作成功",
     * "data":{
     * "jsl":"0.16908",
     * "jsl_freeze":"0.00000",
     * "ds":"0.42439",
     * "ds_freeze":"0.00000",
     * "jsl_lock_view":"0.00000",
     * "jsl_gyj":"0.00000"
     * }
     * }
     */
    public static final String USER_ACCOUNT = "api/home/user/accountDetail";
    //用户身份证认证信息
    /**
     * 用户身份证认证信息
     * {
     * "code":1,
     * "msg":"操作成功",
     * "data":{
     * "user_status":"待审核",
     * "user_name":"",
     * "id_no":"",
     * "company_name":"公司名称",
     * "company_license_no":"123456789",
     * "user_type":2
     * }
     * }
     */
    public static final String AUTH_DETAIL = "api/home/user/authDetail";
    //交易明细
    public static final String TRADE_DETAIL = "api/home/user/tradeDetail";
    /**
     * 修改密码--安全中心
     */
    public static final String CHANGE_PASS = "api/home/user/editPw";
    /**
     * 系统消息
     */
    public static final String SYSMSG = "api/home/user/sysMsg";

    /**
     * 我的资产
     * {
     * "code":1,
     * "msg":"用户资产",
     * "data":{
     * "c2c":{
     * "ds":"302.00000T",
     * "ds_freeze":"0.00000T",
     * "total":"302.00000T",
     * "rmb":"906.00000元"
     * },
     * "bb":{
     * "ds":"1523.68243T",
     * "ds_freeze":"0.00011T",
     * "total":"1523.68254T",
     * "rmb":"4571.04762元"
     * }
     * }
     * }
     */
    public static final String USER_MONEY = "api/home/user/money";

    /**
     * 交易市场
     * {
     * "code":1,
     * "msg":"操作成功！",
     * "data":{
     * "total":17,
     * "page":1,
     * "list":[
     * {
     * "trade_id":7,
     * "trade_uid":37,
     * "avatar":"",
     * "user_nickname":"看你不爽",
     * "sold":"0",
     * "sold_rate":"0%",
     * "total":"300.00000T",
     * "pay_type_bank_card":"1",
     * "pay_type_alipay":"0",
     * "pay_type_wechat":"1",
     * "pay_min":"10.00000T",
     * "pay_timeout":0
     * },
     * {
     * "trade_id":12,
     * "trade_uid":52,
     * "avatar":"admin\/20190108\/907251bff177cd8052086d3e497e013a.jpg",
     * "user_nickname":"小白",
     * "sold":"0",
     * "sold_rate":"0%",
     * "total":"1.00000T",
     * "pay_type_bank_card":"1",
     * "pay_type_alipay":"1",
     * "pay_type_wechat":"1",
     * "pay_min":"2.00000T",
     * "pay_timeout":0
     * }
     * ],
     * "is_login":"0",
     * "is_auth":0,
     * "tip":"在开始交易之前，您需要完善必要的交易信息"
     * }
     * }
     */
    public static final String TRADE_INDEX = "api/home/trade/index";

    /**
     * 用户下单
     * {
     * "code":1,
     * "msg":"下单成功！",
     * "data":"57"
     * }
     */
    public static final String USER_ORDER = "api/home/trade/userOrder";

    /**
     * 订单列表
     * {
     * "code":1,
     * "msg":"订单列表",
     * "data":[
     * {
     * "my_action":"出售",
     * "other_uid":8,
     * "other_nickname":"bb",
     * "other_avatar":"222",
     * "createtime":"2018-12-13 18:55:00",
     * "status_text":"已完成",
     * "total":"3.10000T",
     * "rmb":"9.30000元",
     * "order_id":1,
     * "next_step":"sellerSucc"
     * },
     * {
     * "my_action":"出售",
     * "other_uid":8,
     * "other_nickname":"bb",
     * "other_avatar":"222",
     * "createtime":"2018-12-14 17:29:54",
     * "status_text":"已取消",
     * "total":"3.00000T",
     * "rmb":"9.00000元",
     * "order_id":3,
     * "next_step":"sellerCancel"
     * },
     * {
     * "my_action":"出售",
     * "other_uid":8,
     * "other_nickname":"bb",
     * "other_avatar":"222",
     * "createtime":"2018-12-17 19:44:55",
     * "status_text":"进行中",
     * "total":"3.00000T",
     * "rmb":"9.00000元",
     * "order_id":5,
     * "next_step":"sellerPaid"
     * },
     * {
     * "my_action":"出售",
     * "other_uid":52,
     * "other_nickname":"小白",
     * "other_avatar":"admin\/20190109\/25f10b6a8d35bf46968f42cb4acaa290.jpg",
     * "createtime":"2019-01-05 13:25:27",
     * "status_text":"申诉处理",
     * "total":"3.00000T",
     * "rmb":"9.00000元",
     * "order_id":21,
     * "next_step":"sellerAppeal"
     * },
     * {
     * "my_action":"购买",
     * "other_uid":8,
     * "other_nickname":"bb",
     * "other_avatar":"222",
     * "createtime":"2018-12-21 15:46:25",
     * "status_text":"已取消",
     * "total":"3.00000T",
     * "rmb":"9.00000元",
     * "order_id":6,
     * "next_step":"buyerCancel"
     * }
     * ]
     * }
     */
    public static final String LISTORDER = "api/home/userOrder/listOrder";

    /**
     * 买家身份-未付款
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"购买节水指标",
     * "expire_time":"不限制",
     * "rmb":"9.00000元",
     * "total":"3.00000T",
     * "price":"3.00000元\/T",
     * "order_sn":"20190110182211322408",
     * "pay_code":"322408",
     * "createtime":"2019-01-10 18:22:11",
     * "seller_avatar":"https:\/\/www.jieshuizhibiao.com\/upload\/admin\/20190109\/277a2b3341ebbf2a70bf755c0774361e.jpg",
     * "seller_nickname":"丁香满文轩",
     * "order_id":"59"
     * },
     * "pay_info_list":[
     * {
     * "id":10,
     * "uid":39,
     * "type":2,
     * "user_name":"萨文轩",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"18658900249",
     * "qrcode":"admin\/20190108\/ddbded5a8c787ea8108d77aadc25de2f.jpeg",
     * "add_time":1546142739,
     * "status":1
     * },
     * {
     * "id":14,
     * "uid":39,
     * "type":1,
     * "user_name":"萨文轩",
     * "bank_name":"北京银行",
     * "bank_detail_name":"朝阳支行",
     * "account_name":"6227001142040082275",
     * "qrcode":"",
     * "add_time":1546917730,
     * "status":1
     * }
     * ]
     * }
     * }
     */
    public static final String BUYER_UNPAY = "api/home/userOrder/buyerUnpay";

    /**
     * 卖家身份-未收款
     */
    public static final String BUYER_PAID = "api/home/userOrder/buyerPaid";

    /**
     * 买家身份-取消   卖家身份没有取消订单操作
     * {
     * "code":1,
     * "msg":"取消成功",
     * "data":""
     * }
     */
    public static final String BUYER_DO_CANCEL = "api/home/userOrder/buyerCancel";

    /**
     * 买家身份-申诉
     * {
     * "code":1,
     * "msg":"申诉成功",
     * "data":""
     * }
     */
    public static final String BUYER_DO_APPEAL = "api/home/userOrder/buyerAppeal";

    /**
     * 买家身份-支付
     */
    public static final String BUYER_DO_PAY = "api/home/userOrder/buyerPay";

    /**
     * 买家身份-交易成功
     */
    public static final String BUYER_SUCC = "api/home/userOrder/buyerSuccInfo";

    /**
     * 买家身份-申诉处理中
     */
    public static final String BUYER_APPEAL = "api/home/userOrder/buyerAppealInfo";

    /**
     * 买家身份-交易取消
     */
    public static final String BUYER_CANCEL = "api/home/userOrder/buyerCancelInfo";

    /**
     * 卖家身份-未付款
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"出售节水指标",
     * "expire_time":"不限制",
     * "rmb":"3.00000元",
     * "total":"1.00000T",
     * "price":"3.00000元\/T",
     * "order_sn":"20190110153159246886",
     * "pay_code":"246886",
     * "createtime":"2019-01-10 15:31:59",
     * "buyer_avatar":"https:\/\/www.jieshuizhibiao.com\/upload\/admin\/20190111\/2cb733cf51ef8fe5f9c955bc78919c6c.jpg",
     * "buyer_nickname":"石头",
     * "order_id":"57"
     * },
     * "pay_info_list":[
     * {
     * "id":2,
     * "uid":6,
     * "type":3,
     * "user_name":"",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"",
     * "qrcode":"",
     * "add_time":0,
     * "status":1
     * },
     * {
     * "id":3,
     * "uid":6,
     * "type":1,
     * "user_name":"",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"",
     * "qrcode":"",
     * "add_time":0,
     * "status":1
     * }
     * ]
     * }
     * }
     */
    public static final String SELLER_UNPAY = "api/home/userOrder/sellerUnpay";

    /**
     * 卖家身份-确认订单
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"收款",
     * "rmb":"9.00000元",
     * "total":"3.00000T",
     * "price":"3.00000元\/T",
     * "pay_type":"3",
     * "order_sn":"20181217141135485517",
     * "pay_code":"485517",
     * "createtime":"2018-12-17 19:44:55",
     * "paytime":"2018-12-17 16:12:55",
     * "buyer_avatar":"https:\/\/www.jieshuizhibiao.com\/upload\/222",
     * "buyer_nickname":"bb",
     * "order_id":"5",
     * "pay_snapshot":"https:\/\/www.jieshuizhibiao.com\/upload\/123123123"
     * },
     * "pay_info":{
     * "id":2,
     * "uid":6,
     * "type":3,
     * "user_name":"",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"",
     * "qrcode":"",
     * "add_time":0,
     * "status":1
     * }
     * }
     * }
     */
    public static final String SELLER_PAID = "api/home/userOrder/sellerPaid";

    /**
     * 卖家身份-申诉
     */
    public static final String SELLER_DO_APPEAL = "api/home/userOrder/sellerAppeal";

    /**
     * 卖家身份-放行
     */
    public static final String SELLER_CHECKOUT = "api/home/userOrder/sellerCheckout";

    /**
     * 卖家身份-交易成功
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"交易成功",
     * "rmb":"9.30000元",
     * "total":"3.10000T",
     * "price":"3.00000元\/T",
     * "pay_type":"3",
     * "order_sn":"20181213185500910092",
     * "pay_code":"910092",
     * "createtime":"2018-12-13 18:55:00",
     * "paytime":"1970-01-01 08:00:00",
     * "appealtime":"2018-12-14 13:48:07",
     * "buyer_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/222",
     * "buyer_nickname":"bb",
     * "seller_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/20190108\/37d2a843bb73642738cf3bdcc082c550.png",
     * "seller_nickname":"sss",
     * "order_id":"1"
     * },
     * "pay_info":{
     * "id":2,
     * "uid":6,
     * "type":3,
     * "user_name":"",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"",
     * "qrcode":"",
     * "add_time":0,
     * "status":1
     * }
     * }
     * }
     */
    public static final String SELLER_SUCC = "api/home/userOrder/sellerSuccInfo";

    /**
     * 卖家身份-申诉处理中
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"申诉处理中",
     * "rmb":"9.00000元",
     * "total":"3.00000T",
     * "price":"3.00000元\/T",
     * "pay_type":"3",
     * "order_sn":"20181217141135485517",
     * "pay_code":"485517",
     * "createtime":"2018-12-17 19:44:55",
     * "paytime":"2018-12-17 16:12:55",
     * "appealtime":"2019-01-17 20:28:11",
     * "buyer_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/222",
     * "buyer_nickname":"bb",
     * "seller_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/20190108\/37d2a843bb73642738cf3bdcc082c550.png",
     * "seller_nickname":"sss",
     * "order_id":"5"
     * },
     * "pay_info":{
     * "id":2,
     * "uid":6,
     * "type":3,
     * "user_name":"",
     * "bank_name":"",
     * "bank_detail_name":"",
     * "account_name":"",
     * "qrcode":"",
     * "add_time":0,
     * "status":1
     * }
     * }
     * }
     */
    public static final String SELLER_APPEAL = "api/home/userOrder/sellerAppealInfo";

    /**
     * 卖家身份-交易取消
     * {
     * "code":1,
     * "msg":"订单详情",
     * "data":{
     * "order_info":{
     * "title":"交易已取消",
     * "rmb":"9.00000元",
     * "total":"3.00000T",
     * "price":"3.00000元\/T",
     * "pay_type":"",
     * "order_sn":"20181214172954550831",
     * "pay_code":"550831",
     * "createtime":"2018-12-14 17:29:54",
     * "paytime":"1970-01-01 08:00:00",
     * "appealtime":"1970-01-01 08:00:00",
     * "buyer_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/222",
     * "buyer_nickname":"bb",
     * "seller_avatar":"http:\/\/www.jieshuizhibiao.com\/upload\/20190108\/37d2a843bb73642738cf3bdcc082c550.png",
     * "seller_nickname":"sss",
     * "order_id":"3"
     * }
     * }
     * }
     */
    public static final String SELLER_CANCEL = "api/home/userOrder/sellerCancelInfo";

    /**
     * 基础信息    节水指标单价
     */
    public static final String SYS_CONFIG = "api/home/common/sysConfig";

    /**
     * 身份认证
     */
    public static final String USER_AUTH_INFO = "api/home/user/userAuthInfo";

    /**
     * 文件上传
     */
    public static final String UPLOAD = "api/user/Upload/one";

    /**
     * 更换头像
     */
    public static final String AVATAR = "api/home/user/setAvatar";

    /**
     * 基本资料
     */
    public static final String USER_INFO = "api/home/user/info";

    /**
     * 身份认证-个人
     */
    public static final String PERSONAL_AUTH = "api/home/user/userAuth";

    /**
     * 买卖需求 - 列表
     */
    public static final String LIST_TRADE = "api/home/userTrade/listTrade";
    /**
     * 获取H5链接
     */
    public static final String GET_URL = "api/home/common/getUrl";

    /**
     * 修改资金密码
     */
    public static final String CHANGE_CAPITAL_PASS = "api/home/user/editSafePw";

    /**
     * 设置资金密码
     */
    public static final String SET_CAPITAL_PASS = "api/home/user/safePw";

    /**
     * 用户首页
     */
    public static final String USER_INDEX = "api/home/user/index";

    /**
     * 移交资产
     * {
     * "code":1,
     * "msg":"划转成功",
     * "data":{
     * "total":"5",
     * "arrive":"5",
     * "gyj":"0.00000"
     * }
     * }
     */
    public static final String MOVE_MONERY = "api/home/user/mvMoney";

    /**
     * 收款方式
     * "data":{
     * "1":{
     * "id":0,
     * "type":1,
     * "isopen":0,
     * "link_text":"添加",
     * "type_text":"银行",
     * "account_name":"未添加"
     * },
     * "2":{
     * "id":19,
     * "type":2,
     * "isopen":1,
     * "link_text":"编辑",
     * "type_text":"支付宝",
     * "account_name":"3685269949@qq.com"
     * },
     * "3":{
     * "id":18,
     * "type":3,
     * "isopen":1,
     * "link_text":"编辑",
     * "type_text":"微信",
     * "account_name":"wx13718478437"
     * }
     * }
     */
    public static final String PAYMENT_TYPE = "api/home/userPayInfo/index";

    /**
     * 添加收款方式
     */
    public static final String ADD_PAYMENT_TYPE = "api/home/userPayinfo/addPayInfo";

    /**
     * 收款方式 - 开启|禁用
     */
    public static final String PAYMENT_TYPE_SWITCH = "api/home/userPayinfo/switchPayInfo";

    /**
     * 收款方式 - 修改
     */
    public static final String CHANGE_PAYMENT_TYPE = "api/home/userPayinfo/editPayInfo";

    /**
     * 买卖需求-发布
     */
    public static final String ADD_TRADE = "api/home/userTrade/addTrade";

    /**
     * 买卖需求-下架
     */
    public static final String DELETE_TRADE = "api/home/userTrade/delTrade";

    /**
     * 系统消息
     * * "lists":[
     * {
     * "id":6,
     * "add_time":"2018-12-08 17:47:49",
     * "content":"完成首次交易，系统赠送10.00000个JSL"
     * },
     * {
     * "id":4,
     * "add_time":"2018-12-08 16:49:11",
     * "content":"通过身份认证，系统赠送10.00000个JSL"
     * },
     * {
     * "id":2,
     * "add_time":"2018-12-08 16:46:24",
     * "content":"通过身份认证，系统赠送10.00000个JSL"
     * },
     * {
     * "id":1,
     * "add_time":"2018-12-08 16:44:50",
     * "content":"通过身份认证，系统赠送10.00000个JSL"
     * }
     * ]
     */
    public static final String SYSTEM_MSSAGE = "api/home/user/sysMsg";

    /**
     * 资产划转-划转前确认
     * {
     * "code":1,
     * "msg":"确认转账金额",
     * "data":{
     * "total_ds":"5",
     * "res_ds":"5",
     * "gyj":"0.00000"
     * }
     * }
     */
    public static final String BEFORE_MOVE_MONERY = "api/home/user/beforeMvMoney";

    /**
     * 版本检查
     */
    public static final String APP_VERSION = "api/home/public/ver";
}
