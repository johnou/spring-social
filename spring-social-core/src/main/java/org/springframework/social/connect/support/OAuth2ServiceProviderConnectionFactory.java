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
package org.springframework.social.connect.support;

import org.springframework.social.connect.ServiceProviderConnection;
import org.springframework.social.connect.ServiceProviderConnectionFactory;
import org.springframework.social.connect.ServiceProviderConnectionKey;
import org.springframework.social.connect.ServiceProviderConnectionData;
import org.springframework.social.connect.ServiceProviderUser;
import org.springframework.social.connect.spi.ServiceApiAdapter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class OAuth2ServiceProviderConnectionFactory<S> extends ServiceProviderConnectionFactory<S> {
	
	public OAuth2ServiceProviderConnectionFactory(String providerId, OAuth2ServiceProvider<S> serviceProvider, ServiceApiAdapter<S> serviceApiAdapter) {
		super(providerId, serviceProvider, serviceApiAdapter);
	}

	public OAuth2Operations getOAuthOperations() {
		return getOAuth2ServiceProvider().getOAuthOperations();
	}
	
	public ServiceProviderConnection<S> createConnection(AccessGrant accessGrant) {
		return new OAuth2ServiceProviderConnection<S>(getProviderId(), extractProviderUserId(accessGrant),
				accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getServiceApiAdapter());		
	}
	
	public ServiceProviderConnection<S> createConnection(ServiceProviderConnectionData data) {
		ServiceProviderConnectionKey key = new ServiceProviderConnectionKey(data.getProviderId(), data.getProviderUserId());
		ServiceProviderUser user = new ServiceProviderUser(data.getProviderUserId(), data.getProfileName(), data.getProfileUrl(), data.getProfilePictureUrl());
		return new OAuth2ServiceProviderConnection<S>(key, user, data.getAccessToken(), data.getRefreshToken(), data.getExpireTime(), getOAuth2ServiceProvider(), getServiceApiAdapter());
	}
	
	// subclassing hooks
	
	protected String extractProviderUserId(AccessGrant accessGrant) {
		return null;
	}

	// internal helpers
	
	private OAuth2ServiceProvider<S> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<S>) getServiceProvider();
	}
	
}