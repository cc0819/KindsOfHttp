package com.kindofhttp.cc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.show_text)
    TextView showText;
//    @BindView(R.id.okhttp_get)
//    Button okhttpGet;
//    @BindView(R.id.okhttp_post)
//    Button okhttpPost;
//    @BindView(R.id.retrofit_get)
//    Button retrofitGet;
//    @BindView(R.id.retrofit_post)
//    Button retrofitPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.okhttp_get, R.id.okhttp_post, R.id.retrofit_get, R.id.retrofit_post})
    public void onClickViews(View view) {
        switch (view.getId()) {
            case R.id.okhttp_get:
                okHttpGetLoad();
                break;
            case R.id.okhttp_post:
                okHttpPostLoad();

                break;
            case R.id.retrofit_get:
                retrofitGetLoad();

                break;
            case R.id.retrofit_post:
                retrofitPostLoad();
                break;

            default:
                break;
        }


    }

    private void okHttpGetLoad() {
    }

    private void okHttpPostLoad() {
    }

    private void retrofitGetLoad() {

    }

    private void retrofitPostLoad() {


    }


}
