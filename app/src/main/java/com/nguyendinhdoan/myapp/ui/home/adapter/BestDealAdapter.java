package com.nguyendinhdoan.myapp.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nguyendinhdoan.myapp.R;
import com.nguyendinhdoan.myapp.data.models.BestDealDTO;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class BestDealAdapter extends BaseBannerAdapter<BestDealDTO, BestDealAdapter.BestDealViewHolder> {

    @Override
    protected void onBind(BestDealViewHolder holder, BestDealDTO data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public BestDealViewHolder createViewHolder(View itemView, int viewType) {
        return new BestDealViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_best_deal;
    }

    static class BestDealViewHolder extends BaseViewHolder<BestDealDTO> {

       public BestDealViewHolder(@NonNull View itemView) {
           super(itemView);
       }

       @Override
       public void bindData(BestDealDTO data, int position, int pageSize) {
           ImageView imageView = findView(R.id.iv_best_deal);

           Glide.with(itemView.getContext())
                   .load(data.getImage())
                   .into(imageView);

           setText(R.id.tv_best_deal, data.getName());
       }
   }
}
