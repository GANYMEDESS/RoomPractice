# Android Architecture Components (AAC)
Androidは、より良い開発環境のためにAndroidJetpackで構成された様々なライブラリを提供しました。 Android Architecture Componets(AAC)は、JetpackにおけるArchitectureを取り扱い、管理する様々なライブラリの集合です。
> 参考）https://developer.android.com/jetpack/androidx/explorer

## Part 1. Room Database
元々SQLite を活用してアプリケーション内部のリポジトリ（DB）を作って保存していた。
しかし、SQLiteを使用する場合は、

* Queryの有効性検査機能を提供できなかった点
* Schemeが変わると自動的にアップデートできなかった点
* ORMサポートがされていないため、データをオブジェクトに変換させるためにデータ処理を加えなければならなかった点
* Observerパターンのデータ(Live Data, Rx Java)を生成し動作させるために追加的なBoiler Plateコードを作成する必要がある点

などを追加投資する必要があるので、Android X バージョン以降、アプリケーション内部のDB としてRoom を推奨している。

### Roomとは🧐 ？
SQLiteの抽象化されたバージョンだと思っていただければ楽だと思います。

以前はSQLiteに直接データを加工してDBに合った形式でデータをCRUDする方式でしたが、Roomではオブジェクトでデータをやり取りでき、反応型データでもデータをやり取りできるように設計されています！

Room Persistence Library

* Googleが提供しているORM(Object Relational Mapping)
* 機器にデータを保存したい場合に使用
* 構成要素
  * Room DataBase
    * 基本SQLiteデータベースへのアクセスポイント役割
    * データベース作業の単純化
    * DAOを使用してSQLiteデータベースにクエリーを実行
    * RoomDatabaseをextendするabstract Classである必要があり、テーブルとバージョンを定義する場所
  * DAO(Data Access Object)
    * SQLクエリを関数でマッピングする。
    * ユーザがDAOの関数を呼び出したら、Room Databaseでその関数の作業を実行 
  * Entitity
    * Roomで作業するとき、データベーステーブルを説明するannotated class 

<div style="text-align:center"><img title='room.png' alt='room' src='https://user-images.githubusercontent.com/41669385/124881483-58db6480-e00a-11eb-94fb-b1d40d09a9e4.png' width="50%" height="10%">
</div>


### Room活用(簡単に利用する方法説明）
**<img src="https://pic.sopili.net/pub/emoji/noto-emoji/png/128/emoji_u1f4cc.png" width=24 height=24> 1.Dependency 追加**
Roomを使用するためにgradleに以下のように追加します。

```kotlin:build.gradle
dependencies {
    def room_version = "2.3.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor "androidx.room:room-compiler:$room_version"

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbolic Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation "androidx.room:room-rxjava2:$room_version"

    // optional - RxJava3 support for Room
    implementation "androidx.room:room-rxjava3:$room_version"

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation "androidx.room:room-guava:$room_version"

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")
}
```
<p>&nbsp;</p>

**<img src="https://pic.sopili.net/pub/emoji/noto-emoji/png/128/emoji_u1f4cc.png" width=24 height=24> 2.Database**
### 2-1 Entity

<span style="color:">uid(Primary Key)|name|age|phone|sex|
|:---:|:---:|:---:|:---:|:---:|
|1|JACK|35|010-1232-1242|MALE|
|2|KIM|22|070-21232-1232|FEMALE|
|3|GENJI|26|080-2542-3321|MALE|

「個体」であるEntity は、関連のある属性が集まり、一つの情報単位となったものであります。
例)上記のように人の名前、年齢、番号という属性が集まって一つの情報単位になれば、これをEntityと言います。
```kotlin:JSUser.kt
@Entity(tableName = "js_user_table")
data class JSUser(
    var name: String,
    var age: String,
    var phone: String,
    var sex: String,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
```
Entity を生成しなければならない。 (Database Tableを作ると思います）
data classに@Entityアノテーションを付け、保存したいプロパティの変数名とタイプを決めます。
PrimaryKeyは鍵値であるため、Unique値でなければならないです。
直接指定してもいいが、autoGenerateをtrueとすると自動的に値を生成します。

tableの名前は別に決めないとクラス名を使用することになりますが、
もしtableの名前を決めたいなら
@Entity（tableName="js_user_table"）を使います。

### 2-2 Dao
Dao(Data Access Object)データにアクセスできるメソッドを定義しておいたインタフェースです。
Databaseにアクセスして実質的にinsert、deleteなどを行うメソッドを含みます。
```kotlin:JSUserDao.kt
@Dao
interface JSUserDao
{
    @Query("SELECT * FROM JS_USER_TABLE ORDER BY ID")
    fun getAllUserList(): Flow<List<JSUser>>

    @Insert
    suspend fun insertUser(jsUser: JSUser)
}
```
- Flowと suspendの場合内容が少しあるので後で別で作成して説明追加します。簡単に言うと順次実行されるデータストリームを非同期的に処理できる特徴があります。

@Insertつけるとtableルのデータを挿入
@Updateをつけるとtableのデータを修正
@Deleteをつけるとtableのデータを削除

その他の場合@Query アノテーションを付けてその中にどのような動作をするのかsql文法で作成します。
@Query("SELECT * FROM JS_USER_TABLE ")

### 2-3 Database
```kotlin:JSRoomDatabase.kt
@Database(entities = [JSUser::class], version = 1)
abstract class JSRoomDatabase: RoomDatabase()
{
    //データベースと接続されるDAO
    //DAOはabstractで"getter"を提供
    abstract fun jsUserDao(): JSUserDao

    companion object {
        @Volatile
        private var INSTANCE: JSRoomDatabase? = null

        fun getDatabase(context: Context): JSRoomDatabase {
            //同時に2つのINSTANCEが生成されるのを防ぐためのsynchronized
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JSRoomDatabase::class.java,
                    "js_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
```
Databaseを生成して管理するDatabaseObjectを作るためには、上のようなabstract classを作らなければならない。 まず、RoomDatabase クラスを継承し、@Database アノテーションでDatabaseであることを表示します。

アノテーションカッコの中を見ると、entitiesがありますが、ここに[2-1 Entity]で作ったentityを入れます。
versionはアプリをアップデートする途中、entityの構造を変更しなければならないことが発生した時、以前の構造と現在の構造を区分する役割をします。 もし構造が変わったのにバージョンが同じならエラーが出てデバッグになりません。
初めてデータベースを作成する状況なら、ただ1を入れるだけです。

[公式文書](https://developer.android.com/training/data-storage/room)では、データベースオブジェクトをインスタンスする際にシングルトーンで実現することを推奨しています。
まず、複数のインスタンスにアクセスすることがほとんどなく、オブジェクト生成にコストが多くかかるからです。

それで上のようなコンポーネントでオブジェクトを宣言して使います。
オブジェクトを作成するとき、databaseBuilderというstaticメソッドを使いますが、contextとdatabaseクラス、そしてDatabaseを保存するときに使用するDatabaseの名前を決めて送信すればOKです。
他のデータベースと名前が重なるとエラーが発生するので注意しなければなりません。


```kotlin:複数のentity
@Database(entities = arrayOf(JSUser::class, JSType::class), version = 1)
abstract class JSRoomDatabase: RoomDatabase() {
      abstract fun jsUserDao(): JSUserDao
}
```
もし、ひとつのデータベースが複数のentityを持つなら、arrayOf()の中にコンマで区切ってentityを入れればいいです。

### 2-4 Repository
<div style="text-align:center"><img title='repository.png' alt='repository' src='https://user-images.githubusercontent.com/41669385/124884597-634b2d80-e00d-11eb-8bb6-aaca272af26d.png' width="50%" height="20%">
</div>
<p>&nbsp;</p>
  
**Repositoryとは🧐？**

実は、RepositoryクラスはRoomDatabaseの一部ではなく、ただの推奨事項に過ぎません。 
しかし、Repositoryクラスでのみデータを保管し、操作するとViewModelでLiveDataを管理する過程が簡単になります。 Repositoryは、clean API、clean Interfaceを提供し、ユーザインタフェースとデータをやり取りできます。
 一般的にRepositoryはDAOからデータを取得し、Networkやtextfileを結びつけて他人のデータを受け取ることができる一種のヘルプです。
Repositoryが抽象化レイヤーの役割をするため、ViewModelはDAOやNetwork、textfileがデータにどのように作用するのかを気にせずにLiveDataを管理できます。

```kotlin:JSApplication.kt
    // Database
    private val database by lazy { JSRoomDatabase.getDatabase((this@JSApplication)) }
    val repository by lazy { JSUserRepository(database.jsUserDao()) }
```
このプロジェットではRepositoryを便利に利用するためApplicationに定義して呼ぶ方法で作りました。

<div style="text-align:center"><img title='repository_structure.png' alt='repository_structure' src='https://user-images.githubusercontent.com/41669385/124884942-bae99900-e00d-11eb-8eb3-a31c394a9e4e.png' width="60%" height="30%"></div>

**<img src="https://pic.sopili.net/pub/emoji/noto-emoji/png/128/emoji_u1f60f.png" width=28 height=28> Imageを見ると理解できると思います。**

**<img src="https://pic.sopili.net/pub/emoji/noto-emoji/png/128/emoji_u1f4cc.png" width=24 height=24> 3.View**
### 3-1 ViewModel
**ViewModelとは🧐？**
<div style="text-align:center"><img title='viemodelScope.png' alt='viemodelScope' src='https://user-images.githubusercontent.com/41669385/124885041-d5237700-e00d-11eb-9f7e-950a245cdb74.png' width="50%" height="30%"}'></div>

ViewModelはUIControllerとデータを連結する資料構造です。 
先ほど、Repositoryがデータの出所(ローカルDB、ネットワーク)に従って処理する役割をするので、ViewModelはデータバインディングにのみ集中できます。
ViewModelはアプリのUIに関するデータをViewModelに登録されたcontext Lifecycle分だけ保管しています。
そのため、機器の回転などを理由にActivityが再生され、データが消失した場合、ViewModelからデータを読み込むことができます。
Lifecycle Ownerとなる対象がFinishedになるまでRecreateにもView Modelは消滅せずに維持されます。

```kotlin:JSUserViewModel.kt
class JSUserViewModel(private val repository: JSUserRepository): ViewModel()
{
    val allUserList: LiveData<List<JSUser>> = repository.allUserList.asLiveData()

    fun insert(jsUser: JSUser) = viewModelScope.launch {
        repository.insertUserData(jsUser)
    }
}

//model ClassがNoParam View Modelクラスが合えば、View Modelインスタンスを作成して
//あるいは、IllegalArgumentException()例外を発生させるようになっています。
class JSUserViewModelFactory(private val repository: JSUserRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JSUserViewModel::class.java)) {
            return JSUserViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}
```

LiveDataもLifeCycleを持つので、ViewModelはLiveDtaと一緒に使うことができ、LiveDataはObservableやNotification機能を提供します。

* View Modelはabstract classなので、継承だけでView Modelクラスを作成可能です。
   * abstract classなので、new()生成子関数でのオブジェクト生成は不可(View Model Providerでオブジェクトを生成)
* カスタム作成者使用時にView Model Provider.FactoryInterface使用
* View ModelはOwner Scope内でSingleTonオブジェクトのように使用可能

### 3-2 View(Activity, Fragmentなど。。)
今まで定義した部分をViewに表示する部分です。

```kotlin:MainActivity.kt
    private val mJSUserViewModel: JSUserViewModel by viewModels {
        JSUserViewModelFactory((application as JSApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainBinding.root)

        mJSUserViewModel.allUserList.observe(this) { users ->
            users.let {
                if(it.isNotEmpty()) {
                      //DBが空いている場合の処理
                }
                else {
                   //DBがデータがある場合の処理
                }
            }
        }
    }
```
ViewModelを宣言して利用する方法です。
ViewModelObeserverの()は　→　observer(LifecycleOwner)になります。


<img src="https://pic.sopili.net/pub/emoji/noto-emoji/png/128/emoji_u1f436.png" width=24 height=24>ここまでが簡単にRoomを利用したMVVMパターンでアプリを作る方法です。

### まとめ
>ソースコード : https://github.com/GANYMEDESS/RoomPractice

ソースコードもダウンロードして説明の一緒に確認してください。
上の説明はSELECTして表示する部分だけですが、コードの中にはINSERTまで作っております。
INSERTの方はAddUserActivity.ktの方を参考すると理解できると思います。
