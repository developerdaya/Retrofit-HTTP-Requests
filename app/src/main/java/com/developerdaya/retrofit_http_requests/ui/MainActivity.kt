package com.developerdaya.retrofit_http_requests.ui
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developerdaya.retrofit_http_requests.R
import com.developerdaya.retrofit_http_requests.data.model.EmployeeResponse
import com.developerdaya.retrofit_http_requests.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchEmployees()
    }

    private fun fetchEmployees() {
        RetrofitClient.apiService.getEmployees().enqueue(object : Callback<EmployeeResponse> {
            override fun onResponse(call: Call<EmployeeResponse>, response: Response<EmployeeResponse>) {
                if (response.isSuccessful) {
                    val employees = response.body()?.employees
                    var textView  = findViewById<TextView>(R.id.textView)
                    textView.text = employees.toString()
                    employees?.forEach { employee ->
                        println("Name: ${employee.name}, Profile: ${employee.profile}")
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmployeeResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
