#EasySQLite [![Build Status](https://travis-ci.org/tonilopezmr/Android-EasySQLite.svg?branch=master)](https://travis-ci.org/tonilopezmr/Android-EasySQLite) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.tonilopezmr/easysqlite/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.tonilopezmr/easysqlite)

Use easily the database SQLite on android using the DAO and transformer design patterns, I modified the library of professor [@jvprofe][11], I learned from him.

Basic methods are already implemented in the class SQLiteDelegate<T>, if you need further queries you should extend from it.

#How to use

####Import EasySQLite dependency:

Grab via maven:

```xml
<dependency>
    <groupId>com.github.tonilopezmr</groupId>
    <artifactId>easysqlite</artifactId>
    <version>2.0.0</version>
</dependency>
```

or gradle:

```gradle
compile 'com.github.tonilopezmr:easysqlite:2.0.0'
````

####1. Create the object Transformer which implements SQLiteTransformer<T>

```java
	public class SubjectTransformer implements SQLiteTransformer<SubjectEntity>{

	    public static final String ID = "id";
	    public static final String NAME = "name";

	    public static final String TABLE_NAME = "subject";
	    public static final String[] FIELDS = {ID, NAME};

	    @Override
	    public SubjectEntity transform(Cursor cursor) throws Exception {
	        int id = cursor.getInt(0);
	        String name = cursor.getString(1);
	        return new SubjectEntity(id, name);
	    }

	    @Override
	    public ContentValues transform(SubjectEntity dto) throws Exception {
	        ContentValues values = new ContentValues();
			//values.put(ID, dto.getId());   it is not necessary, autoincrement!
	        values.put(NAME, dto.getName());

	        return values;
	    }

	    @Override
	    public String getWhereClause(SubjectEntity dto) throws Exception {
	        return  ID+"="+dto.getId();
	    }

	    @Override
	    public SubjectEntity setId(SubjectEntity dto, Object id) throws Exception {
	        dto.setId((Integer.valueOf(id.toString())));
	        return dto;
	    }

	    @Override
	    public String[] getFields() throws Exception {
	        return FIELDS;
	    }

	    @Override
	    public String getTableName() throws Exception {
	        return TABLE_NAME;
	    }
	}
```
####2. Create the object DAO which extends SQLiteDelegate<T>

The SQLiteDelegate<T> has implemented the following methods:

    //Default methods implement with SQLiteDelegate
    T create(T dto)
    boolean update(T dto)
    T read(T id)
    Collection<T> readAll()
    boolean delete(T dto)
    boolean deleteAll()

```java
	public class SubjectDAO extends SQLiteDelegate<SubjectEntity> {

	    public SubjectDAO(SQLiteDatabase db) {
	        super(db, new SubjectTransformer());
	    }
		
	    //In the case you need more querys, write this for example:
	    public List<T> getSubjectsApproved() {
	    	...	    
	    }
	    
	}
```
####If you need one relation between objects (foreign key)

```java
	public class SubjectTransformer implements SQLiteTransformer<SubjectEntity>{
		
		...

		private ExamDAO examDAO;

	   	public SubjectTransformer(SQLiteDatabase db){
	   		examDAO = new ExamDAO(db);
	   	}

		...	
	}
```
####and

```java
	public class SubjectDAO extends SQLiteDelegate<SubjectEntity> {
	
	    	public SubjectDAO(SQLiteDatabase db) {
	        	super(db, new SubjectTransformer(db));
	    	}
	    	
	}
```

####3. Instance the object DAO and use the CRUD methods

```java
    
    ...
    
    SubjectDAO subjectDAO = new SubjectDAO(database);
    Subject subject = new Subject("maths");
    
    //Create
    subject = subjectDAO.create(subject);
    
    //Delete
    boolean isDelete = subjectDAO.delete(subject);
    ...
    
```

Use the SQLiteHelper and get rid of SQLiteOpenHelper
---------------------------------------------------

####If you need one simple database: 

```java
final private String SUBJECT_TABLE =
        "CREATE TABLE SUBJECT(" +
	        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
	        "NAME TEXT NOT NULL" +
        ")";
final private String SUBJECT = "SUBJECT";

final private String[] TABLES = {SUBJECT_TABLE};
final private String[] TABLENAMES = {SUBJECT};

SQLiteHelper helper = SQLiteHelper.builder()
	.tables(TABLES)
 	.tableNames(TABLENAMES)
 	.build(context);
 	
SQLiteDatabase dataBase = helper.getWritableDatabase(); 	
```
In this case, the name and version of the database by default are Name: com.easysqlite and Version: 1. For put the name and version you can do:

```java
SQLiteHelper helper = SQLiteHelper.builder()
	.tables(TABLES)
 	.tableNames(TABLENAMES)
 	.build(context, DATABASE_NAME, SQLITE_VERSION);
```

OR

```java
SQLiteHelper helper = SQLiteHelper.builder()
	.tables(TABLES)
 	.tableNames(TABLENAMES)
 	.name(DATABASE_NAME)
 	.version(DATABASE_VERSION)
 	.build(context);
```

####If you need one custom implementation:

```java
private static SQLiteHelper.SQLiteHelperCallback helperCallback = new SQLiteHelper.SQLiteHelperCallback() {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SUBJECT_TABLE);
            db.execSQL("INSERT INTO "+SUBJECT+"(name) VALUES ('Maths')");
            
            ...
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            ...	
        }
};

SQLiteHelper helper = SQLiteHelper.builder()
     	.beginConfig()
                .helperCallback(helperCallback)
                .foreignKey(true)		//PRAGMA foreign_keys = ON
        .endConfig()
	.tables(TABLES)
 	.tableNames(TABLENAMES)
 	.build(context, DATABASE_NAME, cursorFactory, SQLITE_VERSION);
```

If you need change only the method onCreate or Upgrade, use onCreateCallback(OnCreateCallback callback) or onUpgradeCallback(OnUpgradeCallback callback).


Sample Clean architecture
-------------------------
The code which uses this library is in the package 'com.tonilopezmr.sample.data.SQLite'.

I use the Clean architecture with the pattern MVP, I have been motivated for the speaker [pedrovgs][10] in DroidCon 2014.

After see the MVP implementations of:

* [MVPCleanArchitecture][6] by [glmoadrian][5]
* [EffectiveAndroidUI][7] by [pedrovgs][4]
* [Android-CleanArchitecture][8] by [android10][9] 


Libraries used on the sample project
------------------------------------

* [Dagger][1]
* [Butterknife][2]
* [FloatingActionButton][3]

If anything is wrong contact me
-------------------------------------------

* Antonio López Marín - <tonilopezmr@gmail.com>

<a href="https://twitter.com/tonilz">
  <img alt="Follow me on Twitter" src="https://cdn3.iconfinder.com/data/icons/free-social-icons/67/twitter_circle_black-128.png" width="60" height="60"/>
</a>
<a href="http://www.linkedin.com/in/tonilopezmr">
  <img alt="Add me to Linkedin" src="https://cdn3.iconfinder.com/data/icons/free-social-icons/67/linkedin_circle_black-128.png" width="60" height="60"/>
</a>

######Sorry for my English :(

License
-------

    Copyright 2015 Antonio López Marín <tonilopezmr.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://github.com/square/dagger
[2]: https://github.com/JakeWharton/butterknife
[3]: https://github.com/makovkastar/FloatingActionButton
[4]: https://github.com/pedrovgs
[5]: https://github.com/glomadrian
[6]: https://github.com/glomadrian/MvpCleanArchitecture
[7]: https://github.com/pedrovgs/EffectiveAndroidUI
[8]: https://github.com/android10/Android-CleanArchitecture
[9]: https://github.com/android10
[10]: http://www.slideshare.net/PedroVicenteGmezSnch/effective-android-ui-english
[11]: https://twitter.com/jvprofe
