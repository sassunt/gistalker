# giStalker

giStalker is web application to keep track of Gist.

This is the sample app using [unfiltered](https://github.com/unfiltered/unfiltered) and [dispatch](https://github.com/dispatch/dispatch).

And you can use this on the heroku.

## usage

Add gist-ID and file name to Procfile:(Interim)

	web: target/start com.sample.Web <gist-ID <file name>

[example](https://gist.github.com/1850453)

	web: target/start com.sample.Web 1850453 gistalker.md

Create an app on the Cedar stack and deploy:

	heroku create --stack cedar
	git push heroku master


## example

`http://gistalker.herokuapp.com`
