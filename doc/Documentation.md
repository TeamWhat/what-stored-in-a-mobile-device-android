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

The app consists of...

### Back end

Backend is a ruby on rails server...

Testing
-------

We have tested using...

### Android

Calabash, mockito, robotium...

### Back end

Rspec, cucumber, factory girl...
