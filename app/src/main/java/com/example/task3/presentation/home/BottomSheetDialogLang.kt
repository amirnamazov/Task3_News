package com.example.task3.presentation.home

import android.content.Context
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import com.example.task3.common.Languages
import com.example.task3.databinding.BottomSheetLangBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialogLang(
    context: Context,
    sharedPref: SharedPreferences,
    onItemClickListener: (String) -> Unit
) : BottomSheetDialog(context) {

    init {
        val list = Languages.values()
        val binding = BottomSheetLangBinding.inflate(layoutInflater)
        binding.root.apply {
            adapter = ArrayAdapter(context,
                android.R.layout.simple_list_item_1, list)
            setOnItemClickListener { _, _, position, _ ->
                sharedPref.edit().putString("LANGUAGE", list[position].field).apply()
                onItemClickListener(list[position].name)
                dismiss()
            }
        }

        setContentView(binding.root)
    }
}