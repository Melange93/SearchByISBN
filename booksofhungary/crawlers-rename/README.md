# SearchByISBN / Books of Hungary

### Project description:
The main goal of the project is to create a com.reka.lakatos.bookofhungary.database of books published in Hungary based on ISBN codes.<br/>
Therefore, I started implementing web crawlers.<br/>
I use Spring Boot for the web crawlers and MongoDB as a com.reka.lakatos.bookofhungary.database.<br/>
<strong>The application does not save books that are part of a series or contains other books!</strong><br/>
<strong>Please run just one crawler at the same time!</strong><br/>

#### Catalogs:
Metropolitan Ervin Szabó Library: http://saman.fszek.hu/ <br/>
National Széchenyi Library: http://nektar1.oszk.hu/ and http://nektar2.oszk.hu/ <br/>
Széchenyi István University Library: https://hunteka.sze.hu/ <br/>
Semmelweis University Library: http://hunteka.lib.semmelweis.hu/ <br/>
Museum of Ethnography Library: http://hunteka.neprajz.hu/ <br/>
Museum of Fine Arts Library: http://hunteka.szepmuveszeti.hu/ <br/>
Ferenc Hopp Museum of Asian Arts Library: http://hunteka.hoppmuseum.hu/ <br/>

### Settings:

##### Basic MongoDB settings:
<pre>
host: localhost
port: 27017
com.reka.lakatos.bookofhungary.database: bookdb
You can change them in the application.properties
</pre>

##### How to run the web crawlers:

<pre><ul><li>Metropolitan Ervin Szabó Library:
   Set run configuration parameters to:
        parameter name: crawler.book-crawler
        parameter value: ervin
   And run the application
   </li>
<li>National Széchenyi Library:
  Set run configuration parameters to:
       parameter name: crawler.book-crawler
       parameter value: szechenyi
  And run the application
  </li>
  
<li>Széchenyi István University Library:
     Set run configuration parameters to:
          first parameter name: crawler.book-crawler
          first parameter value: universities
          
          second parameter name: crawler.book-crawler.university-catalog-main-url
          second parameter value: https://hunteka.sze.hu/
     And run the application
     </li>
     
<li>Semmelweis University Library:
      Set run configuration parameters to:
           first parameter name: crawler.book-crawler
           first parameter value: universities
           
           second parameter name: crawler.book-crawler.university-catalog-main-url
           second parameter value: http://hunteka.lib.semmelweis.hu/
      And run the application
      </li>
      
<li>Museum of Ethnography Library:
      Set run configuration parameters to:
           first parameter name: crawler.book-crawler
           first parameter value: universities
           
           second parameter name: crawler.book-crawler.university-catalog-main-url
           second parameter value: http://hunteka.neprajz.hu/
      And run the application
      </li>

<li>Museum of Fine Arts Library:
    Set run configuration parameters to:
         first parameter name: crawler.book-crawler
         first parameter value: universities
         
         second parameter name: crawler.book-crawler.university-catalog-main-url
         second parameter value: http://hunteka.szepmuveszeti.hu/
    And run the application
    </li>
<li>Ferenc Hopp Museum of Asian Arts Library:
    Set run configuration parameters to:
         first parameter name: crawler.book-crawler
         first parameter value: universities
         
         second parameter name: crawler.book-crawler.university-catalog-main-url
         second parameter value: http://hunteka.hoppmuseum.hu/
    And run the application</li></ul></pre>
