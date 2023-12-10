package com.crazyostudio.to_do_list.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.ImageAttachmentLayoutBinding;
import com.crazyostudio.to_do_list.interface_class.ImageAttachmentClick;

import java.util.ArrayList;

public class ImageAttachmentAdapter extends RecyclerView.Adapter<ImageAttachmentAdapter.ImageAttachmentAdapterViewHolder>{
    ArrayList<String> uris;
    Context context;
    ImageAttachmentClick click;

    public ImageAttachmentAdapter(ArrayList<String> uris, Context context, ImageAttachmentClick click) {
        this.uris = uris;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ImageAttachmentAdapter.ImageAttachmentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageAttachmentAdapter.ImageAttachmentAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.image_attachment_layout,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ImageAttachmentAdapter.ImageAttachmentAdapterViewHolder holder, int position) {
        String image = uris.get(position);
        Glide.with(context).load(image).placeholder(R.drawable.loading).into(holder.binding.Attachment);
//        holder.binding.Attachment.setImageURI(image);
        holder.binding.removeImage.setOnClickListener(remove-> click.remove(position));
        
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public static class ImageAttachmentAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageAttachmentLayoutBinding binding;
        public ImageAttachmentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ImageAttachmentLayoutBinding.bind(itemView);
        }
    }
}
