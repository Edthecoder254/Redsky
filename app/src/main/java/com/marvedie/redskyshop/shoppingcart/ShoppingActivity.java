package com.marvedie.redskyshop.shoppingcart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.marvedie.redskyshop.R;
import com.marvedie.redskyshop.shoppingcart.adapters.ShopRecyclerViewAdapter;
import com.marvedie.redskyshop.shoppingcart.entities.ProductObject;
import com.marvedie.redskyshop.shoppingcart.helpers.SpacesItemDecoration;
import com.marvedie.redskyshop.take2.Model;
import com.marvedie.redskyshop.take2.PostDetailActivity;
import com.marvedie.redskyshop.take2.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    private static final String TAG = ShoppingActivity.class.getSimpleName();

    private RecyclerView shoppingRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        shoppingRecyclerView = (RecyclerView)findViewById(R.id.product_list);
        GridLayoutManager mGrid = new GridLayoutManager(ShoppingActivity.this, 2);
        shoppingRecyclerView.setLayoutManager(mGrid);
        shoppingRecyclerView.setHasFixedSize(true);
        shoppingRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 12, false));

        ShopRecyclerViewAdapter shopAdapter = new ShopRecyclerViewAdapter(ShoppingActivity.this, getAllProductsOnSale());
        shoppingRecyclerView.setAdapter(shopAdapter);
    }


    private List<ProductObject> getAllProductsOnSale(){

        List<ProductObject> products = new ArrayList<ProductObject>();
        products.add(new ProductObject(1, "Sleek Black Top", R.drawable.productonesmall, "Beautiful sleek black top for casual outfit and evening walk", 20, 38, "Black"));

        products.add(new ProductObject(1, "VansOldschool Black", R.drawable.vansbape, "Get these met black Limited Edition Oldskul Vans", 1700, 38, "Black"));
        products.add(new ProductObject(2, "Timberland", R.drawable.timbered, "Red Timberland", 3900, 38-42, "Red"));
        products.add(new ProductObject(3, "Balenciega", R.drawable.balenciagabw, "Beautiful sleek Balenciega", 2200, 36-42, "Black White"));
        products.add(new ProductObject(4, "Nike 13", R.drawable.nikeair_background, "Nike 13", 2500, 38, "Spotted Military Grey"));
        products.add(new ProductObject(5, "VansRedWhite", R.drawable.vansredwhite, "Vans", 2000, 36-44, "Red"));
        products.add(new ProductObject(6, "OffWhite", R.drawable.vulcanized, "Off-white Chuck Taylor Converse All Star", 6000, 36-44, "Transparent"));
        return products;



    }
}
