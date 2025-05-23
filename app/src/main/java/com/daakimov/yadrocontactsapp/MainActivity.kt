package com.daakimov.yadrocontactsapp

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daakimov.data.ContactsServiceConnection
import com.daakimov.yadrocontactsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.CALL_PHONE
                ),
            0
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                binding.contactsRcView.adapter = viewModel.getRcAdapter()

                launch {
                    viewModel.uiState.collectLatest {
                        when(it) {
                            MainViewModel.UiState.Empty -> {}
                            MainViewModel.UiState.Loading -> showProgressBar()
                            is MainViewModel.UiState.ShowContacts -> {
                                showRc()
                            }


                        }
                    }
                }

                launch {
                    viewModel.makePhoneCall.collectLatest {
                        startActivity(it)
                    }
                }

                launch {
                    viewModel.makeToast.collectLatest {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

        binding.deleteDuplicatesButtonView.setOnClickListener {
            viewModel.deleteDuplicates()

        }

    }

    private fun showProgressBar(){
        binding.progressView.visibility = VISIBLE
        binding.contactsGroup.visibility = GONE
    }

    private fun showRc(){
        binding.progressView.visibility = GONE
        binding.contactsGroup.visibility = VISIBLE
    }




}