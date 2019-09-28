package com.testrates.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.testrates.data.model.RateItemObject;

import java.util.ArrayList;

public class MyDiffUtilCallBack extends DiffUtil.Callback {
    /**
     *
     */
    private ArrayList<RateItemObject> newList;
    private ArrayList<RateItemObject> oldList;

    public MyDiffUtilCallBack(ArrayList<RateItemObject> newList, ArrayList<RateItemObject> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getCountryRate() == (oldList.get(oldItemPosition).getCountryRate());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        RateItemObject newModel = newList.get(newItemPosition);
        RateItemObject oldModel = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if (newModel.getCountryRate() != (oldModel.getCountryRate())) {
            diff.putDouble("price", newModel.getCountryRate());
        }
        if (diff.size() == 0) {
            return null;
        }
        return diff;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

