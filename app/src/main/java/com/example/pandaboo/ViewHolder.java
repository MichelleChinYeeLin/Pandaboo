/*package com.example.pandaboo;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

    view = itemView;
    itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());

        }
    });
    itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mClickListener.onItemLongClick(view, getAdapterPosition);
            return false;
        }
    });

        }

    }

    public void setdetails(Context context, String title, String image) {

        TextView mtitletv = view.findViewById(R.id.rTitleView);
        ImageView mImagetv = view.findViewById(R.id.rImageView);

        mtitletv.setText(title);
        Picasso.get().load(image).into(mImagetv);

        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.startAnimation(animation);

    }
    private ViewHolder.ClickListener mClickListener;

    public abstract class ClickListener {
        abstract void onItemclick(View view, int position);
        abstract void onItemLongclick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }


}
*/