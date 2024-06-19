package com.example.growthtrack.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.growthtrack.R
import com.example.growthtrack.api.ApiConfig
import com.example.growthtrack.api.UsersResponse
import com.example.growthtrack.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_diagnose2)
        }
        binding.cardView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_history2)

        }
        binding.cardView4.setOnClickListener {
            Log.d("pencet","dah dipencet")
            findNavController().navigate(R.id.action_navigation_home_to_articleActivity)

        }
        binding.cardView3.setOnClickListener {
            Log.d("pencet","dah dipencet")
            findNavController().navigate(R.id.action_navigation_home_to_navigation_rumahsakit2)

        }

        getUsers()

        return root
    }

    private fun getUsers() {
        val client = ApiConfig.getApiService().getuser("Tk5JFFL6JhhYnpkYCKIPQs6mOhJ3")
        client.enqueue(object :Callback<UsersResponse>{
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                Log.d("usernya", "masuk")
                val responseData = response.body()
                Log.d("usernya","${responseData?.data}")

                if (responseData?.data ==null){
                    binding.usernamesHome.text = "username"
                }else{
                    binding.usernamesHome.text = responseData?.data!!.email
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.d("usernya","${t}")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}