package qiqi.love.you;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import qiqi.love.you.view.PanelView;


/**
 * Created by iscod on 2016/5/6.
 */
public class PanelActivity extends Activity {
    private Button mClean;
    private PanelView panelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        mClean = (Button) findViewById(R.id.clean);
        panelView = (PanelView) findViewById(R.id.panelview);
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panelView.clearPanel();
            }
        });
    }
}
