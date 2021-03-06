package com.impetus.kundera.tests.crossdatastore.useraddress;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.impetus.kundera.tests.cli.CassandraCli;
import com.impetus.kundera.tests.crossdatastore.useraddress.entities.HabitatBiMToM;
import com.impetus.kundera.tests.crossdatastore.useraddress.entities.PersonnelBiMToM;

public class MTMBiAssociationTest extends TwinAssociation

{

    /**
     * Inits the.
     */
    @BeforeClass
    public static void init() throws Exception
    {
        if (RUN_IN_EMBEDDED_MODE)
        {
            CassandraCli.cassandraSetUp();
            
        }
        
        if (AUTO_MANAGE_SCHEMA)
        {
            CassandraCli.initClient();
        }          
        
        List<Class> clazzz = new ArrayList<Class>(2);
        clazzz.add(PersonnelBiMToM.class);
        clazzz.add(HabitatBiMToM.class);
        init(clazzz, ALL_PUs_UNDER_TEST);
    }

    /**
     * Sets the up.
     * 
     * @throws Exception
     *             the exception
     */
    @Before
    public void setUp() throws Exception
    {
        setUpInternal();
    }

    /**
     * Test insert.
     */
    
    @Test
    public void dummyTest(){
        
    }

    @Test
    public void testCRUD()
    {
        tryOperation();
    }   

    @Override
    protected void insert()
    {
        PersonnelBiMToM person1 = new PersonnelBiMToM();
        person1.setPersonId("bimanytomany_1");
        person1.setPersonName("Amresh");        

        PersonnelBiMToM person2 = new PersonnelBiMToM();
        person2.setPersonId("bimanytomany_2");
        person2.setPersonName("Vivek");        

        HabitatBiMToM address1 = new HabitatBiMToM();
        address1.setAddressId("bimanytomany_a");
        address1.setStreet("AAAAAAAAAAAAA");

        HabitatBiMToM address2 = new HabitatBiMToM();
        address2.setAddressId("bimanytomany_b");
        address2.setStreet("BBBBBBBBBBBBBBB");

        HabitatBiMToM address3 = new HabitatBiMToM();
        address3.setAddressId("bimanytomany_c");
        address3.setStreet("CCCCCCCCCCC");

        Set<HabitatBiMToM> person1Addresses = new HashSet<HabitatBiMToM>();
        Set<HabitatBiMToM> person2Addresses = new HashSet<HabitatBiMToM>();

        person1Addresses.add(address1);
        person1Addresses.add(address2);

        person2Addresses.add(address2);
        person2Addresses.add(address3);

        person1.setAddresses(person1Addresses);
        person2.setAddresses(person2Addresses);
        
        Set<PersonnelBiMToM> persons = new HashSet<PersonnelBiMToM>();
        persons.add(person1);
        persons.add(person2);

        dao.savePersons(persons);

        col.add(person1);
        col.add(person2);
        col.add(address1);
        col.add(address2);
        col.add(address3);

    }
    
    @Override
    protected void find()
    {
        PersonnelBiMToM person1 = (PersonnelBiMToM) dao.findPerson(PersonnelBiMToM.class, "bimanytomany_1");
        Assert.assertNotNull(person1);
        Assert.assertEquals("bimanytomany_1", person1.getPersonId());
        Assert.assertEquals("Amresh", person1.getPersonName());
        
        Set<HabitatBiMToM> addresses1 = person1.getAddresses();
        Assert.assertNotNull(addresses1);
        Assert.assertFalse(addresses1.isEmpty());
        Assert.assertEquals(2, addresses1.size());
        HabitatBiMToM address11 = (HabitatBiMToM) addresses1.toArray()[0];
        Assert.assertNotNull(address11);
        HabitatBiMToM address12 = (HabitatBiMToM) addresses1.toArray()[1];
        Assert.assertNotNull(address12);

        PersonnelBiMToM person2 = (PersonnelBiMToM) dao.findPerson(PersonnelBiMToM.class, "bimanytomany_2");
        Assert.assertNotNull(person2);

        Assert.assertEquals("bimanytomany_2", person2.getPersonId());
        Assert.assertEquals("Vivek", person2.getPersonName());
        
        Set<HabitatBiMToM> addresses2 = person2.getAddresses();
        Assert.assertNotNull(addresses2);
        Assert.assertFalse(addresses2.isEmpty());
        Assert.assertEquals(2, addresses2.size());
        HabitatBiMToM address21 = (HabitatBiMToM) addresses2.toArray()[0];
        Assert.assertNotNull(address21);
        HabitatBiMToM address22 = (HabitatBiMToM) addresses2.toArray()[1];
        Assert.assertNotNull(address22);

    }

    @Override
    protected void update()
    {
    }

    @Override
    protected void remove()
    {
    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {
        tearDownInternal();        
    }

    @Override
    protected void loadDataForPERSONNEL() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException
    {

        KsDef ksDef = null;

        CfDef cfDef = new CfDef();
        cfDef.name = "PERSONNEL";
        cfDef.keyspace = "KunderaTests";
        //cfDef.column_type = "Super";

        cfDef.setComparator_type("UTF8Type");
        cfDef.setDefault_validation_class("UTF8Type");
        ColumnDef columnDef = new ColumnDef(ByteBuffer.wrap("PERSON_NAME".getBytes()), "UTF8Type");
        cfDef.addToColumn_metadata(columnDef);

        ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("ADDRESS_ID".getBytes()), "IntegerType");
        cfDef.addToColumn_metadata(columnDef1);

        // ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("PERSON_ID"
        // .getBytes()), "IntegerType");
        // cfDef.addToColumn_metadata(columnDef2);

        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(cfDef);

        try
        {
            ksDef = CassandraCli.client.describe_keyspace("KunderaTests");
            CassandraCli.client.set_keyspace("KunderaTests");

            List<CfDef> cfDefn = ksDef.getCf_defs();

            // CassandraCli.client.set_keyspace("KunderaTests");
            for (CfDef cfDef1 : cfDefn)
            {

                if (cfDef1.getName().equalsIgnoreCase("PERSONNEL"))
                {

                    CassandraCli.client.system_drop_column_family("PERSONNEL");

                }
            }
            CassandraCli.client.system_add_column_family(cfDef);

        }
        catch (NotFoundException e)
        {

            ksDef = new KsDef("KunderaTests", "org.apache.cassandra.locator.SimpleStrategy", cfDefs);
            ksDef.setReplication_factor(1);
            CassandraCli.client.system_add_keyspace(ksDef);
        }

        CassandraCli.client.set_keyspace("KunderaTests");
        
        loadDataForPersonnelAddress();

    }

    @Override
    protected void loadDataForHABITAT() throws TException, InvalidRequestException, UnavailableException,
            TimedOutException, SchemaDisagreementException
    {
        KsDef ksDef = null;
        CfDef cfDef2 = new CfDef();

        cfDef2.name = "ADDRESS";
        cfDef2.keyspace = "KunderaTests";

        ColumnDef columnDef1 = new ColumnDef(ByteBuffer.wrap("STREET".getBytes()), "UTF8Type");
        columnDef1.index_type = IndexType.KEYS;
        cfDef2.addToColumn_metadata(columnDef1);
        //
        // ColumnDef columnDef3 = new ColumnDef(ByteBuffer.wrap("ADDRESS_ID"
        // .getBytes()), "IntegerType");
        // columnDef3.index_type = IndexType.KEYS;
        // cfDef2.addToColumn_metadata(columnDef3);

        ColumnDef columnDef2 = new ColumnDef(ByteBuffer.wrap("PERSON_ID".getBytes()), "IntegerType");
        columnDef2.index_type = IndexType.KEYS;
        cfDef2.addToColumn_metadata(columnDef2);

        List<CfDef> cfDefs = new ArrayList<CfDef>();
        cfDefs.add(cfDef2);

        try
        {
            ksDef = CassandraCli.client.describe_keyspace("KunderaTests");
            CassandraCli.client.set_keyspace("KunderaTests");
            List<CfDef> cfDefss = ksDef.getCf_defs();
            // CassandraCli.client.set_keyspace("KunderaTests");
            for (CfDef cfDef : cfDefss)
            {

                if (cfDef.getName().equalsIgnoreCase("ADDRESS"))
                {

                    CassandraCli.client.system_drop_column_family("ADDRESS");

                }
            }
            CassandraCli.client.system_add_column_family(cfDef2);
        }
        catch (NotFoundException e)
        {

            ksDef = new KsDef("KunderaTests", "org.apache.cassandra.locator.SimpleStrategy", cfDefs);

            ksDef.setReplication_factor(1);
            CassandraCli.client.system_add_keyspace(ksDef);

        }
        CassandraCli.client.set_keyspace("KunderaTests");

    }
    
    static void loadDataForPersonnelAddress() 
    {
        try
        {
            KsDef ksDef = CassandraCli.client.describe_keyspace("KunderaTests");
            CfDef cfDef2 = new CfDef();
            cfDef2.name = "PERSONNEL_ADDRESS";
            cfDef2.keyspace = "KunderaTests";

            List<CfDef> cfDefss = ksDef.getCf_defs(); 
            CassandraCli.client.set_keyspace("KunderaTests");
            for (CfDef cfDef : cfDefss)
            {

                if (cfDef.getName().equalsIgnoreCase("PERSONNEL_ADDRESS"))
                {

                    CassandraCli.client.system_drop_column_family("PERSONNEL_ADDRESS");

                }
            }
            CassandraCli.client.system_add_column_family(cfDef2);
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InvalidRequestException e)
        {
            e.printStackTrace();
        }
        catch (TException e)
        {
            e.printStackTrace();
        }
        catch (SchemaDisagreementException e)
        {
            e.printStackTrace();
        }

    }

}
