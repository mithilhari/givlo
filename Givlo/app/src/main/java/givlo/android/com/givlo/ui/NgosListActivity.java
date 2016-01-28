package givlo.android.com.givlo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import givlo.android.com.givlo.R;
import givlo.android.com.givlo.adapter.NgoAdapter;
import givlo.android.com.givlo.beans.NGODataBean;
import givlo.android.com.givlo.data.NGOData;

public class NgosListActivity extends AppCompatActivity {
    private ListView mNgosListView;
    private ArrayList<NGODataBean> mNgosArrayList;
    private NgoAdapter mNgoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngos_list);
        mNgosListView = (ListView)findViewById(R.id.listView);
        mNgosArrayList=new ArrayList<>();
        mNgosArrayList.addAll(new NGOData().getNgoDetails());
        mNgoAdapter=new NgoAdapter(NgosListActivity.this,mNgosArrayList);
        mNgosListView.setAdapter(mNgoAdapter);
    }
}
