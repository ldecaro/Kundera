/*******************************************************************************
 * * Copyright 2011 Impetus Infotech.
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
package com.impetus.client.schemamanager.entites;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The Class CassandraEntityPersonBiMTo1.
 */
@Entity
@Table(name = "CassandraEntityPersonBiMTo1", schema = "KunderaCoreExmples@cassandra")
public class CassandraEntityPersonBiMTo1
{

    /** The person id. */
    @Id
    @Column(name = "PERSON_ID")
    private String personId;

    /** The person name. */
    @Column(name = "PERSON_NAME")
    private String personName;

    /** The personal data. */
    @Embedded
    private CassandraPersonalData personalData;

    /** The address. */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    @LazyCollection(LazyCollectionOption.FALSE)
    private CassandraEntityAddressBiMTo1 address;

    /**
     * Gets the person id.
     * 
     * @return the person id
     */
    public String getPersonId()
    {
        return personId;
    }

    /**
     * Gets the person name.
     * 
     * @return the person name
     */
    public String getPersonName()
    {
        return personName;
    }

    /**
     * Sets the person name.
     * 
     * @param personName
     *            the new person name
     */
    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    /**
     * Sets the person id.
     * 
     * @param personId
     *            the new person id
     */
    public void setPersonId(String personId)
    {
        this.personId = personId;
    }

    /**
     * Gets the personal data.
     * 
     * @return the personalData
     */
    public CassandraPersonalData getPersonalData()
    {
        return personalData;
    }

    /**
     * Sets the personal data.
     * 
     * @param personalData
     *            the personalData to set
     */
    public void setPersonalData(CassandraPersonalData personalData)
    {
        this.personalData = personalData;
    }

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public CassandraEntityAddressBiMTo1 getAddress()
    {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address
     *            the new address
     */
    public void setAddress(CassandraEntityAddressBiMTo1 address)
    {
        this.address = address;
    }

}
