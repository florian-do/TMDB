package com.dof.myapplication.ui.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

class MainViewModel : ViewModel() {
     var amount = ObservableField<String>()
}
