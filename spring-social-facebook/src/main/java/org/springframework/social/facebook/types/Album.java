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
package org.springframework.social.facebook.types;

import java.util.Date;

public class Album {

	private String id;

	private Reference from;

	private String name;

	private String description;

	private String location;

	private String link;

	private Privacy privacy;

	private int count;

	private Type type;

	private Date createdTime;

	private Date updatedTime;

	private Album(String id, Reference from, String name, Type type, String link, int count, Privacy privacy, Date createdTime) {
		this.id = id;
		this.from = from;
		this.name = name;
		this.link = link;
		this.privacy = privacy;
		this.count = count;
		this.type = type;
		this.createdTime = createdTime;
	}
	
	public String getId() {
		return id;
	}

	public Reference getFrom() {
		return from;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

	public String getLink() {
		return link;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public int getCount() {
		return count;
	}

	public Type getType() {
		return type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	// TODO: Determine if this is the complete set of possible values
	public static enum Type { NORMAL, MOBILE, PROFILE }
	
	// TODO Determine if this is the complete set of possible values. 
	//      Also decide if this needs to be extracted for use elsewhere
	public static enum Privacy { EVERYONE, FRIENDS_OF_FRIENDS, FRIENDS, CUSTOM } 

	public static class Builder {
		private String id;

		private Reference from;

		private String name;

		private String description;

		private String location;

		private String link;

		private Privacy privacy;

		private int count;

		private Type type;

		private Date createdTime;

		private Date updatedTime;
		
		public Builder(String id, Reference from, String name, Type type, String link, int count, Privacy privacy, Date createdTime) {
			this.id = id;
			this.from = from;
			this.name = name;
			this.link = link;
			this.privacy = privacy;
			this.count = count;
			this.type = type;
			this.createdTime = createdTime;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder updatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
			return this;
		}

		public Album build() {
			Album album = new Album(id, from, name, type, link, count, privacy, createdTime);
			album.description = description;
			album.location = location;
			album.updatedTime = updatedTime;
			return album;
		}
	}
}
