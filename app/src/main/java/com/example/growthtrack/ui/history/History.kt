package com.example.growthtrack.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.growthtrack.R
import com.example.growthtrack.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class History : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvhistory)

        // Siapkan data dan atur adapter


        getHistory()
    }
    private fun loding(b:Boolean) {
        val loding : ProgressBar = view?.findViewById(R.id.progressBar)!!

        if (b){
            loding.visibility = View.VISIBLE
        }else{
            loding.visibility = View.INVISIBLE
        }
    }
    private fun getHistory() {
        loding(true)
        val client = ApiConfig.getApiService().getHistory("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJUazVKRkZMNkpoaFlucGtZQ0tJUFFzNm1PaEozIiwiZW1haWwiOiJ5YXNpcmh1ZGFAZ21haWwuY29tIiwiaWF0IjoxNzE4Nzg0NDM1LCJleHAiOjE3MTg3ODgwMzV9.JcC9sLqXzpDD_i_UiR5yvRPxT10iEko9ZMiZzF-HnI0")
        client.enqueue(object : Callback<com.example.growthtrack.api.Response>{
            override fun onResponse(
                call: Call<com.example.growthtrack.api.Response>,
                response: Response<com.example.growthtrack.api.Response>
            ) {
                loding(false)
                if (response.isSuccessful){
                    Log.d("responn", "masuk")
                    val responseData = response.body()
                    Log.d("responn", "masuk2${responseData?.data}")
                    if (responseData != null && responseData.data != null) {
                        val historyList = responseData.data
                        val rcv: RecyclerView? = view?.findViewById(R.id.rvhistory)
                        val adapter = AdapterHistory(historyList)

                        rcv?.adapter = adapter

                        // Lakukan sesuatu dengan historyList, misalnya tampilkan dalam RecyclerView
                    } else {
                        // Tangani kasus respons tidak berhasil atau data kosong
                    }

                }else{
                    Log.d("responn", "Gagal: ${response.raw()}")
                }

            }

            override fun onFailure(call: Call<com.example.growthtrack.api.Response>, t: Throwable) {
                Log.d("responn", "Gagal1: ${t}")

            }

        })

    }


}