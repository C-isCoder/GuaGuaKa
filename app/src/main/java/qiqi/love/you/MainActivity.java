package qiqi.love.you;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mPanel;
    private Button mGuatu;
    private Intent intent;
    private Button mGuagua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPanel = (Button) findViewById(R.id.panel);
        mGuatu = (Button) findViewById(R.id.guatu);
        mGuagua = (Button) findViewById(R.id.guaguaka);
        mPanel.setOnClickListener(this);
        mGuatu.setOnClickListener(this);
        mGuagua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel:
                intent = new Intent(MainActivity.this, PanelActivity.class);
                startActivity(intent);
                break;
            case R.id.guatu:
                intent = new Intent(MainActivity.this, GuaTuActivity.class);
                startActivity(intent);
                break;
            case R.id.guaguaka:
                intent = new Intent(MainActivity.this, GuaGuaKaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
