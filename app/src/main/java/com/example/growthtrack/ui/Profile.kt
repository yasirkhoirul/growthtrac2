package com.example.growthtrack.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.growthtrack.R
import com.example.growthtrack.api.ApiConfig
import com.example.growthtrack.api.Reset
import com.example.growthtrack.api.ResetRespon
import com.example.growthtrack.api.UsersResponse
import com.example.growthtrack.databinding.FragmentHomeBinding
import com.example.growthtrack.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : Fragment(), View.OnClickListener {
    private lateinit var email:String
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUsers()
        binding.reset.setOnClickListener(this)


    }
    private fun getUsers() {
        val client = ApiConfig.getApiService().getuser("Tk5JFFL6JhhYnpkYCKIPQs6mOhJ3")
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                Log.d("usernya", "masuk")
                val responseData = response.body()
                Log.d("usernya","${responseData?.data}")
                if (responseData?.data ==null){
                    binding.textView2.text = "username"
                    binding.name.text = "username"
                    binding.email.text = "email"

                }else{
                    binding.textView2.text = responseData!!.data!!.name
                    binding.name.text = responseData.data!!.name
                    email = responseData.data.email.toString()
                    binding.email.text = email

                }

            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.d("usernya","${t}")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {

        val client = ApiConfig.getApiService().sendEmail("yasirhuda@gmail.com")
        client.enqueue(object : Callback<ResetRespon> {
            override fun onResponse(call: Call<ResetRespon>, response: Response<ResetRespon>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.d("reset", "Response body: ${responseBody?.msg}")
                    Toast.makeText(requireContext(),responseBody?.msg, Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("reset", "Failed to send email: ${response.message()}")
                    Toast.makeText(requireContext(),responseBody?.msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResetRespon>, t: Throwable) {
                Log.e("reset", "Error: ${t.message}")
            }
        })

    }


}