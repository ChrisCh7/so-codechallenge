package com.example.so_codechallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UsersTopTagsResponse(@JsonProperty("items") val items: List<TopTag>)
