package com.example.phieldshield;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Adpter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adpter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView;
        TextView title;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Developers Information");
                final View customLayout1
                        = layoutInflater.inflate(R.layout.customdialogdetect,null);
                builder.setView(customLayout1);
                builder.setCancelable(false);
                dialog=builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogdrawable);
                ImageView success = customLayout1.findViewById(R.id.result_img);
                TextView title = customLayout1.findViewById(R.id.main_text);
                TextView msg = customLayout1.findViewById(R.id.mes_text);
                success.setImageResource(models.get(position).getImage());
                title.setText(models.get(position).getTitle());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    msg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                }
                msg.setText(models.get(position).getDesc());
                Button ok=customLayout1.findViewById(R.id.button);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}