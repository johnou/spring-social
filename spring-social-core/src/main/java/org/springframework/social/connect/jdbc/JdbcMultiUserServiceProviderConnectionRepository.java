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
package org.springframework.social.connect.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.MultiUserServiceProviderConnectionRepository;
import org.springframework.social.connect.ServiceProviderConnectionFactoryLocator;
import org.springframework.social.connect.ServiceProviderConnectionKey;
import org.springframework.social.connect.ServiceProviderConnectionRepository;

public class JdbcMultiUserServiceProviderConnectionRepository implements MultiUserServiceProviderConnectionRepository {

	private final JdbcTemplate jdbcTemplate;
	
	private final ServiceProviderConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public JdbcMultiUserServiceProviderConnectionRepository(DataSource dataSource, ServiceProviderConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	public String findLocalUserIdConnectedTo(ServiceProviderConnectionKey connectionKey) {
		try {
			return jdbcTemplate.queryForObject("select localUserId from ServiceProviderConnection where providerId = ? and providerUserId = ?", String.class, connectionKey.getProviderId(), connectionKey.getProviderUserId());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Set<String> findLocalUserIdsConnectedTo(String providerId, List<String> providerUserIds) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("providerId", providerId);
		parameters.addValue("providerUserIds", providerUserIds);
		final Set<String> localUserIds = new HashSet<String>();
		return new NamedParameterJdbcTemplate(jdbcTemplate).query("select localUserId from ServiceProviderConnection where providerId = :providerId and providerUserId in (:providerUserIds)", parameters,
			new ResultSetExtractor<Set<String>>() {
				public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					while (rs.next()) {
						localUserIds.add(rs.getString("localUserId"));
					}
					return localUserIds;
				}
			});
	}

	public ServiceProviderConnectionRepository createConnectionRepository(String localUserId) {
		return new JdbcServiceProviderConnectionRepository(localUserId, jdbcTemplate, connectionFactoryLocator, textEncryptor);
	}

}