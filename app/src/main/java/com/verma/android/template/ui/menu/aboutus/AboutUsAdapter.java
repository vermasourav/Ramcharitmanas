package com.verma.android.template.ui.menu.aboutus;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.verma.android.template.R;
import com.verma.android.template.ui.menu.aboutus.models.Member;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder> {

    private static final String TAG = "AboutRecyclerviewAdapte";
    Context context;
    ArrayList<Member> members;

    public AboutUsAdapter(Context context, List<Member> members) {
        this.context = context;
        this.members = (ArrayList<Member>) members;
    }

    @NonNull
    @Override

    public AboutUsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AboutUsViewHolder(LayoutInflater.from(context).inflate(R.layout.office_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AboutUsViewHolder holder, int position) {
        final Member member = members.get(position);

        try {
           AboutUsHelper.setImageWithGlide(context, member.getImageUrl(), holder.imageView);
        } catch (Exception e) {
            Timber.e("onBindViewHolder: %s", e.toString());
        }
        holder.textViewName.setText(member.getName());
        holder.textViewPost.setText(member.getPost());

        holder.cardView.setOnClickListener(view -> {
            if (member.getContactUrl().isEmpty()) {
                Toast.makeText(context, "" + member.getName() + " has added no contact info", Toast.LENGTH_SHORT).show();
            } else {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setShowTitle(true);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(member.getContactUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class AboutUsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewName;
        TextView textViewPost;
        CardView cardView;

        public AboutUsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.faceImage);
            textViewName = itemView.findViewById(R.id.nameTv);
            textViewPost = itemView.findViewById(R.id.postTv);

            cardView = itemView.findViewById(R.id.about_card);
        }
    }
}
