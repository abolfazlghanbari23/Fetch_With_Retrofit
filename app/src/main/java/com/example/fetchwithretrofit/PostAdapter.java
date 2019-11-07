package com.example.fetchwithretrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    OnPostListener mOnPostListener;




    public PostAdapter(List<Post> postList, OnPostListener mOnPostListener) {
            if (postList == null)
            postList = new ArrayList<>();
        this.postList = postList;
        this.mOnPostListener = mOnPostListener;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View postView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_post, viewGroup, false);

        return new PostViewHolder(postView, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, final int i) {
        postViewHolder.bind(postList.get(i));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_title;
        OnPostListener onPostListener;

        public PostViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.tv_title_post);
            this.onPostListener = onPostListener;
            itemView.setOnClickListener(this);
        }


        public void bind(Post post) {
            txt_title.setText(post.getTitle());
        }



        @Override
        public void onClick(View v) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener {
        void onPostClick(int position);
    }
}


