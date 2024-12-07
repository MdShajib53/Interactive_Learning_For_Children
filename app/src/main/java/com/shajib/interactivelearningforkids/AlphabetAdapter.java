package com.shajib.interactivelearningforkids;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AlphabetAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mAlphabetImages;

    public AlphabetAdapter(Context context, int[] alphabetImages) {
        mContext = context;
        mAlphabetImages = alphabetImages;
    }

    @Override
    public int getCount() {
        return mAlphabetImages.length;
    }

    @Override
    public Object getItem(int position) {
        return mAlphabetImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // If convertView is null, inflate the layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.item_alphabet, parent, false);
        }

        // Get reference to ImageView in item layout
        imageView = convertView.findViewById(R.id.imageViewAlphabet);

        // Set image resource for the ImageView
        imageView.setImageResource(mAlphabetImages[position]);

        return convertView;
    }
}
