package com.example.foodorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodorder.Model.Canteen;
import com.example.foodorder.ViewHolder.CanteenViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class StallsActivity extends AppCompatActivity  {
    private DatabaseReference CanteenRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stalls);
        CanteenRef = FirebaseDatabase.getInstance().getReference().child("Canteen");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(StallsActivity.this,CartActivity.class);
            startActivity(intent);
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Canteen> options =
                new FirebaseRecyclerOptions.Builder<Canteen>()
                        .setQuery(CanteenRef, Canteen.class)
                        .build();

        FirebaseRecyclerAdapter<Canteen, CanteenViewHolder> adapter =
                new FirebaseRecyclerAdapter<Canteen, CanteenViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CanteenViewHolder holder, int position, @NonNull final Canteen model)
                    {
                        holder.txtCanteenName.setText(model.getCanteen());
                        holder.itemView.setOnClickListener(view -> {
                            Intent intent =new Intent(StallsActivity.this,HomeActivity.class);
                            intent.putExtra("canteen",model.getCanteen());
                            startActivity(intent);
                        });
                    }

                    @NonNull
                    @Override
                    public CanteenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stalls_layout, parent, false);
                        CanteenViewHolder holder = new CanteenViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      if (id == R.id.nav_logout) {
          return true;
      }

        return super.onOptionsItemSelected(item);
    }
}