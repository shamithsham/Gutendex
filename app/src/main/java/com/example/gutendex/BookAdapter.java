package com.example.gutendex;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<BookModel> bookList;

    public BookAdapter(List<BookModel> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = bookList.get(position);

        holder.title.setText(book.getTitle());

        String authors = TextUtils.join(", ", book.getAuthors());
        holder.author.setText(authors);

        holder.itemView.setOnClickListener(v -> {
            JSONObject formats = bookList.get(position).getFormats();
            String bookUrl = "";

            if (formats != null) {
                if (formats.has("text/html")) {
                    bookUrl = formats.optString("text/html");
                } else if (formats.has("text/html; charset=utf-8")) {
                    bookUrl = formats.optString("text/html; charset=utf-8");
                } else if (formats.has("text/plain; charset=utf-8")) {
                    bookUrl = formats.optString("text/plain; charset=utf-8");
                }
            }

            if (!bookUrl.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookUrl));
                v.getContext().startActivity(browserIntent);
            } else {
                Toast.makeText(v.getContext(), "No readable format found", Toast.LENGTH_SHORT).show();
            }
        });


        String imageUrl = book.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.no_data_available)
                    .error(R.drawable.no_data_available) // in case the URL is broken
                    .into(holder.image);
        } else {
            // Load a default image if the URL is empty
            holder.image.setImageResource(R.drawable.no_data_available);
        }

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.book_image);
            title = itemView.findViewById(R.id.book_title);
            author = itemView.findViewById(R.id.book_author);
        }
    }
}
