package com.gh.myrxretrofitdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.gh.myrxretrofitdemo.entity.SubjectResulte;
import com.gh.myrxretrofitdemo.entity.api.SubjectPostApi;
import com.gh.rxretrofitlibrary.api.BaseResultEntity;
import com.gh.rxretrofitlibrary.http.HttpManager;
import com.gh.rxretrofitlibrary.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 封装完成以后就用这个了
 * getpost请求可以
 * https://github.com/wzgiceman/RxjavaRetrofitDemo-master
 * 对缓存的处理还不会做,想要把返回的string转换成对象不成功,有时间研究下泛型
 */
public class MainActivity extends RxAppCompatActivity {

    @BindView(R.id.id_tv_showmsg)
    TextView id_tv_showmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.id_btn_getmsg)
    public void getNet() {
        SubjectPostApi postApi = new SubjectPostApi(listener, this);
        postApi.setAll(true);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(postApi);
    }

    HttpOnNextListener listener = new HttpOnNextListener<List<SubjectResulte>>() {

        @Override
        public void onNext(List<SubjectResulte> subjects) {
            id_tv_showmsg.setText("网络返回：\n" + subjects.toString());
           for (SubjectResulte subject : subjects) {
               Log.d("gh", "网络返回" + subject.getId() + "---" + subject.getName() + "---" + subject.getTitle());
            }
        }

        @Override
        public void onCacheNext(String cache, String explain) {
            Gson gson = new Gson();
            Type type = new TypeToken<BaseResultEntity<List<SubjectResulte>>>() {
            }.getType();
            BaseResultEntity resultEntity= gson.fromJson(cache, type);
            id_tv_showmsg.setText("缓存返回：\n"+ resultEntity.getData().toString());
        }

        @Override
        public void onCacheNext(List<SubjectResulte> cache, String explain) {
            //返回的数据一直有问题
            id_tv_showmsg.setText("缓存返回：\n" + cache.toString());
        }

        @Override
        public void onError(Throwable e) {
            id_tv_showmsg.setText("失败：\n" + e.toString());
        }

        @Override
        public void onCancel() {
            id_tv_showmsg.setText("取消請求");
        }
    };

    //测试跟上数据     BaseResultEntity中data必须改成对应的参数名....所以很麻烦
//    @OnClick(R.id.id_btn_getgenshangmsg)
//    public void getGenShangNet() {
//        GenShangTripApi api = new GenShangTripApi(genshangListener, this);
//        HttpManager.getInstance().doHttpDeal(api);
//    }
//
//    HttpOnNextListener genshangListener = new HttpOnNextListener<List<LeaderScheduleVo>>() {
//
//        @Override
//        public void onNext(List<LeaderScheduleVo > subjects) {
//            id_tv_showmsg.setText("网络返回：\n" + subjects.toString());
//        }
//
//        @Override
//        public void onCacheNext(String cache, String explain) {
//            Gson gson = new Gson();
//            Type type = new TypeToken<BaseResultEntity<List<LeaderScheduleVo >>>() {
//            }.getType();
//            BaseResultEntity resultEntity = gson.fromJson(cache, type);
//            id_tv_showmsg.setText("缓存返回：\n" + resultEntity.getData().toString());
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            id_tv_showmsg.setText("失败：\n" + e.toString());
//        }
//
//        @Override
//        public void onCancel() {
//            id_tv_showmsg.setText("取消請求");
//        }
//    };
}
