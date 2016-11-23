package bwie.mylibrary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import bwie.mylibrary.utils.OkHttpUtils;
import bwie.mylibrary.utils.XUtils_HttpUtils;
import okhttp3.Callback;

/**
 * Created by zjj on 2016/11/8.
 */
public abstract class BaseActivity extends AppCompatActivity implements MyOnCreate {
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /**
     * [是否设置沉浸状态栏]
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    private View mRootView;

//没有用到
    public View getmRootView() {
        return mRootView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //有布局
        if (bindlayout() != 0) {
            int layout = bindlayout();
            mRootView = View.inflate(this, layout, null);
        //是否设置沉浸式状态栏:注意位置是在设置布局之前*****
             if(isSetStatusBar){
                 //extends activity调RequestWindowFeature(Window.FEATURE_NO_TITLE);
                 //extends AppCompatActivity 调supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
                supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
                steepStatusBar();
            }
            //设置布局文件
            setContentView(mRootView);
            initdata();
            //此方法要写到初始化控件之前:否则报空指针****
            ButterKnife.bind(this);
            initView();
            loaddata();


        } else {
            Log.e("BaseActivity", "bindlayout return 0");
        }

    }
//写了泛型---用了插件之后,这个方法也不用写了
    public < T extends View > T findView(int resId){
        return (T)findViewById(resId);
    }


    /**
     * Okhttp get请求异步
     * @param url
     * @param callback
     */
    public void getasyn(String url, int tag,Callback callback) {
        OkHttpUtils.get(url, callback);
    }


    /**
     *  xUtils--httpUtils请求网络
     * @param url 请求的网址
     * @param tag 标识(INIT,REFRESH,LOADMORE)
     * @param handler Handler
     */
    public void gethttpUtils(String url, int tag, Handler handler) {
        XUtils_HttpUtils.requestdata(url, tag, handler);
    }


    /**
     * Toast吐司
     * @param text 吐司的内容
     */
    public void showToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     *不带值跳转
     * @param cls 要跳转的activity
     */
    public void startAct(Class<? extends BaseActivity> cls) {
        startActivity(new Intent(this, cls));

    }

    /**
     *带值跳转
     * @param cls 要跳转的activity
     * @param bundle intent 的传参bundle类型
    */
    public void startAct(Class<? extends BaseActivity> cls,Bundle bundle){
        Intent intent=new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 添加fragment(两个参数)
     * @param containerViewId 要添加到的容器
     * @param fragment 要添加的fragment
     */
    public void addfragment(int containerViewId, Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerViewId,fragment);
        transaction.commit();
    }

    /**
     *  替换fragment(两个参数)
     * @param containerViewId
     * @param fragment
     */
    public void replacefragment(int containerViewId, Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerViewId,fragment);
        transaction.commit();
    }

    /**
     * 移除fragment
     * @param fragment 要移除的fragment
     */
    public void removefragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }
    /**
     * [沉浸状态栏]
     */
    public  void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

//activity销毁的时候,把mRootView置为空
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootView=null;
        ButterKnife.unbind(this);
    }
}
