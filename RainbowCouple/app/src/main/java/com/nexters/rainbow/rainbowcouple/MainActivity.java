package com.nexters.rainbow.rainbowcouple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nexters.rainbow.rainbowcouple.bill.list.BillListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 임시 테스트용 첫 화면. 리스트 뷰 */
        BillListFragment fragment = BillListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentPanel, fragment, fragment.getFragmentTag()).commitAllowingStateLoss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
