package com.example.task3.presentation.home

import android.content.Context
import android.widget.ArrayAdapter
import com.example.task3.common.Languages
import com.example.task3.databinding.BottomSheetLangBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialogLang(
    context: Context,
    onItemClickListener: (Languages) -> Unit
) : BottomSheetDialog(context) {

    init {
        val list = Languages.values()
        val binding = BottomSheetLangBinding.inflate(layoutInflater)

        binding.root.apply {
            adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, list)

            setOnItemClickListener { _, _, position, _ ->
                onItemClickListener(list[position])
                dismiss()
            }
        }

        setContentView(binding.root)
    }
}