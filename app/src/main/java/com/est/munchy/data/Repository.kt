/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.est.munchy.data

import com.est.munchy.data.database.LocalDataSource
import com.est.munchy.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * [Repository] serves as the central data management hub for the application.
 *
 * This class abstracts the sources of data, allowing the rest of the application to interact with
 * a single, unified interface for retrieving and managing data. It uses both a [RemoteDataSource]
 * and a [LocalDataSource] to handle network and local database operations respectively. This
 * implementation facilitates the implementation of various data access strategies, like caching and
 * providing offline support.
 *
 * @property remote An instance of [RemoteDataSource] for network-related data operations.
 * @property local An instance of [LocalDataSource] for local database operations.
 *
 * @constructor Creates a [Repository] instance with the provided data sources.
 *
 * @param remoteDataSource The [RemoteDataSource] to use for remote data access.
 * @param localDataSource The [LocalDataSource] to use for local data access.
 */
@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    /**
     * Provides access to remote data operations.
     *
     * This property exposes the [RemoteDataSource] instance, which is responsible for fetching
     * data from the network.
     */
    val remote: RemoteDataSource = remoteDataSource

    /**
     * Provides access to local data operations.
     *
     * This property exposes the [LocalDataSource] instance, which is responsible for interacting
     * with the local database.
     */
    val local: LocalDataSource = localDataSource
}