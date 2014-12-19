What is stored on a mobile device (fall 2014) - documentation
=============================================================

Deployment instructions
-----------------------

### System definition

The operating system on the backend is Ubuntu 14.04. We have postgres and ruby installed from PPA's and nginx and supervisor installed from the official repositories. The source code is located at /home/tkt_pdp/app/

We set up nginx to listen on port 80 and to act as a reverse proxy for the app. nginx configuration:

```
server {
  listen 80;
  server_name pdp.cs.helsinki.fi;
  root /home/tkt_pdp/app;

  location / {
    proxy_pass http://0.0.0.0:3000;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
  }
}
```

Application is managed using Supervisor. It takes care of starting stopping and making sure the app stays up. This was accomplished by adding following lines to supervisord.conf:

```
[program:rails]
environment=RAILS_ENV=production
command=bundle exec rails s --binding 127.0.0.1
directory=/home/tkt_pdp/app/
user=tkt_pdp
```

The program can be managed using supervisorctl. The server can be started with

```
# supervisorctl start rails
```

and to stop

```
# supervisorctl stop rails
```

to view logs:

```
# supervisorctl tail rails
```

or if you want to follow the logs:

```
# supervisorctl tail -f rails
```

### Getting the latest code from repo

```
$ sudo su
# supervisorctl stop rails
# su tkt_pdp
$ cd /home/tkt_pdp/app/
$ git pull
$ bundle install
$ bundle exec rake db:migrate
$ bundle exec rake assets:precompile
$ exit
# supervisorctl start rails
```

### Creating and deleting a user for the backend




Architecture
------------

### Android

The Android application consists of two main parts, the UI and background data collection.

#### UI

The UI consists of the parts that the user can interact with, such as settings and graphs. If the user opens the application for the first time, the user is shown the first time settings. On the MainScreen user can select to collect and send data, or to open the Graphs page. From the context menu on the top of the screen the user can open Settings or the About page. 

![Android UI diagram](android-UI.png)

#### Background data collection

The background data collection is the automated system of user device data collection and their sending to the reasearch server. This part of the application can be disabled by the user from the settings. 

![Background data collection diagram](background-data-collection-diagram.png)

The DataCollectionAlarmReceiver is the class responsible for starting the scheduled task of data collection. The task is repeated either daily, weekly or monthly according to the setting selected by the user. Because the Android OS clears all scheduled tasks on shutdown, the DataCollectionBootReceiver starts it again with the DataCollectionAlarmReceiver when the system starts up (if the user has enabled data collection and sending). At the moment, the data sending frequency is counted from the system startup rather than from a certain time and date in order to avoid clogging the backend server with requests. After the data sending scheduled task has been set (either after enabling data sending or after system startup), the first collection is started after 5 minutes of delay.

The DataHandlerIntentService is the service that collects the user device data on a separate thread. DataHandler is the class that has the method to collect all data by calling the getData() method of all classes that implement the DataCollector interface. The database_utilities package has the necessary classes to save the collected data on the devices local SQLite database as in [Androids own guides](https://developer.android.com/training/basics/data-storage/databases.html).

The SendDataIntentService is the service that sends the collected data to the server when network connectivity is aquired. It uses the HttpPostHandler to send a http POST request containing the data in the JSON format. JSONPackager is the class that creates a valid JSON object from the data on the local SQLite database. UniqueIdentifier class creates a unique id for the mobile device that will anonymously connect the collected data with the specific mobile device on the backend.  

### Back end

Backend is a ruby on rails server. Each mobile device that sends data is a unique "Subject" in the database. A "Collection" is a single set of data collected from a Subject. A Subject can have multiple Collections. A Collection contains "Application", "Image", "Audio", "Video" and "Text" data of the Subjects mobile device. "Email" is the list of emails of participating people that have entered their emails on their mobile devices and shared data with the server. "User" list contains the user credentials that one can login with. 

![Backend diagram](backend-architechture.png)

Testing
-------

### Android

The Android application has automated tests. Most were unit tests for settings and the different data collectors, using Mockito for object mocking and the Robotium library for feature tests to mimic user interactions. The application also had integration tests for common use cases made with Calabash. These tests were run on several different devices and emulators. The application was also tested manually with devices ranging from 4.3 inch phones to 10.5 inch tablets, with Android versions 4.1.2, 4.4.2, 4.4.4 and 5.0. The manual testing consisted of testing the stability of the apps UI and the working of the background data sending by leaving the test device on with network connectivity for several days.

You can run the calabash tests with the command 
```
$ ./gradlew build
$ calabash-android resign app/build/outputs/apk/app-release-unsigned.apk
$ calabash-android run app/build/outputs/apk/app-release-unsigned.a
```
Other tests can be run as their test suite documentation instructs.


### Back end

Rspec, cucumber, factory girl...

Known bugs
----------

- Share might not work
- Changeing data sending frequency does not come in to effect until the device restarts or data sending setting is checked and unchecked
- If a POST request is too large, the backend wont accept the data
- Device collects and sends data after system restart no matter what the data sending frequency option is (only if data sending is enabled)
