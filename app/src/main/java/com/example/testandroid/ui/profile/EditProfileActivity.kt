package com.example.testandroid.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.example.testandroid.R
import com.example.testandroid.databinding.ActivityEditProfileBinding
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_home.toolbar
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    companion object {
        const val EDIT_PROFILE_REQUEST = 1
    }
    lateinit var username: String
    lateinit var name: String
    lateinit var biography: String
    lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.editProfile = this
        initToolbar()
        setupUI()
        getUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_close_button)
        toolbar.setNavigationOnClickListener { returnResult() }
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_check_24)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_check -> {
            returnResult()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            var image: Image = ImagePicker.getFirstImageOrNull(data)
            path = image.path
            loadPicture(path)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getUser() {
        username = intent.getStringExtra("username")
        name = intent.getStringExtra("name")
        biography = intent.getStringExtra("biography")
        path = intent.getStringExtra("path")
        println(path)
        if (!path.isNullOrBlank())
            loadPicture(path)

    }

    private fun setupUI() {
        Glide.with(profile.context)
            .load(ContextCompat.getDrawable(this, R.drawable.photo))
            .circleCrop()
            .into(profile)
        change.setOnClickListener {
            ImagePicker.create(this)
                .single()
                .start();
        }
    }

    private fun loadPicture(path: String) {
        Glide.with(profile.context)
            .load(File(path))
            .circleCrop()
            .into(profile)
    }

    private fun returnResult() {
        val data = Intent().apply {
            putExtra("username", username)
            putExtra("name", name)
            putExtra("biography", biography)
            putExtra("path", path)
        }
        setResult(RESULT_OK, data)
        finish()
    }

}