package com.app.ndiazgranados.currency.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.app.ndiazgranados.currency.R;
import com.app.ndiazgranados.currency.model.CurrencyItem;

import java.util.List;

/**
 * Created by user on 08/04/2016.
 */
public class ConverterAdapter extends RecyclerView.Adapter<ConverterAdapter.ViewHolder> {
    private List<CurrencyItem> dataSet;
    private int usdValue;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView txtViewCurrency;
        public TextView txtViewValue;
        public TextView txtViewRate;

        public ViewHolder(View viewRow) {
            super(viewRow);
            container = viewRow;
            txtViewCurrency = (TextView) viewRow.findViewById(R.id.fragment_converter_item_currency);
            txtViewValue = (TextView) viewRow.findViewById(R.id.fragment_converter_item_value);
            txtViewRate = (TextView) viewRow.findViewById(R.id.fragment_converter_item_rate);
        }
    }

    public ConverterAdapter(List<CurrencyItem> myDataset, int usdValue) {
        dataSet = myDataset;
        this.usdValue = usdValue;
    }

    public List<CurrencyItem> getDataSet(){
        return dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConverterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View viewRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_converter_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(viewRow);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CurrencyItem currencyItem = (CurrencyItem) dataSet.get(position);
        holder.txtViewCurrency.setText(currencyItem.getAcronym());
        holder.txtViewValue.setText(currencyItem.getValue());
        holder.txtViewRate.setText(currencyItem.getRate());

        if (position == 0) {
            holder.txtViewCurrency.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            holder.txtViewValue.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            holder.txtViewRate.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        } else {
            setAnimation(holder.container, position);
        }
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = (position%2 == 0) ?
            AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left) :
                    AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_out_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return (dataSet != null) ? dataSet.size() : 0;
    }
}