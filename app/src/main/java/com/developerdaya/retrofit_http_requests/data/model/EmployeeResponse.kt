package com.developerdaya.retrofit_http_requests.data.model

data class Employee(
    val name: String,
    val profile: String
)

data class EmployeeResponse(
    val message: String,
    val employees: List<Employee>
)
