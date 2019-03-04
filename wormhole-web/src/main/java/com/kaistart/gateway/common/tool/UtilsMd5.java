package com.kaistart.gateway.common.tool;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *
 * MD5计算工具类
 * @author chenhailong
 * @date 2019年1月31日 下午2:32:01
 */
public class UtilsMd5 {
  private static final Logger logger = LoggerFactory.getLogger(UtilsMd5.class);


  /**
   * 密码md5加密
   * 
   * @param s
   * @return
   */
  public final static String md5(String s) {
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      if (s == null) {
        s = "";
      }
      byte[] btInput = s.getBytes("utf-8");
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // 使用指定的字节更新摘要
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      // 将字符串转化为小写
      return new String(str);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return null;
    }
  }


}
