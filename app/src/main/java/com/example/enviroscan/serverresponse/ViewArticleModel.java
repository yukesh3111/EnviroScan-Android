package com.example.enviroscan.serverresponse;

import java.util.ArrayList;
import java.util.Collection;

public class ViewArticleModel {
    public Collection<? extends Article> data;

    public class Article{
        public String getArticleAbstraction() {
            return articleAbstraction;
        }

        public void setArticleAbstraction(String articleAbstraction) {
            this.articleAbstraction = articleAbstraction;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String articleAbstraction;
        public String articleTitle;
        public String filename;

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String articleid;

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String publishDate;
    }


    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Article> articles;
    public boolean status;

}
