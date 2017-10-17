package com.konradkluz.websourceviewer.model.entities;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.HttpException;

/**
 * Response holder provided to the UI
 *
 * @param <T>
 */
public class Response<T> {

    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable error;

    private Response(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(Status.SUCCESS, data, null);
    }

    public static <T> Response<T> error(Throwable error) {
        if (error instanceof HttpException) {
            return new Response<>(Status.ERROR_SERVER, null, error);
        } else if (error instanceof IOException) {
            return new Response<>(Status.ERROR_NETWORK, null, error);
        }else {
            return new Response<>(Status.ERROR_OTHER, null, error);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response<?> response = (Response<?>) o;

        if (status != response.status) return false;
        if (data != null ? !data.equals(response.data) : response.data != null) return false;
        return error != null ? error.equals(response.error) : response.error == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }
}
