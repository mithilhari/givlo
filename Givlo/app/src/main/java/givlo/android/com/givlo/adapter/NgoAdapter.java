package givlo.android.com.givlo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import givlo.android.com.givlo.R;
import givlo.android.com.givlo.beans.NGODataBean;

public class NgoAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<NGODataBean> mNgoList;
    public Holder holder;

    public NgoAdapter(Context context,
                      ArrayList<NGODataBean> fragmentTitles) {
        this.context = context;
        this.mNgoList = fragmentTitles;
    }

    @Override
    public int getCount() {
        return mNgoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNgoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new Holder();
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = infalInflater.inflate(R.layout.list_row_ngo_data,
                    null);
            holder.mNgoName = (TextView) convertView
                    .findViewById(R.id.ngo_title);
            holder.mNgoAddress = (TextView) convertView
                    .findViewById(R.id.ngo_address);
            holder.mNgoEmail = (TextView) convertView
                    .findViewById(R.id.ngo_email);
            holder.mNgoPhone = (TextView) convertView
                    .findViewById(R.id.ngo_phone);
            holder.mNgoWebsite = (TextView) convertView
                    .findViewById(R.id.ngo_website);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if(mNgoList.get(position).getNgoName()!=null&&mNgoList.get(position).getNgoName().length()>0){
            holder.mNgoName.setVisibility(View.VISIBLE);
            holder.mNgoName.setText("" + mNgoList.get(position).getNgoName());
        }else{
            holder.mNgoName.setVisibility(View.GONE);
        }
        if(mNgoList.get(position).getNgoAddress()!=null&&mNgoList.get(position).getNgoAddress().length()>0){
            holder.mNgoAddress.setVisibility(View.VISIBLE);
            holder.mNgoAddress.setText(""+mNgoList.get(position).getNgoAddress());
        }else{
            holder.mNgoAddress.setVisibility(View.GONE);
        }
        if(mNgoList.get(position).getNgoEmail()!=null&&mNgoList.get(position).getNgoEmail().length()>0){
            holder.mNgoEmail.setVisibility(View.VISIBLE);
            String text = "<font color=#888888>email: </font> <font color=#3F51B5>"+mNgoList.get(position).getNgoEmail()+"</font>";
            holder.mNgoEmail.setText(Html.fromHtml(text));
        }else{
            holder.mNgoEmail.setVisibility(View.GONE);
        }
        if(mNgoList.get(position).getNgoNumber()!=null&&mNgoList.get(position).getNgoNumber().length()>0){
            holder.mNgoPhone.setVisibility(View.VISIBLE);
            String text = "<font color=#888888>tel: </font> <font color=#3F51B5>"+mNgoList.get(position).getNgoNumber()+"</font>";
            holder.mNgoPhone.setText(Html.fromHtml(text));
        }else{
            holder.mNgoPhone.setVisibility(View.GONE);
        }
        if(mNgoList.get(position).getmNgoWebsite()!=null&&mNgoList.get(position).getmNgoWebsite().length()>0){
            holder.mNgoWebsite.setVisibility(View.VISIBLE);
            String text = "<font color=#3F51B5>"+mNgoList.get(position).getmNgoWebsite()+"</font>";
            holder.mNgoWebsite.setText(Html.fromHtml(text));
        }else{
            holder.mNgoWebsite.setVisibility(View.GONE);
        }
        holder.mNgoPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTOCallScreen(position);
            }
        });
        holder.mNgoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEmailScreen(position);
            }
        });
        holder.mNgoWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTOWebScreen(position);
            }
        });

        return convertView;
    }

    public void goTOWebScreen(int position){
        if(mNgoList.get(position).getmNgoWebsite()!=null&&mNgoList.get(position).getmNgoWebsite().length()>0){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mNgoList.get(position).getmNgoWebsite()));
            context.startActivity(i);
        }else{
            Toast.makeText(context,"No phone number",Toast.LENGTH_SHORT).show();
        }
    }

    public void goTOCallScreen(int position){
        if(mNgoList.get(position).getNgoNumber()!=null&&mNgoList.get(position).getNgoNumber().length()>0){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mNgoList.get(position).getNgoNumber()));
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"No web site url",Toast.LENGTH_SHORT).show();
        }
    }
    public void goToEmailScreen(int position){
        if(mNgoList.get(position).getNgoEmail()!=null&&mNgoList.get(position).getNgoEmail().length()>0){
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            emailIntent.setType("vnd.android.cursor.item/email");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {mNgoList.get(position).getNgoEmail()});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Email Subject");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My email content");
            context.startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
        }else{
            Toast.makeText(context,"No email id",Toast.LENGTH_SHORT).show();
        }
    }

    public class Holder {
        public TextView mNgoName, mNgoAddress,mNgoPhone,mNgoEmail,mNgoWebsite;
    }

}
