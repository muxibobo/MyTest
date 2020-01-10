package com.bo.mytest.testclass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Author:jianbo
 * <p>
 * Create Time:2019/12/27 10:22
 * <p>
 * Email:1245092675@qq.com
 * <p>
 * Describe:
 */
public class numbertest {
    public static void main(String[] args) {
//        System.out.println("四舍五入:" + String.format("%.2f", 5.369));//转换精度损失9不够多
//        //浮点转字符串,会有一个优化,比如有很多个0或9会四拾伍入
//        System.out.println(":::" + 100.1 * 3);//转换精度损失9不够多
//        System.out.println(100.1 * 3 + 0.00000000000004);//9够多
//        System.out.println(new BigDecimal(100.1).multiply(new BigDecimal(3)));//无意义 传入double精度就已经损失了
//        System.out.println(new BigDecimal("100.1").multiply(new BigDecimal(3)).toPlainString());//正确
//
//
//        BigDecimal bigLoanAmount = new BigDecimal("53.6");   //创建BigDecimal对象
//        BigDecimal
//                bigInterestRate = new BigDecimal("0.102499");
//
//        BigDecimal bigInterest = bigLoanAmount.multiply(bigInterestRate);//BigDecimal运算
//        NumberFormat
//                currency = NumberFormat.getCurrencyInstance();    //建立货币格式化引用
//
//        NumberFormat percent = NumberFormat.getPercentInstance();     //建立百分比格式化用
//
//        percent.setMaximumFractionDigits(3);               //百分比小数点最多3位
//
////利用BigDecimal对象作为参数在format()中调用货币和百分比格式化
//
//        System.out.println("Loan amount:\t" + currency.format(bigLoanAmount));
//
//        System.out.println("Interest rate:\t" + percent.format(bigInterestRate));
//
//        System.out.println("Interest:\t" + currency.format(bigInterest));


        int a = 5; // 0000 0101
        int b = 3; // 0000 0011
        a ^= b; // 0000 0110
        System.out.println(a);
    }
}
