package com.example.foodorder;

import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Model.CanteenOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CanteenNewOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_new_orders);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CanteenOrders> options=
                new FirebaseRecyclerOptions.Builder<CanteenOrders>()
                        .setQuery(ordersRef, CanteenOrders.class)
                        .build();
        FirebaseRecyclerAdapter<CanteenOrders, CanteenOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<CanteenOrders, CanteenOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CanteenOrdersViewHolder holder, final int position, @NonNull final CanteenOrders model) {

                        holder.Name.setText("Name: "+model.getName());
                        holder.Email.setText("Email: "+model.getEmail());
                        holder.TotalPrice.setText("Total Ammount = Php."+model.getTotalAmount());
                        holder.DateTime.setText("Order at: "+model.getDate()+" "+ model.getTime());
                        holder.Message.setText("Shipping Address: "+model.getMessage());
                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();
                                Intent intent = new Intent(CanteenNewOrdersActivity.this,CanteenUserProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                CharSequence options[] =new CharSequence[]{
                                        "Yes",
                                        "No"

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(CanteenNewOrdersActivity.this);
                                builder.setTitle("Have you shipped this order products?");
                                builder.setItems(options, (dialogInterface, i) -> {
                                    if (i==0){
                                        String uID = getRef(position).getKey();
                                        RemoverOrder(uID);

                                    }
                                    else {
                                        finish();
                                    }

                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CanteenOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return new CanteenOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }



    public static class CanteenOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView Name, Email,TotalPrice,DateTime,Message;
        public Button showOrdersBtn;
        public CanteenOrdersViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.order_user_name);
            Email = itemView.findViewById(R.id.order_email);
            TotalPrice = itemView.findViewById(R.id.order_total_price);
            DateTime = itemView.findViewById(R.id.order_date_time);
            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
        }
    }
    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }
}
