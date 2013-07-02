/*
    Calimero - A library for KNX network access
    Copyright (C) 2006-2008 W. Kastner

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package at.ac.tuwien.auto.calimero.buffer.cache;

import at.ac.tuwien.auto.calimero.exception.KNXIllegalArgumentException;

/**
 * Holds a key and a value entry and is used by a {@link Cache}.
 * <p>
 * The key object is not allowed to change after creation of a cache object.<br>
 * An access count is maintained to tell the number of requests for this
 * CacheObject by a cache.<br>
 * The usage value might be used by a caching policy to store a different way of
 * access counting.<br>
 * A timestamp is stored of the most recent assignment of a value object hold by
 * this CacheObject. This timestamp is created with
 * {@link System#currentTimeMillis()}.
 * 
 * @author B. Malinowsky
 * @see Cache
 * @see System#currentTimeMillis()
 */
public class CacheObject
{
	/**
	 * The value object hold by this cache object.
	 */
	protected Object value;
	
	private final Object key;
	// hit count (increment count) of this object
	private int count;
	// timestamp of last value assignment
	private volatile long timestamp;
	// client usage indicator of this object (cache specific)
	private volatile int usage;

	/**
	 * Creates a CacheObject associated with <code>key</code> holding a
	 * <code>value</code> entry.
	 * <p>
	 * 
	 * @param key key of this {@link CacheObject}
	 * @param value value of this {@link CacheObject}
	 */
	public CacheObject(Object key, Object value)
	{
		if (key == null || value == null)
			throw new KNXIllegalArgumentException(
				"key and value must not be null");
		this.key = key;
		this.value = value;
		resetTimestamp();
	}

	/**
	 * Returns the key associated with this cache object.
	 * <p>
	 * 
	 * @return the key object
	 */
	public Object getKey()
	{
		return key;
	}

	/**
	 * Gets the value entry hold by this cache object.
	 * <p>
	 * Note that the value is guaranteed to be always a non <code>null</code>
	 * reference.
	 * 
	 * @return the value object
	 */
	public synchronized Object getValue()
	{
		return value;
	}

	/**
	 * Returns the most up to date timestamp of the moment the current value
	 * object was assigned to this cache object, or the moment this object was
	 * {@link Cache#put(CacheObject)} into a cache.
	 * <p>
	 * 
	 * @return the timestamp in milliseconds of type long
	 * @see System#currentTimeMillis()
	 */
	public final long getTimestamp()
	{
		return timestamp;
	}

	/**
	 * Sets the timestamp to time = now.
	 * <p>
	 * The timestamp is obtained by {@link System#currentTimeMillis()}.
	 */
	public final void resetTimestamp()
	{
		timestamp = System.currentTimeMillis();
	}
	
	/**
	 * Returns the access count this cache object was queried from the cache.
	 * <p>
	 * 
	 * @return the access count
	 */
	public final synchronized int getCount()
	{
		return count;
	}

	/**
	 * Increments the access count by 1.
	 * <p>
	 * It is invoked by a {@link Cache} implementation to record a client access to
	 * this object.
	 */
	public final synchronized void incCount()
	{
		++count;
	}

	/**
	 * Sets the access count to 0.
	 * <p>
	 */
	protected final synchronized void resetCount()
	{
		count = 0;
	}

	/**
	 * Returns the usage value of this cache object as defined by a particular cache.
	 * <p>
	 * 
	 * @return the usage value
	 */
	public final int getUsage()
	{
		return usage;
	}

	/**
	 * Sets a new usage value.
	 * <p>
	 * The usage value might be used by a caching policy to store a different
	 * way of access counting.<br>
	 * 
	 * @param newUsage new usage value
	 */
	protected final void setUsage(int newUsage)
	{
		usage = newUsage;
	}
}
