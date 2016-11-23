package bwie.mylibrary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import bwie.mylibrary.utils.OkHttpUtils;
import okhttp3.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements MyOnCreate{


    private View mRootView;

    public BaseFragment() {

    }
    public View getmRootView() {
        return mRootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //有布局
        if (bindlayout() != 0) {
            int layout = bindlayout();
            mRootView = View.inflate(getActivity(), layout, null);
            //设置布局文件
          //  setContentView(mRootView);
            initdata();
            //此方法要写到初始化控件之前:否则报空指针****
            ButterKnife.bind(getActivity());
            initView();
            loaddata();
        } else {
            Log.e("BaseFragment", "bindlayout return 0");
        }

        // Inflate the layout for this fragment
        return mRootView;
    }

    /**
     * Okhttp get请求异步
     * @param url
     * @param callback
     */
    public void getasyn(String url, int tag, Callback callback) {
        OkHttpUtils.get(url, callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRootView=null;
      ButterKnife.unbind(this);
    }
    /**
     * Toast吐司
     * @param context 吐司的context上下文
     * @param text 吐司的内容
     */
    public void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
