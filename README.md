# Retrofit-HTTP-Requests

### Step 1: Add Dependencies

First, ensure that you have the necessary dependencies in your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
}
```

### Step 2: Create Data Models

Create the data models that correspond to the JSON structure you'll receive from the API.

```kotlin
data class Employee(
    val name: String,
    val profile: String
)

data class EmployeeResponse(
    val message: String,
    val employees: List<Employee>
)
```

### Step 3: Define the API Service

Define the API service interface using Retrofit.

```kotlin
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v1/1a44a28a-7c86-4738-8a03-1eafeffe38c8")
    fun getEmployees(): Call<EmployeeResponse>
}
```

### Step 4: Set Up Retrofit Instance

Create a singleton object for Retrofit to manage network calls.

```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://mocki.io/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
```

### Step 5: Make the API Call

You can now make the API call from your activity or fragment. Below is an example in an activity:

```kotlin
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
                    // Process the employees data (e.g., display in a RecyclerView)
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
```

### Step 6: Update Your Layout

Make sure to have a layout for your activity (e.g., `activity_main.xml`), even if you are just testing the API. You can add a simple TextView to display results or use Logcat for debugging.

### Example of `activity_main.xml`

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Employee List" />

</LinearLayout>
```

### Summary

In this implementation:

1. We defined data classes to map the JSON structure.
2. We created an API service interface using Retrofit.
3. We set up a Retrofit instance to handle network requests.
4. We made an API call in the `MainActivity` and handled the response accordingly.

Here’s a suggested folder structure for your Android project implementing Retrofit for a simple API GET request, following best practices. This structure keeps your project organized and makes it easier to maintain.

### Suggested Folder Structure

```
YourProjectName/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── developerdaya/
│   │   │   │           ├── data/
│   │   │   │           │   ├── model/
│   │   │   │           │   │   ├── Employee.kt
│   │   │   │           │   │   └── EmployeeResponse.kt
│   │   │   │           │   └── api/
│   │   │   │           │       └── ApiService.kt
│   │   │   │           ├── network/
│   │   │   │           │   └── RetrofitClient.kt
│   │   │   │           ├── ui/
│   │   │   │           │   ├── MainActivity.kt
│   │   │   │           │   └── adapter/
│   │   │   │           │       └── EmployeeAdapter.kt // Optional, if you're using RecyclerView
│   │   │   │           └── utils/
│   │   │   │               └── NetworkUtils.kt // Optional, for network-related utilities
│   │   │   │
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       │   └── activity_main.xml
│   │   │       └── values/
│   │   │           └── strings.xml
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   └── build.gradle
│
├── build.gradle
└── settings.gradle
```

### Explanation of the Structure

- **data/**: Contains classes related to data handling.
  - **model/**: Contains data model classes (`Employee.kt`, `EmployeeResponse.kt`) that map the JSON response.
  - **api/**: Contains API service interface (`ApiService.kt`) for defining the Retrofit API endpoints.

- **network/**: Contains classes related to networking.
  - **RetrofitClient.kt**: Singleton object to configure and create a Retrofit instance.

- **ui/**: Contains UI-related classes.
  - **MainActivity.kt**: Main Activity where the API call is made and results are displayed.
  - **adapter/**: Optional folder for RecyclerView adapters, such as `EmployeeAdapter.kt`, if you implement a list to display employees.

- **utils/**: Optional folder for utility classes.
  - **NetworkUtils.kt**: Contains any network-related utility methods (e.g., checking network connectivity).

- **res/**: Contains resource files for the app.
  - **layout/**: Contains XML layout files (like `activity_main.xml`).
  - **values/**: Contains other resource files, such as strings defined in `strings.xml`.

### Gradle Files

- **build.gradle**: Contains dependencies for the app.
- **settings.gradle**: Contains project-level settings.

