/*******************************************************************************
 * * Copyright 2012 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.kundera;

/**
 * Contains all constants properties supported in persistence.xml
 * @author amresh.singh
 *
 */
public interface PersistenceProperties
{

    public static final String KUNDERA_NODES = "kundera.nodes";
    public static final String KUNDERA_PORT = "kundera.port";
    public static final String KUNDERA_KEYSPACE = "kundera.keyspace";
    public static final String KUNDERA_DIALECT = "kundera.dialect";
    public static final String KUNDERA_CLIENT = "kundera.client";
    public static final String KUNDERA_CACHE_PROVIDER_CLASS = "kundera.cache.provider.class";
    public static final String KUNDERA_CACHE_CONFIG_RESOURCE = "kundera.cache.config.resource";
    public static final String KUNDERA_POOL_SIZE = "kundera.pool.size";
    public static final String KUNDERA_FETCH_MAX_DEPTH = "kundera.fetch.max.depth";     
    
}