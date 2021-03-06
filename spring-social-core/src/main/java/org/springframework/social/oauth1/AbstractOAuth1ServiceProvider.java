/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.oauth1;

/**
 * Base class for ServiceProviders that use the OAuth1 protocol.
 * OAuth1-based ServiceProvider implementations should extend and implement {@link #getServiceApi(String, String)}.
 * @author Keith Donald
 * @param <S> the service API type
 */
public abstract class AbstractOAuth1ServiceProvider<S> implements OAuth1ServiceProvider<S> {

	private final String consumerKey;
	
	private final String consumerSecret;
	
	private final OAuth1Operations oauth1Operations;

	public AbstractOAuth1ServiceProvider(String consumerKey, String consumerSecret, OAuth1Operations oauth1Operations) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.oauth1Operations = oauth1Operations;
	}

	protected final String getConsumerKey() {
		return consumerKey;
	}

	protected final String getConsumerSecret() {
		return consumerSecret;
	}

	public final OAuth1Operations getOAuthOperations() {
		return oauth1Operations;
	}
	
}