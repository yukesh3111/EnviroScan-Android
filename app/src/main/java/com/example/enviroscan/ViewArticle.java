package com.example.enviroscan;

import static com.example.enviroscan.api.RetroClient.Base_Url;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityViewArticleBinding;
import com.example.enviroscan.databinding.ArticleItemBinding;
import com.example.enviroscan.serverresponse.ViewArticleModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewArticle extends AppCompatActivity {

    private ActivityViewArticleBinding binding;
    private ArrayList<ViewArticleModel.Article> articleList;
    private ViewArticleAdapter adapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Inflate layout and set content view
        binding = ActivityViewArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Initialize context and article list
        context = ViewArticle.this;
        articleList = new ArrayList<>();
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Set up RecyclerView
        adapter = new ViewArticleAdapter(context, articleList);
        binding.recarticleitems.setLayoutManager(new LinearLayoutManager(context));
        binding.recarticleitems.setAdapter(adapter);
        binding.loadingAnimation.setVisibility(View.VISIBLE);
        binding.darkOverlay.setVisibility(View.VISIBLE);
        // Fetch articles
        loadArticle();




    }

    private void loadArticle() {
        Call<ViewArticleModel> articleDetailsCall = RetroClient.getInterface().getviewarticle();

        articleDetailsCall.enqueue(new Callback<ViewArticleModel>() {
            @Override
            public void onResponse(Call<ViewArticleModel> call, Response<ViewArticleModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    // Check if data exists
                    if (response.body().articles != null && !response.body().articles.isEmpty()) {
                        // Clear existing list and add new data
                        articleList.clear();
                        articleList.addAll(response.body().articles);
                        adapter.notifyDataSetChanged(); // Notify adapter about data change
                        Toast.makeText(context, "Articles fetched successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No articles found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Failed to fetch articles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ViewArticleModel> call, Throwable throwable) {
                // Handle failure
                Toast.makeText(context, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // RecyclerView Adapter
    public class ViewArticleAdapter extends RecyclerView.Adapter<ViewArticleAdapter.ArticleHolder> {

        private final Context context;
        private final ArrayList<ViewArticleModel.Article> list;

        public ViewArticleAdapter(Context context, ArrayList<ViewArticleModel.Article> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate item layout
            ArticleItemBinding itemBinding = ArticleItemBinding.inflate(LayoutInflater.from(context), parent, false);
            return new ArticleHolder(itemBinding);
        }


        @Override
        public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
            // Bind data to views
            ViewArticleModel.Article article = list.get(position);
            holder.itemBinding.articleTitle.setText(article.getArticleTitle());
            holder.itemBinding.articleAbstraction.setText(article.getArticleAbstraction());
            holder.itemBinding.publishdate.setText(article.getPublishDate());
            Glide.with(context)
                    .load(Base_Url+"/articleimage/"+article.getFilename())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.errorimage)
                    .into(holder.itemBinding.articleimg);
            binding.loadingAnimation.setVisibility(View.GONE);
            binding.darkOverlay.setVisibility(View.GONE);



            // Handle item click
            holder.itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String idstr =article.getArticleid();
                    Intent intent = new Intent(context, AritcleActivity.class);
                    intent.putExtra("message_key",idstr + "");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        // ViewHolder class
        public static class ArticleHolder extends RecyclerView.ViewHolder {
            private final ArticleItemBinding itemBinding;

            public ArticleHolder(ArticleItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }
        }
    }
}
