package bwie.mylibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import bwie.mylibrary.R;

/**
 * Created by zjj on 2016/11/15.
 */
public class GlideUtils {
 public static void loadpics(Context context, String path, ImageView img){
     Glide.with(context)
             .load(path)
             .thumbnail( 0.3f)//缩略图
             //.centerCrop()
             .crossFade()
             .dontAnimate()//没有动画
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)//磁盘缓存策略
             .skipMemoryCache(true)//跳过内存缓存-----如果不设置的话默认缓存内存
             .placeholder(R.drawable.ic_launcher)//加载中 等待图片
             .error(R.drawable.ic_launcher)//错误图片
             .into(img);
 }

}
