package com.bo.mytest.bean;

/**
 * Author:jianbo
 * <p>
 * Create Time:2019/12/16 10:44
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:app底部bar状态查询接口
 */
public class ChargingOrderModel {

    /**
     * reChargeOrderDetail : {"tradeNo":"111111555454","tradeAmount":50,"tradeType":1,"tradeTypeName":"公众号支付","payChannel":1,"payChannelName":"wechat","accountCategory":1,"createTime":"2019-12-05 15:10:12"}
     */

    private ReChargeOrderDetailBean reChargeOrderDetail;

    public ReChargeOrderDetailBean getReChargeOrderDetail() {
        return reChargeOrderDetail;
    }

    public void setReChargeOrderDetail(ReChargeOrderDetailBean reChargeOrderDetail) {
        this.reChargeOrderDetail = reChargeOrderDetail;
    }

    public static class ReChargeOrderDetailBean {
        /**
         * tradeNo : 111111555454
         * tradeAmount : 50
         * tradeType : 1
         * tradeTypeName : 公众号支付
         * payChannel : 1
         * payChannelName : wechat
         * accountCategory : 1
         * createTime : 2019-12-05 15:10:12
         */

        private String tradeNo;
        private int tradeAmount;
        private int tradeType;
        private String tradeTypeName;
        private int payChannel;
        private String payChannelName;
        private int accountCategory;
        private String createTime;

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(int tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeTypeName() {
            return tradeTypeName;
        }

        public void setTradeTypeName(String tradeTypeName) {
            this.tradeTypeName = tradeTypeName;
        }

        public int getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(int payChannel) {
            this.payChannel = payChannel;
        }

        public String getPayChannelName() {
            return payChannelName;
        }

        public void setPayChannelName(String payChannelName) {
            this.payChannelName = payChannelName;
        }

        public int getAccountCategory() {
            return accountCategory;
        }

        public void setAccountCategory(int accountCategory) {
            this.accountCategory = accountCategory;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public String toString() {
            return "ReChargeOrderDetailBean{" +
                    "tradeNo='" + tradeNo + '\'' +
                    ", tradeAmount=" + tradeAmount +
                    ", tradeType=" + tradeType +
                    ", tradeTypeName='" + tradeTypeName + '\'' +
                    ", payChannel=" + payChannel +
                    ", payChannelName='" + payChannelName + '\'' +
                    ", accountCategory=" + accountCategory +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChargingOrderModel{" +
                "reChargeOrderDetail=" + reChargeOrderDetail +
                '}';
    }
}
