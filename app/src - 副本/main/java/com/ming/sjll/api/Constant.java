package com.ming.sjll.api;


public class Constant {
    public static final String BASE_API = "http://www.coolpaishop.com/";
    public static final String WxPayAppId = "wxcd430c1c832a0dc4";

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
     * 注册
     */
    public static final String REGISTER = "api/user/register";

    /**
     * 发送验证码
     */
    public static final String SENDCODE = "/api/user/sendCode";

    /**
     * 登录
     */
    public static final String LOGIN = "/api/user/login";

    /**
     * 快速登录
     */
    public static final String QUICKLOGIN = "/api/user/quickLogin";

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
     * 项目参与确认(是否同意)
     */
    public static final String INVITEPASS = "/api/user_operation/invitePass";

    /**
     * 公司申请(是否同意)
     */
    public static final String COMPANYPASS = "/api/user_operation/companyPass";

    /**
     * 设置单聊正在谈的项目
     */
    public static final String SETCHATPROJECT = "/api/rc/setChatProject";

    /**
     * 获取聊天分享消息列表
     */
    public static final String GETCHATLIST = "/api/rc/getChatList";
    /**
     * 分享接口
     */
    public static final String SHAREAPPMSG = "/api/share/shareAppMsg";
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
     * 首页问候语
     */
    public static final String WELCOME = "/api/personal_center/welcome";
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
     * 分享
     */
    public static final String USERSHARE = "/api/share/userShare";

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
