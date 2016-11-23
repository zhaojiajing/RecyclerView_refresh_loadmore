package bwie.recyclerview_refresh_loadmore;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bwie.mylibrary.DividerItemDecoration;
import bwie.mylibrary.utils.OkHttpUtils;
import bwie.recyclerview_refresh_loadmore.adapter.RecyclerViewAdapter;
import bwie.recyclerview_refresh_loadmore.bean.Joke;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private int i;
    private String path;
    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADMORE = 2;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    List<Joke.ResultBean.DataBean> mydata = new ArrayList<>();

    /**
     * 注意位置,太岁头上,即oncreat()方法之上,但在类之下
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String json = (String) msg.obj;
                Joke joke = new Gson().fromJson(json, Joke.class);
                List<Joke.ResultBean.DataBean> data = joke.result.data;
                switch (msg.arg1) {
                    case INIT:
                        mydata.addAll(data);
                        // mPullLoadMoreRecyclerView.setGridLayout(2);
                        //设置布局
                        mPullLoadMoreRecyclerView.setLinearLayout();
                        mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mydata);
                        //设置适配器
                        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
                        //添加分割线 mylibrary中的DividerItemDecoration类参数2:DividerItemDecoration类点出的属性
                        mPullLoadMoreRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
                        break;
                    case REFRESH:
                        mydata.clear();
                        mydata.addAll(data);
                        //通知适配器数据改变了
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        //刷新/加载完成
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        break;
                    case LOADMORE:
                        //设置上拉刷新的文字
                        mPullLoadMoreRecyclerView.setFooterViewText("loading");
                        mydata.addAll(data);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        break;
                }
                mRecyclerViewAdapter.setOnItemSelectedListernner(new RecyclerViewAdapter.OnitemselectedListenner() {
                    @Override
                    public void onSelectedListenner(int position) {
                        Toast.makeText(MainActivity.this, mydata.get(position).content, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //desc 降序, asc 升序默认的
        i = 2;
        path = "http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=" + i + "&pagesize=10&sort=asc&time=1418745237";
        //请求数据
        requestdata(path, INIT);
        //设置上拉加载,下拉刷新
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                path = "http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=1&pagesize=10&sort=asc&time=1418745237";
                requestdata(path, REFRESH);
            }

            @Override
            public void onLoadMore() {
                path = "http://japi.juhe.cn/joke/content/list.from?key=%20874ed931559ba07aade103eee279bb37%20&page=" + ++i + "&pagesize=10&sort=asc&time=1418745237";
                requestdata(path, LOADMORE);
            }
        });
    }

    private void requestdata(String path, final int tag) {
        OkHttpUtils.get(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("异常" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                handler.obtainMessage(1, tag, 0, string).sendToTarget();
            }
        });
    }

    private void initView() {
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
    }
}