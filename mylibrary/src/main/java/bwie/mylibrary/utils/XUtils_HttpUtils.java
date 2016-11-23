package bwie.mylibrary.utils;

import android.os.Handler;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by zjj on 2016/11/8.
 */
public class XUtils_HttpUtils {
    public   static void requestdata(String url, final int tag, final Handler handler) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                //将请求的结果发给handler
                handler.obtainMessage(1, tag, 0, json).sendToTarget();
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }
}
