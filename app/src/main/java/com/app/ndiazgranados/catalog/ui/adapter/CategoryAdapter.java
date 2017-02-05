package com.app.ndiazgranados.catalog.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.app.ndiazgranados.catalog.R;
import com.app.ndiazgranados.catalog.model.web.Category;

import java.util.List;
import java.util.Random;

/**
 * @author n.diazgranados
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClicked(View view);
    }

    private List<Category> dataSet;
    private OnCategoryClickListener onCategoryClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView txtViewName;

        public ViewHolder(View viewRow) {
            super(viewRow);
            container = viewRow;
            txtViewName = (TextView) viewRow.findViewById(R.id.fragment_category_item);
        }
    }

    public CategoryAdapter(List<Category> myDataset, OnCategoryClickListener onCategoryClickListener) {
        dataSet = myDataset;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    public List<Category> getDataSet() {
        return dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        // create a new view
        View viewRow = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.fragment_category_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(viewRow);
        viewRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCategoryClickListener.onCategoryClicked(view);
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = (Category) dataSet.get(position);
        holder.txtViewName.setText(category.getAttributes().getLabel());

        setAnimation(holder.container, position);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        Random random = new Random();
        setAnimation(holder.container, random.nextInt(((9 - 7) + 1) + 7));
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        Animation animation = (position % 2 == 0) ?
                              AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_in_from_left) :
                              AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_in_right);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.container.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return (dataSet != null) ? dataSet.size() : 0;
    }

    public List<Category> getDataset(){
        return dataSet;
    }
}