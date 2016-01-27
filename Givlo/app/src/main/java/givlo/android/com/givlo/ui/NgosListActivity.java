package givlo.android.com.givlo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import givlo.android.com.givlo.R;
import givlo.android.com.givlo.adapter.NgoAdapter;
import givlo.android.com.givlo.beans.NGODataBean;

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
        for(int i=0;i<11;i++){
            NGODataBean bean=new NGODataBean();
            bean.setNgoId(i);
            bean.setNgoName("Orphanage homes");
            bean.setNgoAddress("Flat no: 203, SRT 390, Sanath Nagar, Hyderabad - 500018");
            bean.setNgoEmail("07it.jbrec@gmail.com");
            bean.setNgoNumber("07075254213");
            mNgosArrayList.add(bean);

        }
        mNgoAdapter=new NgoAdapter(NgosListActivity.this,mNgosArrayList);
        mNgosListView.setAdapter(mNgoAdapter);
    }
}
