package com.example.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.models.Rows;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactsViewHolder> {

    private Context context;
    private List<Rows> facts;
    private OnItemClickListener listener;

    public FactsAdapter(Context context, List<Rows> facts, OnItemClickListener listener) {
        this.context = context;
        this.facts = facts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facts_item_list_row, parent, false);
        return new FactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FactsViewHolder holder, int position) {
        if (facts != null) {
            Rows rows = facts.get(position);
            if (rows != null) {
                if (rows.getTitle() != null && rows.getTitle().length() > 0) {
                    holder.title.setText(rows.getTitle());
                } else {
                    holder.title.setText(context.getResources().getString(R.string.empty_title));
                }
                if (rows.getDescription() != null && rows.getDescription().length() > 0) {
                    holder.description.setText(rows.getDescription());
                } else {
                    holder.description.setText(context.getResources().getString(R.string.empty_desc));
                }
                if (rows.getImageHref() != null && rows.getImageHref().length() > 0) {
                    Picasso.get().load(rows.getImageHref()).into(holder.imageHref);
                }
            }
            holder.itemView.setOnClickListener(view -> {
                if (listener != null)
                    listener.onItemClick(rows);
            });
        }


    }

    @Override
    public int getItemCount() {
        return facts.size();
    }

    public class FactsViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private ImageView imageHref;

        public FactsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageHref = itemView.findViewById(R.id.imageHref);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Rows rows);
    }
}
