package com.smartcity.qiuchenly.Base;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.google.gson.Gson;
import com.smartcity.qiuchenly.Adapter.mDataBaseHelper;
import com.smartcity.qiuchenly.DataModel.userLoginCallBackModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Author: qiuchenly
 * Date  : 09/11/2017
 * Usage :
 * Lasted:2017 11 09
 * ProjectName:SmartRoadSystem
 * Create: 2017 11 09 , on 23:58
 */

public class Utils {
  public static boolean isTwiceOpen() {
    boolean is = ShareUtils.getBoolean("twiceOpen");
    if (!is) ShareUtils.put("twiceOpen", true);
    return is;
  }

  public static Bitmap getBlurBitmap(Bitmap bit, View view) {
    Bitmap bit1 = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap
            .Config.ARGB_8888);
    Canvas canvas = new Canvas(bit1);
    canvas.translate(-view.getLeft(), -view.getTop());
    canvas.drawBitmap(bit, 0, 0, null);
    return bit1;
  }


  /**
   * RAM performance optimization
   * QiuChenly 11.10
   *
   * @param bit
   * @param Scale
   * @return
   */
  public static Bitmap zoomBitmap(Bitmap bit, float Scale) {
    int w, h;
    w = bit.getWidth();
    h = bit.getHeight();
    Matrix matrix = new Matrix();
    matrix.postScale(Scale, Scale);
    Bitmap bit_new = Bitmap.createBitmap(bit, 0, 0, w, h, matrix, true);
    return bit_new;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static Bitmap blurBitmapRender(Bitmap bit, View view, float radius, int MODE) {
    //get will blur bitmap
    //QiuChenly 2017.11.10 Fix.
    bit = getBlurBitmap(bit, view);
    //bit = zoomBitmap(bit, 0.8f);
    if (MODE == 1) {
      //start blur
      RenderScript script = RenderScript.create(SharedContext.getContext());
      Allocation overlayAlloc = Allocation.createFromBitmap(script, bit);
      ScriptIntrinsicBlur blur = ScriptIntrinsicBlur
              .create(script, overlayAlloc.getElement());
      blur.setInput(overlayAlloc);
      blur.setRadius(radius);
      blur.forEach(overlayAlloc);
      overlayAlloc.copyTo(bit);
      script.destroy();//Destroy Object Release RAM with performance optimization
    } else {
      bit = blur(bit, radius);
    }
    return bit;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static Bitmap blur(Bitmap bitmap, float radius) {
    Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
    RenderScript rs = RenderScript.create(SharedContext.getContext()); // 构建一个RenderScript对象
    ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); //
    // 创建高斯模糊脚本
    Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 开辟输入内存
    Allocation allOut = Allocation.createFromBitmap(rs, output); // 开辟输出内存
    gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
    gaussianBlue.setInput(allIn); // 设置输入内存
    gaussianBlue.forEach(allOut); // 模糊编码，并将内存填入输出内存
    allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
    rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
    return output;
  }

  /**
   * 高斯模糊
   *
   * @param srcBitmap 源位图
   * @param radius    模糊半径
   * @return bitmap
   */
  public static Bitmap blurByGauss(Bitmap srcBitmap, int radius) {

    Bitmap bitmap = srcBitmap.copy(srcBitmap.getConfig(), true);

    if (radius < 1) {
      return (null);
    }

    int w = bitmap.getWidth();
    int h = bitmap.getHeight();

    int[] pix = new int[w * h];
    bitmap.getPixels(pix, 0, w, 0, 0, w, h);

    int wm = w - 1;
    int hm = h - 1;
    int wh = w * h;
    int div = radius + radius + 1;

    int r[] = new int[wh];
    int g[] = new int[wh];
    int b[] = new int[wh];
    int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
    int vmin[] = new int[Math.max(w, h)];

    int divsum = (div + 1) >> 1;
    divsum *= divsum;
    int temp = 256 * divsum;
    int dv[] = new int[temp];
    for (i = 0; i < temp; i++) {
      dv[i] = (i / divsum);
    }

    yw = yi = 0;

    int[][] stack = new int[div][3];
    int stackpointer;
    int stackstart;
    int[] sir;
    int rbs;
    int r1 = radius + 1;
    int routsum, goutsum, boutsum;
    int rinsum, ginsum, binsum;

    for (y = 0; y < h; y++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
      for (i = -radius; i <= radius; i++) {
        p = pix[yi + Math.min(wm, Math.max(i, 0))];
        sir = stack[i + radius];
        sir[0] = (p & 0xff0000) >> 16;
        sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);
        rbs = r1 - Math.abs(i);
        rsum += sir[0] * rbs;
        gsum += sir[1] * rbs;
        bsum += sir[2] * rbs;
        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        } else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }
      }
      stackpointer = radius;

      for (x = 0; x < w; x++) {

        r[yi] = dv[rsum];
        g[yi] = dv[gsum];
        b[yi] = dv[bsum];

        rsum -= routsum;
        gsum -= goutsum;
        bsum -= boutsum;

        stackstart = stackpointer - radius + div;
        sir = stack[stackstart % div];

        routsum -= sir[0];
        goutsum -= sir[1];
        boutsum -= sir[2];

        if (y == 0) {
          vmin[x] = Math.min(x + radius + 1, wm);
        }
        p = pix[yw + vmin[x]];

        sir[0] = (p & 0xff0000) >> 16;
        sir[1] = (p & 0x00ff00) >> 8;
        sir[2] = (p & 0x0000ff);

        rinsum += sir[0];
        ginsum += sir[1];
        binsum += sir[2];

        rsum += rinsum;
        gsum += ginsum;
        bsum += binsum;

        stackpointer = (stackpointer + 1) % div;
        sir = stack[(stackpointer) % div];

        routsum += sir[0];
        goutsum += sir[1];
        boutsum += sir[2];

        rinsum -= sir[0];
        ginsum -= sir[1];
        binsum -= sir[2];

        yi++;
      }
      yw += w;
    }
    for (x = 0; x < w; x++) {
      rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
      yp = -radius * w;
      for (i = -radius; i <= radius; i++) {
        yi = Math.max(0, yp) + x;

        sir = stack[i + radius];

        sir[0] = r[yi];
        sir[1] = g[yi];
        sir[2] = b[yi];

        rbs = r1 - Math.abs(i);

        rsum += r[yi] * rbs;
        gsum += g[yi] * rbs;
        bsum += b[yi] * rbs;

        if (i > 0) {
          rinsum += sir[0];
          ginsum += sir[1];
          binsum += sir[2];
        } else {
          routsum += sir[0];
          goutsum += sir[1];
          boutsum += sir[2];
        }

        if (i < hm) {
          yp += w;
        }
      }
      yi = x;
      stackpointer = radius;
      for (y = 0; y < h; y++) {
        pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

        rsum -= routsum;
        gsum -= goutsum;
        bsum -= boutsum;

        stackstart = stackpointer - radius + div;
        sir = stack[stackstart % div];

        routsum -= sir[0];
        goutsum -= sir[1];
        boutsum -= sir[2];

        if (x == 0) {
          vmin[y] = Math.min(y + r1, hm) * w;
        }
        p = x + vmin[y];

        sir[0] = r[p];
        sir[1] = g[p];
        sir[2] = b[p];

        rinsum += sir[0];
        ginsum += sir[1];
        binsum += sir[2];

        rsum += rinsum;
        gsum += ginsum;
        bsum += binsum;

        stackpointer = (stackpointer + 1) % div;
        sir = stack[stackpointer];

        routsum += sir[0];
        goutsum += sir[1];
        boutsum += sir[2];

        rinsum -= sir[0];
        ginsum -= sir[1];
        binsum -= sir[2];

        yi += w;
      }
    }

    bitmap.setPixels(pix, 0, w, 0, 0, w, h);
    return bitmap;
  }


  public static <T> List<T> jsonToList(String str, Class<T[]> cx) {
    Gson gson = new Gson();
    T[] r = gson.fromJson(str, cx);
    List<T> s = new ArrayList<>();
    s.addAll(Arrays.asList(r));
    return s;
  }

  public static <T> T jsonToListA(String str, Class<T> cx) {
    Gson a = new Gson();
    return a.fromJson(str, cx);
  }

  public static int getMoneyLimitValue() {
    int v = ShareUtils.getInt("getMoneyLimitValue");
    if (v < 0) {
      return 50;
    } else {
      return v;
    }
  }

  public static userLoginCallBackModel userInfo;

  public static mDataBaseHelper dataBaseHelper;

  private static final String DATABASE_NAME = "mDataBase.db";
  private static final int DATABASE_VERSION = 1;

  public static void mInitDataBase() {
    if (dataBaseHelper == null) {
      dataBaseHelper = new mDataBaseHelper(SharedContext.getContext(),
              DATABASE_NAME, null, DATABASE_VERSION);
    }
  }

  public static String getNowLoginUser() {
    return Utils.userInfo.userName;
  }
}
