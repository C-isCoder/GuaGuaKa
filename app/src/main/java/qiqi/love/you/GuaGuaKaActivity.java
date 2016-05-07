package qiqi.love.you;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import qiqi.love.you.view.GuaGuakaView;

/**
 * Created by iscod on 2016/5/7.
 */
public class GuaGuaKaActivity extends Activity {
    private Button mRest;
    private GuaGuakaView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guaguaka);
        mRest = (Button) findViewById(R.id.guaguaka_rest);
        mView = (GuaGuakaView) findViewById(R.id.guaguakaview);
        mRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.rest();
            }
        });
        mView.setText("￥10000,000");
    }
}
