# InnofiedTest
Android Assignment
Use the information given below to create a continuous scrolling paginated listing. The
following conditions must be fulfilled.
● Page size (number of results per API call) must be ​5
● A ​progress indicator​ must be shown while loading a new page
● Pull to refresh​ must be implemented
● Libraries that can be used are
○ Glide​, ​Picasso​, ​Universal Image Loader​ or ​Fresco​ for image loading and
caching
○ Retrofit​ with ​OkHttp​ for networking
○ GSON​ or ​Jackson​ for JSON parsing
○ Optionally ​RxJava​ and/or ​RxAndroid​ can be used
○ Optionally use ​Room​ for SQLite
○ Any other library if at all required
● Use ​material design​ throughout
● Use ​placeholder​ during image loading
● Use either ​MVC​, ​MVP​ or ​MVVM​ architecture pattern
● Use ​Java​ or ​Kotlin​ as the language
● Use ​RecyclerView​ for listing
● No internet indicator​ must be shown when mobile data or Wifi is switched off
● Optionally use ​Android DataBinding​ or ​ButterKnife​ to bind views
● Optionally use ​Repository​ ​pattern​ for offline caching using ​SQLite
API

Endpoint: ​https://reqres.in/api/users
Method: ​GET
Query: ​page={page_number}​, ​per_page={page_size}
