package com.example.admin.mealplanner2new.Views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.admin.mealplanner2new.Fragments.FirstPhotoUploadFragment
import com.example.admin.mealplanner2new.R
import kotlinx.android.synthetic.main.activity_final_photo_upload.*

class FinalImageUploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_final_photo_upload)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { finish() }

        val firstPhotoUploadFragment = FirstPhotoUploadFragment()
        val bundle = Bundle()
        bundle.putString("from", "100")
        firstPhotoUploadFragment.arguments = bundle

        fragmentManager.beginTransaction()
                .add(R.id.container_final_photo_upload, firstPhotoUploadFragment)
                .commit()

    }


}