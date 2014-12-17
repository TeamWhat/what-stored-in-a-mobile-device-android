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

Architecture
------------

### Android

The Android application consists of two main parts, the UI and background data collection.

#### UI

The UI consists of the parts that the user can interact with, such as settings and graphs.

#### Background data collection

The background data collection is the automated system of user device data collection and their sending to the reasearch server. This part of the application can be disabled by the user from the settings. 

[background-data-collection-diagram]

The DataCollectionAlarmReceiver is the class responsible for starting the scheduled task of data collection. The task is repeated either daily, weekly or monthly according to the setting selected by the user. Because the Android OS clears all scheduled tasks on shutdown, the DataCollectionBootReceiver starts it again with the DataCollectionAlarmReceiver when the system starts up (if the user has enabled data collection and sending). At the moment, the data sending frequency is counted from the system startup rather than from a certain time and date in order to avoid clogging the backend server with requests. After the data sending scheduled task has been set (either after enabling data sending or after system startup), the first collection is started after 5 minutes of delay.



### Back end

Backend is a ruby on rails server...

Testing
-------

We have tested using...

### Android

Calabash, mockito, robotium...

### Back end

Rspec, cucumber, factory girl...

Known bugs
----------

- Share might not work
- Changeing data sending frequency does not come in to effect until the device restarts or data sending setting is checked and unchecked
- If a POST request is too large, the backend wont accept the data
- Device collects and sends data after system restart no matter what the data sending frequency option is (only if data sending is enabled)
