package com.gh.myrxretrofitdemo.entity.genshang;

/**
 * @author: gh
 * @description:跟上ben
 * @date: 2016/8/15 09:45.
 */
public class LeaderScheduleVo {
    /**
     * 本地
     * 是否为示例    1是示例    0不是 默认不是
     */
    private int isSample;
    /**
     * 公告未读数
     */
    private int announcementUnread;
    /**
     * 距离团队出行剩余天数
     */
    private int days;
    /**
     * 结束日期
     */
    private long endDate;
    /**
     * 2016.10.31二期debug新增
     * 已经结束了多少天
     */
    private int endDays;
    /**
     * 行程背景图片
     */
    private String featurePhoto;
    /**
     * 已完成天数
     */
    private int finishDays;
    /**
     * 集合未读数
     */
    private int gatherUnread;
    /**
     * 2016.10.31二期debug新增
     * 是否为管理员【1：是；0：否】
     */
    private int isAdmin;
    /**
     * 行程名称
     */
    private String name;
    /**
     * 通知未读数
     */
    private int noticeUnread;
    /**
     * 2016.10.31二期debug新增
     * 是否围观行程：0正常行程 1围观行程
     */
    private int onlookerType;
    /**
     * 2016.10.31二期debug新增
     * 被围观者ID
     */
    private String onlookerUserId;
    /**
     * 行程创建的状态：2已发布-3未发布-4待确认
     */
    private int processStatus;
    /**
     * 2016.10.31二期debug新增
     * 身份【1：领队；2：导游；3：游客】
     */
    private int role;
    /**
     * 行程Id
     */
    private String scheduleId;
    /**
     * 开始日期
     */
    private long startDate;
    /**
     * 2016.10.31二期debug新增
     * 团的状态：1：出行中；2：即将出行；3：已出行
     */
    private int status;
    /**
     * 行程总天数
     */
    private int totalDays;
    /**
     * 腾讯云GroupId
     */
    private String txGroupId;
    /**
     * 2016.10.31二期debug新增
     * 围观未读数量
     */
    private int watchUnreadCount;

    /**
     * 关于二维码的相关参数
     */
    private String qrParam;
    private String qrType;
    private String qrURL;
    private String qrCode;
    /**
     * 群头像
     */
    private String tPhotoUrl;

    public int getIsSample() {
        return isSample;
    }

    public void setIsSample(int isSample) {
        this.isSample = isSample;
    }

    public int getAnnouncementUnread() {
        return announcementUnread;
    }

    public void setAnnouncementUnread(int announcementUnread) {
        this.announcementUnread = announcementUnread;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getEndDays() {
        return endDays;
    }

    public void setEndDays(int endDays) {
        this.endDays = endDays;
    }

    public String getFeaturePhoto() {
        return featurePhoto;
    }

    public void setFeaturePhoto(String featurePhoto) {
        this.featurePhoto = featurePhoto;
    }

    public int getFinishDays() {
        return finishDays;
    }

    public void setFinishDays(int finishDays) {
        this.finishDays = finishDays;
    }

    public int getGatherUnread() {
        return gatherUnread;
    }

    public void setGatherUnread(int gatherUnread) {
        this.gatherUnread = gatherUnread;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoticeUnread() {
        return noticeUnread;
    }

    public void setNoticeUnread(int noticeUnread) {
        this.noticeUnread = noticeUnread;
    }

    public int getOnlookerType() {
        return onlookerType;
    }

    public void setOnlookerType(int onlookerType) {
        this.onlookerType = onlookerType;
    }

    public String getOnlookerUserId() {
        return onlookerUserId;
    }

    public void setOnlookerUserId(String onlookerUserId) {
        this.onlookerUserId = onlookerUserId;
    }

    public int getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public void setTxGroupId(String txGroupId) {
        this.txGroupId = txGroupId;
    }

    public int getWatchUnreadCount() {
        return watchUnreadCount;
    }

    public void setWatchUnreadCount(int watchUnreadCount) {
        this.watchUnreadCount = watchUnreadCount;
    }

    public String getQrParam() {
        return qrParam;
    }

    public void setQrParam(String qrParam) {
        this.qrParam = qrParam;
    }

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    public String getQrURL() {
        return qrURL;
    }

    public void setQrURL(String qrURL) {
        this.qrURL = qrURL;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getTPhotoUrl() {
        return tPhotoUrl;
    }

    public void setTPhotoUrl(String tPhotoUrl) {
        this.tPhotoUrl = tPhotoUrl;
    }
}
