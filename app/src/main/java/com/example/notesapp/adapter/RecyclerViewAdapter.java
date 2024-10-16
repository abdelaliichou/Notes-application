package com.example.notesapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Model.Note_model;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.Notes_ViewModel;
import com.example.view.Update_Note_Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Note_model> Note_list;
    private final List<Note_model> Search_list;
    private final Context context;
    Timer timer ;
    BottomSheetDialog bottomSheetDialog;



    public RecyclerViewAdapter(List<Note_model> contactList, Context context) {
        this.Note_list = contactList;
        this.context = context;
        Search_list = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(Note_list.get(position).getNoteTitle());
        holder.subtitle.setText(Note_list.get(position).getNoteSubTitle());
        holder.date.setText(Note_list.get(position).getNoteDate());
        if (Integer.valueOf(Note_list.get(position).getNotePriority()) == 1 ){
            holder.card.setCardBackgroundColor(context.getColor(R.color.green));
        } else if(Integer.valueOf(Note_list.get(position).getNotePriority()) == 2 ) {
            holder.card.setCardBackgroundColor(context.getColor(R.color.yellow));
        } else {
            holder.card.setCardBackgroundColor(context.getColor(R.color.liteRed));
        }
        holder.parentCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ButtomSheetWork(view,position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(Note_list.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title , subtitle , date;
        public MaterialCardView card , parentCard ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.SubTitle);
            card = itemView.findViewById(R.id.priority);
            date = itemView.findViewById(R.id.date);
            parentCard = itemView.findViewById(R.id.card);

            parentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Update_Note_Activity.class);
                    intent.putExtra("title", Note_list.get(getAdapterPosition()).getNoteTitle());
                    intent.putExtra("subtitle", Note_list.get(getAdapterPosition()).getNoteSubTitle());
                    intent.putExtra("priority", Note_list.get(getAdapterPosition()).getNotePriority());
                    intent.putExtra("body", Note_list.get(getAdapterPosition()).getNoteBody());
                    intent.putExtra("id",String.valueOf(Note_list.get(getAdapterPosition()).getId()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

    }

    public void ButtomSheetWork(View view,int position) {

        bottomSheetDialog = new BottomSheetDialog(view.getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        RelativeLayout yes = bottomSheetDialog.findViewById(R.id.yes);
        RelativeLayout no = bottomSheetDialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes_ViewModel.delete_Note(Note_list.get(position));
                bottomSheetDialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

    }

    public void Search_note(final String SearchedNote){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (SearchedNote.trim().isEmpty()){ // means that we didn't search for anything yet so all the element will be shown
                    // Search_list will have all the items(the initialisation in the constructor)
                    // Note_list is the list that will changes the elements of the recycler view
                    Note_list = Search_list ;
                } else {
                    ArrayList<Note_model> tmplist = new ArrayList<>();
                    for(Note_model note : Search_list){
                        if (note.getNoteTitle().toLowerCase().contains(SearchedNote.toLowerCase())
                           ||note.getNoteBody().toLowerCase().contains(SearchedNote.toLowerCase())
                           || note.getNoteSubTitle().toLowerCase().contains(SearchedNote.toLowerCase())){
                               tmplist.add(note);
                        }
                    }
                    Note_list = tmplist ;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },500); // just for more realistic experience
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
