package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.sandwich_data_not_avail, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Sandwich sammy) {

        // Initialize and find the Views
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView descTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView akaTextView = findViewById(R.id.also_known_tv);

        // Get the PlaceOfOrigin
        String originText = sammy.getPlaceOfOrigin();
        // if PlaceOfOrigin is Empty, set this text
        if (originText.isEmpty()) {
            originTextView.setText(R.string.unknown);
        } else { // otherwise set the actual value
            originTextView.setText(originText);
        }

        // Get the Description
        String descText = sammy.getDescription();
        // if Description is Empty, set this text
        if (descText.isEmpty()) {
            descTextView.setText(R.string.not_avail);
        } else { // otherwise set the actual value
            descTextView.setText(descText);
        }

        // Get the Ingredients
        List<String> ingredText = sammy.getIngredients();
        // if Ingredients is Empty, set this text
        if (ingredText.isEmpty()) {
            ingredientsTextView.setText(R.string.not_avail);
        } else { // otherwise set the actual value
            ingredientsTextView.setText(TextUtils.join(", ", sammy.getIngredients()));
        }

        // Get & Handle the AKA
        TextView alsoKnownAsLabel = findViewById(R.id.aka_tv);
        List<String> akaText = sammy.getAlsoKnownAs();
        // if AKA is Empty, remove Also Known As Label & TextView
        if (akaText.isEmpty()) {
            alsoKnownAsLabel.setVisibility(View.GONE);
            akaTextView.setVisibility(View.GONE);
        } else { // otherwise set the actual value
            akaTextView.setText(TextUtils.join(", ", akaText));
        }

    }
}


