package com.example.ultimatetictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private Activity activity;
    private ArrayList<Player> players;
    private static LayoutInflater inflater;
    private DatabaseHelper dbHelper;
    public Resources res;
    Player tempPlayer;
    int i;
    public CustomAdapter(Activity a, Resources resLocal) {
        activity = a;
        dbHelper = MainActivity.dbHelper;
        this.players = dbHelper.getAllPlayers();
        res = resLocal;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * @return size of Passed Arraylist Size
     */
    public int getCount() {
        return players.size();
    }

    /**
     * @param position
     * @return player in the passed position
     */
    public Object getItem(int position) {
        return this.players.get(position);
    }

    /**
     * @param position
     * @return id of item in the passed position
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * creates  a holder Class to contain inflated xml file elements
     */
    public static class ViewHolder {

        public TextView tvName;
        public TextView tvPoints;
        public ImageView ibDelete;

    }

    /**
     * @return view of each row
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.my_row, null);

            /****** View Holder Object to contain ListView_row.xml file elements ******/

            holder = new ViewHolder();
            holder.tvName = (TextView) vi.findViewById(R.id.playerName);
            holder.tvPoints = (TextView) vi.findViewById(R.id.playerScore);
            holder.ibDelete = (ImageView) vi.findViewById(R.id.btnDelete);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (players.size() <= 0) {
            holder.tvName.setText("No Data");
        } else {
            tempPlayer = players.get(position);

            /************  Set Player values in Holder elements ***********/
            holder.tvName.setText(tempPlayer.getName());
            String points = "" + tempPlayer.getScore();
            holder.tvPoints.setText(points);


            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder
                            .setIcon(R.drawable.deleteicon)
                            .setTitle("Delete Line")
                            .setMessage("Are you sure you Remove this Line ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dbHelper.remove(players.get(position).getName());
                                    players.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }



            });

        }
        return vi;
    }
}

