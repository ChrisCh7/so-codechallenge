package com.example.so_codechallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UsersResponse(@JsonProperty("items") val items: List<User>)
