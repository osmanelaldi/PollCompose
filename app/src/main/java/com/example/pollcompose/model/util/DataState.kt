package com.example.pollcompose.domain.model.util

data class DataState<T>(
    val error: String? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
) {

    companion object {

        fun <T> error(
            message: String
        ): DataState<T> {
            return DataState(
                error = message,
                data = null,
            )
        }

        fun <T> data(
            message: String? = null,
            data: T? = null,
        ): DataState<T> {
            return DataState(
                error = message,
                data = data,
            )
        }

        fun <T>loading() = DataState<T>(isLoading = true)
    }
}