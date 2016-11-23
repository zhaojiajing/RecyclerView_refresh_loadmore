package bwie.recyclerview_refresh_loadmore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import bwie.mylibrary.BaseViewHolder;
import bwie.recyclerview_refresh_loadmore.bean.Joke;

/**
 * Created by zjj on 2016/11/22.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final List<Joke.ResultBean.DataBean> mydata;
    private OnitemselectedListenner onitemselectedListenner;

    public RecyclerViewAdapter(Context context, List<Joke.ResultBean.DataBean> mydata) {
        this.context = context;
        this.mydata = mydata;
    }
    //1.定义接口,接口中有一个抽象方法,接口是public的
    public interface OnitemselectedListenner{
        void onSelectedListenner(int position);
    }
    //2.定义一个方法,参数是一个接口类型
    public void setOnItemSelectedListernner(OnitemselectedListenner onitemselectedListenner){
        this.onitemselectedListenner =onitemselectedListenner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.text1.setText(mydata.get(position).content + "\n" + mydata.get(position).updatetime);
        //3.在需要的地方调用,注意是在点击item时调用,注意判断接口是否为空,如果(那边)没有实例化接口就会报空指针
        if(onitemselectedListenner!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onitemselectedListenner.onSelectedListenner(position);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }

    class MyViewHolder extends BaseViewHolder<Joke.ResultBean.DataBean> {
        private final TextView text1;

        public MyViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void setData(Context context, Joke.ResultBean.DataBean dataBean) {

        }


    }

}
