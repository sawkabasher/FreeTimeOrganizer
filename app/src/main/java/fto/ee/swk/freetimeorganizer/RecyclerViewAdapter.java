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
    List<DataObject> dataObjects;
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RecyclerViewAdapter(List<DataObject> getDataObject, Context context){
        super();
        this.dataObjects = getDataObject;
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
        DataObject dataObjectOBJ =  dataObjects.get(position);
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();
        imageLoader.get(dataObjectOBJ.getEventImageURL(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.drawable.logo, //Before loading server image the default showing image.
                        R.drawable.logo  //Error image if requested image does not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataObjectOBJ.getEventImageURL(), imageLoader);
        Viewholder.EventNameTextView.setText(dataObjectOBJ.getEventName());
        Viewholder.EventDateTextView.setText(dataObjectOBJ.getEventDate().replace("-","."));
        if (!dataObjectOBJ.getEventDate().equals(dataObjectOBJ.getEventEndDate())){
            Viewholder.EventDateTextView.append(" - " + dataObjectOBJ.getEventEndDate().replace("-","."));
        }

        Viewholder.EventTimeTextView.setText("Time: " + dataObjectOBJ.getEventTime());
        if(dataObjectOBJ.getEventPrice().equals("")){
            Viewholder.EventPriceTextView.setText("Price: FREE / unknown" );
        }else {
            Viewholder.EventPriceTextView.setText("Price: " + dataObjectOBJ.getEventPrice() + " €");
        }

        Viewholder.EventGenreTextView.setText("Genre: " + dataObjectOBJ.getEventGenre());

        if(!dataObjectOBJ.getEventCity().equals("null") && !dataObjectOBJ.getEventCity().equals("Estonia  ")){
            Viewholder.EventCityTextView.setText(dataObjectOBJ.getEventCity() + ", ");
            Viewholder.EventCityTextView.append(dataObjectOBJ.getEventCountry());
        } else {
            Viewholder.EventCityTextView.setText(dataObjectOBJ.getEventCountry());
        }


    }

    @Override
    public int getItemCount() {
        return dataObjects.size();
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