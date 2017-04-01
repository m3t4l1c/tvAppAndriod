package com.example.nino.tvlistingmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nino.tvlistingmovies.MoviesListingFragment.OnListFragmentInteractionListener;
import com.example.nino.tvlistingmovies.utils.MovieContent.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MovieRecyclerViewAdapter(List<MovieItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.bindMovieData(holder.mItem);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final static String MOVIES_IMAGES_URL = "http://image.tmdb.org/t/p/w500";

        public final View mView;
        public final ImageView mMoviePoster;
        public final TextView mMovieTitle;
        public final RatingBar mMovieRattingBar;
        public final TextView mMovieVoteCount;
        public final TextView mMovieOverview;
        public MovieItem mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMovieRattingBar = (RatingBar) itemView.findViewById(R.id.rb_vote_average);
            mMovieRattingBar.setFocusable(false);
            mMovieVoteCount = (TextView) itemView.findViewById(R.id.tv_vote_count);
            mMovieOverview = (TextView) itemView.findViewById(R.id.tv_overview);

        }

        public void bindMovieData(MovieItem movieData){
            mItem = movieData;
            Picasso.with(mMoviePoster.getContext())
                    .load(MOVIES_IMAGES_URL + mItem.mPoster)
                    .placeholder(R.drawable.ic_image_photo)
                    .error(R.drawable.ic_image_error)
                    .into(mMoviePoster);
            mMovieTitle.setText(movieData.mTitle);
            mMovieVoteCount.setText(String.valueOf(movieData.mTotalVotes));
            mMovieOverview.setText(movieData.mOverview);
            mMovieRattingBar.setRating(movieData.mAvgVotes);
        }

    }
}