package fto.ee.swk.freetimeorganizer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<DataAdapter> dataAdapters;
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){
        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        imageLoader.get(dataAdapterOBJ.getEventImageURL(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.drawable.logo, //Before loading server image the default showing image.
                        R.drawable.logo  //Error image if requested image does not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getEventImageURL(), imageLoader);
        Viewholder.EventNameTextView.setText(dataAdapterOBJ.getEventName());
        Viewholder.EventDateTextView.setText(dataAdapterOBJ.getEventDate().replace("-","."));
        if (!dataAdapterOBJ.getEventDate().equals(dataAdapterOBJ.getEventEndDate())){
            Viewholder.EventDateTextView.append(" - " + dataAdapterOBJ.getEventEndDate().replace("-","."));
        }

        Viewholder.EventTimeTextView.setText("Time: " + dataAdapterOBJ.getEventTime());
        if(dataAdapterOBJ.getEventPrice().equals("")){
            Viewholder.EventPriceTextView.setText("Price: FREE / unknown" );
        }else {
            Viewholder.EventPriceTextView.setText("Price: " + dataAdapterOBJ.getEventPrice() + " €");
        }

        Viewholder.EventGenreTextView.setText("Genre: " + dataAdapterOBJ.getEventGenre());

        if(!dataAdapterOBJ.getEventCity().equals("null") && !dataAdapterOBJ.getEventCity().equals("Estonia  ")){
            Viewholder.EventCityTextView.setText(dataAdapterOBJ.getEventCity() + ", ");
            Viewholder.EventCityTextView.append(dataAdapterOBJ.getEventCountry());
        } else {
            Viewholder.EventCityTextView.setText(dataAdapterOBJ.getEventCountry());
        }


    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView EventNameTextView;
        public TextView EventDateTextView;
        public TextView EventTimeTextView;
        public TextView EventCityTextView;
        public TextView EventPriceTextView;
        public TextView EventGenreTextView;
        public NetworkImageView VollyImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            EventNameTextView = (TextView) itemView.findViewById(R.id.eventNameTextView) ;
            EventDateTextView = (TextView) itemView.findViewById(R.id.eventDateTextView) ;
            EventTimeTextView = (TextView) itemView.findViewById(R.id.eventTimeTextView) ;
            EventCityTextView = (TextView) itemView.findViewById(R.id.eventCityTextView) ;
            EventPriceTextView = (TextView) itemView.findViewById(R.id.eventPriceTextView) ;
            EventGenreTextView = (TextView) itemView.findViewById(R.id.eventGenreTextView) ;
            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;
        }
    }
}