package com.hantash.dependancy_injection.screens.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hantash.dependancy_injection.R
import com.hantash.dependancy_injection.screens.BaseActivity
import com.hantash.dependancy_injection.screens.common.navigator.ScreensNavigator
import com.hantash.dependancy_injection.screens.common.toolbar.MyToolbar
import javax.inject.Inject

class ViewModelActivity: BaseActivity() {
    @Inject lateinit var screensNavigator: ScreensNavigator
    @Inject lateinit var myViewModelFactory: MyViewModelFactory
    lateinit var viewModel: MyViewModel
    lateinit var viewModel2: MyViewModel2

    private lateinit var toolbar: MyToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_view_model)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            screensNavigator.toBack()
        }

        viewModel = ViewModelProvider(this, myViewModelFactory).get(MyViewModel::class.java)
        viewModel2 = ViewModelProvider(this, myViewModelFactory).get(MyViewModel2::class.java)

        viewModel.questionsLiveData.observe(this, Observer {
            questions -> Toast.makeText(this, "Fetched ${questions.size} questions", Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewModelActivity::class.java)
            context.startActivity(intent)
        }
    }
}