package com.sliide.usermanagement.controller

import ExceptionHandler
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sliide.usermanagement.R
import com.sliide.usermanagement.adapter.UsersListAdapter
import com.sliide.usermanagement.databinding.ActivityMainBinding
import com.sliide.usermanagement.factory.MainActivityFactory
import com.sliide.usermanagement.models.UsersItem
import com.sliide.usermanagement.states.UsersListState
import com.sliide.usermanagement.viewmodel.UsersListViewModel


class MainActivity : AppCompatActivity(), UsersListAdapter.UsersItemCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            lifecycleOwner = this@MainActivity
            binding = this
        }
        init()
    }

    private fun init() {
        setupToolbar()
        setViewModel()
        observeData()
        updateViewModel()
    }

    private fun setupToolbar() {
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
        }
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitle("Users")

        binding.fbAdd.setOnClickListener{
            addNewUserDialog()
        }
    }

    private fun setViewModel() {
        val mainActivityFactory = MainActivityFactory()
        viewModel =
            ViewModelProvider(this, mainActivityFactory).get(UsersListViewModel::class.java)
        binding.usersListViewModel = viewModel
    }

    private fun observeData() {
        viewModel.getUsersListState().observe(this, Observer {
            when (it) {
                is UsersListState.ShowLoader -> showProgress()
                is UsersListState.ShowToast -> showToast(it.message)
                is UsersListState.ShowError -> showError(it.throwable)
                is UsersListState.userAdded -> {
                    hideProgress()
                    showUserAddedDialog()
                }
                is UsersListState.userDeleted -> {
                    hideProgress()
                    showUserRemovedDialog()
                }
                is UsersListState.FillUsers -> {
                    hideProgress()
                    binding.rvUsers.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.rvUsers.adapter =
                        UsersListAdapter(it.users, this@MainActivity,this)
                }
            }
        })
    }

    private fun updateViewModel() {
        viewModel.getUsers()
    }

    private fun showProgress() {
        binding.lyLoading.visibility = View.VISIBLE
        binding.lyLoading.setOnClickListener{}
    }

    private fun hideProgress() {
        binding.lyLoading.visibility = View.GONE
    }

    private fun showError(throwable: Throwable) {
        hideProgress()
        ExceptionHandler.handle(throwable, this)
    }

    private fun showToast(message: String) {
        hideProgress()
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onItemLongClick(userItem: UsersItem) {

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.dialog_remove_alert)

        val lyNo = bottomSheetDialog.findViewById<RelativeLayout>(R.id.lyNo)
        val lyRemove = bottomSheetDialog.findViewById<RelativeLayout>(R.id.lyRemove)

        lyNo!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        lyRemove!!.setOnClickListener {
            bottomSheetDialog.dismiss()
            viewModel.deleteUser(""+userItem.id)
        }
        bottomSheetDialog.show()
    }

    private fun addNewUserDialog() {
        var gender : String?=""

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.dialog_add_user)

        val etName = bottomSheetDialog.findViewById<EditText>(R.id.etuserName)
        val etEmail = bottomSheetDialog.findViewById<EditText>(R.id.etEmail)
        val rdrGrpGender = bottomSheetDialog.findViewById<RadioGroup>(R.id.rdrGrpGender)
        val btCancel = bottomSheetDialog.findViewById<Button>(R.id.btCancel)
        val btOK = bottomSheetDialog.findViewById<Button>(R.id.btOK)

        rdrGrpGender!!.setOnCheckedChangeListener { radioGroup, i ->
            val radio:RadioButton = radioGroup.findViewById(i)
            Log.e("selectedtext-->",radio.text.toString())
            gender = radio.text.toString()
        }

        btCancel!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        btOK!!.setOnClickListener {
            if(etName!!.text.toString().isEmpty()){
                etName.error="Enter valid name"
                etName.requestFocus()
            }else if(etEmail!!.text.toString().isValidEmail()==false){
                etEmail.error="Enter valid email"
                etEmail.requestFocus()
            }else if(gender.isNullOrEmpty()){
                Toast.makeText(this,"Select valid gender",Toast.LENGTH_LONG).show()
            }else{
                bottomSheetDialog.dismiss()
                viewModel.addUser(etName.text.toString(),etEmail.text.toString(), gender!!)
            }
        }
        bottomSheetDialog.show()
    }

    private fun showUserRemovedDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.dialog_user_removed)

        val btOK = bottomSheetDialog.findViewById<Button>(R.id.btOK)

        bottomSheetDialog.setOnDismissListener {
            viewModel.getUsers()
        }
        btOK!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun showUserAddedDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.dialog_add_user_success)

        val btOK = bottomSheetDialog.findViewById<Button>(R.id.btOK)

        bottomSheetDialog.setOnDismissListener {
            viewModel.getUsers()
        }
        btOK!!.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
    fun String.isValidEmail() =
        isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}