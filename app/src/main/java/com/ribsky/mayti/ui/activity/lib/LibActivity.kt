package com.ribsky.mayti.ui.activity.lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.mayti.databinding.ActivityLibBinding
import com.ribsky.mayti.model.lib.LibModel
import com.ribsky.mayti.ui.adapters.lib.RecyclerViewAdapterLib

class LibActivity : AppCompatActivity() {

    lateinit var binding: ActivityLibBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener { onBackPressed() }


        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = RecyclerViewAdapterLib(initLib())

    }

    private fun initLib(): ArrayList<LibModel> {

        val arrayList: ArrayList<LibModel> = ArrayList()
        arrayList.add(
            LibModel(
                "CardStackView",
                "Copyright 2018 yuyakaido\n\nLicensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at\n\nhttp://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
            )
        )

        arrayList.add(
            LibModel(
                "Secure-preferences",
                "Copyright (C) 2013, Daniel Abraham, Scott Alexander-Bown\n\nLicensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at\n\nhttp://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
            )
        )

        arrayList.add(
            LibModel(
                "coil",
                "Copyright 2021 Coil Contributors\n\nLicensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at\n\nhttp://www.apache.org/licenses/LICENSE-2.0\n\nUnless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."
            )
        )

        return arrayList
    }
}

