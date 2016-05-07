package qiqi.love.you;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import qiqi.love.you.view.GuaTuView;


/**
 * Created by iscod on 2016/5/7.
 */
public class GuaTuActivity extends Activity {
    private Button mRest;
    private GuaTuView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guatu);
        mRest = (Button) findViewById(R.id.rest);
        mView = (GuaTuView) findViewById(R.id.guatuview);
        mRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.rest();
            }
        });

    }
}
