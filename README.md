#EasySQLite

Use easily the database SQLite on android using the DAO and transformer design patterns.

#How to use


####Import with Gradle:

####1. Create the object Transformer
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
####2. Create the object DAO
```java
	public class SubjectDAO extends SQLiteDelegate<SubjectEntity> {

	    public SubjectDAO(SQLiteDatabase db) {
	        super(db, new SubjectTransformer());
	    }
		
		//In the case you need more querys, write this.

		...	    
	}
```
####If you needs one relation between objects (foreign key)

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


Sample Clean architecture
-------------------------
The code what use this library he is in package 'com.tonilopezmr.sample.data.SQLite'.

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

If I have anything is wrong contact with me
-------------------------------------------

* Antonio López Marín - <tonilopezmr@gmail.com>

<a href="https://twitter.com/tonilz">
  <img alt="Follow me on Twitter" src="https://cdn3.iconfinder.com/data/icons/free-social-icons/67/twitter_circle_black-128.png" width="60" height="60"/>
</a>
<a href="http://www.linkedin.com/in/tonilopezmr">
  <img alt="Add me to Linkedin" src="https://cdn3.iconfinder.com/data/icons/free-social-icons/67/linkedin_circle_black-128.png" width="60" height="60"/>
</a>

####Sorry for my English :(

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
