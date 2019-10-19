package com.example.dnail1.Product;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dnail1.R;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder> {

    private Context mContext;
    private List<Model> modelList;
    private int selectedPosition=-1;
    TextView txtMoney, text_linearBookSuccessful_selectedModel, text_linearBookSuccessful_selectedModelPrice;
    ImageView image_linearBookSuccessful_selectedModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNameModel, txtPriceModel;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtNameModel = view.findViewById(R.id.txtNameModel);
            txtPriceModel =  view.findViewById(R.id.txtPriceModel);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    public ModelAdapter(Context mContext, List<Model> modelList, TextView txtMoney,
                        TextView text_linearBookSuccessful_selectedModel, TextView text_linearBookSuccessful_selectedModelPrice,
                        ImageView image_linearBookSuccessful_selectedModel) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.text_linearBookSuccessful_selectedModel = text_linearBookSuccessful_selectedModel;
        this.text_linearBookSuccessful_selectedModelPrice = text_linearBookSuccessful_selectedModelPrice;
        this.txtMoney = txtMoney;
        this.image_linearBookSuccessful_selectedModel = image_linearBookSuccessful_selectedModel;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_model, parent, false);

        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Model model = modelList.get(position);
        holder.txtNameModel.setText(model.getName());
        holder.txtPriceModel.setText(model.getprice() + "K");

        // loading model cover using Glide library
        Glide.with(mContext).load(model.getThumbnail()).into(holder.thumbnail);

        if(selectedPosition==position){
            holder.itemView.setBackgroundColor(Color.parseColor("#fd718b"));
        }
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;

                // Set text money in bottom bar
                Model m = modelList.get(selectedPosition);
                txtMoney.setText(m.getprice() + "K");

                // Set dialog show information of model
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.item_model);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView name =  dialog.findViewById(R.id.txtNameModel);
                TextView price = dialog.findViewById(R.id.txtPriceModel);
                ImageView thumbnail = dialog.findViewById(R.id.thumbnail);

                setDataToView(name, price, thumbnail, position);

                Window window = dialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);

                dialog.show();

                // Set text in linear book successful
                text_linearBookSuccessful_selectedModel.setText(mContext.getString(R.string.text_da_chon) + " " + m.getName());
                text_linearBookSuccessful_selectedModelPrice.setText(mContext.getString(R.string.text_mau_nay_co_gia) + " " + m.getprice() + "K");
                image_linearBookSuccessful_selectedModel.setImageResource(m.getThumbnail());

                notifyDataSetChanged();
            }
        });
    }

    private void setDataToView(TextView name, TextView price, ImageView thumbnail, int position) {
        name.setText(modelList.get(position).getName());
        price.setText(modelList.get(position).getprice() + "K");
        thumbnail.setImageResource(modelList.get(position).getThumbnail());
    }

    public int getItemCount() {
        return modelList.size();
    }
}