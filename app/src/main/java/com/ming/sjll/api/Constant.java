package com.ming.sjll.api;


public class Constant {
    public static final String BASE_API = "http://api.coolpaishop.com";
    public static final String BASE_IMAGE = "http://api.coolpaishop.com";
    public static final String WxPayAppId = "wxcd430c1c832a0dc4";


    /**
     * 项目订单：用户同意加入    (项目邀请：同意，取消   (邀请加入))
     */
    public static final String INVITEPASS = "/apiv2/user_operation/invitePass";

    /**
     * 项目订单：公司 （同意成员加入）
     */
    public static final String ORDER_INVITE_COMPANY_PASS = "/apiv2/order/inviteCompanyPass";


    //用户协议
    public static final String USER_PROTOCOL = "/web/protocol/show";

    /**
     * 图片上传
     */
    public static final String UPLOAD = "/api/common/upload";
    /**
     * 视频上传
     */
    public static final String UPLOADVIDEO = "/api/common/uploadVideo";
    /**
     * 多图片上传
     */
    public static final String UPLOADIMG = "/api/common/uploadImg";

    /**
     * 分享
     */
    public static final String SHARE_USER_SHARE = "/api/share/userShare";


    /**
     * 首页问候语
     */
    public static final String WELCOME = "/apiv2/home/greetings";

    public static final String INFORM_SYS_MSG = "/api/Inform/sysMsg";//通知

    public static final String PAY_INITIATE_PAY = "/api/pay/InitiatePay";//拉取支付宝和微信的支付信息
    public static final String PAY_ORDER_INFO = "/api/order/orderInfo";//获取订单页面详细信息
    public static final String PAY_PAY_COMPLETED = "/api/pay/payCompleted";//公司账户支付点击已完成

    public static final String USER_CHECK_PHONE = "/apiv2/user/checkPhone";//检查手机号是否注册
    public static final String USER_SENDCODE = "/apiv2/user/sendCode";//发送验证码
    public static final String USER_LOGIN = "/apiv2/user/login";//用户密码账号登录
    public static final String USER_GETDEFAULTAVATAR = "/apiv2/user/getDefaultAvatar";//得到系统默认头像
    public static final String USER_FORGOT_PWD = "/apiv2/user/forgotPwd";//忘记密码
    public static final String USER_CHECK_CODE = "/apiv2/user/checkCode";//检测注册验证码是否正确

    public static final String QUICKLOGIN = "/apiv2/user/quickLogin";//快速登录
    public static final String REGISTER = "/apiv2/user/register";//注册


    public static final String HOME_ADS_BANNER = "/apiv2/home/adsBanner";//首页轮播广告
    public static final String HOME_GREETINGS = "/apiv2/home/greetings";//首页问候语


    public static final String PROJECT_PROJECT = "/apiv2/project/project";//项目综合（搜索）
    public static final String PROJECT_NOTICE = "/apiv2/project/notice";//项目通告（搜索）

    public static final String PROJECT_GET_BG_IMG = "/apiv2/project/getBgImg";//项目背景图

    public static final String PROJECT_GETOCCUPATION = "/apiv2/project/getOccupation";//职业列表

    public static final String PROJECT_MANAGE_MY_PROJECT = "/apiv2/project_manage/myProject";//我的项目管理列表
    public static final String PROJECT_SAVE_PROJECT = "/apiv2/project/saveProject";//编辑保存项目
    public static final String PROJECT_PROJECT_INFO = "/apiv2/project/projectInfo";//项目详情
    public static final String PROJECT_PROJECT_LIST = "/apiv2/project/projectList";//    得到所有接单项目
    public static final String PROJECT_MY_PROJECT = "/apiv2/project/myProject";//    我的项目列表
    public static final String PROJECT_DEL_PROJECT = "/api/project/delProject";//    删除项目



    public static final String USER_WORK_GET_WORKS_LIST = "/apiv2/user_work/getWorksList";//个人作品列表




    public static final String HOME_SHOW = "/apiv2/home/show";//我的项目管理列表


    public static final String RC_GET_INFO = "/apiv2/rc/getInfo";//消息的用戶信息
    /**
     * 获取聊天分享消息列表
     */
    public static final String RC_GET_CHAT_LIST = "/api/rc/getChatList";
    /**
     * 分享接口
     */
    public static final String SHARE_SHARE_APP_MSG = "/api/share/shareAppMsg";


    public static final String USER_WORK_SAVE_WORK = "/apiv2/user_work/saveWork";//作品编辑添加
    public static final String USER_WORK_DEL_WORK = "/apiv2/user_work/delWork";//作品删除


    public static final String USER_APPROVE_GET_MODEL_INFO = "/apiv2/user_approve/getModelInfo";//获取身材信息
    public static final String USER_APPROVE_SAVE_FACE_INFO = "/apiv2/user_approve/saveFaceInfo";//保存身材信息
    public static final String MEMBER_TAGS_LIST = "/apiv2/member/tagsList";//默认标签
    public static final String MEMBER_USER_CENTER = "/apiv2/member/userCenter";//获取个人信息
    public static final String MEMBER_SAVE_USER_INFO = "/apiv2/member/saveUserInfo";//保存个人信息
    public static final String MEMBER_GET_USER_TYPE = "/apiv2/member/getUserType";//得到用户账号类型
    public static final String MEMBER_SELECT_ROLE = "/apiv2/member/selectRole";//个人与公司账号切换
    public static final String MEMBER_CHECK_COMPANY = "/apiv2/member/checkCompany";//检测用户公司状态
    public static final String MEMBER_SAVE_TAGS = "/apiv2/member/saveTags";//添加、删除个人标签
    public static final String MEMBER_SCHEDULE_DATE = "/apiv2/member/scheduleDate";//个人有排期的日期
    public static final String MEMBER_MEMBER_SCHEDULE = "/apiv2/member/memberSchedule";//个人有排期的日期
    public static final String MEMBER_ADD_SCHEDULE = "/apiv2/member/addSchedule";//添加、修改个人自添项目排期
    public static final String MEMBER_SCHEDULE_INFO = "/apiv2/member/scheduleInfo";//自添项目排期详情
    public static final String MEMBER_DEL_SCHEDULE = "/apiv2/member/delSchedule";//删除自添项目排期




    public static final String COMPANY_MEMBER_GET_AUTH = "/apiv2/company_member/getAuth";//得到人员权限
    public static final String COMPANY_MEMBER_SET_AUTH = "/apiv2/company_member/setAuth";//设置人员权限
    public static final String MEMBER_COMPANY_MEMBER = "/apiv2/company/companyMember";//公司同事
    public static final String COMPANY_MEMBER_MEMBER_LIST = "/apiv2/company_member/memberList";//可管理公司同事
    public static final String COMPANY_MEMBER_DEL_MEMBER = "/apiv2/company_member/delMember";//移除公司同事
    public static final String COMPANY_ADD_MEMBER = "/apiv2/company/addMember";//添加公司成员

    public static final String COMPANY_SCHEDULE_DATE = "/apiv2/company/scheduleDate";//公司成员有排期的日期
    public static final String COMPANY_MEMBER_SCHEDULE = "/apiv2/company/memberSchedule";//公司成员日期
    public static final String COMPANY_MY_COMPANY = "/apiv2/company/myCompany";//我的公司
    public static final String COMPANY_COMPANY_LIST = "/apiv2/company/companyList";//公司列表
    public static final String COMPANY_LEAVE_COMPANY = "/apiv2/company/leaveCompany";//公司列表


    public static final String USER_COLLECTION_COLLECT_USER = "/apiv2/user_collection/collectUser";//我的粉丝
    public static final String USER_COLLECTION_COLLECTION = "/apiv2/user_collection/collection";//收藏：用户列表
    public static final String USER_COLLECTION_COLLECT = "/apiv2/user_collection/collect";//收藏/取消收藏（用户，作品，商品，课程）


    public static final String ORDER_MY_ORDER = "/apiv2/order/myOrder";//订单信息（个人、公司）
    public static final String ORDER_PROJECT_ORDER = "/apiv2/order/projectOrder";//订单待处理：项目订单（个人）
    public static final String ORDER_UNREAD = "/apiv2/order/unread";//待处理：未读订单
    public static final String ORDER_COMPANY_PROJECT_ORDER = "/apiv2/order/companyProjectOrder";//订单待处理：项目订单（公司）
    public static final String ORDER_GOODS_ORDER = "/apiv2/order/goodsOrder";//待处理：租赁（公司）
    public static final String ORDER_SERVICE_ORDER = "/apiv2/order/serviceOrder";//待处理：服务（公司）
    public static final String ORDER_PAID_ORDER = "/apiv2/order/paidOrder";//我的付款 -已付款、待付款
    public static final String ORDER_COLLECT_PAYMENT = "/apiv2/order/collectPayment";//待对方付款列表
    public static final String ORDER_SAVE_GOODS_STATUS = "/apiv2/order/saveGoodsStatus";//待处理订单： 修改商品、服务订单状态（公司：接单，已完成，拒绝合作）
    public static final String ORDER_ORDER_MONEY = "/apiv2/order/orderMoney";//得到商品，服务 订单价格
    public static final String GOODS_BUY_SERVICE = "/apiv2/goods/buyService";//购买服务
    public static final String PROJECT_MANAGE_BUY_GOODS = "/apiv2/project_manage/buyGoods";//商品立即租赁（道具、场地)

    public static final String WALLET_ADD_ACCOUNT = "/apiv2/wallet/addAccount";//添加账户
    public static final String WALLET_DEL_ACCOUNT = "/apiv2/wallet/delAccount";//删除银行账户
    public static final String WALLET_MY_WALLET = "/apiv2/wallet/myWallet";//钱包信息
    public static final String WALLET_ACCOUNT_LIST = "/apiv2/wallet/accountList";//账户列表
    public static final String WALLET_APPLY = "/apiv2/wallet/apply";//提现申请

    public static final String INVOICE_INVOICE_INFO = "/apiv2/invoice/invoiceInfo";//发票首页信息
    public static final String INVOICE_NEED_INVOICE = "/apiv2/invoice/needInvoice";//需要、可开发票列表
    public static final String INVOICE_INVOICE_LIST = "/apiv2/invoice/invoiceList";//发票列表
    public static final String INVOICE_SAVE_INVOICE = "/apiv2/invoice/saveInvoice";//添加、编辑 发票信息
    public static final String INVOICE_DETAIL_INFO = "/apiv2/invoice/detailInfo";//明细信息
    public static final String INVOICE_MONEY_DETAIL = "/apiv2/invoice/moneyDetail";//明细列表
    public static final String INVOICE_NEED_INVOICE_SUPPLIERS = "/apiv2/invoice/needInvoiceSuppliers";//选择开发票的供应商
    public static final String INVOICE_APPLY_INVOICE  = "/apiv2/invoice/applyInvoice";//申请开发票

    public static final String WALLET_PROJECT_MONEY  = "/apiv2/wallet/projectMoney";//项目资金查看
    public static final String WALLET_FREEZE_FUNDS  = "/apiv2/wallet/freezeFunds";//冻结资金
    public static final String WALLET_CHECK_REFUND  = "/apiv2/wallet/checkRefund";//检测是否能退押金（退保证金）




    public static final String SHOW_CASE_CONFIG_SHOW = "/apiv2/show_case/configShow";//系统橱窗类型
    public static final String SHOW_CASE_SAVE_SHOW = "/apiv2/show_case/saveShow";//添加公司橱窗分类
    public static final String SHOW_CASE_DEL_CLASS = "/apiv2/show_case/delClass";//删除公司橱窗分类
    public static final String SHOW_CASE_COMPANY_SHOW = "/apiv2/show_case/companyShow";//公司橱窗分类（主页）
    public static final String SHOW_CASE_COMPANY_GOODS = "/apiv2/show_case/companyGoods";//橱窗商品列表（主页）
    public static final String SHOW_CASE_GET_CLASS_IMG = "/apiv2/show_case/getClassImg";//橱窗商品列表（主页）
    public static final String GOODS_GOODS_INFO = "/apiv2/goods/goodsInfo";//获取商品详情
    public static final String GOODS_ADD_GOODS = "/apiv2/goods/addGoods";//添加公司商品
    public static final String GOODS_ADD_SERVICE = "/apiv2/goods/addService";//添加公司服务
    public static final String GOODS_DEL_GOODS = "/apiv2/goods/delGoods";//删除商品



    public static final String SEARCH_SEARCH_USER = "/apiv2/search/searchUser";//搜索人员（公司添加员工）
    public static final String SEARCH_SEARCHMEMBER = "/apiv2/search/searchMember";//搜索职业

    public static final String SEARCH_GOODS = "/apiv2/search/goods";//搜索商品（我想借物）
    public static final String HOME_GET_BRAND = "/apiv2/home/getBrand";//得到品牌
    public static final String SEARCH_GET_TAGS = "/apiv2/search/getTags";//得到品牌
    public static final String USER_APPROVE_GET_SKIN_COLOR = "/apiv2/user_approve/getSkinColor";//得到肤色


    public static final String SEARCH_SEARCH_LOG = "/apiv2/search/searchLog";//图片找人搜索记录
    public static final String SEARCH_DEL_LOG = "/apiv2/search/delLog";//删除搜索记录
    public static final String SEARCH_SEARCH_IMG = "/apiv2/search/searchImg";//图片找人,搜索
    public static final String SEARCH_PROJECT_INFO = "/apiv2/search/projectInfo";//图片找人,搜索：项目详情



    public static final String PROJECT_MEMBER_PROJECT_DATE = "/apiv2/project_member/projectDate";//    项目时间
    public static final String PROJECT_MEMBER_ADD_DATE = "/apiv2/project_member/addDate";//    修改项目时间
    public static final String PROFESSION_PROJECT_OCCUPATION = "/apiv2/profession/projectOccupation";//得到项目职业（是否选中）
    public static final String PROJECT_MANAGE_ADD_OCCUPATION = "/apiv2/project_manage/addOccupation";//添加
    public static final String PROJECT_MANAGE_GET_OCCUPATION_USER = "/apiv2/project_manage/getOccupationUser";//修改项目职业
    public static final String PROJECT_MANAGE_PROJECT_PUSH = "/apiv2/project_manage/projectPush";//    项目添加招募
    public static final String PROJECT_MEMBER_MEMBER_LIST = "/apiv2/project_member/memberList";//项目职业添加成员列表（我的同事/群成员）
    public static final String PROJECT_MEMBER_ADD_OCCUPATION_USER = "/apiv2/project_manage/addOccupationUser";///    添加项目职业成员（针对群内成员，同事）
    public static final String PROJECT_MEMBER_DEL_OCCUPATION_USER = "/apiv2/project_manage/delOccupationUser";//    删除项目职业成员
    public static final String PROJECT_MEMBER_DEL_OCCUPATION = "/apiv2/project_manage/delOccupation";//    删除项目职业
    public static final String PROJECT_MEMBER_ADDRESS = "/apiv2/project_manage/address";//    地点选择
    public static final String PROJECT_MEMBER_FREE_ADDRESS = "/apiv2/project_manage/freeAddress";//    添加自由场地
    public static final String PROJECT_MANAGE_SAVE_SITE_COST = "/apiv2/project_manage/saveSiteCost";//    修改自由场地费用
    public static final String PROJECT_MANAGE_SAVE_SITE_TIME = "/apiv2/project_manage/saveSiteTime";//    修改自由场地时间
    public static final String PROJECT_MANAGE_ADD_PROJECT_GOODS = "/apiv2/project_manage/addProjectGoods";//    商品添加进项目（道具、场地)
    public static final String PROJECT_MANAGE_DEL_PROJECT_GOODS = "/apiv2/project_manage/delProjectGoods";//    删除场地/设备

    public static final String PROJECT_FLOW_SEND_MSG = "/apiv2/project_flow/sendMsg";//发送项目变更信息
    public static final String PROJECT_FLOW_SEND_ORDER = "/apiv2/project_flow/sendOrder";//通知采购商付款
    public static final String PROJECT_FLOW_GET_PROJECT_FLOW = "/apiv2/project_flow/getProjectFlow";//得到项目流程
    public static final String PROJECT_FLOW_GET_FLOW_INFO = "/apiv2/project_flow/getFlowInfo";//得到项目流程信息
    public static final String PROJECT_FLOW_SELECT_WORK = "/apiv2/project_flow/selectWork";//选择/取消 项目作品
    public static final String PROJECT_FLOW_SET_SAMPLE = "/apiv2/project_flow/setSample";//选择/取消 项目作品样片
    public static final String PROJECT_FLOW_TO_EMAIL = "/apiv2/project_flow/toEmail";//文件提取 email

    public static final String PROJECT_MEMBER_MEMBER_DATE = "/apiv2/project_member/memberDate";//    人员时间
    public static final String PROJECT_MEMBER_USER_PAYMENT = "/apiv2/project_member/userPayment";//    人员时间安排（得到项目人员的职业薪酬和项目排期日期）
    public static final String PROJECT_MEMBER_SCHEDULE = "/apiv2/project_member/schedule";//    人员时间安排（添加排期）

    public static final String PROJECT_MEMBER_COSTUMES = "/apiv2/project_manage/costumes";//    服装道具

    public static final String PROJECT_COST_COST_DETAIL = "/apiv2/project_cost/costDetail";//    项目费用明细
    public static final String PROJECT_COST_SAVE_PRICE = "/apiv2/project_cost/savePrice";//    修改明细金额
    public static final String PROJECT_COST_ADD_DETAIL = "/apiv2/project_cost/addDetail";//    添加项目其他费用




    public static final String RC_COOPERATION = "/apiv2/rc/cooperation";//    邀请合作 | 发送项目 | 添加项目职业成员
    /*********************************************************************/



    /**
     * 获取城市
     */

    public static final String GETAREA = "/api/common/getArea";

    /**
     * 获取职业
     */

    public static final String GETQCCUPATION = "/api/home/getOccupation";
    /**
     * 综合
     */
    public static final String GETPROJECTINTEGRATED = "/api/project/projectIntegrated";
    /**
     * 通告
     */
    public static final String GETNOTICE = "/api/project/projectNotice";

    /**
     * 达人
     */
    public static final String TABLENTPUSH = "/api/home/talentPush";
    /**
     * 获取服务类型
     */
    public static final String GETSERVICE = "/api/user_company/getService";
    /**
     * 可能感兴趣的用户
     */
    public static final String MAYBELIKE = "/api/show/maybeLike";
    /**
     * 首页功能栏目
     */
    public static final String HOMECOLUM = "/api/home/getHomeColunm";

    /**
     * 首页功能栏目
     */
    public static final String HOMEADS = "/api/home/homeAds";
    /**
     * 得到物品
     */
    public static final String GETTHINGS = "/api/home/getThings";

    /**
     * 置顶文章
     */
    public static final String TOPARTICLE = "/api/home/topArticle";

    /**
     * 热门文章
     */
    public static final String HOTARTICLE = "/api/home/hotArticle";

    /**
     * 热门作者
     */
    public static final String HOTAUTHOR = "/api/home/hotAuthor";

    /**
     * 文章列表
     */
    public static final String ARTICLELIST = "/api/home/articleList";

    /**
     * 文章列表
     */
    public static final String COLUMNLIST = "/api/special_column/columnList";


    /**
     * 找人找物
     */
    public static final String GETCOLUMCHILD = "api/home/getColunmChild";

    /**
     * show 列表
     */
    public static final String WORK = "/api/show/work";

    /**
     * 收藏
     */
    public static final String WORKCOLLECT = "/api/show/workCollect";


    /**
     * 发送验证码
     */
    public static final String SENDCODE = "/api/user/sendCode";

    /**
     * 登录
     */
    public static final String LOGIN = "/api/user/login";


    /**
     * 判断用户认证类型(基础资料)
     */
    public static final String ISAPPROVECOMPANY = "/api/personal_center/isApproveCompany";

    /**
     * 个人(公司)作品列表
     */
    public static final String GETWORKSLIST = "/api/personal_center/getWorksList";

    /**
     * 项目详情
     */
    public static final String PROJECTINFO = "/api/project/projectInfo";

    /**
     * 删除作品
     */
    public static final String DELWORK = "/api/personal_center/delWork";

    /**
     * 个人主页
     */
    public static final String USERCENTER = "/api/personal_center/userCenter";

    /**
     * 作品详情
     */
    public static final String GETWORKSINFO = "/api/personal_center/getWorksInfo";

    /**
     * 收藏用户
     */
    public static final String COLLECTUSER = "/api/collect/collectUser";

    /**
     * 举报用户
     */
    public static final String USERREPORT = "/api/user_report/user";


    /**
     * 项目管理
     */
    public static final String PROJECTMANAGE = "/api/project/projectManage";


    /**
     * 项目合伙
     */
    public static final String PARTNERSHIP = " /api/project/partnership";


    /**
     * 我的订单*业务
     */
    public static final String GETBUSINESS = "/api/order/getBusiness";

    /**
     * 我的订单*课程
     */

    public static final String CURRICULUM = "/api/order/curriculum";

    /**
     * 我的订单*投广
     */
    public static final String GETADS = "/api/order/getAds";

    /**
     * 获取专栏职业
     */
    public static final String SPQCCUPATION = "/api/special_column/getOccupation";

    /**
     * 获取专栏详情
     */
    public static final String GETCOLUMNINFO = "/api/special_column/getColumnInfo";

    /**
     * 收藏课程(专栏)
     */
    public static final String COLLECTCOLUMN = "/api/collect/collectColumn";

    /**
     * 客服中心
     */
    public static final String USERLEAVEMSG = "/api/personal_center/userLeaveMsg";

    /**
     * APP更新
     */
    public static final String GETVERSION = "/api/common/getVersion";

    /**
     * 发送验证码(重置密码)
     */
    public static final String SAVECODE = "/api/personal_center/saveCode";

    /**
     * 重置密码
     */
    public static final String SAVEPWD = "/api/personal_center/savePwd";


    /**
     * 通知(未读)
     */
    public static final String UNREADMSG = "/api/Inform/unreadMsg";

    /**
     * 系统信息列表
     */
    public static final String SYSMSG = "/api/Inform/sysMsg";

    /**
     * 项目统筹列表
     */
    public static final String PROJECTAPPLY = "/api/Inform/projectApply";

    /**
     * 项目参与确认列表
     */
    public static final String PROJECTINVITE = "/api/Inform/projectInvite";


    /**
     * 公司申请列表
     */
    public static final String COMPANYAPPLY = "/api/Inform/companyApply";


    /**
     * 项目统筹申请(是否同意)
     */
    public static final String APPLYPASS = "/api/user_operation/applyPass";


    /**
     * 公司申请(是否同意)
     */
    public static final String COMPANYPASS = "/api/user_operation/companyPass";

    /**
     * 设置单聊正在谈的项目
     */
    public static final String SETCHATPROJECT = "/api/rc/setChatProject";


    /**
     * 保存聊天信息
     */
    public static final String SAVECHAT = "/api/rc/saveChat";
    /**
     * 获取单聊正在谈的项目
     */
    public static final String GETCHATPROJECT = "/api/rc/getChatProject";
    /**
     * 获取会话信息
     */
    public static final String GETINFO = "/api/rc/getInfo";
    /**
     * 确认合作发送的请求
     */
    public static final String COOPERATION = "/api/rc/cooperation";

    /**
     * 切换项目
     */
    public static final String MYPROJECT = "/api/project/myProject";

    /**
     * 获取群成员
     */
    public static final String GROUPMEMBER = "/api/rc/groupMember";

    /**
     * 更新群员信息
     */
    public static final String GROUPMANAGE = "/api/rc/groupManage";
    /**
     * 删除群成员
     */
    public static final String DELMEMBER = "/api/rc/delMember";
    /**
     * 删除招聘组员
     */
    public static final String DELMEMBERQUCCUPATION = "/api/rc/delMemberOccupation";
    /**
     * 得到群成员信息
     */
    public static final String GETMEMBERINFO = "/api/rc/getMemberInfo";

    /**
     * 添加关注
     */
    public static final String ADDFOCUS = "/api/show/addFocus";
    /**
     * 收藏 项目
     */
    public static final String PROJECTLIST = "/api/collect/projectList";
    /**
     * 收藏 人员
     */
    public static final String USERLIST = "/api/collect/userList";
    /**
     * 收藏 物品
     */
    public static final String GOODSLIST = "/api/collect/goodsList";
    /**
     * 收藏 课程
     */
    public static final String COURSELIST = "/api/collect/courseList";

    /**
     * 系统(未读)
     */
    public static final String SYSUNREAD = "/api/Inform/sysUnread";
    /**
     * 申请认证
     */
    public static final String APPLYAPPROVE = "/api/approve/applyApprove";
    /**
     * 申请个人认证
     */
    public static final String APPPERSONAL = "/api/approve/personal";
    /**
     * 申请公司认证
     */
    public static final String APPCOMAPNY = "/api/approve/comapny";

    /**
     * 我的同事
     */
    public static final String MYCOLLEAGUE = "/api/user_company/myColleague";


    /**
     * 搜索页面的专栏
     */
    public static final String MYCOLUMN = "/api/special_column/columnSearch";

    /**
     * 搜索页面的视觉
     */
    public static final String MYVISUAL = "/api/home/articleSearch";
    /**
     * 搜索页面的视觉
     */
    public static final String SEARCH = "/api/search/search";
    /**
     * 搜索商品/服务
     */
    public static final String SEARCHGOODS = "/api/search/searchGoods";

    /**
     * 得到用户专题列表
     */
    public static final String GETCILUMN = "/api/special_column/getColumn";
    /**
     * 得到用户专题类型
     */
    public static final String GETCILUMNTYPE = "/api/special_column/getColumnType";
    /**
     * 添加专栏付费内容
     */
    public static final String ADDPAIDCONTENT = "/api/special_column/addPaidContent";
    /**
     * 添加专栏
     */
    public static final String ADDCOLUMN = "/api/special_column/addColumn";
    /**
     * 专栏预览
     */
    public static final String COLUMNPREVIEW = "/api/special_column/columnPreview";
    /**
     * 主页:橱窗分组(商品/服务/人员)
     */
    public static final String SHOWCASEGROUP = "/api/company/showCaseGroup";
    /**
     * 主页:公司橱窗(商品/服务/人员)
     */
    public static final String COMPANYCASE = "/api/company/companyCase";
    /**
     * 投广详情
     */
    public static final String GETADSINFO = "/api/user_company/getAdsInfo";
    /**
     * 投广:得到不可选时间,和单价
     */
    public static final String GETPUTMSG = "/api/user_company/getPutMsg";
    /**
     * 投广发布
     */
    public static final String POSTADS = "/api/user_company/postAds";
    /**
     * 投广预览
     */
    public static final String ADSPREVIEW = "/api/user_company/Adspreview";
    /**
     * 得到用户公司信息
     */
    public static final String GETCOMPANYINFO = "/api/personal_center/getCompanyInfo";
    /**
     * 得到用户个人信息
     */
    public static final String GETUSERINFO = "/api/personal_center/getUserInfo";
    /**
     * 获取钱包链接
     */
    public static final String GETWALLET = "/api/oauth/getWallet";
    /**
     * 获取钱包链接
     */
    public static final String GETPROGRESS = "api/oauth/getProgress";
    /**
     * 个人资料：更新公司信息
     */
    public static final String SAVECOMPANYINFO = "/api/personal_center/saveCompanyInfo";
    /**
     * 个人资料：更新个人信息
     */
    public static final String SAVEUSERINFO = "/api/personal_center/saveUserInfo";
    /**
     * 查看公司橱窗(顶部公司信息)
     */
    public static final String COMPANYINFO = "/api/company/companyInfo";
    /**
     * 商品/服务 详情
     */
    public static final String GOODSINFO = "/api/company/goodsInfo";

    /**
     * 获取加入公司信息
     */
    public static final String GETJOINCOMPANY = "/api/personal_center/getJoinCompany";

    /**
     * 申请加入公司
     */
    public static final String JOINCOMPANY = "/api/personal_center/joinCompany";

    /**
     * 取消申请(退出公司)
     */
    public static final String CANCELJOIN = "/api/personal_center/cancelJoin";

    /**
     * 添加公司商品或服务
     */
    public static final String ADDGOODS = "/api/user_company/addGoods";

    /**
     * 发布项目/ 项目合伙
     */
    public static final String ISSUE = "/api/project/issue";
    /**
     * 项目详情 物件
     */
    public static final String PROJECTGOODS = "/api/project_manage/projectGoods";
    /**
     * 项目详情 概况
     */
    public static final String OVERVIEW = "/api/project_manage/overview";
    /**
     * 编辑项目执行时间
     */
    public static final String PROJECTDATE = "/api/project/projectDate";
    /**
     * 项目管理:得到项目流程设置类型
     */
    public static final String FLOWTYPE = "/api/project_manage/flowType";

    /**
     * 项目招募职业
     */
    public static final String MANAGEOCCUPATION = "/api/project_manage/occupation";
    /**
     * 项目招募职业对应成员(已排除接单人和发布人)
     */
    public static final String OCCUPATIONMEMBER = "/api/project_manage/occupationMember";

    /**
     * 添加/修改  项目招募职业
     */
    public static final String SAVEPOSITION = "/api/project_manage/savePosition";
    /**
     * 项目职业发布招募推送
     */
    public static final String PUSH = "/api/project_manage/push";
    /**
     * 个人资料：用户排期
     */
    public static final String MEMBERSCHEDULE = "/api/personal_center/memberSchedule";


    /**
     * 退出登录
     */
    public static final String LOGINOUT = "/api/personal_center/loginOut";
    /**
     * 公司员工根据时间显示排期列表 (个人根据时间显示排期)
     */
    public static final String SCHEDULEINFO = "/api/personal_center/memberScheduleInfo";
    /**
     * 公司成员 / 当前排期成员
     */
    public static final String COMPANYMEMBER = "/api/personal_center/companyMember";
    /**
     * 添加分组(商品/服务/人员)
     */
    public static final String ADDGROUP = "/api/company/addGroup";
    /**
     * 删除分组(商品/服务/人员)
     */
    public static final String DELGROUP = "/api/company/delGroup";
    /**
     * 设置里面的关于H5
     */
    public static final String ABOUT = "/api/oauth/about";
    /**
     * 发布(编辑)作品
     */
    public static final String POSTWORK = "/api/personal_center/postWork";
    /**
     * 项目管理-明细页面
     */
    public static final String GETPROGRRESSLEDGER = "/api/oauth/getProgressLedger";

    /**
     * 项目管理-时间页面
     */
    public static final String GETPROGRESSMANAGE = "/api/oauth/getProgressManage";

    /**
     * 钱包页面-账单明细
     */
    public static final String GETACCOUNTDETAIL = "/api/oauth/getAccountDetail";

    /**
     * 购买课程
     */
    public static final String BUYCOLUMN = "/api/special_column/buyColumn";
    /**
     * 添加/修改 個人排期
     */
    public static final String ADDSCHEDULE = "/api/personal_center/addSchedule";
    /**
     * 项目时间管理:  得到用户可选时间长度(时/天)
     */
    public static final String GETLONGTIME = "/api/project_manage/getLongTime";

    /**
     * 得到用户个人排期详情
     */
    public static final String GETUSERSCHEDULE = "/api/personal_center/getUserSchedule";
    /**
     * 商品/服务  可拉取时间长度( 时 / 天 后台自动判断)
     */
    public static final String GETGOODSLONGTIME = "/api/project_manage/getGoodsLongTime";

    /**
     * 添加商品到项目  / 购买服务 (后台可根据goodsId 判断)
     */
    public static final String ADDPROJECTGOODS = "/api/project_manage/addProjectGoods";
    /**
     * 删除项目地图地址
     */
    public static final String DELMAP = "/api/project_manage/delMap";
    /**
     * 删除项目物品
     */
    public static final String DELPROJECTGOODS = "/api/project_manage/delProjectGoods";
    /**
     * 添加地图地址
     */
    public static final String ADDMAPADDRESS = "/api/project_manage/addMapAddress";
    /**
     * 立即沟通
     */
    public static final String CHECKCOMPANY = "/api/personal_center/checkCompany";
    /**
     * 忘记密码重置密码
     */
    public static final String FORGOTPWD = "/api/user/forgotPwd";
    /**
     * 得到专栏编辑信息
     */
    public static final String COLUMNINFO = "/api/special_column/columnInfo";
    /**
     * 得到付费编辑信息
     */
    public static final String GETPADICONTEMT = "/api/special_column/getPaidContent";
    /**
     * 删除专栏
     */
    public static final String DELCOLUMN = "/api/special_column/delColumn";

    /**
     * 我的同事
     */
    public static final String COLLEAGUE = "/api/user_company/colleague";

    /**
     * 群成员（项目招募：添加）
     */
    public static final String GROUPUSER = "/api/rc/groupUser";

    /**
     * 人脸识别
     */
    public static final String SAVEMODELFACE = "/api/approve/saveModelFace";

    /**
     * 面部识别：详情
     */
    public static final String SAVEFACEINFO = "/api/approve/saveFaceInfo";

    /**
     * 得到肤色
     */
    public static final String GETSKINCOLOR = "/api/approve/getSkinColor";
    /**
     * 保存我的同事到项目职业
     */
    public static final String SAVECOMPANYMEMBER = "/api/project_manage/saveCompanyMember";

    /**
     * 保存群成员职业
     */
    public static final String SAVEGRUPMEMBER = "api/project_manage/saveGroupMember";
    /**
     * 面部识别：获取详情
     */
    public static final String GETMODELINFO = "/api/approve/getModelInfo";
    /**
     * 判断 用户是否认证
     */
    public static final String CHECK = "/api/approve/check";


}
